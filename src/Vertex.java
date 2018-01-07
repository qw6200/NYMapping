// Author: Won Kuk Lee

import java.util.LinkedList;
import java.util.List;

public class Vertex{
	public String name;
	public double x, y;
	public List<Edge> adj;
	public double dist;
	public Vertex previous;
	public Vertex parent;
	
	public Vertex(double xc, double yc, String vID){
		x = xc;
		y = yc;
		name = vID;
		adj = new LinkedList<Edge>();
		reset();
	}
	public void reset(){
		dist = Canvas.INFINITY;
		previous = null;
	}
	public String toString(){
		return "" + x + ", " + y + ", " + name;
	}
}
