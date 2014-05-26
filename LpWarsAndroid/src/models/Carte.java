package models;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

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

	public Carte(Integer theCote, Gc.Couleur [] theEquipes){
		carte = new Case[theCote][theCote];

		for(int i=0; i < theCote; ++i){
			for (int j=0; j < theCote ; ++j){
				carte[i][j] = new Case(i, j);
			}
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