package OSMData;

import java.util.ArrayList;

import Test.OSMReader;
import igeo.IVec2;
import processing.core.PApplet;
import processing.data.XML;

public class node {
	String id;
	String timestamp;
	double lat;
	double lon;
	tag[] tags;
	IVec2 loc;
	ArrayList<way> ways;
	

	public node(XML xml) {
		//System.out.println(xml.getInt("id"));
		ways = new ArrayList<>();
		this.id = xml.getString("id");
		this.timestamp = xml.getString("timestamp");
		this.lat = xml.getDouble("lat");
		this.lon = xml.getDouble("lon");
		this.loc = new IVec2(this.lat,this.lon);
		
		if (xml.getChildren("tag").length > 0) {
			tags = new tag[xml.getChildren("tag").length];
			for (int i = 0; i < xml.getChildren("tag").length; i++) {
				tags[i] = new tag(xml.getChildren("tag")[i].getString("k"),
						xml.getChildren("tag")[i].getString("v"));
			
			tags[i] = new tag("","");	
			}
		}

	}

	public String getID() {
		return this.id;
	}

	public String getTime() {
		return this.timestamp;
	}

	public double getLat() {
		return this.lat;
	}

	public double getLon() {
		return this.lon;
	}
	
	public IVec2 getLoc(){
		return this.loc;
	}

public String TagKey(String k){
		
		for(tag t : this.tags){
			if(t.getK().equals(k))
				return t.getV();
		}
		return "null";
		
	}
	

public boolean hasKey(String k){
	if(this.tags!=null)
	for(tag t : this.tags){
		if(t.getK().equals(k))
			return true;
	}
	return false;

}
	public void draw(PApplet app){
		float x = PApplet.map((float)(this.getLoc().x()),(float)(OSMReader.AABB()[0].x()), (float)(OSMReader.AABB()[1].x()), 0, (float)(app.height*OSMReader.HWRatio()));
		float y = PApplet.map((float)(this.getLoc().y()),(float)(OSMReader.AABB()[0].y()), (float)(OSMReader.AABB()[1].y()), 0, app.height);

		//app.println(rel.x+","+rel.y);
		app.pushStyle();
		app.strokeWeight(2);
		app.stroke(150,0,0);
		app.point(x, y);
		
		app.popStyle();
	}
	
	
}
