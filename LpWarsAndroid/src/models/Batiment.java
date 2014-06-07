package models;

import interfaces.Serialized;

import java.io.Serializable;
import java.util.List;

import models.Gc.Couleur;
import configuration.CodeActions;
import configuration.UnitesEtBatiment;

/**
 * 
 * Le fait que la class implémente l'interface Pion, permet
 * de pouvoir placer un Gc ou un Batiment sur une case
 *
 */
public class Batiment implements Serializable, Serialized {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Integer type;

	private Couleur equipe;
	
	private Case maCase;
	
	/**
	 * Getters and setters
	 */

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
	public void setEquipe(Couleur theEquipe){
		this.equipe = theEquipe;
	}

	@Override
	public void setMaCase(Case maCase) {
		this.maCase = maCase;
	}

	public Batiment(Couleur theEquipe, Case theCase, int theCodeBatiment) {
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

	/**
	 * Instancie et affiche un nouveau Gc sur la case du batiment
	 * @param theCodeUnite code type du Gc demandé
	 * @return le Gc correspondant au code passé en paramètre
	 */
	public Gc createGc(int theCodeUnite){
		if(maCase.getSerialized().getClass().equals(Batiment.class)){
			// Gc(...) appelle case.setGc() appelle updateMonImage() ...
			maCase.setGc(new Gc(equipe, maCase, theCodeUnite));
			return (Gc)maCase.getSerialized();
		}
		return null;
	}
}
