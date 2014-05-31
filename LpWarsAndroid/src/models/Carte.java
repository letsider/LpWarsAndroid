package models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.example.lpwarsandroid.R;

import controllers.MainActivity;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * Classe gerant le plateau de jeu
 */
public class Carte{

	/**
	 * Tableau de case faisant office du plateau
	 * @see Case
	 */
	private Case [][] carte;

	/**
	 * Variable comptant le nombre de tours de jeu
	 */
	private Integer compteur;

	private List<Gc.Couleur> equipes;
	private Gc.Couleur equipeActuelle;
	private Iterator<Gc.Couleur> equipeActuelleIt;
	
	/**
	 * Permet de stocker les différents contenus dynamiques créés
	 */
	LinearLayout[] postLayout;

	/**
	 * Getters and setters
	 */

	public Case getCase(Integer i, Integer j){
		if(isValidCoords(i, j)){
			return this.carte[i][j];
		}
		return null;
	}

	public Integer getCompteur(){
		return this.compteur;
	}

	public Gc.Couleur getEquipeActuelle(){
		return this.equipeActuelle;
	}

	public void setcase(Integer i, Integer j, Case theCase){
		this.carte[i][j] = theCase;
	}

	public Carte(MainActivity theContext, Integer theCote, Gc.Couleur [] theEquipes) {
		postLayout = new LinearLayout[theCote];
		carte = new Case[theCote][theCote];

		// Définition du Layout à construire.
		LinearLayout existLayout = (LinearLayout) theContext.findViewById(R.id.layoutOfDynamicContent);
		LayoutParams params = new LayoutParams(80, 80);

		Log.d("Carte::Carte", "Rentrée dans la boucle d'init de chaque case");

		for(int i=0; i < theCote; ++i){
			postLayout[i] = new LinearLayout(theContext);
			postLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			for (int j=0; j < theCote ; ++j){
				carte[i][j] = new Case(i, j, theContext);
				postLayout[i].addView( carte[i][j].getMonImage(), params ); 
			}
			existLayout.addView(postLayout[i]);
		}

		Log.i("Carte::Carte", "init des positionnement des pions");
		carte[0][0].setGc(new Gc(theEquipes[0], carte[0][0]));
		carte[theCote - 1][theCote - 1].setGc(new Gc(theEquipes[1], carte[theCote - 1][theCote - 1]));

		carte[6][6].setGc(new Gc(theEquipes[0], carte[6][6]));

		carte[2][2].setGc(new Gc(theEquipes[0], carte[2][2]));
		carte[1][1].setGc(new Gc(theEquipes[1], carte[1][1]));
		carte[1][2].setGc(new Gc(theEquipes[1], carte[1][2]));
		carte[1][3].setGc(new Gc(theEquipes[1], carte[1][3]));
		carte[2][1].setGc(new Gc(theEquipes[1], carte[2][1]));
		carte[2][3].setGc(new Gc(theEquipes[1], carte[2][3]));
		carte[3][1].setGc(new Gc(theEquipes[1], carte[3][1]));
		carte[3][2].setGc(new Gc(theEquipes[1], carte[3][2]));
		carte[3][3].setGc(new Gc(theEquipes[1], carte[3][3]));

		Log.i("Carte::Carte", "init des équipes et des pointeurs sur listes d'équipes");
		compteur = 0;
		equipes = new LinkedList<Gc.Couleur>();
		for(Gc.Couleur newOne : theEquipes){
			equipes.add(newOne);
		}

		equipeActuelleIt = equipes.iterator();
		equipeActuelle = equipeActuelleIt.next();
	}

	public void finTour(){

		++compteur;

		if(equipeActuelleIt.hasNext()){
			equipeActuelle = equipeActuelleIt.next();
		} else {
			equipeActuelleIt = equipes.iterator();
			equipeActuelle = equipeActuelleIt.next();
		}
		
		for(int i=0; i < carte.length; ++i){
			for (int j=0; j < carte[i].length ; ++j){ 
				if(getCase(i, j).getGc() != null){
					getCase(i, j).getGc().setPm(2);
				}
			}
		}

	}

	public Gc.Couleur gagner(){
		Gc.Couleur enVie = null;

		for(Case [] ligne : carte){
			for(Case cellule : ligne){
				if(cellule.getGc() != null){
					if(enVie != null && cellule.getGc().getEquipe() != enVie){
						return null;
					} else {
						enVie = cellule.getGc().getEquipe();
					}
				}
			}
		}

		return enVie;
	}

	public boolean isValidCoords(int theI, int theJ) {
		if(theI < 0 || theJ < 0){ 
			return false;
		} else if(theI >= carte.length || theJ >= carte[0].length){
			return false;
		} 
		
		return true;
	}

}