package controllers;

import interfaces.Pion;

import java.util.ArrayList;
import java.util.List;

import models.Batiment;
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
	 * temporaire adapté à un évenement préci
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
		case IdentifiantsActivity.DETAILS_UNIT :

			switch (theResultCode) {
			case Activity.RESULT_OK:

				// Avant de faire l'action attendu, on assure la validité du plateau
				// en le mettant dans son état stable
				reinitImageButtonPlateau();

				Gc gcClicked = (Gc)theIntent.getExtras().getSerializable(Names.Generales.GC_CLICKED);
				// le gcClicked à perdu les infomations Context (MainActivity et ImageButton)
				// alors que le plateauDeJeu de jeu n'a pas bougé !
				caseAffichageTemporaire.addAll(
						((Gc)plateauDeJeu.getCase(gcClicked.geti(), gcClicked.getj())
						.getPion()).setActionPossible());
				
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
	 * c-a-d une image indiquant un mouvement possible ou une unité attaquable
	 * ainsi qu'un listener relatif à ceux en début de tour
	 */
	public void reinitImageButtonPlateau(){
		// Pour chaque case ayant été modifié temporairement
		for(Case curCase : caseAffichageTemporaire){
			curCase.changeMonImage(false);
			if(curCase.getPion() == null){
				removeListenerOAction(curCase.getMonImage());
			} else {
				if(curCase.getPion().getClass().equals(Gc.class)){
					setListenerOnButton(curCase.getMonImage(), (Gc)curCase.getPion());
				} else if(curCase.getPion().getClass().equals(Batiment.class)){
					setListenerOnButton(curCase.getMonImage(), (Batiment)curCase.getPion());
				}
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
	 * Ce Listener ouvrira une nouvelle activité affichant les informations
	 * du Gc theGc
	 * 
	 * @param theTarget l'image bouton à affecter
	 * @param theGc le Gc réprésenté sur cet image bouton
	 */
	public void setListenerOnButton(ImageButton theTarget, final Pion thePion) {

		// Si l'objet à passer n'a pas de valeur, 
		// on évite le NullPointerException 
		// lors de son utilisation dans la prochaine
		// activité
		if(thePion == null){
			return;
		}

		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, ActionsActivity.class);

				if(thePion.getClass().equals(Gc.class)){

					intent.putExtra(Names.Generales.GC_CLICKED, (Gc)thePion);
					if(thePion.getEquipe().equals(plateauDeJeu.getEquipeActuelle())){
						intent.putExtra(Names.Generales.SELECTIONNALBLE, true);
					} else {
						intent.putExtra(Names.Generales.SELECTIONNALBLE, false);
					}

				} else if(thePion.getClass().equals(Batiment.class)){

					intent.putExtra(Names.Generales.BATIMENT_CLICKED, (Batiment)thePion);
					if(thePion.getEquipe().equals(plateauDeJeu.getEquipeActuelle())){
						intent.putExtra(Names.Generales.SELECTIONNALBLE, true);
					} else {
						intent.putExtra(Names.Generales.SELECTIONNALBLE, false);
					}

				}
				startActivityForResult(intent, IdentifiantsActivity.DETAILS_UNIT);
			}

		});
		Log.i("MainActivity::addListenerOnButton", theTarget.getTag().toString() + " à changé d'OnClick");

	}

	public void setActionOnButton(ImageButton theTarget, final int theCodeAction,
			final Gc theGcTargeted, final Case theWhere) {
		
		theTarget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch(theCodeAction){
				case CodeActions.ATTAQUER:
					theGcTargeted.attaque(theWhere.getPion());
					break;
				case CodeActions.SE_DEPLACER:
					theGcTargeted.mouvement(plateauDeJeu, theWhere.geti(), theWhere.getj());
					break;
				}
				
				// Après une action, on remet le plateau dans un état stable
				reinitImageButtonPlateau();
			}

		});
		Log.i("MainActivity::addListenerOnButton", theTarget.getTag().toString() + " à changé d'OnClick");

	}

	/**
	 * Cette fonction permet de supprimer le listener d'un ImageButton
	 * @param theTarget l'image bouton dont le litener n'a plus lieu d'être
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
