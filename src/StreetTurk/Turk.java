package StreetTurk;

import processing.core.PApplet;
import igeo.IVec2;

public class Turk {
	IVec2 pos;//position to draw
	IVec2 posR;//position by real data
	float cl;//cleanliness
	float sf;//safety
	IVec2 size;
	
	public Turk(IVec2 posR, IVec2 lt, IVec2 br, float cl, float sf, IVec2 size){
		//position real, left-top location, bottom-right location, cleanliness, size of canvas
		this.size = size;
		this.cl = cl;
		this.sf = sf;
		this.posR = posR;
		float x,y;
		x = (float)((posR.x-lt.x)/(br.x-lt.x)*size.x);
		y = (float)((posR.y-br.y)/(lt.y-br.y)*size.y);
		this.pos = new IVec2(x,y);
		
	}
	
	public float disSQ(float x, float y){
		return (float)(this.pos.dist2(new IVec2(x,y)));
	}
	
	public void draw(PApplet app){
		app.pushStyle();
		app.stroke(255);
		app.point((float)pos.x , (float)size.y-(float)pos.y);
		app.popStyle();
	}
	
	public String toString(){
		return this.pos.toString()+","+this.cl+","+this.sf;
		
	}
	
	
	
	
}
