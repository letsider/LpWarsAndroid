package models;

import java.io.Serializable;

import android.util.Log;
import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import controllers.MainActivity;

/**
 * Classe gerant le contenu d'une case
 */
public class Case implements Serializable{

	/**
	 * pointeur vers le GC
	 * @see Gc
	 */
	private Gc gc;

	private Integer i;
	private Integer j;

	/**
	 * Pointeur vers le bouton dynamique de cette case
	 * transient est le .gitignore de Serializable
	 * sauf que celui la marche � tout les coups
	 */
	private transient ImageButton monImage;
	private transient MainActivity monContext;

	/**
	 * Getters and setters
	 */
	public Gc getGc(){
		return this.gc;
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


	public void setGc(Gc theGc){
		this.gc = theGc;
		if(gc != null) {
			changeMonImage(theGc.getEquipe());
			monContext.addListenerOnButton(monImage, theGc);
		} else {
			changeMonImage(null);
		}
	}

	public void changeMonImage(Gc.Couleur theColor){
		changeMonImage(theColor, false);
	}
	
	public void changeMonImage(Gc.Couleur theColor, boolean theDark){
		Log.i("Case::changeMonImage", "changement d'image de la case {" + i + "," + j + "}");
		if(Gc.Couleur.bleu.equals(theColor)){
			monImage.setBackgroundResource(R.drawable.blue_inf);
		} else if(Gc.Couleur.rouge.equals(theColor)){
			if(theDark){
				monImage.setBackgroundResource(R.drawable.red_inf_dark);
			} else {
				monImage.setBackgroundResource(R.drawable.red_inf);
			}
		} else {
			if(theDark){
				monImage.setBackgroundResource(R.drawable.can_move);
			}
			else {
				monImage.setBackgroundResource(R.drawable.ic_launcher);
			}
		}
	}

	public Case(Integer thei, Integer thej, MainActivity theContext){
		gc = null;
		i = thei;
		j = thej;

		monImage = new ImageButton( theContext );
		changeMonImage(null);
		monImage.setId(i * 10 +  j);
		monImage.setTag(i * 10 +  j);
		
		monContext = theContext;
		Log.i("Case::Case", "Cr�ation d'une nouvelle case");
	}

	public Case(Gc theGc, Integer thei, Integer thej, MainActivity theContext){
		this.gc = theGc;
		i = thei;
		j = thej;

		monImage = new ImageButton( monContext );
		changeMonImage(theGc.getEquipe());
		monImage.setId(i * 10 +  j);

		monContext =theContext; 
		monContext.addListenerOnButton(monImage, theGc);
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
	 * @return si theCase � un c�t� ou un angle commun
	 */
	public Boolean isVoisin(Case theCase) {
		// Si l'on est sur la m�me ligne et qu'il y a une case de
		// diff�rence sur la hauteur
		if(Math.abs(i - theCase.geti()) == 0 && Math.abs(j - theCase.getj()) == 1){
			return true;
		}
		// Si l'on est sur la m�me hauteur et qu'il y a une case de
		// diff�rence sur la ligne
		else if (Math.abs(i - theCase.geti()) == 1 && Math.abs(j - theCase.getj()) == 0){
			return true;
		}

		return false;
	}

}