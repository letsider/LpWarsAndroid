package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Carte;
import models.Case;
import models.Gc;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import configuration.CodeActions;
import configuration.IdentifiantsActivity;
import configuration.Names;

public class MainActivity extends ActionBarActivity {

	/**
	 * Doit contenir tout les plateaux
	 * A ce jour, qu'un !
	 */
	public Carte plateauDeJeu = null;
	/**
	 * Cette variable permet de stocker un historique des cases ayant une image
	 * temporaire adapt� � un �venement pr�ci
	 */
	public final List<Case> caseAffichageTemporaire = new ArrayList<Case>();

	public final Integer cote = Integer.valueOf(6);

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
	
	@Override 
	public void onActivityResult(int theRequestCode, int theResultCode, Intent theIntent) {
		super.onActivityResult(theRequestCode, theResultCode, theIntent);

		switch(theRequestCode) {
		case IdentifiantsActivity.ID_ACTIVITY_SHOW_DETAILS :

			switch (theResultCode) {
			case Activity.RESULT_OK:

				// Avant de faire l'action attendu, on assure la validit� du plateau
				// en le mettant dans son �tat stable
				reinitImageButtonPlateau();

				Gc gcClicked = (Gc)theIntent.getExtras().getSerializable(Names.Generales.GC_CLICKED);
				// le gcClicked � perdu les infomations Context (MainActivity et ImageButton)
				// alors que le plateauDeJeu de jeu n'a pas boug� !
				caseAffichageTemporaire.addAll(
						plateauDeJeu.getCase(gcClicked.geti(), gcClicked.getj())
						.getGc().setActionPossible());
				
				break;
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
	 * Cette fonction va permettre de stocker les ImmageBuuton n'ayant pas
	 * une valeur stable
	 * c-a-d une image indiquant un mouvement possible ou une unit� attaquable
	 * ainsi qu'un listener relatif � ceux en d�but de tour
	 */
	public void reinitImageButtonPlateau(){
		// Pour chaque case ayant �t� modifi� temporairement
		for(Case curCase : caseAffichageTemporaire){
			if(curCase.getGc() == null){
				curCase.changeMonImage(false);
				removeListenerOAction(curCase.getMonImage());
			} else {
				curCase.changeMonImage(false);
				setListenerOnButton(curCase.getMonImage(), curCase.getGc());
			}
		}
		caseAffichageTemporaire.clear();
	}

	/**
	 * ---------------------------------------------------------------------------
	 * 								Gestion des listeners
	 * ---------------------------------------------------------------------------
	 */
	
	/**
	 * Ajoute un listener sur le bouton theTarget
	 * Ce Listener ouvrira une nouvelle activit� affichant les informations
	 * du Gc theGc
	 * 
	 * @param theTarget l'image bouton � affecter
	 * @param theGc le Gc r�pr�sent� sur cet image bouton
	 */
	public void setListenerOnButton(ImageButton theTarget, final Gc theGc) {

		// Si l'objet � passer n'a pas de valeur, 
		// on �vite le NullPointerException 
		// lors de son utilisation dans la prochaine
		// activit�
		if(theGc == null){
			return;
		}

		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ActionsActivity.class);
				intent.putExtra(Names.Generales.GC_CLICKED, theGc);
				if(theGc.getEquipe().equals(plateauDeJeu.getEquipeActuelle())){
					intent.putExtra(Names.Generales.SELECTIONNALBLE, true);
				} else {
					intent.putExtra(Names.Generales.SELECTIONNALBLE, false);
				}
				startActivityForResult(intent, IdentifiantsActivity.ID_ACTIVITY_SHOW_DETAILS);
			}

		});
		Log.i("MainActivity::addListenerOnButton", theTarget.getTag().toString() + " � chang� d'OnClick");

	}
	
	public void setActionOnButton(ImageButton theTarget, final int theCodeAction,
			final Gc theGcTargeted, final Case theWhere) {
		
		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch(theCodeAction){
				case CodeActions.ATTAQUER:
					theGcTargeted.attaque(theWhere.getGc());
					break;
				case CodeActions.SE_DEPLACER:
					theGcTargeted.mouvement(plateauDeJeu, theWhere.geti(), theWhere.getj());
					break;
				}
				
				// Apr�s une action, on remet le plateau dans un �tat stable
				reinitImageButtonPlateau();
			}

		});
		Log.i("MainActivity::addListenerOnButton", theTarget.getTag().toString() + " � chang� d'OnClick");

	}

	/**
	 * Cette fonction permet de supprimer le listener d'un ImageButton
	 * @param theTarget l'image bouton dont le litener n'a plus lieu d'�tre
	 */
	public void removeListenerOAction(ImageButton theTarget){
		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				return;
			}

		});
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
		switch (id) {
		case R.id.fin_de_tour:
			plateauDeJeu.finTour();
			if(plateauDeJeu.gagner() != null){
				finish();
			};
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
