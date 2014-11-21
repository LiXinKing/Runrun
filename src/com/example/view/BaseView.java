package com.example.view;


import com.example.runrun.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	protected float screen_width;			// ��ͼ�Ŀ��
	protected float screen_height;			// ��ͼ�ĸ߶�
	protected   boolean threadFlag;			// �߳����еı��
	protected Paint paint; 					// ���ʶ���
	protected Canvas canvas; 				// ��������
	protected Thread thread; 				// ��ͼ�߳�
	
	protected float scalex;					// ����ͼƬ�����ű���
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
		screen_width = this.getWidth();		//�����ͼ�Ŀ��
		screen_height = this.getHeight();	//�����ͼ�ĸ߶�
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
