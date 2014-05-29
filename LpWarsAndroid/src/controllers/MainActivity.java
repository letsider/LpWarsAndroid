package controllers;

import models.Carte;
import models.Gc;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

public class MainActivity extends ActionBarActivity {

	private Carte plateauDeJeu = null;
	private final Integer cote = Integer.valueOf(5);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plateau);

		plateauDeJeu = new Carte(this, cote, new Gc.Couleur[]{Gc.Couleur.bleu, Gc.Couleur.rouge});

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
				intent.putExtra("gcClicked", theGc);
				startActivity(intent);

			}

		});

	}

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.plateau, container, false);
			return rootView;
		}
	}

}
