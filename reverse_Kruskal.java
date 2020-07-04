package Algo2;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class reverse_Kruskal {

    /**
     The Edge class represents an edge: (A,B) with the weight
     */
    static class Edge implements Comparable<Edge>{
        int vertexA, vertexB;
        int weight;

        // CONSTRUCTOR
        public Edge(int vertexA, int vertexB, int weight){
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
        }

        public String toString() { return "(" + vertexA + "," + vertexB + ",w:" + weight+")"; }

        public int compareTo(Edge edge){
            int ans = 0;
            if (this.weight < edge.weight) ans = -1;
            else  ans = 1;  //this.weight >= edge.weight
            return ans;
        }
    } // End class Edge





    /**
     *  @param size: number of vertexes+1
     *  @param infinity - the big number
     *  @param vertex - the source vertex
     *  @param graph - the graph is represented using adjacency-list
     *  @param q - the queue to manage the set of gray vertices
     *  @param color[] - the color of  vertex u is stored in color[u]
     *  @param pred[] - the predecessor (parent) of vertex u is stored
     *  in pred[u]. If u has no predecessor then pred[u]=NIL
     *  @param dist[] - the distance from the source vertex to vertex u
     *  computed by the algorithm is stored in dist[u].
     *  The algorithm also uses a first-in first-out ArrayBlockingQueue q
     *  to manage the set of gray vertices
     */
    public static class BFSVect {

        private  int size; //number of vertexes
        private final int infinity = 99999;
        int vertex;
        Vector<LinkedList<Integer>>  graph = new Vector<LinkedList<Integer>>();
        Queue<Integer> q;
        int dist[], pred[], color[];
        final int WHITE=1, GRAY=2,  BLACK=3, NIL = -1;

        // constructor
        public BFSVect(Vector<LinkedList<Integer>> graph){
            this.graph = graph;
            this.size = graph.size();
            q = new ArrayBlockingQueue<Integer>(size);
            dist = new int[size];
            pred = new int[size];
            color = new int[size];
        }

        // clear arrays
        private void clear(){
            for (int i=0; i<size; i++){
                dist[i] = infinity;
                pred[i] = NIL;
                color[i] = WHITE;
            }
        }

        // BFS Algorithm O(E+V)
        public void BFS(int s){
            clear();
            vertex = s;
            color[vertex] = GRAY;
            dist[vertex] = 0;
            pred[vertex] = NIL;
            q.offer(vertex);
            Iterator<Integer> iter;
            LinkedList<Integer> list;
            while (!q.isEmpty()){
                int u = q.poll();
                list  = graph.elementAt(u);
                iter = list.iterator();
                while (iter.hasNext()){
                    int v = iter.next();
                    if (color[v]==WHITE){
                        color[v] = GRAY;
                        dist[v] = dist[u]+1;
                        pred[v] = u;
                        q.offer(v);
                    }
                }
                color[u] = BLACK;
            }
        }
        public boolean isConnected(){
            boolean ans = true;
            for (int i=1; ans && i<size; i++){
                if(dist[i] == infinity) ans = false;
            }
            return ans;
        }




        /*
         * Minimum Spanning Tree Reverse Kruskal Algorithm
         */

        public static class MST_graph {
            Vector<Edge> input_graph; // the input = all the edge in the graph
            Vector<LinkedList<Integer>> MST_Tree; // The Answer that will be- MST Tree
            int num_Edges, num_Vertexes;


            // CONSTRUCTOR
            public MST_graph(Vector<Edge> graph) {

                MST_Tree = new Vector<LinkedList<Integer>>();
                this.input_graph = graph;
                num_Edges = graph.size();

                sortGraph(); // sort the Edges from the biggest value to the smallest value
                MST_ReverseAlgo(num_Edges +1);

                int count = 1;
                // number of vertexes calculation
                while(!MST_Tree.elementAt(count).isEmpty()){
                    count++;
                }
                num_Vertexes = count-1;
                MST_Tree.setSize(count);
            }


            public void MST_ReverseAlgo(int length){
                //initialization
                MST_Tree.clear();
                for (int i=0; i<length+1; i++){
                    MST_Tree.add(new LinkedList<Integer>());
                }
                // creation
                for (int i = 0; i< num_Edges; i++){
                    int a = input_graph.elementAt(i).vertexA;
                    int b = input_graph.elementAt(i).vertexB;
                    if (!MST_Tree.elementAt(a).contains(b)){
                        MST_Tree.elementAt(a).add(b);
                    }
                    if (!MST_Tree.elementAt(b).contains(a)){
                        MST_Tree.elementAt(b).add(a);
                    }
                }
            }
            public void createMst(){
                // count = number of edges in minimum spanning tree
                // count = numOfVertexes-1
                // number of edges to remove = numOgEdges - (numOfVertexes-1)
                int index=0, edgeIndex=0;
                MST_ReverseAlgo(num_Vertexes +1);
                MST_Tree.setSize(num_Vertexes +1);
                int count= num_Edges -(num_Vertexes -1);
                while (index < count){
                    Edge e = input_graph.elementAt(edgeIndex);
                    input_graph.remove(edgeIndex);
                    num_Edges--;
                    MST_ReverseAlgo(num_Vertexes +1);
                    MST_Tree.setSize(num_Vertexes +1);
                    BFSVect bfs = new BFSVect(MST_Tree);
                    bfs.BFS(e.vertexA);
                    if (bfs.isConnected()){
                        index++;
                    }
                    else{
                        input_graph.add(edgeIndex, e);
                        edgeIndex++;
                        num_Edges++;
                    }
                }
            }


            public double calcSummWieight(){
                double w = 0;
                for (int i = 0; i< input_graph.size(); i++){
                    w = w + input_graph.elementAt(i).weight;
                }
                return w;
            }


            public void sortGraph(){
                boolean flag = true;
                for (int i = 0; flag && i< num_Edges; i++){
                    flag = false;
                    for (int j = 0; j< num_Edges -1; j++){
                        if (input_graph.elementAt(j).compareTo(input_graph.elementAt(j+1))==-1){
                            Edge t = input_graph.elementAt(j);
                            input_graph.set(j, input_graph.elementAt(j+1));
                            input_graph.set(j+1,t);
                            flag = true;
                        }
                    }
                }
            }


            public static Vector<Edge> init1(){
                Vector<Edge> graph  = new Vector<Edge>();
                graph.add(new Edge(1,2,4));// 1-st edge
                graph.add(new Edge(2,3,8));// 2-st edge
                graph.add(new Edge(3,4,7));// 3-st edge
                graph.add(new Edge(4,5,9));// 4-st edge
                graph.add(new Edge(5,6,10));// 5-st edge
                graph.add(new Edge(6,7,2));// 6-st edge
                graph.add(new Edge(7,8,1));// 7-st edge
                graph.add(new Edge(8,1,8));// 8-st edge
                graph.add(new Edge(2,8,11));// 9-st edge
                graph.add(new Edge(3,9,2));// 10-st edge
                graph.add(new Edge(3,6,4));// 11-st edge
                graph.add(new Edge(4,6,14));// 12-st edge
                graph.add(new Edge(7,9,6));// 13-st edge
                graph.add(new Edge(8,9,7));// 14-st edge
                return graph;
            }

            public static void main(String[] args) {
                MST_graph mst = new MST_graph(init1());
                mst.createMst();
                System.out.println("Graph : "+mst.input_graph.toString());
                System.out.println("MST Weight: "+mst.calcSummWieight());
            }
        }
/**
 Graph : [(4,5,w:9), (8,1,w:8), (3,4,w:7), (1,2,w:4),
 (3,6,w:4), (6,7,w:2), (3,9,w:2), (7,8,w:1)]

 **/








        /**
         *  graph with circles
         */
        @SuppressWarnings("unchecked")
        public static Vector<LinkedList<Integer>> initList1(){
            Vector<LinkedList<Integer>>  graph = new Vector<LinkedList<Integer>>();
            int size = 9;
            LinkedList<Integer>[]  list = new LinkedList[size];
            for (int i=0; i<size; i++){
                list[i] = new LinkedList<Integer>();
            }
            list[0].add(-1);
            graph.add(list[0]);
// first node
            list[1].add(2);list[1].add(5);
            graph.add(list[1]);
// second node
            list[2].add(1);list[2].add(6);
            graph.add(list[2]);
// third node
            list[3].add(6);list[3].add(7);list[3].add(4);
            graph.add(list[3]);
// fourth node
            list[4].add(3);list[4].add(7);list[4].add(8);
            graph.add(list[4]);
// fifth node
            list[5].add(1);
            graph.add(list[5]);
// sixth mode
            list[6].add(2);list[6].add(3);list[6].add(7);
            graph.add(list[6]);
// seventh node
            list[7].add(6);list[7].add(3);list[7].add(4);list[7].add(8);
            graph.add(list[7]);
// eighth node
            list[8].add(7);list[8].add(4);
            graph.add(list[8]);
            return graph;
        }
        /**
         *  graph with circles
         */
        @SuppressWarnings("unchecked")
        public static Vector<LinkedList<Integer>> initList2(){
            Vector<LinkedList<Integer>>  graph = new Vector<LinkedList<Integer>>();
            int size = 9;
            LinkedList<Integer>[]  list = new LinkedList[size];
            for (int i=0; i<size; i++){
                list[i] = new LinkedList<Integer>();
            }
            list[0].add(-1);
            graph.add(list[0]);
// first node
            list[1].add(2);list[1].add(5);
            graph.add(list[1]);
// second node
            list[2].add(1);list[2].add(6);
            graph.add(list[2]);
// third node
            list[3].add(6);
            graph.add(list[3]);
// fourth node
            list[4].add(7);
            graph.add(list[4]);
// fifth node
            list[5].add(1);
            graph.add(list[5]);
// sixth mode
            list[6].add(2);list[6].add(3);list[6].add(7);
            graph.add(list[6]);
// seventh node
            list[7].add(6);list[7].add(4);list[7].add(8);
            graph.add(list[7]);
// eighth node
            list[8].add(7);
            graph.add(list[8]);
            return graph;
        }
        @SuppressWarnings("unchecked")
        public static Vector<LinkedList<Integer>> initList3(){
            Vector<LinkedList<Integer>>  graph = new Vector<LinkedList<Integer>>();
            int size = 10;
            LinkedList<Integer>[]  list = new LinkedList[size];
            for (int i=0; i<size; i++){
                list[i] = new LinkedList<Integer>();
            }
            list[0].add(-1);
            graph.add(list[0]);
// first node
            list[1].add(2);list[1].add(5);
            graph.add(list[1]);
// second node
            list[2].add(1);list[2].add(6);
            graph.add(list[2]);
// third node
            list[3].add(6);
            graph.add(list[3]);
// fourth node
            list[4].add(7);
            graph.add(list[4]);
// fifth node
            list[5].add(1);
            graph.add(list[5]);
// sixth mode
            list[6].add(2);list[6].add(3);list[6].add(7);
            graph.add(list[6]);
// seventh node
            list[7].add(6);list[7].add(4);list[7].add(8);
            graph.add(list[7]);
// eighth node
            list[8].add(7); list[8].add(9);
            graph.add(list[8]);
// ninth node
            list[9].add(8);
            graph.add(list[9]);
            return graph;
        }
        @SuppressWarnings("unchecked")
        /// disconnected graph
        public static Vector<LinkedList<Integer>> initList4(){
            Vector<LinkedList<Integer>>  graph = new Vector<LinkedList<Integer>>();
            int size = 10;
            LinkedList<Integer>[]  list = new LinkedList[size];
            for (int i=0; i<size; i++){
                list[i] = new LinkedList<Integer>();
            }
            list[0].add(-1);
            graph.add(list[0]);
// first node
            list[1].add(2);   list[1].add(3);
            graph.add(list[1]);
// second node
            list[2].add(1);  list[2].add(3);
            graph.add(list[2]);
// third node
            list[3].add(1);  list[3].add(2);
            graph.add(list[3]);
// 4-th node
            list[4].add(5);
            graph.add(list[4]);
// 5-th node
            list[5].add(4);
            graph.add(list[5]);
            return graph;
        }

        public static void main(String[] args) {
            // number of first node is 1
            BFSVect bf= new BFSVect(initList4());
            bf.BFS(1);
            System.out.println("Graf is connected: "+bf.isConnected());
        }
    }


}
