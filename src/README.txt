Author: Won Kuk Lee

My code works by running the command line arguments as:
"ur.txt -show -directions i4 HOEING" in the Canvas.java class

Or any text map file such as "nys.txt" or any other locations.


It was a challenge to create all the vertices and edges, since this was
different from the other labs. For instance, in the other labs, the vertices
only contained 1 integer as its point. However in this project, the vertices
contained not simple integers, but longitudes and latitudes. I had to convert
these longitudes and latitudes to reasonable x and y coordinates so that I can
graph them. It was also difficult to adjust the algorithms so that it took in
the kind of data structures that I added the vertices and edges into, for example
HashMaps and ArrayLists. 
I decided to use HashMaps because they have a key and value,
and I can use the key to retrieve the vertex or edge based on the ID.
I used Dijkstra's algorithm to find the shortest path and Kruskal's algorithm
to find the minimum weight spanning tree of the graph. 
The Big Oh of Dijkstra's algorithm was: O(|E| + |V|log|V|).
- This is because in the loop, one vertex is removed from the queue
so there are O(V) vertices. So the total time to finish the loop
is O(V log V). Then there is O(E) time for each edge because there is
O(V) vertices in the loop.

The Big Oh of Kruskal's algorithm was: O(|E| + |V|log|V|).
- Similarly, the loop to merge the sets of Kruskal's algorithm
takes O(V log V) for the vertices and O(E) for each vertice so the
algorithm becomes O(|E| + |V|log|V|).

Files:
Canvas.java
-contains all the algorithms and creation of vertices and edges
from the text file.
DisjSets.java
- contains methods necessary for Kruskal's algorithm for combining
the sets.
Edge.java
- contains edge class that takes in roadID, 2 Vertices, and the weight.
Location.java
- contains latitude and longitude variables
Map.java
- adds frame to map
Path.java
- contains path for Dijkstra's algorithm that contains Vertex
destination and the weight
Point.java
- contains x and y coordinates that scales to map
Vertex.java
- contains Vertex class that contains the x and y coordinates scaled
to map and the ID
monroe.txt
- contains all components for monroe county
ur.txt
- contains all components for UR
nys.txt
- contains all components for nys
