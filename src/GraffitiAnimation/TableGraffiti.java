package GraffitiAnimation;

import GraffitiZoomIn.ZoomIn;
import processing.core.PApplet;
import processing.core.PGraphics;

public class TableGraffiti implements Runnable {
	public double lng;// 0
	public double lat;// 1

	int yearO;// the year the case opens
	int monthO;// the month the case opens
	int dayO;// the day the case opens
	public int[] YMD;
	String detail;

	public int lastTime;// the time the case lasts

	// set the remaining time of the case
	// If max value, then not opened case(will be opened in the future)
	// If negative value, then is a closed case
	int timeLeft = Integer.MAX_VALUE;
	Thread thread;

	public TableGraffiti(double lng, double lat, int yearO, int monthO,
			int dayO, int days, String detail){
		this.lng = lng;
		this.lat = lat;

		this.yearO = yearO;
		this.monthO = monthO;
		this.dayO = dayO;
		YMD = new int[3];
		YMD[0]=yearO;
		YMD[1]=monthO;
		YMD[2]=dayO;
		this.detail = detail;
		
		// this.last_time = ...
		this.lastTime = days;

	}
	
	public TableGraffiti(double lng, double lat, int yearO, int monthO,
			int dayO, int yearC, int monthC, int dayC,String detail) {

		this.lng = lng;
		this.lat = lat;

		this.yearO = yearO;
		this.monthO = monthO;
		this.dayO = dayO;
		YMD = new int[3];
		YMD[0]=yearO;
		YMD[1]=monthO;
		YMD[2]=dayO;
		this.detail = detail;
		
		// this.last_time = ...
		this.lastTime = Main.days(yearC - yearO, monthC - monthO, dayC - dayO) + 1;
	}

	public void show(PApplet app, int r, int g, int b) {
		float[] pos = Main.mmap(this);
		app.pushStyle();
		app.stroke(r, g, b);
		app.point(pos[0], pos[1]);

		app.popStyle();
	}

	public void showZoom(PApplet app, float r, float g, float b){
		float[] pos = ZoomIn.mmap(this, ZoomIn.getTG(), ZoomIn.h, ZoomIn.w);
		app.pushStyle();
		app.fill(r, g, b);
		app.stroke(0);
		app.strokeWeight(0.5f);
		app.ellipse(pos[0], pos[1], 5f,5f);

		app.popStyle();

	}
	
	public void saveZoom(PGraphics pg, float r, float g, float b, float scale){
		float[] pos = ZoomIn.mmap(this, ZoomIn.getTG(), ZoomIn.h, ZoomIn.w);
		pg.pushStyle();
		pg.fill(r, g, b);
		pg.stroke(0);
		pg.strokeWeight(0.5f*scale);
		pg.ellipse(pos[0]*scale, pos[1]*scale, 5f*scale,5f*scale);

		pg.popStyle();
	}
	
	public void show(PApplet app) {
		// draw color based on the timeLeft
		float[] pos = Main.mmap(this);
		app.pushStyle();
		app.stroke(30,30,30);
		app.strokeWeight(1.5f);
		app.point(pos[0], pos[1]);

		app.popStyle();

	}
	
	
	public void showYear(PApplet app) {
		// draw color based on the timeLeft
		float[] pos = Main.mmap(this);
		app.pushStyle();
		if(this.yearO==2008)
		app.stroke(117,0,176);
		if(this.yearO==2009)
		app.stroke(55,38,214);
		if(this.yearO==2010)
		app.stroke(113,158,248);
		if(this.yearO==2011)
		app.stroke(255,240,0);
		if(this.yearO==2012)
		app.stroke(150,199,38);
		if(this.yearO==2013)
		app.stroke(214,90,19);
		if(this.yearO==2014)
		app.stroke(255,255,255);
		
		app.strokeWeight(2f);
		app.point(pos[0], pos[1]);

		app.popStyle();

	}
	
	public void saveYear(PGraphics pg, float scale){
		float[] pos = Main.mmapScale(this, scale);
		pg.pushStyle();
		if(this.yearO==2008)
			pg.stroke(117,0,176);
		if(this.yearO==2009)
			pg.stroke(55,38,214);
		if(this.yearO==2010)
			pg.stroke(113,158,248);
		if(this.yearO==2011)
			pg.stroke(255,240,0);
		if(this.yearO==2012)
			pg.stroke(150,199,38);
		if(this.yearO==2013)
			pg.stroke(214,90,19);
		if(this.yearO==2014)
			pg.stroke(255,255,255);
		
		pg.strokeWeight(2f*scale);
		pg.point(pos[0], pos[1]);

		pg.popStyle();
		
	}

	public void showTimeLine(PApplet app, float w, float r,float g, float b) {
		float[] pos = Main.mmap(this);
		app.pushStyle();
			
			app.stroke(r,g,b);
			app.strokeWeight(w);
			app.point(pos[0], pos[1]);

		

		app.popStyle();
	}

	
	public void setTimeLeft() {
		// set the timeLeft for the case

		if (Main.days(Main.Year - yearO, Main.Month - monthO, Main.Day - dayO) > 0) {
			this.timeLeft = this.lastTime
					- Main.days(Main.Year - yearO, Main.Month - monthO,
							Main.Day - dayO);
		}

	}

	public int time(){
		return Main.days(yearO, monthO, dayO);
	}
	

	
	public int timeLasted() {
		return Main.days(Main.Year - yearO, Main.Month - monthO, Main.Day
				- dayO);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//showTimeLine(PApplet app);
	}

}
