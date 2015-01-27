package GraffitiAnimation;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;
import processing.data.TableRow;

public class block extends PApplet{

	float[] leftTop;
	float[] rightBottom;
	int number = 0;// number of total item to count
	float incre;// increase speed of number
	ArrayList<Integer> numList = new ArrayList<>();// store the number history
	ArrayList<int[]> YMDs = new ArrayList<>();// store the dates
	ArrayList<TableGraffiti> GraffitiInBlock = new ArrayList<>();
	int max = 0;
	int IDw;
	int IDh;

	public block(float[] leftTop, float[] rightBottom) {
		this.leftTop = leftTop;
		this.rightBottom = rightBottom;
	}

	public void countPlus() {
		this.number++;
	}

	public void reset() {
		this.number = 0;
		numList.clear();
		YMDs.clear();
	}

	public boolean inBlock(float[] pt) {
		// judge whether a point is inside of block
		if (pt[0] <= rightBottom[0] && pt[0] >= leftTop[0]) {
			if (pt[1] <= rightBottom[1] && pt[1] >= leftTop[1]) {
				return true;
			}
		}
		return false;
	}

	public boolean inBlock(TableGraffiti gt) {
		return inBlock(Main.mmap(gt));

	}

	public void show(PApplet app, int red, int green, int blue) {
		app.pushStyle();
		app.noFill();
		app.strokeWeight(0.1f);
		app.stroke(red, green, blue);
		app.rect(leftTop[0], leftTop[1], rightBottom[0] - leftTop[0],
				rightBottom[1] - leftTop[1]);

		app.popStyle();
	}

	public void saveShow(PGraphics pg, float size) {
		pg.pushStyle();
		pg.noFill();
		pg.strokeWeight(0.1f);
		pg.stroke(80);
		pg.rect(leftTop[0] * size, leftTop[1] * size,
				(rightBottom[0] - leftTop[0]) * size,
				(rightBottom[1] - leftTop[1]) * size);

		pg.popStyle();

	}

	public void drawChart(PApplet app, int w, int h, float[] pos, int step) {
		app.pushStyle();
		app.fill(0);
		app.stroke(255);
		app.rect(pos[0] + 40, pos[1] + 1, w - 2, h);

		// draw average line;
		app.strokeWeight(0.5f);
		app.stroke(255, 0, 0);
		app.line(pos[0] + 40, PApplet.map(averageSum(step), 0, max, h - 1, 1)
				+ pos[1], pos[0] + w,
				PApplet.map(averageSum(step), 0, max, h - 1, 1) + pos[1]);

		// draw grid
		app.strokeWeight(0.1f);
		app.stroke(50);
		for (int i = 0; i < numList.size(); i += step) {
			float x = PApplet.map(i, 0, numList.size(), 40, w) + pos[0];
			app.line(x, pos[1] + 1, x, pos[1] + h - 1);

		}

		// draw flow
		app.beginShape();
		app.strokeWeight(0.5f);
		app.stroke(0, 255, 0);
		app.noFill();

		for (int i = 0; i < numList.size(); i += step) {
			int sum = sumNum(i, i + step, step);

			float x = PApplet.map(i, 0, numList.size(), 40, w) + pos[0];
			if (max < sum) {
				max = sum;
			}
			float y = PApplet.map(sum, 0, max, h - 1, 1) + pos[1];

			//PApplet.println(max);

			app.vertex(x, y);
		}
		app.endShape();
		app.fill(255);
		app.text("0", pos[0] + 20, pos[1] + h + 25);
		app.text(max, pos[0] + 20, pos[1] + 15);
		app.text(Main.TG[Main.cases].monthO + "/" + Main.TG[Main.cases].dayO
				+ "/" + Main.TG[Main.cases].yearO, pos[0] - 80 + w, pos[1] + h
				+ 25);
		app.text("Block[" + this.IDw + "][" + this.IDh + "]", pos[0] - 80 + w,
				pos[1] + h + 45);

		app.popStyle();
	}

	public void saveChart(PGraphics pg, float w, float h, int step, float size) {
		pg.pushStyle();
		pg.fill(0);
		pg.stroke(255);
		pg.rect(40 * size, 1, w * size - 2, h * size - 2);

		// draw average line;
		pg.strokeWeight(0.5f);
		pg.stroke(255, 0, 0);
		pg.line(40 * size,
				PApplet.map(averageSum(step), 0, max, h * size - 1, 1), w
						* size,
				PApplet.map(averageSum(step), 0, max, h * size - 1, 1));

		// draw grid
		pg.strokeWeight(0.1f);
		pg.stroke(50);
		for (int i = 0; i < numList.size(); i += step) {
			float x = PApplet.map(i, 0, numList.size(), 40 * size, w * size);
			pg.line(x, 1, x, h * size - 1);

		}

		// draw flow
		pg.beginShape();
		pg.strokeWeight(0.5f);
		pg.stroke(0, 255, 0);
		pg.noFill();

		for (int i = 0; i < numList.size(); i += step) {
			int sum = sumNum(i, i + step, step);

			float x = PApplet.map(i, 0, numList.size(), 40 * size, w * size);
			if (max < sum) {
				max = sum;
			}
			float y = PApplet.map(sum, 0, max, h * size - 1, 1);

			//PApplet.println(max);

			pg.vertex(x, y);
		}
		pg.endShape();
		pg.fill(255);

		pg.popStyle();

	}



	public void exportTable() {
		// export the graffiti cases that are only within this
		Table table = new Table();
		table.addColumn("id");
		table.addColumn("lat");
		table.addColumn("lng");
		table.addColumn("YO");
		table.addColumn("MO");
		table.addColumn("DO");
		table.addColumn("TIME");
		table.addColumn("DETAIL");


		for (TableGraffiti tg : this.GraffitiInBlock) {
			TableRow newRow = table.addRow();
			newRow.setInt("id", table.lastRowIndex());

			newRow.setDouble("lat", tg.lat);
			newRow.setDouble("lng", tg.lng);

			newRow.setInt("YO", tg.yearO);
			newRow.setInt("MO", tg.monthO);
			newRow.setInt("DO", tg.dayO);
			
			newRow.setInt("TIME", tg.lastTime);
			newRow.setString("DETAIL", tg.detail);
			
			println("updating:"+table.lastRowIndex());

		}
		  saveTable(table, "data/"+"block_" + this.IDw + "_" + this.IDh
					+"_" +".csv");
		  
	}

	public int sumNum(int a, int b, int step) {// get the sum of number list
		// a,b is the starting and ending index
		int c = 0;
		int bb = b;
		if (b >= numList.size())
			bb = numList.size() - 1;
		for (int i = a; i < bb; i++) {
			c += this.numList.get(i);
		}
		return c;
	}


	public float averageSum(int step) {
		float c = 0;

		for (int i = 0; i < numList.size(); i += step) {
			int sum = sumNum(i, i + step, step);
			c += sum;
		}
		return c / ((float) numList.size() / step);

	}

}
