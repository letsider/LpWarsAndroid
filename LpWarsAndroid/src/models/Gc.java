package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import configuration.CodeActions;

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
		if(thePv <= 0){
			getMaCase().setGc(null);
		} else {
			this.pv = thePv;
		}
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
		pv = 10;
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
	public Boolean mouvement(Carte theCarte, Integer thei, Integer thej){
		if(maCase.isMine()){
			// Si la cible est a port� !
			if(Math.abs(geti() - thei) + Math.abs(getj() - thej) <= pm){
				theCarte.getCase(thei, thej).setGc(this);
				theCarte.getCase(geti(), getj()).setGc(null);
				maCase = theCarte.getCase(thei, thej);
				pm = 0;
				return true;
			}
		}
		return false;
	}

	public Boolean attaque(Gc gcDef){
		if(maCase.isMine()){
			// Si la cible est a port�
			if(Math.abs(geti() - gcDef.geti()) + Math.abs(getj() - gcDef.getj()) == 1){
				gcDef.setPv(gcDef.getPv() - this.pa);
				this.pm = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 *
	 * Cette fonction permet de connaitre toutes les actions que
	 * le Gc peut faire
	 *
	 * @return un tabelau de tableau contenant des pointeurs sur les cases accessibles
	 * Si une action n'est pas possible LA CASE SERA NULL ! ! !
	 */
	public List<Case> setActionPossible(){
		Carte carte = maCase.getMonPlateau();
		List<Case> casesALImageTemporaire = new ArrayList<Case>();
		
		// R�duction du carr� d'it�ration gr�ce au d�placement max du Gc
		for(int cpti = (geti() - pm); cpti <= (geti() + pm); ++cpti) {
			for(int cptj = (getj() - pm); cptj <= (getj() + pm); ++cptj){
				
				// Si les coordonn�es sont bien sur le plateau de jeu \\n
				// Que la case fait partie de ma port�e
				// et que la case test� n'est pas celle de notre Gc courrant
				if(maCase.getMonPlateau().isValidCoords(cpti, cptj)
						&& (Math.abs(geti() - cpti)
								+ Math.abs(getj() - cptj)
								<= pm)
						&& ! (cpti == geti() && cptj == getj())){
				
					// Si la case est vide, je peux y aller
					if(carte.getCase(cpti, cptj).getGc() == null){
						// changement de l'image de la case et on
						carte.getCase(cpti, cptj).changeMonImage(true);
						// Mise en place du listener pour effectuer l'action
						carte.getCase(cpti, cptj).changeMonAction(CodeActions.SE_DEPLACER, this);
						
						// indexation de la case chang�
						casesALImageTemporaire.add(carte.getCase(cpti, cptj));
						// it�re sur la case suivante
						continue;
					}
					
					// S'il n'est pas dans mon camp
					// On v�rifie que l'on peut l'atteindre
					if(carte.getCase(cpti, cptj).getGc().getEquipe() != equipe
							&& carte.getCase(geti(), getj()).isVoisin(carte.getCase(cpti, cptj))){
						
						carte.getCase(cpti, cptj).changeMonImage(true);
						carte.getCase(cpti, cptj).changeMonAction(CodeActions.ATTAQUER, this);

						casesALImageTemporaire.add(carte.getCase(cpti, cptj));
						continue;
					}
					
					// Dans tout les autres cas, la case n'est pas accessible
				}
			}
		}
		return casesALImageTemporaire;
	}
}