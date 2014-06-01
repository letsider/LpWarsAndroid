package models;

import java.io.Serializable;

import models.Gc.Couleur;
import configuration.UnitesEtBatiment;

public class Batiment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Point de vie du GC
	 * en pourcent
	 */
	private Integer pv;
	
	private final Integer type;

	private Couleur equipe;
	
	private Case maCase;
	
	/**
	 * Getters and setters
	 */
	public Integer getPv(){
		return this.pv;
	}

	public Integer getType() {
		return type;
	}

	public Couleur getEquipe(){
		return this.equipe;
	}

	public Case getMaCase() {
		return maCase;
	}
	
	public int geti(){
		return maCase.geti();
	}

	public int getj(){
		return maCase.getj();
	}

	public void setPv(Integer thePv){
		if(thePv <= 0){
			getMaCase().setGc(null);
		} else {
			this.pv = thePv;
		}
	}

	public void setEquipe(Couleur theEquipe){
		this.equipe = theEquipe;
	}

	public void setMaCase(Case maCase) {
		this.maCase = maCase;
	}

	public Batiment(Couleur theEquipe, Case theCase, int theCodeBatiment) {
		switch(theCodeBatiment){
		case 0:
			pv = UnitesEtBatiment.Batiment.CASERNE.PV;
			break;
		case 1:
			pv = UnitesEtBatiment.Batiment.USINE_DE_CHAR.PV;
			break;
		default:
			throw new IllegalArgumentException("Cet entier n'est pas connu des codes d'initialisation d'un GC cf Names.Unites");
		}
		type = theCodeBatiment;
		equipe = theEquipe;
		maCase = theCase;
	}
	
	public Gc createGc(int theCodeUnite){
		return new Gc(equipe, maCase, theCodeUnite);
	}
}
