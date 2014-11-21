package com.example.view;


import com.example.runrun.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	protected float screen_width;			// 视图的宽度
	protected float screen_height;			// 视图的高度
	protected   boolean threadFlag;			// 线程运行的标记
	protected Paint paint; 					// 画笔对象
	protected Canvas canvas; 				// 画布对象
	protected Thread thread; 				// 绘图线程
	
	protected float scalex;					// 背景图片的缩放比例
	protected float scaley;
	protected SurfaceHolder sfh;
	protected MainActivity mainActivity;
	
	public BaseView(Context context, AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		paint.setTextSize(80);
		sfh = this.getHolder();
		sfh.addCallback(this);
		this.mainActivity = (MainActivity) context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();		//获得视图的宽度
		screen_height = this.getHeight();	//获得视图的高度
		threadFlag = true;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	public void setThreadFlag(boolean threadFlag){
		this.threadFlag = threadFlag;
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}

	public void drawSelf() {
		// TODO Auto-generated method stub
		
	}

	public void drawSelf(int select) {
		// TODO Auto-generated method stub
		
	}

}
