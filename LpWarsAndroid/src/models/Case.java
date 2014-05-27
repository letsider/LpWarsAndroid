package models;

import android.widget.ImageButton;

import com.example.lpwarsandroid.R;

import controllers.MainActivity;

/**
 * Classe gerant le contenu d'une case
 */
public class Case{

	/**
	 * pointeur vers le GC
	 * @see Gc
	 */
	private Gc gc;

	private Integer i;
	private Integer j;

	/**
	 * Pointeur vers le bouton dynamique de cette case
	 */
	private ImageButton monImage;
	private MainActivity monContext;
	
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

	public ImageButton getMonImage() {
		return monImage;
	}
	

	public void setGc(Gc theGc){
		this.gc = theGc;
	}

	public Case(Integer thei, Integer thej, MainActivity theContext){
		gc = null;
		i = thei;
		j = thej;

		monImage = new ImageButton( theContext );
		monImage.setBackgroundResource(R.drawable.ic_launcher);
		monImage.setId(i * 10 +  j);
		
		theContext.addListenerOnButton(monImage, monImage.getId());
	}

	public Case(Gc theGc, Integer thei, Integer thej, MainActivity theContext){
		this.gc = theGc;
		i = thei;
		j = thej;

		monImage = new ImageButton( monContext );
		monImage.setBackgroundResource(R.drawable.ic_launcher);
		monImage.setId(i * 10 +  j);
		
		theContext.addListenerOnButton(monImage, monImage.getId());
	}

	public Boolean estVide() {
		return (gc == null);
	}

}