package controllers;


import models.Gc;
import models.Batiment;
import models.Gc.Couleur;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpwarsandroid.R;

import configuration.Names;
import configuration.UnitesEtBatiment;

public class ActionsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actions);

		if(getIntent().getExtras().containsKey(Names.Generales.GC_CLICKED)){
			initGcView();
		} else if (getIntent().getExtras().containsKey(Names.Generales.BATIMENT_CLICKED)) {
			initBatimentView();
		}

		Button getBackR = (Button) findViewById(R.id.getBack);
		setListenerOnButton(getBackR, Activity.RESULT_CANCELED);

	}

	public void initGcView(){

		// R�cup�ration du Gc transmit
		Gc gc = (Gc) getIntent().getExtras().getSerializable(Names.Generales.GC_CLICKED);

		// Affichage des donn�es dynamiques transmises
		TextView currentText = (TextView) findViewById(R.id.pv);
		currentText.setText(currentText.getText().toString() + gc.getPv());
		currentText = (TextView) findViewById(R.id.pa);
		currentText.setText(currentText.getText().toString() + gc.getPa());
		currentText = (TextView) findViewById(R.id.pm);
		currentText.setText(currentText.getText().toString() + gc.getPm());

		Button getBackV = (Button) findViewById(R.id.select);
		if((Boolean) getIntent().getExtras().getSerializable(Names.Generales.SELECTIONNALBLE)){
			setListenerOnButton(getBackV, Activity.RESULT_OK);
		} else {
			((LinearLayout)findViewById(R.id.returnButtons)).removeView(getBackV);
		}
	}

	public void initBatimentView(){

		// Suppression des �l�ments utiles aux Gc
		((LinearLayout)findViewById(R.id.hightest)).removeView(
				findViewById(R.id.layout_info));
		((LinearLayout)findViewById(R.id.returnButtons)).removeView(
				findViewById(R.id.select));

		// R�cup�ration du Gc transmit
		Batiment batiment = (Batiment) getIntent().getExtras().getSerializable(Names.Generales.BATIMENT_CLICKED);
		LayoutParams params = new LayoutParams(80, 80);

		// La suite n'est execut� que si la couleur de la selection est la m�me que le joueur courrant
		if( ! (Boolean)getIntent().getExtras().getSerializable(Names.Generales.SELECTIONNALBLE)){
			return;
		}
		for(Integer i : batiment.getUnitsCreatable()){
			ImageButton unitToCreate = new ImageButton(this);
			switch(i){
			case UnitesEtBatiment.Unites.Infanterie.ID:
				if(batiment.getMaCase().getSerialized().getEquipe().equals((Couleur.bleu))){
					unitToCreate.setBackgroundResource(R.drawable.blue_inf);
				} else if(batiment.getMaCase().getSerialized().getEquipe().equals((Couleur.rouge))){
					unitToCreate.setBackgroundResource(R.drawable.red_inf);
				}
				setListenerOnButton(unitToCreate, UnitesEtBatiment.Unites.Infanterie.ID);
				break;
			case UnitesEtBatiment.Unites.Vehicule.ID:
				if(batiment.getMaCase().getSerialized().getEquipe().equals((Couleur.bleu))){
					unitToCreate.setBackgroundResource(R.drawable.blue_tank);
				} else if(batiment.getMaCase().getSerialized().getEquipe().equals((Couleur.rouge))){
					unitToCreate.setBackgroundResource(R.drawable.red_tank);
				}
				setListenerOnButton(unitToCreate, UnitesEtBatiment.Unites.Vehicule.ID);
				break;
			default:
				break;
			}
			((LinearLayout)findViewById(R.id.actionsButtons)).addView(unitToCreate, params);
		}
	}

	public void setListenerOnButton(View theButton, final Integer theCodeReturn){
		theButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setResult(theCodeReturn, getIntent());
				finish();

			}

		});
	}
}
