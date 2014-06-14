package controllers;

import java.util.List;

import com.example.lpwarsandroid.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity
{
	/** Called when the activity is first created. */
	static int start = 0;
	static int totalPlayer;
	Intent play = null;
	Intent aboutIt=null;
	
	Button playB;
	MediaPlayer mp;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		playB = (Button) findViewById(R.id.play);
		mp = MediaPlayer.create(MenuActivity.this, R.raw.menu);
		mp.setLooping(true);
		mp.start();
		System.out.println("main1");

	}

	public void clickChoice(View currentView)
	{
		
		switch (currentView.getId()){
		 case R.id.play:
			play = new Intent(MenuActivity.this, MainActivity.class);
			startActivity(play);
			start=0;
			finishActivity();
			break;
		case R.id.aboutit:
			aboutIt = new Intent(MenuActivity.this,AboutIt.class);
			startActivity(aboutIt);
			finishActivity();
			break;
		}

	}
	
	public void onResume()
	{
		super.onResume();
		if(!mp.isPlaying()){
			mp = MediaPlayer.create(MenuActivity.this, R.raw.menu);
			mp.setLooping(true);
			mp.start();
		}
        

	}
	
	public void finishActivity()
	{
		mp.stop();
		this.finish();
	}
	protected void onPause() {
	    if (this.isFinishing()){ //basically BACK was pressed from this activity
	      mp.stop();
	    }
	    Context context = getApplicationContext();
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
	    if (!taskInfo.isEmpty()) {
	      ComponentName topActivity = taskInfo.get(0).topActivity; 
	      if (!topActivity.getPackageName().equals(context.getPackageName())) {
	        mp.stop();
	      }
	    }
	    super.onPause();
	  }
	

}
	

