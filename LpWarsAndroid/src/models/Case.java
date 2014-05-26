package models;

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

	public void setGc(Gc theGc){
		this.gc = theGc;
	}

	public Case(Integer thei, Integer thej){
		gc = null;
		i = thei;
		j = thej;
	}

	public Case(Gc theGc){
		this.gc = theGc;
	}

	public Boolean estVide() {
		return (gc == null);
	}

}