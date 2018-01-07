// Author: Won Kuk Lee

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	Map map;
	protected int x, y;
	protected double latitude, longitude;
	protected static int diameter = 20;
	public static String initial;
	public static String destination;
	public static String show;
	public static final double INFINITY = Double.MAX_VALUE;
	public HashMap<String, Edge> edgeHash;
	public HashMap<String, Point> vertexHash = new HashMap<String, Point>();
	public HashMap<String, Location> locationHash = new HashMap<String, Location>();
	public HashMap<String, Vertex> vertexGraph = new HashMap<String, Vertex>();
	public HashMap<String, Edge> edgeXY = new HashMap<String, Edge>();
	public static String text;

	public Canvas() {
		edgeHash = new HashMap<String, Edge>();
	}

	// takes data from text file and creates appropriate objects
	public void data(String map) throws IOException {

		Scanner input = new Scanner(
				new FileReader("C:\\Users\\leew15\\Desktop\\College\\CSC 171\\Project 4\\src\\" + map));
		while (input.hasNext()) {
			String intersection = input.next();
			String id;
			String roadID = null;
			if (intersection.equals("i")) {
				id = input.next();
				latitude = Double.parseDouble(input.next());
				longitude = Double.parseDouble(input.next());
				locationHash.put(id, new Location(longitude, latitude));
				double yc = getYCoordinate(latitude);
				double xc = getXCoordinate(longitude);
				Point p = new Point(xc, yc);
				Vertex v = new Vertex(longitude, latitude, id);
				vertexHash.put(id, p);
				vertexGraph.put(id, v);
			} else {
				roadID = input.next();
				String start = input.next();
				String end = input.next();
				Vertex origin = new Vertex(vertexHash.get(start).longitude, vertexHash.get(start).latitude, start);
				Vertex destination = new Vertex(vertexHash.get(end).longitude, vertexHash.get(end).latitude, end);
				double miles = calculateDistance(locationHash.get(start).latitude, locationHash.get(end).latitude,
						locationHash.get(start).longitude, locationHash.get(end).longitude, 0.0, 0.0);
				Edge e = new Edge(roadID, vertexGraph.get(start), vertexGraph.get(end), miles);
				Edge xy = new Edge(roadID, origin, destination, miles);
				// origin.addEdge(e);
				edgeHash.put(roadID, e);
				edgeXY.put(roadID, xy);
				addEdge(roadID, start, end, miles);
			}

		}
	}

	// calculates miles from longitudes and latitudes
	public double calculateDistance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {
		final int R = 6371;
		el1 = 0.0;
		el2 = 0.0;
		double latitudeD = Math.toRadians(lat2 - lat1);
		double longitudeD = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latitudeD / 2) * Math.sin(latitudeD / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(longitudeD / 2) * Math.sin(longitudeD / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000;
		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		double meters = Math.sqrt(distance);
		double miles = meters * 0.000621371192;

		return miles;
	}

	// scaling for panel x and y coordinates based on the map provided in the
	// command line arguments
	public int getXCoordinate(double longitude) {
		if (text.equals("ur.txt"))
			x = (int) ((Math.abs(longitude + 77.625301)) * 100000);
		if (text.equals("monroe.txt"))
			x = (int) ((Math.abs(longitude + 77.37142)) * 1000);
		if (text.equals("nys.txt"))
			x = (int) ((Math.abs(longitude + 71.85861899999999)) * 100);
		return x;
	}

	public int getYCoordinate(double latitude) {
		if (text.equals("ur.txt"))
			y = (int) Math.abs((latitude - 43.125214) * 100000);
		if (text.equals("monroe.txt"))
			y = (int) Math.abs((latitude - 42.940061) * 1000);
		if (text.equals("nys.txt"))
			y = (int) ((Math.abs(latitude - 40.518202999999986)) * 100);
		return y;
	}

	public void addEdge(String ID, String origin, String dest, double weight) {
		Vertex from = getVertex(origin);
		Vertex to = getVertex(dest);
		from.adj.add(new Edge(ID, from, to, weight));
	}

	public Vertex getVertex(String vertexID) {
		Vertex v = vertexGraph.get(vertexID);
		if (v == null) {
			v = new Vertex(locationHash.get(vertexID).longitude, locationHash.get(vertexID).latitude, vertexID);
			vertexGraph.put(vertexID, v);
		}
		return v;
	}

	// Dijkstra's algorithm to find shortest path
	public void Dijkstra(String origin) {
		PriorityQueue<Path> pq = new PriorityQueue<Path>();

		Vertex start = vertexGraph.get(origin);
		pq.add(new Path(start, 0));
		start.dist = 0;

		int nodesSeen = 0;
		while (!pq.isEmpty() && nodesSeen < vertexGraph.size()) {
			Path p = pq.remove();
			Vertex v = p.destination;

			nodesSeen++;
			for (Edge e : v.adj) {
				Vertex w = e.to;
				double cvw = e.weight;

				if (w.dist > v.dist + cvw) {
					w.dist = v.dist + cvw;
					w.previous = v;
					pq.add(new Path(w, w.dist));
				}
			}

		}
	}
	// prints paths for Dijkstra
	public void printPath(String dest) {
		Vertex w = vertexGraph.get(dest);
		if (w == null)
			throw new NoSuchElementException("Destination vertex not found");
		else {
			System.out.println("Shortest Path from " + initial + " to " + dest + " is " + w.dist + " miles.");
			System.out.println("The paths are: ");
			printPath(w);
			System.out.println("Destination Arrived.");
		}
	}

	private void printPath(Vertex dest) {
		if (dest.previous != null) {
			printPath(dest.previous);
			System.out.print(" ");
		}
		System.out.print(dest.name + " --> ");
	}
	// krukals algorithm for MST
	public ArrayList<Edge> Kruskal(Collection<Edge> edges) {

		DisjSets ds = new DisjSets(vertexGraph.values());
		PriorityQueue<Edge> pq = new PriorityQueue<>(edges);
		ArrayList<Edge> mst = new ArrayList<>();

		while (mst.size() != vertexGraph.size() - 1) {
			Edge e = pq.remove();
			Vertex uset = ds.findRoot(e.from);
			Vertex vset = ds.findRoot(e.to);
			if (uset != vset) {
				mst.add(e);
				ds.merge(uset, vset);
			}
		}

		return mst;

	}

	// loop to draw roads
	public void paintComponent(Graphics g) {
		try {
			data(text);
			Dijkstra(initial);
			printPath(destination);
			System.out.println("The minimum spanning tree is: " + Kruskal(edgeHash.values()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, Edge> edgeXY : edgeXY.entrySet()) {
			int originX = (int) edgeXY.getValue().from.x;
			int originY = (int) edgeXY.getValue().from.y;
			int targetX = (int) edgeXY.getValue().to.x;
			int targetY = (int) edgeXY.getValue().to.y;

			g.drawLine(originX, originY, targetX, targetY);
		}

	}

	public static void main(String[] args) throws IOException {
		if (args[1].equals("-show"))
			show = "-show";
		
		if (args[2].equals("-directions")){
			initial = args[3];
			destination = args[4];
		}
		Map map = new Map("Map");
		map.setVisible(true);
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		map.setBackground(Color.WHITE);
		text = args[0];

	}
}
