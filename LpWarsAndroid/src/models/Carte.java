package models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.example.lpwarsandroid.R;

import controllers.MainActivity;
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
	 * Permet de stocker les diff�rents contenus dynamiques cr��s
	 */
	LinearLayout[] postLayout;

	/**
	 * Getters and setters
	 */

	public Case getCase(Integer i, Integer j){
		return this.carte[i][j];
	}

	public Integer getCompteur(){
		return this.compteur;
	}

	public Gc.Couleur getEquipeActuelle(){
		return this.equipeActuelle;
	}

	public Case [][] getCarte(){
		return this.carte;
	}

	public void setcase(Integer i, Integer j, Case theCase){
		this.carte[i][j] = theCase;
	}

	public Carte(MainActivity theContext, Integer theCote, Gc.Couleur [] theEquipes){
		postLayout = new LinearLayout[theCote];
		carte = new Case[theCote][theCote];

		// D�finition du Layout � construire.
		LinearLayout existLayout = (LinearLayout) theContext.findViewById(R.id.layoutOfDynamicContent);
		LayoutParams params = new LayoutParams(80, 80);
		
		for(int i=0; i < theCote; ++i){
			postLayout[i] = new LinearLayout(theContext);
			postLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			for (int j=0; j < theCote ; ++j){
				carte[i][j] = new Case(i, j, theContext);
				postLayout[i].addView( carte[i][j].getMonImage(), params ); 
			}
			existLayout.addView(postLayout[i]);
		}

		carte[0][0].setGc(new Gc(theEquipes[0], 0, 0));
		carte[theCote - 1][theCote - 1].setGc(new Gc(theEquipes[1], theCote - 1, theCote - 1));

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

		return;
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

}