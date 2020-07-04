package Algo2;
import java.util.*;


public class kruskal_DS {

    //----------------------------inner classes : Node, Edge, DisjointSets-----------------------------------

    public static class Node {
        int node;
        int weight;

        public Node(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        public String toString() {
            return "node: " + node + ", weight: " + weight;
        }
    }


    public static class Edge implements Comparable<Edge> {

        private int vertexA, vertexB;
        private int weight;

        // CONSTRUCTOR
        public Edge(int vertexA, int vertexB, int weight) {
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
        }

        public int getVertexA() { return vertexA; }
        public int getVertexB() { return vertexB; }
        public int getWeight() { return weight; }
        public String toString() { return "(" + vertexA + "," + vertexB + ",w:" + weight + ")"; }

        public int compareTo(Edge edge) {
            int ans = 0;
            if (this.weight < edge.weight) ans = -1;
            else if (this.weight > edge.weight) ans = 1;
            else ans = 0;
            return ans;
        }

        public boolean equals(Edge edge) {
            boolean ans = (this.vertexA == edge.vertexA && this.vertexB == edge.vertexB) ||
                    (this.vertexA == edge.vertexB && this.vertexB == edge.vertexA);
            return ans;
        }
    }


    public static class DisjointSets {

        private int[] parent;
        private int[] rank;

        public DisjointSets(int v) {
            parent = new int[v];
            rank = new int[v];
        }

        /**
         * Create a set that Every vertex is an component by himself
         */
        public void makeSet(int v) { // O(1)
            parent[v] = v; // every vertex alone by himself
            rank[v] = 0; // his rank is 0
        }


        /**
         * This function find who is the father of vertex v
         * @param v is the vertex that we wont to find his father
         * @return the father of vertex v
         */
        public int find(int v) { // O(log(n))
            int p = parent[v];
            if (v == p) {
                return v;
            }
            return parent[v] = find(parent[p]);
        }


        //Join two subsets into a single subset.
        // returns true if rootU != rootV

        /**
         * This function connect 2 vertexes and create a new Edge in the MST Tree
         * @param u is the first vertex
         * @param v is the second vertex
         * @return True is u and v are from different sets, False if they are in the same sets
         */
        public boolean union(int u, int v) {  // O(log(n))
            boolean ans = false;
            int rootU = find(u); // find the father of u
            int rootV = find(v); // find the father of v
            if (rootV == rootU) ans = false; // its mean that we cant connect u and v caz they at the same component
            else { // u and v are from different components
                ans = true;
                // we decided that the vertex with the Highest rank always will be the father
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU; // U is the father of V
                } else if (rank[rootV] > rank[rootU]) {
                    parent[rootU] = rootV; // V is the father of U
                } else { //rank[root2] == rank[root1]
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }
            return ans;
        }

        public String toString() {
            return "<DisjointSets\nparent " + Arrays.toString(parent) + "\nrank " + Arrays.toString(rank) + "\n>";
        }

        /**
         * @param p the integer representing one site
         * @param q the integer representing the other site
         * @return true if the two sites p and q are in the same component;
         * false otherwise
         */
        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }



//------------------------------------------End inner classes-----------------------------------------------

    public static class KruskalWithDSU {

        private static DisjointSets InitGraphsDS;
        Edge[] graph;
        Edge[] tree;
        DisjointSets vertexGroup; // 2 arrays : one of the parents and the second of the ranks
        int numOfEdges, numOfVertices, numEdgesInMST;

        // n - number of vertices, m - number of edges
        // CONSTRUCTOR
        public KruskalWithDSU(Edge[] graph, int n) {
            numOfEdges = graph.length;
            numEdgesInMST = 0;
            this.graph = graph;
            Arrays.sort(this.graph); //O(mlogm) =O(mlogn^2) =O(mlogn)
            numOfVertices = n;
            tree = new Edge[numOfVertices - 1];
            vertexGroup = new DisjointSets(numOfVertices);
            for (int i = 0; i < numOfVertices; i++) { //O(n)
                vertexGroup.makeSet(i);
            }
        }

        public void CreateMSP() {
            int i = 0;
            while (numEdgesInMST < numOfVertices - 1 && i < numOfEdges) {
                if (vertexGroup.union(graph[i].getVertexA(), graph[i].getVertexB())) { //O(mlogn)
                    tree[numEdgesInMST++] = graph[i];
                }
                i++;
            }
        }

        public double calc_Sum_Weight() {
            double w = 0;
            for (int i = 0; i < numEdgesInMST; i++) {
                w = w + tree[i].getWeight();
            }
            return w;
        }

        public void printTree() {
            for (int i = 0; i < numEdgesInMST; i++) {
                System.out.println(tree[i].toString());
            }
        }


        // main that calc the sum of the MST and print MST Tree
        public static void main(String[] args) {
            ArrayList<Node> graph[] = InitGraphsDS.init2();
            int n = graph.length;
            KruskalWithDSU kruskal = new KruskalWithDSU(InitGraphsDS.getEdges(graph), n);
            kruskal.CreateMSP();
            kruskal.printTree();
            System.out.println("sum weight = " + kruskal.calc_Sum_Weight());
        }

    }
/*init3
(5,7,w:2)
(1,2,w:6)
(7,8,w:8)
(3,4,w:9)
(7,1,w:11)
(4,5,w:14)
(5,6,w:21)
sum weight = 71.0
 */



        public static ArrayList<Node>[] init1() {
            int n = 8;
            ArrayList<Node>[] graph = new ArrayList[n];
            for (int i = 0; i < graph.length; i++) {
                graph[i] = new ArrayList<Node>();
            }
            graph[0].add(new Node(1, 19));
            graph[0].add(new Node(7, 6));
            graph[0].add(new Node(3, 25));
            graph[1].add(new Node(0, 19));
            graph[1].add(new Node(2, 9));
            graph[2].add(new Node(1, 9));
            graph[2].add(new Node(3, 14));
            graph[3].add(new Node(2, 14));
            graph[3].add(new Node(4, 21));
            graph[3].add(new Node(0, 25));
            graph[3].add(new Node(5, 2));
            graph[3].add(new Node(7, 11));
            graph[4].add(new Node(3, 21));
            graph[5].add(new Node(3, 2));
            graph[5].add(new Node(6, 8));
            graph[6].add(new Node(5, 8));
            graph[6].add(new Node(7, 17));
            graph[7].add(new Node(0, 6));
            graph[7].add(new Node(6, 17));
            graph[7].add(new Node(3, 11));
            return graph;
        }

        public static ArrayList<Node>[] init2() {
            int n = 4;
            ArrayList<Node>[] graph = new ArrayList[n];
            for (int i = 0; i < graph.length; i++) {
                graph[i] = new ArrayList<Node>();
            }
            graph[0].add(new Node(1, 12));
            graph[1].add(new Node(0, 12));
            graph[1].add(new Node(2, 3));
            graph[1].add(new Node(3, 5));
            graph[2].add(new Node(1, 3));
            graph[2].add(new Node(3, 1));
            graph[3].add(new Node(1, 5));
            graph[3].add(new Node(2, 1));
            return graph;
        }

        public static Edge[] getEdges(ArrayList<Node>[] graph) {
            int numOfEdges = 0, n = graph.length;
            for (int i = 0; i < graph.length; i++) {
                numOfEdges = numOfEdges + graph[i].size();
            }
            numOfEdges = numOfEdges / 2;
            Edge[] edges = new Edge[numOfEdges];
            for (int i = 0, k = 0; k < numOfEdges && i < n; i++) {
                for (int j = 0; j < graph[i].size(); j++) {
                    Node nn = graph[i].get(j);
                    Edge e = new Edge(i, nn.node, nn.weight);
                    if (!contains(edges, e, k)) edges[k++] = e;
                }
            }
            return edges;
        }

        private static boolean contains(Edge[] edges, Edge e, int k) {
            boolean ans = false;
            for (int i = 0; !ans && i < k; i++) {
                if (edges[i].equals(e)) ans = true;
            }
            return ans;
        }

        // main that create the vertexes by them-self
        public static void main(String[] args) {
            Edge edges[] = getEdges(init1());
            System.out.println(Arrays.toString(edges));
            edges = getEdges(init2());
            System.out.println(Arrays.toString(edges));


            int numSets = 5;
            DisjointSets ds = new DisjointSets(numSets);
            for (int i = 0; i < numSets; i++) {
                ds.makeSet(i);
            }
            System.out.println(ds);
            ds.union(1, 2);
            System.out.println("union 1 & 2");
            System.out.println("find for 1  - root is " + ds.find(1) + ", find for 2 - root is " + ds.find(2));
            System.out.println(ds);

            ds.union(3, 4);
            System.out.println("union 3 & 4");
            System.out.println(ds);

            ds.union(1, 3);
            System.out.println("union 1 & 3");
            System.out.println(ds);

            ds.union(1, 4);
            System.out.println("union 1 & 4");
            System.out.println(ds);
            System.out.println("find for 4  - root is " + ds.find(4));

		/*		ds.union(1,2);
		System.out.println("union 1 2");
		System.out.println(ds);

		ds.union(1,2);
		System.out.println("union 1 2");
		System.out.println(ds);

		ds.union(3,4);
		System.out.println("union 3 4");
		System.out.println(ds);

		ds.union(1,0);
		System.out.println("union 1 0");
		System.out.println(ds);

		ds.union(1,3);
		System.out.println("union 1 3");
		System.out.println(ds);

		ds.find(4);
		System.out.println("find 4");
		System.out.println(ds);

		 */
        }

	/*

  The tests in main should produce:

<DisjointSets
parent [0, 1, 2, 3, 4]
rank [0, 0, 0, 0, 0]
>
union 1 & 2
find for 1  - root is 1, find for 2 - root is 1
<DisjointSets
parent [0, 1, 1, 3, 4]
rank [0, 1, 0, 0, 0]
>
union 3 & 4
<DisjointSets
parent [0, 1, 1, 3, 3]
rank [0, 1, 0, 1, 0]
>
union 1 & 3
<DisjointSets
parent [0, 1, 1, 1, 3]
rank [0, 2, 0, 1, 0]
>
union 1 & 4
<DisjointSets
parent [0, 1, 1, 1, 1]
rank [0, 2, 0, 1, 0]
>

	 */

    }

}







