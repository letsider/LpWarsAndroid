package models;

import interfaces.Pion;

import java.io.Serializable;

import models.Gc.Couleur;
import android.util.Log;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import configuration.CodeActions;
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
	private Pion pion;

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
	public Pion getPion(){
		return this.pion;
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


	public void setPion(Pion thePion){
		this.pion = thePion;
		changeMonImage(false);
		if(pion != null) {
			monContext.setListenerOnButton(monImage, thePion);
			return;
		}

		monContext.removeListenerOAction(monImage);
	}
	
	public Boolean isMine(){
		if(pion == null){
			return false;
		}
		if(monContext.plateauDeJeu.getEquipeActuelle() != pion.getEquipe()){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Cette méthode permet de changer l'image de l'ImageButton entre deux possibilité
	 * 
	 * @param theDark définit si l'image est celle d'origine ou celle d'action
	 * En d'autre terme, si la case est vide et Dark vaut true alors l'image affiché sera
	 * celle d'un mouvement possible, si dark est false, l'image sera de la verdure 
	 */
	public void changeMonImage(boolean theDark){
		Log.i("Case::changeMonImage", "changement d'image de la case {" + i + "," + j + "}");

		Couleur color = null;
		try{
			// Si gc == null ...
			color = pion.getEquipe();
		} catch (NullPointerException ex){
			// on met de la verdure !
			if(theDark){
				monImage.setBackgroundResource(R.drawable.can_move);
			}
			else {
				monImage.setBackgroundResource(R.drawable.ic_launcher);
			}
			return;
		}

		if(Couleur.bleu.equals(color)){
			monImage.setBackgroundResource(R.drawable.blue_inf);
		} else if(Couleur.rouge.equals(color)){
			if(theDark){
				monImage.setBackgroundResource(R.drawable.red_inf_dark);
			} else {
				monImage.setBackgroundResource(R.drawable.red_inf);
			}
		// si la couleur n'est pas connu, il faut la déclarer ! ! !
		} else {
			if(theDark){
				monImage.setBackgroundResource(R.drawable.can_move);
			}
			else {
				monImage.setBackgroundResource(R.drawable.ic_launcher);
			}
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
		pion = null;
		i = thei;
		j = thej;

		monImage = new ImageButton( theContext );
		changeMonImage(false);
		monImage.setId(i * 10 +  j);
		monImage.setTag(i * 10 +  j);
		
		monContext = theContext;
		Log.i("Case::Case", "Création d'une nouvelle case");
	}

	public Boolean estVide() {
		return (pion == null);
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