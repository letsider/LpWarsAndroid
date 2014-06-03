package models;

import interfaces.Pion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import configuration.CodeActions;
import configuration.UnitesEtBatiment;

/**
 * Classe gerant les groupes de combats
 * 
 * Cette classe implémente Serializable afin de pouvoir être échangée
 * d'une activité à l'autre
 * 
 * Le fait que la class implémente l'interface Pion, permet
 * de pouvoir placer un Gc ou un Batiment sur une case
 * @see UnitesEtBatiment
 */
public class Gc implements Serializable, Pion {

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
	
	private final Integer type;

	/**
	 * Equipe du GC
	 */
	public enum Couleur{bleu, rouge};
	private Couleur equipe;
	
	private Case maCase;
	
	/**
	 * Getters and setters
	 */
	@Override
	public Integer getPv(){
		return this.pv;
	}

	public Integer getPa(){
		return this.pa;
	}

	public Integer getPm(){
		return this.pm;
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

	public void setPa(Integer thePa){
		this.pa = thePa;
	}

	public void setPm(Integer thePm){
		this.pm = thePm;
	}

	@Override
	public void setEquipe(Couleur theEquipe){
		this.equipe = theEquipe;
	}

	@Override
	public void setMaCase(Case maCase) {
		this.maCase = maCase;
	}


	/**
	 * 
	 * @param theEquipe couleur de l'équipe du Gc
	 * @param theCase la case ou il doit être placé
	 * @param theCodeUnite code depuis Names.Unites
	 * @throws IllegalArgumentException Si le codeUnite est inconnu
	 */
	public Gc(Couleur theEquipe, Case theCase, int theCodeUnite)
	throws IllegalArgumentException {
		switch (theCodeUnite) {
		case UnitesEtBatiment.Unites.Infanterie.ID:
			pv = UnitesEtBatiment.Unites.Infanterie.PV;
			pa = UnitesEtBatiment.Unites.Infanterie.PA;
			pm = UnitesEtBatiment.Unites.Infanterie.PM;
			break;
		case UnitesEtBatiment.Unites.Vehicule.ID:
			pv = UnitesEtBatiment.Unites.Vehicule.PV;
			pa = UnitesEtBatiment.Unites.Vehicule.PA;
			pm = UnitesEtBatiment.Unites.Vehicule.PM;
			break;

		default:
			throw new IllegalArgumentException("Cet entier n'est pas connu des codes d'initialisation d'un GC cf Names.Unites");
		}
		type = theCodeUnite;
		maCase = theCase;
		equipe = theEquipe;
	}
	
	public Boolean estMort(){
		return (pv <= 0);
	}

	/**
	 * Effectue le mouvement le mouvement du gc this de maCase
	 * jusqu'à theCarte[thei][thej]
	 * 
	 * @param theCarte la carte sur laquelle je suis en mouvement
	 * @param thei abscice
	 * @param thej ordonnée
	 * @return si le mouvement à eu lieu
	 */
	public Boolean mouvement(Carte theCarte, Integer thei, Integer thej){
		if(maCase.isMine()){
			// Si la cible est a porté !
			if(Math.abs(geti() - thei) + Math.abs(getj() - thej) <= pm){
				theCarte.getCase(thei, thej).setPion(this);
				theCarte.getCase(geti(), getj()).setPion(null);
				maCase = theCarte.getCase(thei, thej);
				pm = 0;
				return true;
			}
		}
		return false;
	}

	public Boolean attaque(Pion gcDef){
		if(maCase.isMine()){
			// Si la cible est a porté
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
		
		// Réduction du carré d'itération grâce au déplacement max du Gc
		for(int cpti = (geti() - pm); cpti <= (geti() + pm); ++cpti) {
			for(int cptj = (getj() - pm); cptj <= (getj() + pm); ++cptj){
				
				// Si les coordonnées sont bien sur le plateau de jeu \\n
				// Que la case fait partie de ma portée
				// et que la case testé n'est pas celle de notre Gc courrant
				if(maCase.getMonPlateau().isValidCoords(cpti, cptj)
						&& (Math.abs(geti() - cpti)
								+ Math.abs(getj() - cptj)
								<= pm)
						&& ! (cpti == geti() && cptj == getj())){
				
					// Si la case est vide, je peux y aller
					if(carte.getCase(cpti, cptj).getPion() == null){
						// changement de l'image de la case et on
						carte.getCase(cpti, cptj).changeMonImage(true);
						// Mise en place du listener pour effectuer l'action
						carte.getCase(cpti, cptj).changeMonAction(CodeActions.SE_DEPLACER, this);
						
						// indexation de la case changé
						casesALImageTemporaire.add(carte.getCase(cpti, cptj));
						// itère sur la case suivante
						continue;
					}
					
					// S'il n'est pas dans mon camp
					// On vérifie que l'on peut l'atteindre
					if(carte.getCase(cpti, cptj).getPion().getEquipe() != equipe
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