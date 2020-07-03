package Algo2;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.ArrayList;
import java.util.Stack;


    /******************************************************************************
     *  http://algs4.cs.princeton.edu/41graph/EulerianCycle.java.html
     *
     *  Find an Eulerian cycle in a graph, if one exists.
     *
     *  Runs in O(E + V) = O(E) time.
     *
     *  This implementation is tricker than the one for digraphs because
     *  when we use edge v-w from v's adjacency list, we must be careful
     *  not to use the second copy of the edge from w's adjacency list.
     *
     ******************************************************************************/



    public class euler2 {
        // an undirected edge, with a field to indicate whether the edge has already been used
        static class EdgeP {
            final int v;
            final int w;
            boolean isUsed;

            public EdgeP(int v, int w) {
                this.v = v;
                this.w = w;
                isUsed = false;
            }

            // returns the other vertex of the edge
            public int other(int vertex) {
                if (vertex == v) return w;
                else if (vertex == w) return v;
                else throw new IllegalArgumentException("Illegal endpoint");
            }

            public String toString() {
                return "[" + v + "," + w + "," + isUsed + "]";
            }
        }

        public static class EulerianCyclePrinston {
            /**
             * Eulerian cycle; null if no such cycle
             * Returns the sequence of vertices on an Eulerian cycle.
             * returns null if no such cycle
             *
             * @return the sequence of vertices on an Eulerian cycle;
             * {@code null} if no such cycle
             */
            public static Stack<Integer> eulerianCycle(ArrayList<Integer>[] G) {

                Stack<Integer> cycle = new Stack<Integer>();
                int nEdges = numOfEdges(G); //O(E)

                // must have at least one edge and the graph must be Eulerian
                if (nEdges == 0 || !isEulerian(G)) return null;

                // create local view of adjacency lists, to iterate one vertex at a time
                // the helper EdgeP data type is used to avoid exploring both copies of an edge v-w
                ArrayBlockingQueue<EdgeP>[] adj = buildEdges(G, nEdges);

                // initialize stack with any non-isolated vertex
                int s = nonIsolatedVertex(G);//O(V)
                Stack<Integer> stack = new Stack<Integer>();
                stack.push(s);

                // greedily search through edges in iterative DFS style
                cycle = new Stack<Integer>();
                while (!stack.isEmpty()) { //O(E)
                    int v = stack.pop();
                    while (!adj[v].isEmpty()) {
                        EdgeP edge = adj[v].poll();//remove
                        if (!edge.isUsed) {
                            edge.isUsed = true;
                            stack.push(v);
                            v = edge.other(v);
                        }
                    }
                    // push vertex with no more leaving edges to cycle
                    cycle.push(v);
                }
                // check if all edges are used
                if (cycle.size() != nEdges + 1)
                    cycle = null;
                return cycle;
            }

            // returns any non-isolated vertex; -1 if no such vertex
            private static int nonIsolatedVertex(ArrayList<Integer>[] G) {
                for (int v = 0; v < G.length; v++)
                    if (G[v].size() > 0)
                        return v;
                return -1;
            }

            private static int numOfEdges(ArrayList<Integer>[] G) { //O(E)
                int ans = 0;
                for (int i = 0; i < G.length; i++) {
                    ans = ans + G[i].size();
                }
                return ans / 2;
            }

            // necessary condition: all vertices have even degree
            // (this test is needed or it might find an Eulerian path instead of cycle)
            private static boolean isEulerian(ArrayList<Integer>[] G) {
                for (int v = 0; v < G.length; v++) {//O(E)
                    if (G[v].size() % 2 != 0) {
                        System.out.println("vertex " + v + " has odd degree!");
                        return false;
                    }
                }
                return true;
            }

            private static ArrayBlockingQueue<EdgeP>[] buildEdges(ArrayList<Integer>[] G, int nEdges) {
                int nVertices = G.length;
                ArrayBlockingQueue<EdgeP>[] adj = new ArrayBlockingQueue[nVertices];
                for (int v = 0; v < nVertices; v++)//O(V)
                    adj[v] = new ArrayBlockingQueue<EdgeP>(nEdges + 1);

                for (int v = 0; v < nVertices; v++) {//O(E)
                    for (int w : G[v]) {
                        if (v < w) {
                            EdgeP e = new EdgeP(v, w);
                            adj[v].add(e);
                            adj[w].add(e);
                        }
                    }
                }
                return adj;
            }

            public static class InitGraphs {
                @SuppressWarnings("unchecked")
                public static ArrayList<Integer>[] init1() {//cycle: [0, 2, 4, 3, 2, 1, 0]
                    int numVert = 5;
                    ArrayList<Integer>[] graph = new ArrayList[numVert];
                    for (int i = 0; i < graph.length; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[2].add(0);
                    graph[2].add(1);
                    graph[2].add(3);
                    graph[2].add(4);
                    graph[3].add(2);
                    graph[3].add(4);
                    graph[4].add(3);
                    graph[4].add(2);
                    return graph;
                }

                @SuppressWarnings("unchecked")
                public static ArrayList<Integer>[] init2() {//pentagon, cycle: [0, 4, 3, 2, 4, 1, 3, 0, 2, 1, 0]
                    int numVert = 5;
                    ArrayList<Integer>[] graph = new ArrayList[numVert];
                    for (int i = 0; i < graph.length; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[0].add(3);
                    graph[0].add(4);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[1].add(3);
                    graph[1].add(4);
                    graph[2].add(0);
                    graph[2].add(1);
                    graph[2].add(3);
                    graph[2].add(4);
                    graph[3].add(0);
                    graph[3].add(1);
                    graph[3].add(2);
                    graph[3].add(4);
                    graph[4].add(0);
                    graph[4].add(1);
                    graph[4].add(2);
                    graph[4].add(3);
                    return graph;
                }

                @SuppressWarnings("unchecked")
                public static ArrayList<Integer>[] init3() {// cycle: [0, 8, 7, 0, 6, 5, 0, 4, 3, 0, 2, 1, 0]
                    int numVert = 9;
                    ArrayList<Integer>[] graph = new ArrayList[numVert];
                    for (int i = 0; i < graph.length; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[0].add(3);
                    graph[0].add(4);
                    graph[0].add(5);
                    graph[0].add(6);
                    graph[0].add(7);
                    graph[0].add(8);

                    graph[1].add(0);
                    graph[1].add(2);
                    graph[2].add(0);
                    graph[2].add(1);
                    graph[3].add(0);
                    graph[3].add(4);
                    graph[4].add(0);
                    graph[4].add(3);
                    graph[5].add(0);
                    graph[5].add(6);
                    graph[6].add(0);
                    graph[6].add(5);
                    graph[7].add(0);
                    graph[7].add(8);
                    graph[8].add(0);
                    graph[8].add(7);

                    return graph;
                }

                @SuppressWarnings("unchecked")
                public static ArrayList<Integer>[] init4() {//has no Euler Cycle
                    int numVert = 4;
                    ArrayList<Integer>[] graph = new ArrayList[numVert];
                    for (int i = 0; i < graph.length; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[0].add(3);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[1].add(3);
                    graph[2].add(1);
                    graph[2].add(0);
                    graph[2].add(3);
                    graph[3].add(1);
                    graph[3].add(2);
                    graph[3].add(0);
                    return graph;
                }

                @SuppressWarnings("unchecked")
                public static ArrayList<Integer>[] init5() {//square ,the cycle: [0, 3, 2, 1, 0]
                    int size = 4;
                    ArrayList<Integer>[] graph = new ArrayList[size];
                    for (int i = 0; i < size; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(3);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[2].add(1);
                    graph[2].add(3);
                    graph[3].add(0);
                    graph[3].add(2);
                    return graph;
                }

                public static ArrayList<Integer>[] init6() {//the cycle: [0, 4, 5, 3, 4, 2, 3, 0, 2, 1, 0]
                    int size = 6;
                    ArrayList<Integer>[] graph = new ArrayList[size];
                    for (int i = 0; i < size; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[0].add(3);
                    graph[0].add(4);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[2].add(0);
                    graph[2].add(1);
                    graph[2].add(3);
                    graph[2].add(4);
                    graph[3].add(0);
                    graph[3].add(2);
                    graph[3].add(4);
                    graph[3].add(5);
                    graph[4].add(0);
                    graph[4].add(2);
                    graph[4].add(3);
                    graph[4].add(5);
                    graph[5].add(3);
                    graph[5].add(4);
                    return graph;
                }

                public static ArrayList<Integer>[] init7() {//triangle, the cycle:[0, 2, 1, 0]
                    int size = 3;
                    ArrayList<Integer>[] graph = new ArrayList[size];
                    for (int i = 0; i < size; i++) {
                        graph[i] = new ArrayList<Integer>();
                    }
                    graph[0].add(1);
                    graph[0].add(2);
                    graph[1].add(0);
                    graph[1].add(2);
                    graph[2].add(0);
                    graph[2].add(1);
                    return graph;
                }


                public static void main(String[] args) {
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init1()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init2()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init3()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init4()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init5()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init6()));
                    System.out.println(EulerianCyclePrinston.eulerianCycle(InitGraphs.init7()));
                }
            }
/*
[0, 2, 4, 3, 2, 1, 0]
[0, 4, 3, 2, 4, 1, 3, 0, 2, 1, 0]
[0, 8, 7, 0, 6, 5, 0, 4, 3, 0, 2, 1, 0]
vertex 0 has odd degree!
[]
[0, 3, 2, 1, 0]
[0, 4, 5, 3, 4, 2, 3, 0, 2, 1, 0]
[0, 2, 1, 0]
*/
        }
    }
