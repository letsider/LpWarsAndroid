package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import models.Gc.Couleur;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.example.lpwarsandroid.R;

import configuration.UnitesEtBatiment;
import controllers.MainActivity;

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

	private MainActivity monContext;

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

	private Couleur nextEquipe(){
		if(equipeActuelleIt.hasNext()){
			equipeActuelle = equipeActuelleIt.next();
		} else {
			equipeActuelleIt = equipes.iterator();
			equipeActuelle = equipeActuelleIt.next();
		}

		return equipeActuelle;
	}

	private void initLayout(Integer theCote){
		postLayout = new LinearLayout[theCote];
		carte = new Case[theCote][theCote];

		// Définition du Layout à construire.
		LinearLayout existLayout = (LinearLayout) monContext.findViewById(R.id.layoutOfDynamicContent);
		LayoutParams params = new LayoutParams(80, 80);

		Log.d("Carte::Carte", "Rentrée dans la boucle d'init de chaque case");

		for(int i=0; i < theCote; ++i){
			postLayout[i] = new LinearLayout(monContext);
			postLayout[i].setOrientation(LinearLayout.HORIZONTAL);
			for (int j=0; j < theCote ; ++j){
				carte[i][j] = new Case(i, j, monContext);
				postLayout[i].addView( carte[i][j].getMonImage(), params ); 
			}
			existLayout.addView(postLayout[i]);
		}
	}

	private void initUnit(int thex, int they, int theUnitesEtBatimentId, int theCodeUnit){
		Case curCase = carte[thex + 1][they];
		switch (theUnitesEtBatimentId) {
		case UnitesEtBatiment.Batiment.ID:
			curCase.setBatiment(new Batiment(equipeActuelle, curCase, theCodeUnit));
			break;
		case UnitesEtBatiment.Unites.ID:
			curCase.setGc(new Gc(equipeActuelle, curCase, theCodeUnit));
			break;
		default:
			throw new IllegalArgumentException("Problème d'initialisation du plateau (nombre d'équipe trop nombreux) : " + theUnitesEtBatimentId);
		}
	}

	private void initUnits(int theBeginningi, int theBeginningj){

		initUnit(theBeginningi, theBeginningj,
				UnitesEtBatiment.Batiment.ID, UnitesEtBatiment.Batiment.CASERNE.ID);
		initUnit(theBeginningi + 1, theBeginningj, 
				UnitesEtBatiment.Batiment.ID, UnitesEtBatiment.Batiment.USINE_DE_CHAR.ID);
		initUnit(theBeginningi - 1, theBeginningj, 
				UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Vehicule.ID);
		initUnit(theBeginningi + 2, theBeginningj, 
				UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Vehicule.ID);

		switch(equipes.indexOf(equipeActuelle)){
		case 0:
			initUnit(theBeginningi, theBeginningj + 1, 
					UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Infanterie.ID);
			initUnit(theBeginningi + 1, theBeginningj + 1, 
					UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Infanterie.ID);
			break;
		case 1:
			initUnit(theBeginningi, theBeginningj - 1, 
					UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Infanterie.ID);
			initUnit(theBeginningi + 1, theBeginningj - 1, 
					UnitesEtBatiment.Unites.ID, UnitesEtBatiment.Unites.Infanterie.ID);
			break;
		default:
			throw new IllegalArgumentException("Problème d'initialisation du plateau (nombre d'équipe trop nombreux) : "
					+ equipes.indexOf(equipeActuelle));
		}
	}

	public Carte(MainActivity theContext, Integer theCote, Gc.Couleur [] theEquipes) {
		monContext = theContext;
		initLayout(theCote);

		Log.i("Carte::Carte", "init des équipes et des pointeurs sur listes d'équipes");
		compteur = 0;
		equipes = new LinkedList<Couleur>();
		for(Gc.Couleur newOne : theEquipes){
			equipes.add(newOne);
		}

		equipeActuelleIt = equipes.iterator();
		equipeActuelle = equipeActuelleIt.next();

		Log.i("Carte::Carte", "init des positionnement des pions");
		initUnits((carte.length / 2) - 2, 0);
		nextEquipe();
		initUnits((carte.length / 2) - 2, carte[(carte.length / 2) - 1].length - 1);
		nextEquipe();

		monContext.setTitle(monContext.getResources().getString(R.string.app_name) 
				+ " - " + equipeActuelle.toString());
	}

	public void finTour(){

		++compteur;

		nextEquipe();

		for(int i=0; i < carte.length; ++i){
			for (int j=0; j < carte[i].length ; ++j){ 
				if(getCase(i, j).getSerialized() != null){
					// Seul les groupes de combats ont des points de mouvement
					if(getCase(i, j).getSerialized().getClass().equals(Gc.class)){
						switch(getCase(i, j).getSerialized().getType()){
						case UnitesEtBatiment.Unites.Infanterie.ID:
							((Gc)getCase(i, j).getSerialized()).setPm(UnitesEtBatiment.Unites.Infanterie.PM);
							break;
						case UnitesEtBatiment.Unites.Vehicule.ID:
							((Gc)getCase(i, j).getSerialized()).setPm(UnitesEtBatiment.Unites.Vehicule.PM);
							break;
						}
					}
				}
			}
		}

		if(gagner() != null){
			monContext.finish();
		}
		monContext.reinitImageButtonPlateau();
		monContext.setTitle(monContext.getResources().getString(R.string.app_name) 
				+ " - " + equipeActuelle.toString());

	}

	/**
	 * Cette méthode permet de testé si la partie est fini
	 * @return Si la partie est fini, la couleur du vainqueur est retourné
	 * sinon, null est retourné
	 */
	public Gc.Couleur gagner(){
		List<Gc.Couleur> enVie = new ArrayList<Gc.Couleur>();

		for(Case [] ligne : carte){
			for(Case cellule : ligne){
				if(cellule.getSerialized() != null){
					if(enVie.contains(cellule.getSerialized().getEquipe())){
						continue;
					} else if(enVie.size() == equipes.size()){
						return null;
					} else {
						enVie.add(cellule.getSerialized().getEquipe());
					}
				}
			}
		}

		if(enVie.size() == 1){
			return enVie.get(0);
		} else {
			return null;
		}
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