package com.example.view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.example.backup.Backup;
import com.example.constant.Constant;
import com.example.runrun.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class ReadyView extends BaseView {
	private String pauseGame = "暂停游戏";	// 按钮的文字
	private String startGame = "开始游戏";
	
	private String saveGame = "保存游戏";
	
	private Bitmap run1;				// 背景图片
	private Bitmap run2;				// 背景图片
	private Bitmap run3;				// 背景图片
	private Bitmap run4;				// 背景图片
	private Bitmap jumphigh;				// 背景图片,
	
	private Bitmap block;				// 背景图片
	
	private Bitmap bullet;
	private  int select=0;
	
	private volatile boolean jump1;
	private  volatile float  jump_height1;
	private volatile boolean down1=false;
	
	private volatile boolean jump2;
	private  volatile float  jump_height2;
	private volatile boolean down2=false;
	
	private float speedJump;
	
	private float speedMovex;
	private float speedMovey;
	
	private long startTime;
	private float currTime;
	
	private Map<ArrayList<Integer>,Integer> blockHolder1=new HashMap<ArrayList<Integer>,Integer>();
	private Map<ArrayList<Integer>,Integer> blockHolder2=new HashMap<ArrayList<Integer>,Integer>();
	
	private volatile boolean over=false;
	
	
	private float bulletx;
	private float bullety;
	
	
	private boolean pauseActivity;

	public ReadyView(Context context,AttributeSet attrs,boolean isRestore) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
//		Log.v("11", "222");
		thread = new Thread(this);
		threadFlag=true;
		over=false;
		
		if(isRestore)
			execRestore();
		
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
		Log.v("11", "333");
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
	// 视图销毁的方法
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}
	
	public void initBitmap() {
		// TODO Auto-generated method stub
		run1 = BitmapFactory.decodeResource(getResources(),R.drawable.run1);
		run2 = BitmapFactory.decodeResource(getResources(),R.drawable.run2);
		run3 = BitmapFactory.decodeResource(getResources(),R.drawable.run3);
		run4 = BitmapFactory.decodeResource(getResources(),R.drawable.run4);
		
		jumphigh=BitmapFactory.decodeResource(getResources(),R.drawable.jump);
		block=BitmapFactory.decodeResource(getResources(),R.drawable.block);
		
		bullet=BitmapFactory.decodeResource(getResources(),R.drawable.bullet);
		
	}
	// 释放图片资源的方法
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if (!run1.isRecycled()) {
			run1.recycle();
		}
		if (!run2.isRecycled()) {
			run2.recycle();
		}
		if (!run3.isRecycled()) {
			run3.recycle();
		}	
		if (!run4.isRecycled()) {
			run4.recycle();
		}
		if (!jumphigh.isRecycled()) {
			jumphigh.recycle();
		}
		
		if (!block.isRecycled()) {
			block.recycle();
		}
		if (!bullet.isRecycled()) {
			bullet.recycle();
		}
	}

	public void drawSelf() {
		// TODO Auto-generated method stub
		try {

			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE); 						// 绘制背景色
			canvas.drawText("Time:    "+String.valueOf(currTime+"s"), screen_width/4 , 80, paint);
			if(!pauseActivity)
			canvas.drawText(pauseGame, 50 , screen_height-50, paint);
			else 
			canvas.drawText(startGame, 50 , screen_height-50, paint);
			
			canvas.drawText(saveGame, 420 , screen_height-50, paint);
			

	if(!over&&!pauseActivity){
		select++;
		select=select%3;
		currTime=(float)(System.currentTimeMillis()-startTime)/1000;
		}
	else {
		
	}
		if(!jump1){
				jump_height1=screen_height/4;
				down1=false;

			switch (select) {
			case 0:
				canvas.drawBitmap(run1, screen_width/8, screen_height/4, paint);		// 绘制文字图片
				break;
			case 1:
				canvas.drawBitmap(run2, screen_width/8, screen_height/4, paint);		// 绘制文字图片
				break;
			case 2:
				canvas.drawBitmap(run3, screen_width/8, screen_height/4, paint);		// 绘制文字图片
				break;
			case 3:
				canvas.drawBitmap(run4, screen_width/8, screen_height/4, paint);		// 绘制文字图片
				break;
			default:
				break;
			}
		

		}
			else {
				drawJump1();
			}
			if(!jump2){
				jump_height2=screen_height/2;
				down2=false;
				switch (select) {
				case 0:
					canvas.drawBitmap(run1, screen_width/8, screen_height/2, paint);		// 绘制文字图片
					break;
				case 1:
					canvas.drawBitmap(run2, screen_width/8, screen_height/2, paint);		// 绘制文字图片
					break;
				case 2:
					canvas.drawBitmap(run3, screen_width/8, screen_height/2, paint);		// 绘制文字图片
					break;
				case 3:
					canvas.drawBitmap(run4, screen_width/8, screen_height/2, paint);		// 绘制文字图片
					break;
				default:
					break;
				}
				
			
				
			}
			else {
				drawJump2();
			}
				drawBlock();
			
	if(over) {
//			canvas = sfh.lockCanvas();
			canvas.drawBitmap(bullet, bulletx, bullety-20, paint);
//			sfh.unlockCanvasAndPost(canvas);
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}

	}


	public void drawJump1() {
		try {
			Log.v("11", "drawJump");
			
//			canvas = sfh.lockCanvas();
//			canvas.drawColor(Color.WHITE); 						// 绘制背景色
			canvas.drawBitmap(jumphigh, screen_width/8, jump_height1, paint);
			 if(!over&&!pauseActivity){
			if(down1)jump_height1=jump_height1+speedJump;
			else 
			jump_height1=jump_height1-speedJump;
			 }
			if(jump_height1>=screen_height/4){jump1=false;return;}
			if(jump_height1<=screen_height/8)down1=true;

		} catch (Exception err) {
			err.printStackTrace();
		} 
	}
	public void drawJump2() {
		try {
			canvas.drawBitmap(jumphigh, screen_width/8, jump_height2, paint);
			 if(!over&&!pauseActivity){
			if(down2)jump_height2=jump_height2+speedJump;
			else 
			jump_height2=jump_height2-speedJump;
			 }
			if(jump_height2>=screen_height/2){jump2=false;return;}
			if(jump_height2<=screen_height*3/8)down2=true;
		} catch (Exception err) {
			err.printStackTrace();
		} 
	}
	
	
	public void drawBlock(){
		Log.v("block", String.valueOf(jump_height1));
		if(!blockHolder1.isEmpty()){
			for(Map.Entry<ArrayList<Integer>, Integer> entry:blockHolder1.entrySet()){
				ArrayList<Integer> blockArrayList=entry.getKey();
				int blockBitmapWidth=entry.getValue();
				int widthBlock=blockArrayList.get(0);
				int heightBlock=blockArrayList.get(1);
				if(((blockBitmapWidth+widthBlock>=screen_width/8)&&(screen_width/8>=blockBitmapWidth))||
						((blockBitmapWidth<=screen_width/8+run1.getWidth())&&(screen_width/8+run1.getWidth()<=blockBitmapWidth+widthBlock))){
					if(jump_height1+jumphigh.getHeight()>=(screen_height/4+run1.getHeight()-heightBlock))
					{
						over=true;
						bulletx=screen_width/8+jumphigh.getWidth()/2;
						bullety=jump_height1+jumphigh.getHeight();
					}
				}
				Paint a=new Paint();
				a.setColor(Color.BLACK);
				canvas.drawRect(blockBitmapWidth, screen_height/4+run1.getHeight()-heightBlock, blockBitmapWidth+widthBlock, screen_height/4+run1.getHeight(), a);
				if(!over&&!pauseActivity)blockHolder1.put(blockArrayList, (int) (blockBitmapWidth-speedMovex));
			}
		}
		
		if(!blockHolder2.isEmpty()){
			for(Map.Entry<ArrayList<Integer>, Integer> entry:blockHolder2.entrySet()){
				ArrayList<Integer> blockArrayList=entry.getKey();
				int blockBitmapWidth=entry.getValue();
				int widthBlock=blockArrayList.get(0);
				int heightBlock=blockArrayList.get(1);
				if(((blockBitmapWidth+widthBlock>=screen_width/8)&&(screen_width/8>=blockBitmapWidth))||
						((blockBitmapWidth<=screen_width/8+run1.getWidth())&&(screen_width/8+run1.getWidth()<=blockBitmapWidth+widthBlock))){
				if(jump_height2+jumphigh.getHeight()>=(screen_height/2+jumphigh.getHeight()-heightBlock))
				{
					Log.v("block", "ok222");
					bulletx=screen_width/8+jumphigh.getWidth()/2;
					bullety=jump_height2+jumphigh.getHeight();
					over=true;
				}
			}
				Paint a=new Paint();
				a.setColor(Color.BLACK);
				canvas.drawRect(blockBitmapWidth, screen_height/2+run1.getHeight()-heightBlock, blockBitmapWidth+widthBlock, screen_height/2+run1.getHeight(), a);
				if(!over&&!pauseActivity)blockHolder2.put(blockArrayList, (int) (blockBitmapWidth-speedMovey));
				
			}
		}
		
	}
	// 线程运行的方法
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		backUp();
		speedJump=screen_height/200;
		speedMovex=screen_width/60;
		speedMovey=screen_width/40;
		startTime = System.currentTimeMillis();
		long startTime1=startTime;
		long startTime2=startTime;
		long startSpeed=startTime;
		Random random = new Random();
	while (true) {
//		if(System.currentTimeMillis()-startSpeed>random.nextInt(5000)+8000){
//			startSpeed=System.currentTimeMillis();
//			speedMovex=random.nextInt(20)+40;
//			speedMovey=random.nextInt(20)+40;
//		}
		
		if(System.currentTimeMillis()-startTime1>random.nextInt(2000)+3000){
				startTime1=System.currentTimeMillis();
			   int blockHeight=random.nextInt(100)+20;
			   int blockWidth=random.nextInt(100)+20;
			   ArrayList<Integer> tmpArrayList=new ArrayList<Integer>();
			   tmpArrayList.add(blockWidth);
			   tmpArrayList.add(blockHeight);
			   if(!over&&!pauseActivity) blockHolder1.put(tmpArrayList,(int) screen_width);
		}
		
		if(System.currentTimeMillis()-startTime2>random.nextInt(2000)+3000){
			startTime2=System.currentTimeMillis();
		    int blockHeight=random.nextInt(100)+20;
		    int blockWidth=random.nextInt(100)+20;
		    
		    ArrayList<Integer> tmpArrayList=new ArrayList<Integer>();
			tmpArrayList.add(blockWidth);
			tmpArrayList.add(blockHeight);
		    if(!over&&!pauseActivity) blockHolder2.put(tmpArrayList,(int) screen_width);
		}

			drawSelf();

			try {
				Thread.sleep(5);
				Map<ArrayList<Integer>,Integer> blockRemove1=new HashMap<ArrayList<Integer>,Integer>();
				for(Map.Entry<ArrayList<Integer>, Integer> entry:blockHolder1.entrySet()){
					
					if(entry.getValue()<0)blockRemove1.put(entry.getKey(), entry.getValue());
				}
				for(Entry<ArrayList<Integer>, Integer> entry:blockRemove1.entrySet()){
					blockHolder1.remove(entry.getKey());
				}
				Map<ArrayList<Integer>,Integer> blockRemove2=new HashMap<ArrayList<Integer>,Integer>();
				for(Map.Entry<ArrayList<Integer>, Integer> entry:blockHolder2.entrySet()){
					if(entry.getValue()<0)blockRemove2.put(entry.getKey(), entry.getValue());
				}
				for(Map.Entry<ArrayList<Integer>, Integer> entry:blockRemove2.entrySet()){
					blockHolder2.remove(entry.getKey());
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();

		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
//				&& event.getPointerCount() == 1
				) {
			float y = event.getY();
			float x = event.getX();
			if(!over){
			if((x>50&&x<370)&&(y>screen_height-130&&y<screen_height-50)){
				pauseActivity=!pauseActivity;
				return true;
			}
			
			if((x>420&&x<740)&&(y>screen_height-130&&y<screen_height-50)){
				if(backUp())Toast.makeText(mainActivity.getApplicationContext(),"保存成功" , Toast.LENGTH_SHORT).show();
				return true;
			}
			
			if(y<screen_height/2)
				jump1=true;
			else
				jump2=true;	
			}
			else {
				Message msg=new Message();
				msg.what=Constant.TO_END_VIEW;
				msg.obj=new Float(currTime);
				mainActivity.getHandler().sendMessage(msg);
			}
		}
		return true;
	}
	
	public boolean backUp() {
		Backup backup=new Backup();
		backup.currTime=this.currTime;
		
		backup.blockHolder1=new HashMap<ArrayList<Integer>,Integer>(this.blockHolder1);
		backup.blockHolder2=new HashMap<ArrayList<Integer>,Integer>(this.blockHolder2);
		backup.over=this.over;
		backup.bulletx=this.bulletx;
		backup.bullety=this.bullety;
		backup.pauseActivity=this.pauseActivity;
		backup.select=this.select;
		 
		backup.jump1=this.jump1;
		backup.jump_height1=this.jump_height1;
		backup.down1=this.down1;
		
		backup.jump2=this.jump2;
		backup.jump_height2=this.jump_height2;
		backup.down2=this.down2;
		String filePath=mainActivity.getExternalFilesDir(null).toString()+"/backup";
		File file = new File(filePath);
		if(!file.exists())
			try {
				file.createNewFile();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(backup);
			oos.flush();
			oos.close();
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean execRestore() {
		String filePath=mainActivity.getExternalFilesDir(null).toString()+"/backup";
		File file = new File(filePath);
		Backup backup = null;
		if(!file.exists())
			throw new RuntimeException("file error!");
		
        try {
        	FileInputStream fis = new FileInputStream(filePath);
        	ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				 backup = (Backup) ois.readObject();
				 Log.v("execRestore", String.valueOf(backup==null));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ois.close();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.currTime=backup.currTime;
        this.blockHolder1=new HashMap<ArrayList<Integer>,Integer>(backup.blockHolder1);
        this.blockHolder2=new HashMap<ArrayList<Integer>,Integer>(backup.blockHolder2);
        
        this.over=backup.over;
        this.bulletx=backup.bulletx;
        this.bullety=backup.bullety;
        this.pauseActivity=true;
        this.select=backup.select;
        
        this.jump1=backup.jump1;
        this.jump_height1=backup.jump_height1;
        this.down1=backup.down1;
        
        this.jump2=backup.jump2;
        this.jump_height2=backup.jump_height2;
        this.down2=backup.down2;
		return true;
	}
	

}
