package interfaces;

import models.Case;
import models.Gc.Couleur;

public interface Pion {

	public Integer getPv();

	int getType();

	Couleur getEquipe();

	Case getMaCase();

	Integer geti();

	Integer getj();

	public void setPv(Integer thePv);

	void setEquipe(Couleur theEquipe);

	void setMaCase(Case theCase);
}
