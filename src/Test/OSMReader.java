package Test;

import java.util.ArrayList;

import igeo.IVec2;
import OSMData.node;
//import OSMData.relation;
import OSMData.way;
import processing.core.PApplet;
import processing.data.*;

public class OSMReader {
	/**
	 * Wenzhe Peng 
	 * 
	 * pwz@berkeley.edu
	 * 
	 * Sep 2014
	 */
	public static XML osm;
	private static node[] nodes;
	private static way[] ways;
	//private static relation[] relations;

	public static void read(PApplet app) {
		osm = app.loadXML("C:/Users/Panther/Desktop/datasf/tyuityui.osm");
		// System.out.println(osm.getChildren("node")[0].getChildren("tag").length);

		// set nodes
		nodes = new node[osm.getChildren("node").length];
		for (int i = 0; i < osm.getChildren("node").length; i++) {
			nodes[i] = new node(osm.getChildren("node")[i]);
		}

		// set ways
		ways = new way[osm.getChildren("way").length];
		for (int i = 0; i < osm.getChildren("way").length; i++) {
			ways[i] = new way(osm.getChildren("way")[i], nodes);
		}

		// System.out.println(AABB()[0] + "," + AABB()[1]);
		//PApplet.println(kvWays(ways,"building","yes").length);
	}

	public static void drawNodes(PApplet app) {

		for (way w : kWays(ways,"highway")) {
			w.draw(app, 255,0,0);
		}
		
		for (way w : kWays(ways,"building")) {
			w.draw(app, 0,0,255);
		}
		
		for (way w : kWays(ways,"bicycle")) {
			w.draw(app, 0,255,255);
		}
		
		for (way w : kWays(ways,"foot")) {
			w.draw(app, 255,255,0);
		}
		
		for (way w : kWays(ways,"sidewalk")) {
			w.draw(app, 255,255,0);
		}
		
		for (way w : kWays(ways,"trolley_wire")) {
			w.draw(app, 255,0,255);
		}

		/*for (node n : nodes) {
			n.draw(app);
		}*/
	}
	
	public static way[] kvWays(way[] ws, String k, String v) {
		// search the ws that match this k,v
		ArrayList<way> l = new ArrayList<way>();
		way[] li;
		for (way w : ws) {
			if (w.TagKey(k).equals(v))
				l.add(w);
		}
		if (l.size() > 0) {
			li = new way[l.size()];
			for (int i = 0; i < l.size(); i++) {
				li[i] = l.get(i);
			}
			return li;
		} else
			return null;
	}
	
	public static way[] kWays(way[] ws, String k) {
		// search the ws that match have k as key
		ArrayList<way> l = new ArrayList<>();
		way[] li;
		for (way w : ws) {
			if (w.hasKey(k))
				l.add(w);
		}
		if (l.size() > 0) {
			li = new way[l.size()];
			for (int i = 0; i < l.size(); i++) {
				li[i] = l.get(i);
			}
			return li;
		} else
			return null;
	}
	
	public static way[] nodekeyWAY(way[] ws, String k){
		ArrayList<way> l = new ArrayList<>();
		way[] li;
		for (way w : ws) {
			if (w.nodehasKey(k))
				l.add(w);
		}

		if (l.size() > 0) {
			PApplet.println("test");

			li = new way[l.size()];
			for (int i = 0; i < l.size(); i++) {
				li[i] = l.get(i);
			}

			return li;
		} else
			return null;
		
	}
	
	public static node[] kvWays(node[] ns, String k, String v) {
		// search the ns that match this k,v
		ArrayList<node> l = new ArrayList<>();
		node[] li;
		for (node w : ns) {
			if (w.TagKey(k).equals(v))
				l.add(w);
		}
		if (l.size() > 0) {
			li = new node[l.size()];
			for (int i = 0; i < l.size(); i++) {
				li[i] = l.get(i);
			}
			return li;
		} else
			return null;
	}
	
	
	
	public static node[] kNodes(node[] ns, String k) {
		// search the ws that match have k as key
		ArrayList<node> l = new ArrayList<>();
		node[] li;
		for (node n : ns) {
			if (n.hasKey(k))
				l.add(n);
		}
		if (l.size() > 0) {
			li = new node[l.size()];
			for (int i = 0; i < l.size(); i++) {
				li[i] = l.get(i);
			}
			return li;
		} else
			return null;
	}

	public static IVec2[] AABB() {
		IVec2[] vs = new IVec2[3];
		double x0 = Double.MAX_VALUE, x1 = Double.MIN_VALUE, y0 = Double.MAX_VALUE, y1 = 0 - Double.MAX_VALUE;
		for (node n : nodes) {
			// System.out.println(n.getLoc().y());

			if (n.getLoc().x() < x0)
				x0 = n.getLoc().x();
			if (n.getLoc().x() > x1)
				x1 = n.getLoc().x();
			if (n.getLoc().y() < y0)
				y0 = n.getLoc().y();
			if (n.getLoc().y() > y1) {
				y1 = n.getLoc().y();
				// System.out.println(n.getLoc().y());
			}
		}
		vs[0] = new IVec2(x0, y0);
		vs[1] = new IVec2(x1, y1);
		vs[2] = new IVec2((x0 + x1) / 2, (y0 + y1) / 2);
		return vs;
	}

	public static double HWRatio() {
		return (AABB()[1].y() - AABB()[0].y())
				/ (AABB()[1].x() - AABB()[0].x());
	}

}
