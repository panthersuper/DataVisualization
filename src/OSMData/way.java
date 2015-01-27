package OSMData;

import Test.OSMReader;
import processing.core.PApplet;
import processing.data.XML;

public class way {
	String id;
	String timestamp;
	tag[] tags;
	String[] nds; // connecting nodes reference string
	node[] nodes;// connecting nodes

	public way(XML xml, node[] nodes) {
		this.id = xml.getString("id");
		this.timestamp = xml.getString("timestamp");

		if (xml.getChildren("tag").length > 0) {
			tags = new tag[xml.getChildren("tag").length];
			for (int i = 0; i < xml.getChildren("tag").length; i++) {
				tags[i] = new tag(xml.getChildren("tag")[i].getString("k"),
						xml.getChildren("tag")[i].getString("v"));

			}
		}

		// set nds
		nds = new String[xml.getChildren("nd").length];
		for (int i = 0; i < xml.getChildren("nd").length; i++) {
			nds[i] = xml.getChildren("nd")[i].getString("ref");
		}

		// set nodes
		if (xml.getChildren("nd").length > 0) {
			this.nodes = new node[xml.getChildren("nd").length];
			for (int i = 0; i < nds.length; i++) {
				for (node n : nodes) {
					/*
					 * PApplet.println("-------------");
					 * PApplet.println(nds[i]); PApplet.println(n.getID());
					 */
					if (nds[i].equals(n.getID())) {
						this.nodes[i] = n;
						n.ways.add(this);
					}
				}
			}
		}
	}

	public boolean hasNode(node nd) {
		for (node n : this.nodes) {
			if (n.getID().equals(nd.getID()))
				return true;
		}
		return false;
	}

	public String TagKey(String k) {
		if (this.tags != null)
			for (tag t : this.tags) {
				if (t.getK().equals(k))
					return t.getV();
			}
		return "null";

	}

	public boolean hasKey(String k) {
		if (this.tags != null)
			for (tag t : this.tags) {
				if (t.getK().equals(k))
					return true;
			}
		return false;

	}

	public boolean nodehasKey(String k) {
		if (this.nodes != null) {
			for (node n : this.nodes) {
				if (n.hasKey(k))
					return true;
			}

		}
		return false;

	}

	public void draw(PApplet app, int red, int green, int blue) {
		app.pushStyle();

		app.stroke(red, green, blue);
		app.beginShape();
		app.noFill();
		
		//app.fill((float)(red/2),(float)(green/2),(float)(blue/2));
		if (this.nodes != null)
			for (node n : this.nodes) {
				float x = PApplet.map((float) (n.getLoc().x()),
						(float) (OSMReader.AABB()[0].x()),
						(float) (OSMReader.AABB()[1].x()), 0,
						(float) (app.height * OSMReader.HWRatio()));
				float y = PApplet.map((float) (n.getLoc().y()),
						(float) (OSMReader.AABB()[0].y()),
						(float) (OSMReader.AABB()[1].y()), 0, app.height);
				app.vertex(x, y);
			}

		app.endShape();
		app.popStyle();

	}

}
