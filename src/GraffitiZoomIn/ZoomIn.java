package GraffitiZoomIn;

import GraffitiAnimation.TableGraffiti;
import GraffitiAnimation.block;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;
import processing.data.TableRow;

public class ZoomIn extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table;
	PGraphics pg;
	private static TableGraffiti[] TG;
	public static int h = 900;
	public static int w = 900;
	float scale = 3;// save scale
	String[] keywords = new String[] { "Building_commercial", "Building_other",
			"Mail_box", "Signal_box", "Pole", "Building_residential",
			"Parking_meter", "Fire_hydrant", "Sidewalk_structure", "Pay_phone",
			"City_receptacle", "Sidewalk_in_front_of_property", "News_rack",
			"Street", "Fire_call_box", };

	public void setup() {
		smooth();
		size(w, h);
		//table = loadTable("D:/workspace01/DataVisualization/bin/data/block_24_7.csv");
		//table = loadTable("D:/workspace01/DataVisualization/bin/data/block_21_12.csv");
		table = loadTable("D:/workspace01/DataVisualization/bin/data/block_17_6.csv");

		setTG(new TableGraffiti[table.getRowCount() - 1]);
		for (int i = 1; i < table.getRowCount(); i++) {
			TableRow tr = table.getRow(i);

			// monthO
			int MO = Integer.parseInt(tr.getString(4));
			// dayO
			int DO = Integer.parseInt(tr.getString(5));
			// yearO
			int YO = Integer.parseInt(tr.getString(3));

			double lng = tr.getDouble(2);
			double lat = tr.getDouble(1);

			int time = tr.getInt(6);
			String detail = tr.getString(7);

			// println(lng+","+lat);

			getTG()[i - 1] = new TableGraffiti(lng, lat, YO, MO, DO, time,
					detail);
			getTG()[i - 1].setTimeLeft();

		}

		println("WOSHINIBABA".toLowerCase().contains("nifbaba"));
	}

	public void draw() {
		background(255);
		for (TableGraffiti tg : getTG()) {
			int tl = tg.lastTime;
			if (tl > 0) {
				// println(tl);
				if (tl > 30)
					tl = 30;
				float red = PApplet.map(tl, 0, 30, 0, 255);

				tg.showZoom(this, 255, 255 - red, 255 - red);
			}

		}

		// println(sumDate(days(2008, 10, 7)));
	}

	public static float[] mmap(TableGraffiti t) {

		return null;
	}

	public static float[] mmap(TableGraffiti t, TableGraffiti[] ts, int h, int w) {// map
																					// the
																					// position
																					// to

		float[] min = new float[2];// store the AABB of ts
		float[] max = new float[2];// store the AABB of ts
		min[0] = Float.MAX_VALUE;
		min[1] = Float.MAX_VALUE;
		max[0] = -Float.MAX_VALUE;
		max[1] = -Float.MAX_VALUE;

		for (TableGraffiti tt : ts) {
			if (min[0] > tt.lat)
				min[0] = (float) tt.lat;
			if (min[1] > tt.lng)
				min[1] = (float) tt.lng;
			if (max[0] < tt.lat)
				max[0] = (float) tt.lat;
			if (max[1] < tt.lng)
				max[1] = (float) tt.lng;
		}

		float[] dd = new float[2];
		dd[1] = PApplet.map((float) t.lng, min[1], max[1], (max[1] - min[1])
				* ((int) (h - 20) / (max[1] - min[1])), 10);
		dd[0] = PApplet.map((float) t.lat, min[0], max[0], 10,
				(max[0] - min[0]) * ((int) (h - 20) / (max[1] - min[1])));
		println("min"+min[0]+","+min[1]);
		println("max"+max[0]+","+max[1]);

		
		return dd;
	}

	public static int sumDate() {
		return h;

	}

	public static int sumDate(int date) {
		// return the number of graffitis on the certain date for

		int a = 0;
		int b = 0;
		for (int i = 0; i < TG.length; i++) {
			if (i > 0) {

				if (TG[i - 1].time() != date && TG[i].time() == date) {
					a = i;
				}
				if (TG[i - 1].time() == date && TG[i].time() != date) {
					b = i;
					return b - a;

				}
			}

		}
		return 0;
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

	public static TableGraffiti[] getTG() {
		return TG;
	}

	public static void setTG(TableGraffiti[] tG) {
		TG = tG;
	}

	public void keyReleased() {
		// save the year file
		pg = createGraphics((int) (w * scale), (int) (h * scale));
		pg.background(255);

		pg.beginDraw();
		pg.pushMatrix();
		pg.pushStyle();

		for (TableGraffiti tg : TG) {
			int tl = tg.lastTime;
			if (tl > 0) {
				// println(tl);
				if (tl > 30)
					tl = 30;
				float red = PApplet.map(tl, 0, 30, 0, 255);

				tg.saveZoom(pg, 255, 255 - red, 255 - red, scale);
				;

			}

		}
		pg.popStyle();
		pg.popMatrix();
		pg.endDraw();

		pg.save("zoomin" + ".tiff");
		println("saved");

	}
}
