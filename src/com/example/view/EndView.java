package com.example.view;

import com.example.constant.Constant;
import com.example.runrun.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class EndView extends BaseView {
	private Bitmap endBitmap;				// 背景图片
	private float score;
	private float highScore;

	public EndView(Context context,AttributeSet attrs,Object obj) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		thread = new Thread(this);
		threadFlag=true;
		score=((Float)obj).floatValue();
	}
	// 视图改变的方法
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	// 视图创建的方法
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceCreated(arg0);
		initBitmap(); 
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
		SharedPreferences mySharedPreferences=mainActivity.getSharedPreferences("scoreRunrun", Activity.MODE_PRIVATE);
		if(mySharedPreferences==null){
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putFloat("score",score );
			editor.commit();
			highScore=score;
		}
		else {
			highScore=mySharedPreferences.getFloat("score", 0);
			if(score>highScore){
				highScore=score;
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putFloat("score",score );
				editor.commit();
			}
		}
	}
	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}
	
	public void initBitmap() {
		// TODO Auto-generated method stub
		endBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.end);
		scalex = screen_width / endBitmap.getWidth();
		scaley = screen_height / endBitmap.getHeight();
	}
	// 释放图片资源的方法
	@Override
	public void release() {
		if (!endBitmap.isRecycled()) {
			endBitmap.recycle();
		}
	}
	@Override
	public void drawSelf() {
		try{
		canvas = sfh.lockCanvas();
		canvas.drawColor(Color.WHITE); 						// 绘制背景色
		canvas.save();
		canvas.scale(scalex, scaley, 0, 0);					// 计算背景图片与屏幕的比例
		canvas.drawBitmap(endBitmap,0, 0, paint); 		// 绘制背景图
		canvas.restore();
		canvas.drawText(""+score, screen_width*2/5 , screen_height/4, paint);
		canvas.drawText(""+highScore, screen_width*2/3 , (float) (screen_height*0.35), paint);
		}catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
		
		
	}
	
	// 线程运行的方法
	@Override
	public void run() {
		while(true)
		drawSelf();
		
	}
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getPointerCount() == 1) {
			float y = event.getY();
			float x = event.getX();
			Log.v("endview", ""+x);
			Log.v("endview", ""+y);
			if((x>244&&x<802)&&(y>819&&y<916)){
				mainActivity.getHandler().sendEmptyMessage(Constant.TO_READY_VIEW);
			}
			
			if((x>277&&x<791)&&(y>1105&&y<1236)){
				mainActivity.getHandler().sendEmptyMessage(Constant.END_GAME);
			}
		}
		return true;
	}

}
