// Author: Won Kuk Lee

public class Path implements Comparable<Path>
{
    public Vertex destination;  
    public double weight;  
    
    public Path( Vertex d, double c ){
    	destination = d;
        weight = c;
    }
    
    public int compareTo(Path rhs){
        double otherCost = rhs.weight;
        return weight < otherCost ? -1 : weight > otherCost ? 1 : 0;
    }
    public String toString(){
    	return "" + destination;
    }
}