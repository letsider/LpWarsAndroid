package controllers;


import models.Gc;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.lpwarsandroid.R;

public class ActionsActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions);

        // Récupération du Gc transmit
        Gc gc = (Gc) getIntent().getExtras().getParcelable("gcClicked");
        
        // Affichage des données dynamiques transmises
        TextView currentText = (TextView) findViewById(R.id.pv);
        currentText.setText(currentText.getText().toString() + gc.getPv());
        currentText = (TextView) findViewById(R.id.pa);
        currentText.setText(currentText.getText().toString() + gc.getPa());
        currentText = (TextView) findViewById(R.id.pm);
        currentText.setText(currentText.getText().toString() + gc.getPm());

        Button getBack = (Button) findViewById(R.id.getBack);
        getBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();

			}

		});
        
    }
	
}
