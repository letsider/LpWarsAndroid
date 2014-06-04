package interfaces;

import models.Case;
import models.Gc.Couleur;

public interface Serialized {

	public int getType();

	public Couleur getEquipe();

	public Case getMaCase();

	public Integer geti();

	public Integer getj();

	public void setEquipe(Couleur theEquipe);

	public void setMaCase(Case theCase);
}
