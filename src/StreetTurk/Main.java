package StreetTurk;

import java.util.ArrayList;

import igeo.IVec2;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;
import processing.data.TableRow;

public class Main extends PApplet {
	/**
	 * Wenzhe Peng
	 */
	private static final long serialVersionUID = 1L;
	PGraphics pg;
	Table tableX;
	Table tableY;
	Table tableCl;
	Table tableSf;
	int num;
	Turk[] tks;
	float size1 = 0.15f;
	float size2 = 0.15f;
	int w0 = 3336;
	int h0 = 5004;
	float R = 100;
	float step = 5;
	public void setup() {
		smooth();
		background(255);
		tableX = loadTable("D:/Box Sync/ToXueba/database/turkTransfer/X.csv");
		tableY = loadTable("D:/Box Sync/ToXueba/database/turkTransfer/Y.csv");
		tableCl = loadTable("D:/Box Sync/ToXueba/database/turkTransfer/clleanliness.csv");
		tableSf = loadTable("D:/Box Sync/ToXueba/database/turkTransfer/safety.csv");
		num = tableX.getRowCount();
		tks = new Turk[num];

		for (int i = 0; i < num; i++) {
			TableRow tX = tableX.getRow(i);
			TableRow tY = tableY.getRow(i);
			TableRow tCl = tableCl.getRow(i);
			TableRow tSf = tableSf.getRow(i);
			float cl = tCl.getFloat(0);
			float sf = tSf.getFloat(0);

			IVec2 posR = new IVec2(tX.getDouble(0), tY.getDouble(0));
			IVec2 lt = new IVec2(0.132666, 14.569831);
			IVec2 br = new IVec2(15.771399, -1.336536);
			IVec2 s = new IVec2(w0 * size1, h0 * size1);

			tks[i] = new Turk(posR, lt, br, cl, sf, s);
			// println(tr.getDouble(0));
		}

		size((int) (w0 * size1), (int) (h0 * size1));

		for (int i = 0; i < this.width; i+=step*2) {
			println(i);

			for (int j = 0; j < this.height; j+=step) {

				// println(this.ClRange(tks, R, i, j));
				float c1 = PApplet.map(Cl(InRange(tks,R,i,j), i, j), 0.5f, 1f, 255, 0);
				float c2 = PApplet.map(Sf(InRange(tks,R,i,j), i, j), 0.1f, 0.45f, 255, 0);
				
				stroke(255);
				strokeWeight(0.5f);
				fill(255-c1,0,0);
				rect(i, height - j,step,step);
				println (c2);
				fill(0,0,255-c2);
				rect(i+step, height - j,step,step);

			}
		}

/*		for (int i = 0; i < num; i++) {
			// println(tks[i].toString());
			tks[i].draw(this);

		}
*/
	}

	public void draw() {

	}

	public static Turk[] InRange(Turk[] tks, float R, float x, float y) {
		
		ArrayList<Turk> l = new ArrayList<>();
		
		
		
		for (Turk t : tks) {
			if (t.disSQ(x, y) < R * R) {
				l.add(t);
			}
		}
		
		Turk[] ts = new Turk[l.size()];
		for(int i = 0;i<l.size();i++){
			ts[i] = l.get(i);
		}
		
		return ts;
	}

	public float ClRange(Turk[] tks, float R, int x, int y) {
		Turk[] l = InRange(tks, R, x, y);
		float rate = 0;
		if (l.length > 0) {
			for (Turk s : l) {
				rate += s.cl;
			}

			return rate / l.length;

		}

		else {
			return 0;
		}
	}

	public float Cl(Turk[] tks, float x, float y){
		float rate=0;
			for (Turk s : tks){
				rate+=s.cl*ratio(s,tks,x,y);
			}
			
			return rate;
	}

	public float Sf(Turk[] tks, float x, float y){
		float rate=0;
			for (Turk s : tks){
				rate+=s.sf*ratio(s,tks,x,y);
			}
			
			return rate;
	}
	
	public float ratio(Turk tk, Turk[] tks, float x, float y){
		float disSQ = tk.disSQ(x, y);
		if (disSQ <0.0001){
			return 1;
		}
		else{
			return (float) ((1f/Math.pow(disSQ,1d/3)/ALLIvert(tks, x, y)));
		}
	}

	public float ALLIvert(Turk[] tks, float x, float y) {
		float count = 0;
		for (Turk tk : tks) {
			if (tk.disSQ(x, y) < 0.0001)
				return 0;
			else {
				count += 1f / Math.pow(tk.disSQ(x, y),1d/3);
			}
		}
		return count;

	}

	public float SfRange(Turk[] tks, float R, int x, int y) {
		Turk[] l = InRange(tks, R, x, y);
		float rate = 0;
		if (l.length > 0) {
			for (Turk s : l) {
				rate += s.sf;
			}

			return rate / l.length;
		}

		else {
			return 0;
		}
	}
	public void keyReleased() {
		pg = createGraphics(w0,h0);
		pg.beginDraw();
		pg.background(255);

		for (float i = 0; i < this.width; i+=step*2*size2) {
			println(i);

			for (float j = 0; j < this.height; j+=step*size2) {

				// println(this.ClRange(tks, R, i, j));
				float c1 = PApplet.map(Cl(InRange(tks,R,i,j), i, j), 0.5f, 1f, 255, 0);
				float c2 = PApplet.map(Sf(InRange(tks,R,i,j), i, j), 0.05f, 0.35f, 255, 0);
				//float c1 = 0;
				//float c2 = 0;
				
				pg.stroke(255);
				pg.strokeWeight(0.5f);
				pg.fill(255-c1,0,0);
				pg.rect(i/size2, (height - j)/size2,step-1,step);
				println (c1);
				pg.fill(0,0,255-c2);
				pg.rect((i+step)/size2, (height - j)/size2,step,step);

			}
		}
		pg.endDraw();
		pg.save("temp2.tiff");
		  println("saved");
		} 

		
		
		
	

}
