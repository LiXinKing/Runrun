package com.example.backup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

public class Backup implements Serializable{
	public float currTime;
	public Map<ArrayList<Integer>,Integer> blockHolder1=new HashMap<ArrayList<Integer>,Integer>();
	public Map<ArrayList<Integer>,Integer> blockHolder2=new HashMap<ArrayList<Integer>,Integer>();
	public  boolean over=false;
	public float bulletx;
	public float bullety;
	public boolean pauseActivity;
	public  int select;
	
	public  boolean jump1;
	public   float  jump_height1;
	public  boolean down1=false;
	
	public  boolean jump2;
	public   float  jump_height2;
	public  boolean down2=false;
}
