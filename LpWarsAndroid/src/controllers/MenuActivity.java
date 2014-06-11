package controllers;

import com.example.lpwarsandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity
{
	/** Called when the activity is first created. */
	static int level = 1;
	static int start = 0;
	static int totalPlayer;
	Intent play = null;
	Button playB;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		playB = (Button) findViewById(R.id.play);
		System.out.println("main1");

	}

	public void clickChoice(View currentView)
	{
		switch (currentView.getId())
		{
		case R.id.play:
			play = new Intent(MenuActivity.this, MainActivity.class);
			startActivity(play);
			start=0;
			finishActivity();
			break;
					
		}

	}
	
	public void onResume()
	{
		super.onRestart();
	}
	
	public void finishActivity()
	{
		this.finish();
	}
	public void onPause()
	{
		super.onPause();
	}

}