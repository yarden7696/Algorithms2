package Algo2;
import java.util.Arrays;


/**
 * The Kruskal algorithm for MST O(mlogm + n^2)
 */

   class Normal_Kruskal {
    int vertexGroup[];
    Edge[] graph;
    Edge[] tree;
    int num_Edges, num_Vertexes, MST_Edges;

    // CONSTRUCTOR
    public Normal_Kruskal(Edge[] graph) {
        num_Edges = graph.length;
        MST_Edges = 0;
        int numOfVertexes = 0;
        // number of vertexes calculation
        for (int i = 0; i < num_Edges; i++) {
            Edge e = graph[i];
            if (e.getVertexBegin() > numOfVertexes) numOfVertexes = e.getVertexBegin();
            if (e.getVertexEnd() > numOfVertexes) numOfVertexes = e.getVertexEnd();
        }
        this.num_Vertexes = numOfVertexes;
        this.graph = graph;
        Arrays.sort(this.graph); // sort all Edges from the smallest value to the biggest value
        vertexGroup = new int[numOfVertexes];
        tree = new Edge[numOfVertexes];
        for (int i = 0; i < numOfVertexes; i++) {
            vertexGroup[i] = i;
        }
    }

    public void MSPCreate() {
        int counterEdges = 0;
        while (MST_Edges < num_Vertexes && counterEdges < num_Edges) { // as long as the number of the Edges that we add smaller than num_Vertexes-1 so:
            Edge e = graph[counterEdges];  // choose a minimum Edge
            int groupA = vertexGroup[e.getVertexBegin() - 1];
            int groupB = vertexGroup[e.getVertexEnd() - 1];
            if (groupA != groupB) { // if the 2 vertexes of the Edge are from different groups so-
                tree[MST_Edges++] = graph[counterEdges]; // adding this Edge to the graph
                union(graph[counterEdges].getVertexBegin(), graph[counterEdges].getVertexEnd()); // Union the 2 vertexes by adding the Edge between them
            }
            counterEdges = counterEdges + 1; // plus +1 caz we add Edge to the tree
        }
    }

    /**
     * This function union 2 vertex by adding Edge between them
     * @param vertexA is the first vertex
     * @param vertexB is the second vertex
     */
    private void union(int vertexA, int vertexB) {
        int grA = vertexGroup[vertexA - 1];
        int grB = vertexGroup[vertexB - 1];
        for (int i = 0; i < num_Vertexes; i++) {
            if (vertexGroup[i] == grB) {
                vertexGroup[i] = grA;
            }
        }
    }

    /**
     * This function calculate the sum of MST
     * @return the sum of MST
     */
    public double sumMST() {
        double w = 0;
        for (int i = 0; i < MST_Edges; i++) {
            w = w + tree[i].getWeight();
        }
        return w;
    }

    /**
     * This function print the final MST Tree
     */
    public void printTree() {
        for (int i = 0; i < MST_Edges; i++) {
            System.out.println(tree[i].toString());
        }
    }


//------------------------------inner class : Edge---------------------------------------------------
    /**
     * The Edge class represents an edge: (vertexBegin, vertexEnd) with the weight
     */

    static class Edge implements Comparable<Edge> {
        private int vertexBegin, vertexEnd, weight;

        // CONSTRUCTOR
        public Edge(int vertexA, int vertexB, int weight) {
            this.vertexBegin = vertexA;
            this.vertexEnd = vertexB;
            this.weight = weight;
        }

        public int getVertexBegin() { return vertexBegin; }
        public int getVertexEnd() { return vertexEnd; }
        public int getWeight() { return weight; }
        public String toString() { return "(" + vertexBegin + "," + vertexEnd + ",w:" + weight + ")"; }

        // implements compareTo()function of Comparable Interface
        public int compareTo(Edge edge) {
            int ans = 0;
            if (this.weight < edge.weight) ans = -1;
            else if (this.weight > edge.weight) ans = 1;
            else ans = 0;
            return ans;
        }

        public boolean equals(Edge edge) {
            boolean ans = (this.vertexBegin == edge.vertexBegin && this.vertexEnd == edge.vertexEnd) ||
                    (this.vertexBegin == edge.vertexEnd && this.vertexEnd == edge.vertexBegin);
            return ans;
        }
    }


//----------------------------------Init Graphs and main---------------------------------------------

    public static class InitGraphs {
        public static Edge[] init1() {
            int numOfEdges = 14;
            Edge[] graph = new Edge[numOfEdges];
            graph[0] = new Edge(1, 2, 4);    // 1-st edge
            graph[1] = new Edge(2, 3, 8);    // 2-st edge
            graph[2] = new Edge(3, 4, 7);    // 3-st edge
            graph[3] = new Edge(4, 5, 9);    // 4-st edge
            graph[4] = new Edge(5, 6, 10);// 5-st edge
            graph[5] = new Edge(6, 7, 2);// 6-st edge
            graph[6] = new Edge(7, 8, 1);// 7-st edge
            graph[7] = new Edge(8, 1, 8);// 8-st edge
            graph[8] = new Edge(2, 8, 11);// 9-st edge
            graph[9] = new Edge(3, 9, 2);// 10-st edge
            graph[10] = new Edge(3, 6, 4);// 11-st edge
            graph[11] = new Edge(4, 6, 14);// 12-st edge
            graph[12] = new Edge(7, 9, 6);// 13-st edge
            graph[13] = new Edge(8, 9, 7);// 14-st edge
            return graph;
        }

        public static Edge[] init2() {
            int numOfEdges = 8;
            Edge[] graph = new Edge[numOfEdges];
            graph[0] = new Edge(1, 2, 7);    // 1-st edge
            graph[1] = new Edge(2, 3, 5);    // 2-st edge
            graph[2] = new Edge(3, 4, 7);    // 3-st edge
            graph[3] = new Edge(4, 5, 10);    // 4-st edge
            graph[4] = new Edge(5, 6, 11);// 5-st edge
            graph[5] = new Edge(2, 4, 9);// 6-st edge
            graph[6] = new Edge(1, 4, 4);// 7-st edge
            graph[7] = new Edge(3, 5, 13);// 8-st edge
            return graph;
        }

        public static Edge[] init3() {
            int numOfEdges = 10;
            Edge[] graph = new Edge[numOfEdges];
            graph[0] = new Edge(1, 2, 6);    // 1-st edge
            graph[1] = new Edge(2, 3, 19);    // 2-st edge
            graph[2] = new Edge(3, 4, 9);    // 3-st edge
            graph[3] = new Edge(4, 5, 14);    // 4-st edge
            graph[4] = new Edge(5, 6, 21);// 5-st edge
            graph[5] = new Edge(5, 7, 2);// 6-st edge
            graph[6] = new Edge(7, 8, 8);// 7-st edge
            graph[7] = new Edge(8, 1, 17);// 8-st edge
            graph[8] = new Edge(7, 1, 11);// 9-st edge
            graph[9] = new Edge(7, 2, 25);// 10-st edge
            return graph;
        }


        public static Edge[] init4() {
            int numOfEdges = 4;
            Edge[] graph = new Edge[numOfEdges];
            graph[0] = new Edge(1, 2, 3);    // 1-st edge
            graph[1] = new Edge(2, 3, 12);    // 2-st edge
            graph[2] = new Edge(2, 4, 5);    // 3-st edge
            graph[3] = new Edge(3, 4, 1);    // 4-st edge
            return graph;
        }

        public static Edge[] init5() {
            int numOfEdges = 4;
            Edge[] graph = new Edge[numOfEdges];
            graph[0] = new Edge(1, 2, 12);    // 1-st edge
            graph[1] = new Edge(2, 3, 3);    // 2-st edge
            graph[2] = new Edge(2, 4, 5);    // 3-st edge
            graph[3] = new Edge(3, 4, 1);    // 4-st edge
            return graph;
        }

    }


    public static void main(String[] args) {
        Normal_Kruskal kt = new Normal_Kruskal(InitGraphs.init3());
        kt.MSPCreate();
        kt.printTree();
        System.out.println("sum weight = "+kt.sumMST());
    }
}


/**
 result init1
 (7,8,w:1)
 (6,7,w:2)
 (3,9,w:2)
 (1,2,w:4)
 (3,6,w:4)
 (3,4,w:7)
 (2,3,w:8)
 (4,5,w:9)
 sum weight = 37.0

 result init2
 (1,4,w:4)
 (2,3,w:5)
 (1,2,w:7)
 (4,5,w:10)
 (5,6,w:11)
 sum weight = 37.0

 result init3
 (5,7,w:2)
 (1,2,w:6)
 (7,8,w:8)
 (3,4,w:9)
 (7,1,w:11)
 (4,5,w:14)
 (5,6,w:21)
 sum weight = 71.0


 **/