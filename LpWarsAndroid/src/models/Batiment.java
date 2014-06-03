package models;

import interfaces.Pion;

import java.io.Serializable;
import java.util.List;

import models.Gc.Couleur;
import configuration.UnitesEtBatiment;

/**
 * 
 * Le fait que la class implémente l'interface Pion, permet
 * de pouvoir placer un Gc ou un Batiment sur une case
 *
 */
public class Batiment implements Serializable, Pion {

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
	@Override
	public Integer getPv(){
		return this.pv;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public Couleur getEquipe(){
		return this.equipe;
	}

	@Override
	public Case getMaCase() {
		return maCase;
	}

	@Override
	public Integer geti(){
		return maCase.geti();
	}

	@Override
	public Integer getj(){
		return maCase.getj();
	}

	@Override
	public void setPv(Integer thePv){
		if(thePv <= 0){
			getMaCase().setPion(null);
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
		case UnitesEtBatiment.Batiment.CASERNE.ID:
			pv = UnitesEtBatiment.Batiment.CASERNE.PV;
			break;
		case UnitesEtBatiment.Batiment.USINE_DE_CHAR.ID:
			pv = UnitesEtBatiment.Batiment.USINE_DE_CHAR.PV;
			break;
		default:
			throw new IllegalArgumentException("Cet entier n'est pas connu des codes d'initialisation d'un GC cf Names.Unites");
		}
		type = theCodeBatiment;
		equipe = theEquipe;
		maCase = theCase;
	}
	
	public List<Integer> getUnitsCreatable(){
		switch(type){
		case UnitesEtBatiment.Batiment.CASERNE.ID:
			return UnitesEtBatiment.Batiment.CASERNE.unitesDisponible();
		case UnitesEtBatiment.Batiment.USINE_DE_CHAR.ID:
			return UnitesEtBatiment.Batiment.USINE_DE_CHAR.unitesDisponible();
		// default est inutile car le type est matché sur l'instanciation de 
		// l'objet mais Eclipse veut un default à cause du return au cas ou ... :)
		default:
			return null;
		}
	}
	
	public Gc createGc(int theCodeUnite){
		return new Gc(equipe, maCase, theCodeUnite);
	}
}
