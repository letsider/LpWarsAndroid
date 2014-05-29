package controllers;

import models.Carte;
import models.Gc;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import configuration.IdentifiantsActivity;
import configuration.Names;

public class MainActivity extends ActionBarActivity {

	public Carte plateauDeJeu = null;
	public final Integer cote = Integer.valueOf(5);

	/**
	 * ---------------------------------------------------------------------------
	 * 								Gestion principale
	 * ---------------------------------------------------------------------------
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plateau);

		plateauDeJeu = new Carte(this, cote, new Gc.Couleur[]{Gc.Couleur.bleu, Gc.Couleur.rouge});
		Log.i("MainActivity::OnCreate", "initialisation d'une map");

	}

	public void addListenerOnButton(ImageButton theTarget, final Gc theGc) {

		// Si l'objet à passer n'a pas de valeur, 
		// on évite le NullPointerException 
		// lors de son utilisation dans la prochaine
		// activité
		if(theGc == null){
			return;
		}

		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ActionsActivity.class);
				intent.putExtra(Names.GC_CLICKED, theGc);
				startActivityForResult(intent, IdentifiantsActivity.ID_ACTIVITY_SHOW_DETAILS);

			}

		});
		Log.i("MainActivity::addListenerOnButton", theTarget.getTag().toString() + " à changé d'OnClick");

	}

	@Override 
	public void onActivityResult(int theRequestCode, int theResultCode, Intent theIntent) {
		super.onActivityResult(theRequestCode, theResultCode, theIntent);
		
		switch(theRequestCode) {
		case IdentifiantsActivity.ID_ACTIVITY_SHOW_DETAILS : 
			switch (theResultCode) {
			case Activity.RESULT_OK:
				Gc gcClicked = (Gc)theIntent.getExtras().getSerializable(Names.GC_CLICKED);
				// le gcClicked à perdu les infomations Context (MainActivity et ImageButton)
				// alors que le plateauDeJeu de jeu n'a pas bougé !
				plateauDeJeu.getCarte()[gcClicked.geti()][gcClicked.getj()].getGc().actionPossible();
			case Activity.RESULT_CANCELED:
			default:
				break;
			} 
			break;
		default:
			break;
		}
	}

	/**
	 * ---------------------------------------------------------------------------
	 * 								Gestion du menu
	 * ---------------------------------------------------------------------------
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
