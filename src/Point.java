// Author: Won Kuk Lee

public class Point {
	public double latitude;
	public double longitude;
	
    public Point(double x, double y) {
        longitude = x;
        latitude = y;
    	
    }
    public String toString(){
    	return "" + longitude + ", " + latitude;
    }
}