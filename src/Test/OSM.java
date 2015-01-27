package Test;

import processing.core.PApplet;

public class OSM extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OSMReader r;

	public void setup() {
		smooth();
		size(1900,900);
		OSMReader.read(this);
		background(0);
		OSMReader.drawNodes(this);

	}
	
	public void draw(){
		
	}

}
