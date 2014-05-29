package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gerant les groupes de combats
 * 
 * Cette classe impl�mente Serializable afin de pouvoir �tre �chang�e
 * d'une activit� � l'autre
 */
public class Gc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Point de vie du GC
	 * en pourcent
	 */
	private Integer pv;

	/**
	 * Point d'attaque du GC
	 */
	private Integer pa;

	/**
	 * Point de mouvement
	 */
	private Integer pm;

	/**
	 * Equipe du GC
	 */
	public enum Couleur{bleu, rouge};
	private Couleur equipe;
	
	private Case maCase;
	
	/**
	 * Getters and setters
	 */
	public Integer getPv(){
		return this.pv;
	}

	public Integer getPa(){
		return this.pa;
	}

	public Integer getPm(){
		return this.pm;
	}

	public Couleur getEquipe(){
		return this.equipe;
	}

	public Case getMaCase() {
		return maCase;
	}
	
	public int geti(){
		return maCase. geti();
	}

	public int getj(){
		return maCase. getj();
	}

	public void setPv(Integer thePv){
		this.pv = thePv;
	}

	public void setPa(Integer thePa){
		this.pa = thePa;
	}

	public void setPm(Integer thePm){
		this.pm = thePm;
	}

	public void setEquipe(Couleur theEquipe){
		this.equipe = theEquipe;
	}

	public void setMaCase(Case maCase) {
		this.maCase = maCase;
	}


	public Gc(Couleur theEquipe, Case theCase){
		pv = 100;
		pa = 10;
		pm = 2;
		
		maCase = theCase;
		
		equipe = theEquipe;
	}
	
	public Boolean estMort(){
		return (pv <= 0);
	}

	/**
	 * Effectue le mouvement le mouvement du gc this de maCase
	 * jusqu'� theCarte[thei][thej]
	 * 
	 * @param theCarte la carte sur laquelle je suis en mouvement
	 * @param thei abscice
	 * @param thej ordonn�e
	 * @return si le mouvement � eu lieu
	 */
	public Boolean mouvement(Case [][] theCarte, Integer thei, Integer thej){
		if(Math.abs(geti() - thei) + Math.abs(getj() - thej) <= pm){
			theCarte[thei][thej].setGc(this);
			theCarte[geti()][getj()].setGc(null);
			maCase = theCarte[thei][thej];
			pm = 0;
			return true;
		}
		else return false;
	}

	public Boolean attaque(Gc gcDef){
		if(Math.abs(geti() - gcDef.geti()) + Math.abs(getj() - gcDef.getj()) == 1){
			gcDef.setPv(gcDef.getPv() - this.pa);
			this.pm = 0;
			return true;
		}
		else return false;
	}
	
	/**
	 *
	 * Cette fonction permet de connaitre toutes les actions que
	 * le Gc peut faire
	 *
	 * @return un tabelau de tableau contenant des pointeurs sur les cases accessibles
	 * Si une action n'est pas possible LA CASE SERA NULL ! ! !
	 */
	public List<Case> actionPossible(){
		Case[][] carte = maCase.getMonPlateau().getCarte();
		List<Case> casesALImageTemporaire = new ArrayList<Case>();
		
		// R�duction du carr� d'it�ration gr�ce au d�placement max du Gc
		for(int cpti = (geti() - pm); cpti <= (geti() + pm); ++cpti) {
			for(int cptj = (getj() - pm); cptj <= (getj() + pm); ++cptj){
				
				// Si les coordonn�es sont bien sur le plateau de jeu \\n
				// Que la case fait partie de ma port�e
				// et que la case test� n'est pas celle de notre Gc courrant
				if(maCase.getMonPlateau().isValidCoords(cpti, cptj)
						&& (Math.abs(geti() - carte[cpti][cptj].geti())
								+ Math.abs(getj() - carte[cpti][cptj].getj())
								<= pm)
						&& ! (cpti == geti() && cptj == getj())){
				
					// Si la case est vide, je peux y aller
					if(carte[cpti][cptj].getGc() == null){
						// changement de l'image de la case et on
						// it�re sur la case suivante
						carte[cpti][cptj].changeMonImage(null, true);
						// indexatino de la case chang�
						casesALImageTemporaire.add(carte[cpti][cptj]);
						continue;
					}
					
					// S'il n'est pas dans mon camp
					// On v�rifie que l'on peut l'atteindre
					if(carte[cpti][cptj].getGc().getEquipe() != equipe
							&& carte[geti()][getj()].isVoisin(carte[cpti][cptj])){
						
						carte[cpti][cptj].changeMonImage(carte[cpti][cptj].getGc().getEquipe(), true);
						casesALImageTemporaire.add(carte[cpti][cptj]);
						continue;
					}
					
					// Dans tout les autres cas, la case n'est pas accessible
				}
			}
		}
		return casesALImageTemporaire;
	}
}