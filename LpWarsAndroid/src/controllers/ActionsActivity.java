package controllers;


import models.Gc;
import models.Batiment;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpwarsandroid.R;

import configuration.Names;

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
		
		// Remise du bouton Retour en dernière position
		Button getBackR = (Button) findViewById(R.id.getBack);
		((LinearLayout)findViewById(R.id.returnButtons)).removeView(
				findViewById(R.id.getBack));
		getBackR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setResult(Activity.RESULT_CANCELED);
				finish();

			}
		});
		((LinearLayout)findViewById(R.id.returnButtons)).addView(getBackR);

	}

	public void initGcView(){

		// Récupération du Gc transmit
		Gc gc = (Gc) getIntent().getExtras().getSerializable(Names.Generales.GC_CLICKED);

		// Affichage des données dynamiques transmises
		TextView currentText = (TextView) findViewById(R.id.pv);
		currentText.setText(currentText.getText().toString() + gc.getPv());
		currentText = (TextView) findViewById(R.id.pa);
		currentText.setText(currentText.getText().toString() + gc.getPa());
		currentText = (TextView) findViewById(R.id.pm);
		currentText.setText(currentText.getText().toString() + gc.getPm());

		Button getBackV = (Button) findViewById(R.id.select);
		if((Boolean) getIntent().getExtras().getSerializable(Names.Generales.SELECTIONNALBLE)){
			getBackV.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					setResult(Activity.RESULT_OK, getIntent());
					finish();

				}

			});
		} else {
			((LinearLayout)findViewById(R.id.returnButtons)).removeView(getBackV);
		}
	}

	public void initBatimentView(){

		// Suppression des éléments utiles aux Gc
		((LinearLayout)findViewById(R.id.hightest)).removeView(
				findViewById(R.id.pa));
		((LinearLayout)findViewById(R.id.hightest)).removeView(
				findViewById(R.id.pm));
		((LinearLayout)findViewById(R.id.returnButtons)).removeView(
				findViewById(R.id.select));

		// Récupération du Gc transmit
		Batiment batiment = (Batiment) getIntent().getExtras().getSerializable(Names.Generales.BATIMENT_CLICKED);
		
		TextView currentText = (TextView) findViewById(R.id.pv);
		currentText.setText(currentText.getText().toString() + batiment.getPv());

		for(Integer i : batiment.getUnitsCreatable()){
			switch(i){
			default:
				break;
			}
			ImageButton unitToCreate = new ImageButton(this);
			unitToCreate.setBackgroundResource(R.drawable.blue_inf);
			((LinearLayout)findViewById(R.id.actionsButtons)).addView(unitToCreate);
		}
	}
}
