package models;

import java.io.Serializable;

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

	private Integer i;
	private Integer j;

	/**
	 * Equipe du GC
	 */
	public enum Couleur{bleu, rouge};
	private Couleur equipe;
	
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

	public Integer geti(){
		return this.i;
	}

	public Integer getj(){
		return this.j;
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

	public void seti(Integer thei){
		this.i = thei;
	}

	public void setj(Integer thej){
		this.j = thej;
	}

	public Gc(Couleur theEquipe, Integer thei, Integer thej){
		pv = 100;
		pa = 10;
		pm = 2;
		i = thei;
		j = thej;
		equipe = theEquipe;
	}
	
	public Boolean estMort(){
		return (pv <= 0);
	}

	public Boolean mouvement(Case [][] theCarte, Integer thei, Integer thej){
		if(Math.abs(i - thei) + Math.abs(j - thej) <= pm){
			theCarte[thei][thej].setGc(this);
			theCarte[i][j].setGc(null);
			i = thei;
			j = thej;
			pm = 0;
			return true;
		}
		else return false;
	}

	public Boolean attaque(Gc gcDef){
		if(Math.abs(i - gcDef.geti()) + Math.abs(j - gcDef.getj()) == 1){
			gcDef.setPv(gcDef.getPv() - this.pa);
			this.pm = 0;
			return true;
		}
		else return false;
	}
	
	public void actionPossible(){
		// TODO
//		// Pour toutes les cases � la port� du Gc
//		for(int cpti = (theGc.geti() - theGc.getPm()); 
//				cpti < (theGc.geti() + theGc.getPm()); 
//				++cpti){
//			for(int cptj = (theGc.geti() - theGc.getPm());
//					cptj < (theGc.geti() + theGc.getPm());
//					++cptj){
//				// Si les coordonn�es sont bien sur le plateau de jeu \\n
//				// et que la case test� n'est pas celle de noter Gc courrant
//				if(plateauDeJeu.isValidCoords(cpti, cptj)
//						&& cpti != theGc.geti() && cptj != theGc.getj()){
//					
//					// On test si la case courrant est voisine de celle de notre Gc
//					// courrant
//					if(plateauDeJeu.getCarte()[theGc.geti()][theGc.getj()]
//							.isVoisin(plateauDeJeu.getCarte()[cpti][cptj])){
//						continue;
//					}
//				}
//
//			}
//		}
	}

}