package com.example.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.example.backup.Backup;
import com.example.constant.Constant;
import com.example.runrun.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class StartView extends BaseView{
	private Bitmap startBitmap;				// ����ͼƬ

	public StartView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		thread = new Thread(this);
	}
	// ��ͼ�ı�ķ���
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	// ��ͼ�����ķ���
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
	}
	// ��ͼ���ٵķ���
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}
	
	public void initBitmap() {
		// TODO Auto-generated method stub
		startBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.start);
		scalex = screen_width / startBitmap.getWidth();
		scaley = screen_height / startBitmap.getHeight();
	}
	// �ͷ�ͼƬ��Դ�ķ���
	@Override
	public void release() {
		if (!startBitmap.isRecycled()) {
			startBitmap.recycle();
		}
	}
	@Override
	public void drawSelf() {
		try{
		canvas = sfh.lockCanvas();
		canvas.drawColor(Color.WHITE); 						// ���Ʊ���ɫ
		canvas.save();
		canvas.scale(scalex, scaley, 0, 0);					// ���㱳��ͼƬ����Ļ�ı���
		canvas.drawBitmap( startBitmap,0, 0, paint); 		// ���Ʊ���ͼ
		canvas.restore();
		}catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	// �߳����еķ���
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
			
			Log.v("read", ""+x);
			Log.v("read", ""+y);
			if((x>290&&x<780)&&(y>737&&y<818)){
				mainActivity.getHandler().sendEmptyMessage(Constant.TO_READY_VIEW);
			}
			if((x>290&&x<780)&&(y>1014&&y<1111)){
				restoreData();
			}
			
			if((x>290&&x<780)&&(y>1315&&y<1400)){
				mainActivity.getHandler().sendEmptyMessage(Constant.END_GAME);
			}
		}
		return true;
	}
	public void restoreData() {
		String filePath=mainActivity.getExternalFilesDir(null).toString()+"/backup";
		File file = new File(filePath);
		if(!file.exists()){
			Toast.makeText(mainActivity.getApplicationContext(),"û�д浵" , Toast.LENGTH_SHORT).show();
			return;
		}
		mainActivity.getHandler().sendEmptyMessage(Constant.TO_READY_VIEW_RESTORE);
	}

}
	