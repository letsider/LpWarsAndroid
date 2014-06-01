package controllers;


import models.Gc;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpwarsandroid.R;

import configuration.Names;

public class ActionsActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);

        // Récupération du Gc transmit
        Gc gc = (Gc) getIntent().getExtras().getSerializable(Names.Generales.GC_CLICKED);
        
        // Affichage des données dynamiques transmises
        TextView currentText = (TextView) findViewById(R.id.pv);
        currentText.setText(currentText.getText().toString() + gc.getPv());
        currentText = (TextView) findViewById(R.id.pa);
        currentText.setText(currentText.getText().toString() + gc.getPa());
        currentText = (TextView) findViewById(R.id.pm);
        currentText.setText(currentText.getText().toString() + gc.getPm());

        Button getBackR = (Button) findViewById(R.id.getBack);
        getBackR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setResult(Activity.RESULT_CANCELED);
				finish();

			}

		});
        
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
        	((LinearLayout)getBackR.getParent()).removeView(getBackV);
        }
        
    }
	
}
