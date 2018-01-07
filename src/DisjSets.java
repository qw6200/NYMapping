// Author: Won Kuk Lee

import java.util.ArrayList;
import java.util.Collection;

public class DisjSets {
	
	private ArrayList<Vertex> vertices;
	
	public DisjSets(Collection<Vertex> list){
		vertices = new ArrayList<>();
		for(Vertex v : list){
			vertices.add(v);
			v.parent = v;
		}
	}
	
	public Vertex findRoot(Vertex v) {
		if(v.parent.equals(v)) 
			return v;
		return findRoot(v.parent);
	}

	public void merge(Vertex u, Vertex v) {
		
		Vertex v1 = findRoot(u);
		Vertex v2 = findRoot(v);
		
		v1.parent = v2;
		
	}
	
}
