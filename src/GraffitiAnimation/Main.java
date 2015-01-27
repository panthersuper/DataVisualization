package GraffitiAnimation;

import javax.swing.Spring;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;
import controlP5.*;

public class Main extends PApplet {
	/**
	 * Wenzhe Peng
	 * 
	 * Sep 2014
	 * 
	 * pwz@berkeley.edu
	 */
	private static final long serialVersionUID = 1L;
	Table table;
	public static TableGraffiti[] TG;
	static int h = 1000;
	static int w = 1300;
	static int w2 = 500;
	public static int Year = 2014;// the current year to show
	public static int Month = 12;
	public static int Day = 30;
	public boolean timeline = true;// set whether to show the timeline or not
	public boolean All = false;
	public boolean dra = true;
	public int speed = 10;// speed of day in the timeline
	// public static int days = 0;//
	public static int cases = 0;//
	PGraphics pg;
	float size = 20;// size to out put to pgraphics

	public int wn = 40, hn = 15;
	public block[][] blocks;// matrixed blocks in the field
	public block curBlo;// current block
	public block speBlo;//specified block to output
	
	int max = 0;

	
	ControlP5 cp5;
	PImage photo;

	public void setup() {
		size(w + w2, h);
		smooth();
		photo = loadImage("D:/box_sync/Box Sync/ToXueba/20140921/jpg/coastline2.png");

		// initiate blocks
		speBlo = new block(new float[] {0,0},new float[] {w,h});
		//speBlo = new block(mmapNum(new float[] {37.763702f,-122.421249f}),mmapNum(new float[] {37.750918f,-122.409638f}));
		println(mmapNum(new float[] {37.763702f,-122.421249f}));
		println(mmapNum(new float[] {37.750918f,-122.409638f}));

		
		
		speBlo.IDh = Integer.MAX_VALUE;
		speBlo.IDw = Integer.MAX_VALUE;
		
		blocks = new block[wn][hn];

		for (int i = 0; i < wn; i++) {
			for (int j = 0; j < hn; j++) {
				float[] leftTop = new float[] { (float) w / wn * i,
						(float) h / hn * j };
				float[] rightBottom = new float[] { (float) w / wn * (i + 1),
						(float) h / hn * (j + 1) };

				blocks[i][j] = new block(leftTop, rightBottom);
				blocks[i][j].IDh = j;
				blocks[i][j].IDw = i;
			}
		}

		// Add buttons
		cp5 = new ControlP5(this);
		cp5.addButton("Y2008").setValue(0).setPosition(10, 10).setSize(99, 19);
		cp5.addButton("Y2009").setValue(0).setPosition(110, 10).setSize(99, 19);
		cp5.addButton("Y2010").setValue(0).setPosition(210, 10).setSize(99, 19);
		cp5.addButton("Y2011").setValue(0).setPosition(310, 10).setSize(99, 19);
		cp5.addButton("Y2012").setValue(0).setPosition(410, 10).setSize(99, 19);
		cp5.addButton("Y2013").setValue(0).setPosition(510, 10).setSize(99, 19);
		cp5.addButton("Y2014").setValue(0).setPosition(610, 10).setSize(99, 19);
		cp5.addButton("None").setValue(0).setPosition(10, 30).setSize(99, 19);

		cp5.addButton("All").setValue(0).setPosition(110, 30).setSize(99, 19);
		cp5.addButton("TimeLine").setValue(0).setPosition(1000, 10)
				.setSize(99, 19);
		cp5.addSlider("Speed").setPosition(750, 10).setRange(0, 500)
				.setSize(199, 19);

		// text();

		// Load Table
		table = loadTable("D:/box_sync/Box Sync/ToXueba/database/Case from 311/Graffitti from 311 new.csv");
		TG = new TableGraffiti[table.getRowCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			TableRow tr = table.getRow(i);

			String detail = tr.getString(5);
			// monthO
			int mO = Integer.parseInt(tr.getString(1).split("/")[0]);
			// dayO
			int dO = Integer.parseInt(tr.getString(1).split("/")[1]);
			// yearO
			int yO = Integer
					.parseInt(tr.getString(1).split("/")[2].split(" ")[0]);
			int mC = Integer.MAX_VALUE, dC = Integer.MAX_VALUE, yC = Integer.MAX_VALUE;

			if (tr.getString(2).length() > 0) {
				// monthC
				mC = Integer.parseInt(tr.getString(2).split("/")[0]);
				// dayC
				dC = Integer.parseInt(tr.getString(2).split("/")[1]);
				// yearC
				yC = Integer.parseInt(tr.getString(2).split("/")[2].split(" ")[0]);

			}
			String[] l = tr.getString(6)
					.substring(1, tr.getString(6).length() - 1).split(", ");
			double lng = Double.parseDouble(l[0]);
			double lat = Double.parseDouble(l[1]);

			// println(lng+","+lat);

			TG[i] = new TableGraffiti(lng, lat, yO, mO, dO, yC, mC, dC, detail);

		}

	}

	public void draw() {

		if (!timeline) {// time line is off, showing the frame of a certain year
			background(0);
			image(photo, 0, 0);

			for (TableGraffiti tg : TG) {
				// tg.setTimeLeft();

				if (All) {
					tg.show(this);

				}
			}
			for (TableGraffiti tg : TG) {
				// tg.setTimeLeft();

				if (dra) {
					if (tg.yearO == Year) {
						tg.showYear(this);

					}
				}

			}
			for (block[] bs : blocks) {
				for (block b : bs) {
					b.show(this, 80,80,80);
				}
			}
		} else {// time line is on

			if (cases == 0) {
				background(0);
				

				for (block[] bs : blocks) {
					for (block b : bs) {
						b.reset();
						speBlo.reset();
						
					}
				}
			}
			image(photo, 0, 0);
			blockFlow();
			worldFlow();

			if (cases + speed * 2 < TG.length) {

				speed = (int) cp5.getController("Speed").getValue();
				cases += speed;

				pushStyle();
				fill(0, 5);
				rect(0, 0, Main.w, Main.h);
				popStyle();
				if (cases - speed > 0) {
					for (int i = cases - speed; i < cases; i++) {
						TG[i].showTimeLine(this, 11f, 0, 0, 0);
						TG[i].showTimeLine(this, 3, 255, 255, 255);
					}

				}

				for (int i = cases; i < cases + speed; i++) {
					TG[i].showTimeLine(this, 10f, 200, 100, 0);

					// update blocks
					if (speBlo.inBlock(TG[i])) {
						speBlo.GraffitiInBlock.add(TG[i]);
						speBlo.number++;
						speBlo.YMDs.add(TG[i].YMD);
					}
					
					for (block[] bs : blocks) {
						for (block b : bs) {
							if (b.inBlock(TG[i])) {
								b.GraffitiInBlock.add(TG[i]);
								b.number++;
								b.YMDs.add(TG[i].YMD);
							}
						}
					}
				}

				// override lastloop
			}
			pushStyle();
			fill(0);
			rect(30, 780, 110, 23);
			popStyle();
			for (block[] bs : blocks) {
				for (block b : bs) {
					b.show(this, 20,20,20);
				}
			}

		}

		pushStyle();
		strokeWeight(0.5f);
		text("San francisco Graffiti Visualization", 1095 + w2, 20);
		text("Wenzhe Peng, Dairan Xu  Studio One, CED", 1048 + w2, 40);
		text(mouseX+","+mouseY, 1048 + w2, h-40);

		popStyle();

		// setCurrent blocks
		float xx = mouseX;
		if (xx > w)
			xx = w - 2;
		float yy = mouseY;
		if (yy >= h)
			yy = h - 2;

		int n = (int) Math.floor((float) xx / ((float) w / wn));
		int m = (int) Math.floor((float) yy / ((float) h / hn));
		this.curBlo = blocks[n][m];
		
		curBlo.show(this, 255, 0, 0);

		// set each block current condition
		if (cases + speed * 2 < TG.length){
			for (block[] bs : blocks) {
				for (block b : bs) {
					b.numList.add(b.number);
					b.number = 0;
				}
				
			}
			speBlo.numList.add(speBlo.number);
			speBlo.number = 0;
			}
		/*else{
			cases=0;
		}*/

		// println(blocks[10][5].numList);

		// draw blocks

		// curBlo.drawChart(app, n, m, pos);

	}

	public static int days(int y, int m, int d) {
		// reletive y,m,d
		// return day number;

		int day = 0;
		day += y * 365;
		day += m * 30;
		day += d;
		return day;
	}

	public static int[] YMD(int dd) {
		int[] date = new int[3];
		date[0] = (int) Math.floor((double) dd / 365);
		date[1] = (int) Math.floor((double) (dd - date[0] * 365) / 30);
		date[2] = dd - date[0] * 365 - date[1] * 30;
		return date;
		// return
	}

	public static float[] mmap(TableGraffiti t) {// map the position to

		float[] dd = new float[2];
		dd[1] = map((float) t.lng, 37.7034f, 37.8141f, h - 10, 10);
		dd[0] = map((float) t.lat, -122.5237f, -122.3527f, 10, w - 10);
		return dd;
	}

	public static float[] mmapNum(float[] t) {// map the position to

		float[] dd = new float[2];
		dd[1] = map((float) t[0], 37.7034f, 37.8141f, h - 10, 10);
		dd[0] = map((float) t[1], -122.5237f, -122.3527f, 10, w - 10);
		return dd;
	}
	public static float[] mmapScale(TableGraffiti t, float scale) {
		float[] dd = new float[2];
		dd[1] = map((float) t.lng, 37.7034f, 37.8141f, (h - 10) * scale,
				10 * scale);
		dd[0] = map((float) t.lat, -122.5237f, -122.3527f, 10 * scale, (w - 10)
				* scale);
		return dd;
	}

	public void None(int theValue) {
		if (dra == true) {
			dra = false;
		} else {
			dra = true;
		}
	}

	public void Y2008(int theValue) {
		Year = 2008;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2009(int theValue) {
		Year = 2009;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2010(int theValue) {
		Year = 2010;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2011(int theValue) {
		Year = 2011;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2012(int theValue) {
		Year = 2012;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2013(int theValue) {
		Year = 2013;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void Y2014(int theValue) {
		Year = 2014;
		Month = 12;
		Day = 30;
		dra = true;
	}

	public void All(int theValue) {

		if (All == true) {
			All = false;
		} else {
			All = true;
		}

	}

	public void TimeLine(int theValue) {
		if (timeline == true) {
			timeline = false;
			cases = 0;
		} else {
			timeline = true;
		}

	}

	public void blockFlow() {
		// creat a chart about a certain data time flow
		pushStyle();
		fill(0);
		rect(w, 0, w2, h);

		this.curBlo.drawChart(this, w2, 150, new float[] { w, 200 }, speed);

		popStyle();
	}

	public void worldFlow() {
		//drawWorldTime(this, w2, 150, new float[] { w, 600 }, cases);
	}
	
	public static int sumDate(int date) {
		// return the number of graffitis on the certain date for

		int a = 0;
		int b = 0;
		for (int i=0;i<TG.length;i++){
			if(i>0){

			if (TG[i-1].time() != date&&TG[i].time() == date) {
				a = i;
			}
			if (TG[i-1].time() == date&&TG[i].time() != date) {
				b = i;
				return b-a;

			}
			}

		}
		return 0;
	}

	
	public void saveWorldTime(PGraphics pg, int w, int h, int day, float scale,int step){
		pg.pushStyle();
		pg.fill(0);
		pg.stroke(255);
		pg.rect(40*scale, 1*scale, (w - 2)*scale, h*scale);

/*		// draw average line;
		app.strokeWeight(0.5f);
		app.stroke(255, 0, 0);
		app.line(pos[0] + 40, PApplet.map(averageSum(step), 0, max, h - 1, 1)
				+ pos[1], pos[0] + w,
				PApplet.map(averageSum(step), 0, max, h - 1, 1) + pos[1]);
*/
		// draw grid
		pg.strokeWeight(0.1f*4);
		pg.stroke(50);
		int start_date = Main.TG[0].time();
		int end_date = Main.TG[day].time();

		for (int i = start_date; i < end_date; i += step) {
			// int time = Main.TG[i].time();

			float x = PApplet.map(i, start_date, end_date, 40, w);


			pg.line(x*scale, 1*scale, x*scale, (h - 1)*scale);
			println("drawing1");

		}

		// draw flow
		pg.strokeWeight(0.5f*4);
		pg.stroke(0, 255, 0);
		pg.noFill();
		pg.beginShape();
		//println("------------");

		for (int i = start_date; i < end_date; i += step) {

			int sum = sumDate(i);
			//println(sum);
			float x = PApplet.map(i, start_date, end_date, 40, w);
			if (max < sum) {
				max = sum;
			}
			float y = PApplet.map(sum, 0, max, h - 1, 1);

			// PApplet.println(max);
			//println(y);
			pg.vertex(x*scale, y*scale);
			println("drawing2");

		}
		pg.endShape();
		pg.fill(255);
		//pg.text("0", 20, h + 25);
		//pg.text(max, 20, 15);
		PApplet.print(max);
		pg.popStyle();
	}
	
	public void drawWorldTime(PApplet app, int w, int h, float[] pos, int day) {
		app.pushStyle();
		app.fill(0);
		app.stroke(255);
		app.rect(pos[0] + 40, pos[1] + 1, w - 2, h);

/*		// draw average line;
		app.strokeWeight(0.5f);
		app.stroke(255, 0, 0);
		app.line(pos[0] + 40, PApplet.map(averageSum(step), 0, max, h - 1, 1)
				+ pos[1], pos[0] + w,
				PApplet.map(averageSum(step), 0, max, h - 1, 1) + pos[1]);
*/
		// draw grid
		app.strokeWeight(0.1f);
		app.stroke(50);
		int start_date = Main.TG[0].time();
		int end_date = Main.TG[day].time();

		for (int i = start_date; i < end_date; i += 1) {
			// int time = Main.TG[i].time();

			float x = PApplet.map(i, start_date, end_date, 40, w) + pos[0];


			app.line(x, pos[1] + 1, x, pos[1] + h - 1);
		}

		// draw flow
		app.strokeWeight(0.5f);
		app.stroke(0, 255, 0);
		app.noFill();
		app.beginShape();
		//println("------------");

		for (int i = start_date; i < end_date; i += 1) {

			int sum = sumDate(i);
			//println(sum);
			float x = PApplet.map(i, start_date, end_date, 40, w) + pos[0];
			if (max < sum) {
				max = sum;
			}
			float y = PApplet.map(sum, 0, max, h - 1, 1) + pos[1];

			// PApplet.println(max);
			//println(y);
			app.vertex(x, y);
		}
		app.endShape();
		app.fill(255);
		app.text("0", pos[0] + 20, pos[1] + h + 25);
		app.text(max, pos[0] + 20, pos[1] + 15);

		app.popStyle();
	}
	
	
	

	public void keyReleased() {
		// save the year file
		if(key == 'y' || key == 'Y'){
		pg = createGraphics((int) (w * size), (int) (h * size));

		pg.pushMatrix();
		pg.pushStyle();

		pg.beginDraw();
/*		photo.resize((int) (w * size), (int) (h * size));
		pg.image(photo, 0, 0);
		photo.resize((int) (w), (int) (h));
*/
		int y = Integer.MAX_VALUE;
		for (TableGraffiti tg : TG) {
			if (tg.yearO == Year) {
				y = tg.yearO;
				tg.saveYear(pg, size);

			}
		}
		
		for (block[] bs : blocks) {
			for (block b : bs) {
				b.saveShow(pg, size);
			}
		}
		
		pg.endDraw();
		pg.popStyle();
		pg.popMatrix();
		pg.save(y + ".tiff");
		println("saved");
		
		}
		
		
		//save speBlock
		this.speBlo.exportTable();
/*		pg = createGraphics((int)(w2*size), (int)(150*size));
		pg.beginDraw();

		this.speBlo.saveChart(pg, w2, 150, speed,size);
		pg.endDraw();

		pg.save("block_" + this.speBlo.IDw + "_" + this.speBlo.IDh
				+this.speBlo.max+ ".tiff");
*/		
		
		//this.curBlo.exportTable();
		println("saved");

		
		//save worldTime
/*		pg = createGraphics((int)(w2*size), (int)(150*size));
		pg.beginDraw();

		saveWorldTime(pg, w2, 150,cases, size,10);
		pg.endDraw();
		pg.save("worldTime.tiff");
		println("saved");
*/
	}

	public void mouseReleased() {
		  if (mouseButton == RIGHT) {

				pg = createGraphics((int)(w2*size), (int)(150*size));
				pg.beginDraw();

				this.curBlo.saveChart(pg, w2, 150, speed,size);
				pg.endDraw();

				pg.save("block_" + this.curBlo.IDw + "_" + this.curBlo.IDh
						+this.curBlo.max+ ".tiff");
				
				
				this.curBlo.exportTable();
				
				println("saved");
		}
	}
}
