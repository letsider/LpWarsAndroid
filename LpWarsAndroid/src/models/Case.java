package models;

import interfaces.Serialized;

import java.io.Serializable;

import models.Gc.Couleur;
import android.util.Log;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import configuration.CodeActions;
import configuration.UnitesEtBatiment;
import controllers.MainActivity;

/**
 * Classe gerant le contenu d'une case
 */
public class Case implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * pointeur vers le GC
	 * @see Gc
	 */
	private Gc gc;
	
	private Batiment batiment;

	private Integer i;
	private Integer j;

	/**
	 * Pointeur vers le bouton dynamique de cette case
	 * transient est le .gitignore de Serializable
	 * sauf que celui la marche à tout les coups
	 */
	private transient ImageButton monImage;
	private transient MainActivity monContext;

	/**
	 * Getters and setters
	 */
	public Serialized getSerialized(){
		if(gc != null){
			return gc;
		}
		return batiment;
	}

	public Integer geti(){
		return i;
	}

	public Integer getj(){
		return j;
	}
	
	public Carte getMonPlateau(){
		return monContext.plateauDeJeu;
	}

	public ImageButton getMonImage() {
		return monImage;
	}

	public Batiment getBatiment(){
		return batiment;
	}

	public void setGc(Gc theGc){
		this.gc = theGc;
		updateMonImage(false, null);
		if(gc != null) {
			monContext.setListenerOnButton(monImage, gc);
			return;
		} else if(batiment != null){
			monContext.setListenerOnButton(monImage, batiment);
			return;
		}

		monContext.removeListenerOAction(monImage);
	}
	
	public void setBatiment(Batiment theBatiment){
		if(gc == null){
			this.batiment = theBatiment;
			updateMonImage(false, null);
			if(batiment != null) {
				monContext.setListenerOnButton(monImage, theBatiment);
				return;
			}

			monContext.removeListenerOAction(monImage);			
		} else {
			throw new IllegalStateException("Changer un batiment avec déjà une unité dessus ? ? ?");
		}
	}
	
	public Boolean isMyTurn(){
		if(gc == null){
			return false;
		}
		if(monContext.plateauDeJeu.getEquipeActuelle() != gc.getEquipe()){
			return false;
		}
		
		return true;
	}

	public Boolean isCapturable(){
		if(batiment != null && gc != null
				&& ! batiment.getEquipe().equals(gc.getEquipe())){
			return true;
		}
		return false;
	}

	private void updateMonImage(){
		if(batiment == null && gc == null){
			monImage.setBackgroundResource(R.drawable.empty);
		} else if(batiment != null && gc != null){
			if(batiment.getEquipe().equals(gc.getEquipe())){
				if(gc.getType() == UnitesEtBatiment.Unites.Infanterie.ID){
					if(batiment.getEquipe().equals(Couleur.bleu)){
						monImage.setBackgroundResource(R.drawable.supp_blue_blue_building);
					} else if(batiment.getEquipe().equals(Couleur.rouge)){
						monImage.setBackgroundResource(R.drawable.supp_red_red_building);
					}
				} else if(gc.getType() == UnitesEtBatiment.Unites.Vehicule.ID){
					if(batiment.getEquipe().equals(Couleur.bleu)){
						monImage.setBackgroundResource(R.drawable.supp_blue_building);
					} else if(batiment.getEquipe().equals(Couleur.rouge)){
						monImage.setBackgroundResource(R.drawable.supp_red_building);
					}
				}
			} else {
				if(batiment.getEquipe().equals(Couleur.bleu)){
					if(gc.getEquipe().equals(Couleur.rouge)){
						monImage.setBackgroundResource(R.drawable.supp_blue_red_building);						
					}
				} else if(batiment.getEquipe().equals(Couleur.rouge)){
					if(gc.getEquipe().equals(Couleur.bleu)){
						monImage.setBackgroundResource(R.drawable.supp_red_blue_building);						
					}
				}
			}
		} else if(gc == null){
			if(batiment.getEquipe().equals(Couleur.bleu)){
				monImage.setBackgroundResource(R.drawable.blue_building);
			} else if(batiment.getEquipe().equals(Couleur.rouge)){
				monImage.setBackgroundResource(R.drawable.red_building);
			}
		} else if(batiment == null){
			switch(gc.getType()){
			case UnitesEtBatiment.Unites.Infanterie.ID:
				if(gc.getEquipe().equals(Couleur.bleu)){
					monImage.setBackgroundResource(R.drawable.blue_inf);
				} else if(gc.getEquipe().equals(Couleur.rouge)){
					monImage.setBackgroundResource(R.drawable.red_inf);
				}
				break;
			case UnitesEtBatiment.Unites.Vehicule.ID:
				if(gc.getEquipe().equals(Couleur.bleu)){
					monImage.setBackgroundResource(R.drawable.blue_tank);
				} else if(gc.getEquipe().equals(Couleur.rouge)){
					monImage.setBackgroundResource(R.drawable.red_tank);
				}
				break;
			default:
				break;
			}
		}
	}
	
	private void updateMonImage(Integer theMoveCodeUnit){
		if(gc != null){
			if(gc.getType() == UnitesEtBatiment.Unites.Infanterie.ID){
				if(gc.getEquipe().equals(Couleur.bleu)){
					monImage.setBackgroundResource(R.drawable.blue_inf_dark);
				} else if(gc.getEquipe().equals(Couleur.rouge)){
					monImage.setBackgroundResource(R.drawable.red_inf_dark);
				}
			} else if(gc.getType() == UnitesEtBatiment.Unites.Vehicule.ID){
				if(gc.getEquipe().equals(Couleur.bleu)){
					monImage.setBackgroundResource(R.drawable.blue_tank_dark);
				} else if(gc.getEquipe().equals(Couleur.rouge)){
					monImage.setBackgroundResource(R.drawable.red_tank_dark);
				}
			}
		} else if(batiment != null){
			if(batiment.getEquipe().equals(Couleur.bleu)){
				monImage.setBackgroundResource(R.drawable.can_move_blue_building);
			} else if(batiment.getEquipe().equals(Couleur.rouge)){
				monImage.setBackgroundResource(R.drawable.can_move_red_building);
			}
		} else {
			switch(theMoveCodeUnit){
			case UnitesEtBatiment.Unites.Infanterie.ID:
				monImage.setBackgroundResource(R.drawable.can_move_inf);
				break;
			case UnitesEtBatiment.Unites.Vehicule.ID:
				monImage.setBackgroundResource(R.drawable.can_move_tank);
				break;
			default:
				throw new IllegalArgumentException("theMoveCodeUnit n'est pas connu : " + theMoveCodeUnit);
			}
		}
	}
	
	/**
	 * Cette méthode permet de changer l'image de l'ImageButton entre deux possibilités
	 * 
	 * @param theDark définit si l'image est celle d'origine ou celle d'action
	 * En d'autre terme, si la case est vide et Dark vaut true alors l'image affiché sera
	 * celle d'un mouvement possible, si dark est false, l'image sera de la verdure 
	 * @param theMoveCodeUnit utilisé seulement si theDark est à vrai, correspond au code unité
	 * visé
	 */
	public void updateMonImage(boolean theDark, Integer theMoveCodeUnit){
		Log.i("Case::changeMonImage", "changement d'image de la case {" + i + "," + j + "}");

		if(theDark){
			updateMonImage(theMoveCodeUnit);
		} else {
			updateMonImage();
		}
	}
	
	/**
	 * Cette méthode permet de créer une action sur un click 
	 * @param theCodeAction l'action en question
	 * @param theTarget le gc de la case depuis laquelle l'action est lancée
	 * @see CodeActions
	 */
	public void changeMonAction(int theCodeAction, Gc theTargeted){
		monContext.setActionOnButton(monImage, theCodeAction, theTargeted, this);
	}

	public Case(Integer thei, Integer thej, MainActivity theContext){
		gc = null;
		i = thei;
		j = thej;

		monImage = new ImageButton( theContext );
		updateMonImage(false, null);
		monImage.setId(i * 10 +  j);
		monImage.setTag(i * 10 +  j);
		
		monContext = theContext;
		Log.i("Case::Case", "Création d'une nouvelle case");
	}

	public Boolean estVide() {
		return (gc == null);
	}

	/**
	 * Match si theCase est avoisinante
	 * ATTENTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * Cette fonction n'assume pas une sortie de map
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @param theCase case potentiellement avasinante
	 * 
	 * @return si theCase à un côté ou un angle commun
	 */
	public Boolean isVoisin(Case theCase) {
		// Si l'on est sur la même ligne et qu'il y a une case de
		// différence sur la hauteur
		if(Math.abs(i - theCase.geti()) == 0 && Math.abs(j - theCase.getj()) == 1){
			return true;
		}
		// Si l'on est sur la même hauteur et qu'il y a une case de
		// différence sur la ligne
		else if (Math.abs(i - theCase.geti()) == 1 && Math.abs(j - theCase.getj()) == 0){
			return true;
		}

		return false;
	}

}
