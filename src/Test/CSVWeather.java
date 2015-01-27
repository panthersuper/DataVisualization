package Test;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.Table;
import processing.data.TableRow;


public class CSVWeather extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table;
	static TableWeather[] tw;
	float size = 5f;
	private static int h = 900;
	private static int w = 600;
	PGraphics pg;
	
	int count = 0;
	
	
	public void setup() {
		smooth();
		size(w,h);
		//pg = createGraphics((int)(w*size), (int)(h*size), P3D, "test.pdf");
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//read table
		table = loadTable("D:/arch file/studio1/mappingggr/weather.csv");
		//println(table.getRowCount() + " total rows in table");
		tw = new TableWeather[table.getRowCount()];
		
		for (int i =0; i<table.getRowCount();i++) {
			TableRow tr = table.getRow(i);
			
			tw[i] = new TableWeather(tr.getDouble(1),tr.getDouble(0),tr.getDouble(2),tr.getDouble(3),tr.getDouble(4),tr.getDouble(5),tr.getDouble(6),tr.getDouble(7),tr.getDouble(8));
			
			if(tr.getDouble(4)<0)
				tw[i].humidity = 0;
		}
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		for(int i = 0;i<w;i++){
			for(int j = 0;j<h;j++){
				
				pushStyle();
				
				//temperature
				float tem = map(temp(120, i ,j), 15.3f,30.9f,0,155);
				stroke(100+tem, 100 ,255-tem*255/155);
				
				//humidity
				//println(hum(50, i ,j));
/*				float hum = map(hum(120, i ,j), 32,100,0 ,55);
				if(hum>0)  		stroke(255-hum*255/55, 200+ hum ,hum*255/55);
				else   stroke(0);
*/				
				//cloud
/*				float cloud = map(cloudy(60, i ,j), 0,10,0 ,255);
				stroke(cloud);
*/
				
				
				
				strokeWeight(2f);
				point(i,(h-j));
				popStyle();
			}
		}
		println("drawn");
		
		

	}

	
	public void draw() {
		//background(255);
		
		
		
	}
	
	public static float[] mmap(TableWeather t){//map the position to 
		float[] dd = new float[2];
		dd[1] = map((float)t.lng,37.1297f,38.3589f,0,h);
		dd[0] = map((float)t.lat,-122.8601f,-121.7834f,0,w);
		return dd;
	}
	
	public static float dis(TableWeather t, int x, int y){
		
		return t.distSQ(x, y);
	}
	
	public static ArrayList<TableWeather> InRange(double R, int x, int y){//return the tables that are in the range of R
		ArrayList<TableWeather> l = new ArrayList<TableWeather>();
		
		for(TableWeather t : tw){
			if(t.distSQ(x, y)<R*R){
				l.add(t);
			}
		}
			return l;
	}
	
	public static float temp(double R, int x, int y){
		//get the average item value for x,y, within R
		float value = 0;
		ArrayList<TableWeather> l = InRange(R, x, y);
		for(TableWeather t : l){
			value += t.temp_C;
		}
		
		value /= l.size();
		return value;
	}
	
	public static float hum(double R, int x, int y){
		//get the average item value for x,y, within R
		float value = 0;
		ArrayList<TableWeather> l = InRange(R, x, y);
		for(TableWeather t : l){
			value += t.humidity;
		}
		
		value /= l.size();
		return value;
	}
	
	
		
	
	public static float cloudy(double R, int x, int y){
		//get the average item value for x,y, within R
		float value = 0;
		ArrayList<TableWeather> l = InRange(R, x, y);
		for(TableWeather t : l){
			value += t.weather;
		}
		
		value /= l.size();
		return value;
	}
	
	public void keyReleased() {
		if(key == 't' ||key == 'T'){
		pg = createGraphics((int)(w*size),(int)(h*size));
		pg.beginDraw();

		
		
		for(int i = 0;i<w;i++){
			for(int j = 0;j<h;j++){
				
				//println(temp(100, i ,j));
				
				pg.pushStyle();
				
				//temperature
				float tem = map(temp(100, i ,j), 15.3f,30.9f,0,155);
				pg.stroke(100+tem, 100 ,255-tem*255/155);
				
				pg.strokeWeight(2f*size);
				pg.point(i*size, (h-j)*size);
				pg.popStyle();
			}
		}
		
		pg.endDraw();
		pg.save("temp.tiff");
		  println("saved");
		}
		
		if(key == 'h' ||key == 'H'){
			pg = createGraphics((int)(w*size),(int)(h*size));
			pg.beginDraw();

			
			
			for(int i = 0;i<w*size/2;i++){
				for(int j = 0;j<h*size/2;j++){
					
					//println(temp(100, i ,j));
					
					pg.pushStyle();
					
					float hum = map(hum(120, i ,j), 32,100,0 ,55);
					if(hum>0)  		pg.stroke(255-hum*255/55, 200+ hum ,hum*255/55);
					else   pg.stroke(0);
					
					pg.strokeWeight(2f*size);
					pg.point(i*size, (h-j)*size);
					pg.popStyle();
				}
			}
			
			pg.endDraw();
			pg.save("humi.tiff");
			  println("saved");
			}
		
		if(key == 'c' ||key == 'C'){
			pg = createGraphics((int)(w*size),(int)(h*size));
			pg.beginDraw();

			
			
			for(int i = 0;i<w*size/2;i++){
				for(int j = 0;j<h*size/2;j++){
					
					//println(temp(100, i ,j));
					
					pg.pushStyle();
					
					float cloud = map(cloudy(40*size, i ,j), 0,10,0 ,255);
					pg.stroke(cloud);
					
					pg.strokeWeight(2f*size);
					pg.point(i*size, (h-j)*size);
					pg.popStyle();
				}
			}
			
			pg.endDraw();
			pg.save("cloud.tiff");
			  println("saved");
			}
		
		
		
		
	}

	
}
