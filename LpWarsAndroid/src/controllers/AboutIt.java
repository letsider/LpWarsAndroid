package controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lpwarsandroid.R;

public class AboutIt extends Activity
{
	Intent main;
	TextView version,developer;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutit);
		version = (TextView) findViewById(R.id.version);
		developer = (TextView) findViewById(R.id.developers);
		version.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "Version 1.0",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		developer.setOnClickListener(new View.OnClickListener()
		{

			public void onClick(View v)
			{
				Toast.makeText(getApplicationContext(), "Benjamin Carrière, Jonathan Siffray and Cédric Outreville",
						Toast.LENGTH_SHORT).show();
			}
		});
		

	}

	public void onPause()
	{
		super.onPause();
	}

	public void onBackPressed()
	{
		main = new Intent(this, MenuActivity.class);
		startActivity(main);
		this.finish();
	}

}
