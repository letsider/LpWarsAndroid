package models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe gerant les groupes de combats
 * 
 * Cette classe implémente Serializable afin de pouvoir être échangée
 * d'une activité à l'autre
 */
public class Gc implements Parcelable{

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

	private Integer i;
	private Integer j;

	/**
	 * Equipe du GC
	 */
	public enum Couleur{bleu, rouge};
	private Couleur equipe;
	
	/**
	 * Getters and setters
	 */
	public Integer getPv(){
		return this.pv;
	}

	public Integer getPa(){
		return this.pa;
	}

	public Integer getPm(){
		return this.pm;
	}

	public Couleur getEquipe(){
		return this.equipe;
	}

	public Integer geti(){
		return this.i;
	}

	public Integer getj(){
		return this.j;
	}

	public void setPv(Integer thePv){
		this.pv = thePv;
	}

	public void setPa(Integer thePa){
		this.pa = thePa;
	}

	public void setPm(Integer thePm){
		this.pm = thePm;
	}

	public void setEquipe(Couleur theEquipe){
		this.equipe = theEquipe;
	}

	public void seti(Integer thei){
		this.i = thei;
	}

	public void setj(Integer thej){
		this.j = thej;
	}

	public Gc(Couleur theEquipe, Integer thei, Integer thej){
		pv = 100;
		pa = 10;
		pm = 2;
		i = thei;
		j = thej;
		equipe = theEquipe;
	}
	
	@Override
    public int describeContents(){
        return 0;
    }

	/**
	 * Parce l'objet
	 */
    @Override
    public void writeToParcel(Parcel theDest, int theFlags) {
    	theDest.writeInt(pv);
    	theDest.writeInt(pa);
    	theDest.writeInt(pm);
    	theDest.writeInt(i);
    	theDest.writeInt(j);
    }
    
    public static final Parcelable.Creator<Gc> CREATOR = new Parcelable.Creator<Gc>() {
        public Gc createFromParcel(Parcel in) {
            return new Gc(in); 
        }

        public Gc[] newArray(int size) {
            return new Gc[size];
        }
    };
	
	/**
	 * Permet de reconstruire l'objet à partir de donnée parcé
	 * @param theData ==> sortie de writeToParcel
	 */
	public Gc(Parcel theData){
        
        switch(theData.readInt()){
        case 1:
        	equipe = Couleur.bleu;
        	break;
        case 2:
        	equipe = Couleur.rouge;
        	break;
        }
        pv = Integer.valueOf(theData.readInt());
        pa = Integer.valueOf(theData.readInt());
        pm = Integer.valueOf(theData.readInt());
        i = Integer.valueOf(theData.readInt());
        j = Integer.valueOf(theData.readInt());
    }

	public Boolean estMort(){
		return (pv <= 0);
	}

	public Boolean mouvement(Case [][] theCarte, Integer thei, Integer thej){
		if(Math.abs(i - thei) + Math.abs(j - thej) <= pm){
			theCarte[thei][thej].setGc(this);
			theCarte[i][j].setGc(null);
			i = thei;
			j = thej;
			pm = 0;
			return true;
		}
		else return false;
	}

	public Boolean attaque(Gc gcDef){
		if(Math.abs(i - gcDef.geti()) + Math.abs(j - gcDef.getj()) == 1){
			gcDef.setPv(gcDef.getPv() - this.pa);
			this.pm = 0;
			return true;
		}
		else return false;
	}
	
	public void actionPossible(){
		// TODO
//		// Pour toutes les cases à la porté du Gc
//		for(int cpti = (theGc.geti() - theGc.getPm()); 
//				cpti < (theGc.geti() + theGc.getPm()); 
//				++cpti){
//			for(int cptj = (theGc.geti() - theGc.getPm());
//					cptj < (theGc.geti() + theGc.getPm());
//					++cptj){
//				// Si les coordonnées sont bien sur le plateau de jeu \\n
//				// et que la case testé n'est pas celle de noter Gc courrant
//				if(plateauDeJeu.isValidCoords(cpti, cptj)
//						&& cpti != theGc.geti() && cptj != theGc.getj()){
//					
//					// On test si la case courrant est voisine de celle de notre Gc
//					// courrant
//					if(plateauDeJeu.getCarte()[theGc.geti()][theGc.getj()]
//							.isVoisin(plateauDeJeu.getCarte()[cpti][cptj])){
//						continue;
//					}
//				}
//
//			}
//		}
	}

}