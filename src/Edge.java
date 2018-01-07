
// Author: Won Kuk Lee

public class Edge implements Comparable<Edge> {
	public Vertex from;
	public Vertex to;
	public String id;
	public double weight;
	
	public Edge(String roadID, Vertex argstart, Vertex end, double argweight) {
		id = roadID;
		from = argstart;
		to = end;
		weight = argweight;
	}
	public String toString(){
		return "" + id + ", " + from + ", " + to;
	}
	@Override
	public int compareTo(Edge e) {
		if(this.weight>e.weight)return 1;
		else if(this.weight<e.weight)return -1;
		else return 0;
	}
}
