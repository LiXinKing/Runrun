package com.example.runrun;
import android.view.View.*;
import com.example.view.EndView;
import com.example.view.ReadyView;
import com.example.view.StartView;
import com.example.constant.Constant;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private ReadyView readyView;
	private StartView startView;
	private EndView endView;
	
	private Button startButton;
	private Button readButton;
	private Button quitButton;
	
	
	private Handler handler = new Handler(){ 
		@Override
        public void handleMessage(Message msg){
            if(msg.what == Constant.TO_START_VIEW){
            	toStartView();
            }
            else  if(msg.what == Constant.TO_READY_VIEW){
            	toReadyView();
            }
            else  if(msg.what == Constant.TO_END_VIEW){
            	toEndView(msg.obj);
            }
            else  if(msg.what == Constant.END_GAME){
            	endGame();
            }
            else  if(msg.what == Constant.TO_READY_VIEW_RESTORE){
            	toReadyViewRestore();
            }
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		readyView = new ReadyView(this);
//		startView=new StartView(this);
//		setContentView(R.layout.start);
		startButton=(Button)findViewById(R.id.btnStart);
		readButton=(Button)findViewById(R.id.btnRead);
		quitButton=(Button)findViewById(R.id.btnQuit);
		startButton.setOnClickListener(this);
		readButton.setOnClickListener(this);
		quitButton.setOnClickListener(this);
	}
	
	public void toReadyView(){
		setContentView(R.layout.ready);
		startView = null;
		endView = null;
	}
	
	public void toReadyViewRestore(){
		if(readyView == null){
//			readyView = new ReadyView(this,true);
		}
		Log.v("toReadyView", "OK");
		setContentView(readyView);
		startView = null;
		endView = null;
	}

	public void toEndView(Object obj){
		if(endView == null){
//			endView = new EndView(this,obj);
		}
		setContentView(endView);
		startView = null;
		readyView = null;
	}
	
	public void toStartView(){
		if(startView == null){
//			startView = new StartView(this);
		}
		setContentView(startView);
		readyView = null;
		endView = null;
	}
	//Ω· ¯”Œœ∑
	public void endGame(){
		if(readyView != null){
			readyView.setThreadFlag(false);
		}
		else if(startView != null){
			startView.setThreadFlag(false);
		}
		else if(endView != null){
			endView.setThreadFlag(false);
		}
		this.finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public Handler getHandler() {
		return handler;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  switch (v.getId()) {  
          case R.id.btnStart:  
        	  
              break;  
          case R.id.btnRead:  
        	  
              break;  
          case R.id.btnQuit:  
        	  endGame();
              break; 
          default:  
              break;  
	}
	}
}
