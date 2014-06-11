package models;

import interfaces.Serialized;

import java.io.Serializable;
import java.util.List;

import models.Gc.Couleur;
import configuration.UnitesEtBatiment;

/**
 * 
 * Le fait que la class impl�mente l'interface Pion, permet
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
	
	private Integer fidelite;
	
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
	public void setMaCase(Case maCase) {
		this.maCase = maCase;
	}

	public Batiment(Couleur theEquipe, Case theCase, int theCodeBatiment) {
		switch (theCodeBatiment) {
		case UnitesEtBatiment.Batiment.CASERNE.ID:
			fidelite = UnitesEtBatiment.Batiment.CASERNE.FIDELITE;
			break;
		case UnitesEtBatiment.Batiment.USINE_DE_CHAR.ID:
			fidelite = UnitesEtBatiment.Batiment.USINE_DE_CHAR.FIDELITE;
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
		// default est inutile car le type est match� sur l'instanciation de 
		// l'objet mais Eclipse veut un default � cause du return au cas ou ... :)
		default:
			return null;
		}
	}

	/**
	 * Instancie et affiche un nouveau Gc sur la case du batiment
	 * @param theCodeUnite code type du Gc demand�
	 * @return le Gc correspondant au code pass� en param�tre
	 */
	public Gc createGc(int theCodeUnite){
		if(maCase.getSerialized().getClass().equals(Batiment.class)){
			// Gc(...) appelle case.setGc() appelle updateMonImage() ...
			maCase.setGc(new Gc(equipe, maCase, theCodeUnite));
			((Gc)maCase.getSerialized()).setPm(0);
			return (Gc)maCase.getSerialized();
		}
		return null;
	}
	
	/**
	 * Diminue la fidelite � son �quipe
	 * Si le Gc est de la m�me couleur, la fid�lit� descendra
	 * quand m�me ! ! ! !
	 * @param theGc est le groupe de combat qui effectue la capture
	 */
	public void baisserFidelite(Gc theGc){
		if((fidelite -= theGc.getPa()) <= 0){
			equipe = theGc.getEquipe();
		}
	}
}
