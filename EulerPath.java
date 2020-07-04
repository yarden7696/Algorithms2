package Algo2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


    public class EulerPath {

        ArrayList<Integer>[] graph;//input graph
        ArrayList<Integer> C; // C is the Euler cycle
        Stack<Integer> S; // S present our path on the graph, if we closed cycle so we push the last vertex to the
        // ArrayList C that present the Cycle
        int degrees[]; // the degree of each vertex in the graph
        int numOfVertexes; // how much vertexes there is in the graph
        int source;

        //CONSTRUCTOR
        public EulerPath(ArrayList<Integer>[] graph){
            S = new Stack<Integer>();
            C = new ArrayList<Integer>();
            this.graph = graph;
            numOfVertexes = this.graph.length;
            degrees = new int[numOfVertexes];
            for (int i = 0; i < graph.length; i++) {
                degrees[i] = graph[i].size();
            }
        }


        /**
         * This function checks if the graph has Euler Path.
         * There is an Euler Path if and only if the graph is connected and only 2 vertexes have odd degree.
         * @return True=there id Euler Path,  False=there is no Euler Path
         */
        public boolean checkIfEulerPath(){
            int odd = 0;
            for (int i = 0; i < graph.length; i++) {
                if (graph[i].size()%2 != 0) { // check how many vertexes have odd degrees
                    odd++;
                    source = i;
                }
            }
            return odd==2;
        }


        /**
         * This function return a boolean answer. if the graph has an Euler Path so this function build
         * Euler Cycle on this graph and then use Euler Cycle function to find Euler Cycle.
         * At the end the function remove all edges and vertexes that we add to get the Euler Path.
         * @return
         */
        public boolean buildEulerPath(){

            boolean ans = checkIfEulerPath();
            if (!ans) { System.out.println("The Graph has no Euler Path"); }

            else{
                int v = source, u;
                S.push(v);
                while(!S.empty()){//O(|E|)
                    v = S.peek();//returns the object at the top of this
                    // stack without removing it from the stack.
                    if (degrees[v] == 0){
                        C.add(v);//O(1)
                        S.pop();//O(1)
                    }
                    else{
                        u = graph[v].get(0);
                        S.add(u);
                        degrees[v]--;
                        degrees[u]--;
                        graph[v].remove((Integer)u);//O(|V|)
                        graph[u].remove((Integer)v);//O(|V|)
                    }
                }
            }
            return ans;
        }
        public void printEulerPath(){
            System.out.println("The Euler Path:");
            System.out.println(C.toString());
        }
        public void printGraph(){
            System.out.println("The Graph:");
            System.out.println(Arrays.toString(graph));
        }

        public static void main(String[] args) {
            EulerPath p = new EulerPath(init1());
            p.buildEulerPath();
            p.printEulerPath();
        }
        @SuppressWarnings("unchecked")
        public static ArrayList<Integer>[] init1(){//cycle: [0, 2, 4, 3, 2, 1, 0]
            int numVert = 5;
            ArrayList<Integer>[] graph = new ArrayList[numVert];
            for (int i=0; i<graph.length; i++){
                graph[i] = new ArrayList<Integer>();
            }

            graph[0].add(1); graph[0].add(2);
            graph[1].add(0); graph[1].add(2); graph[1].add(4);
            graph[2].add(0); graph[2].add(1); graph[2].add(3); graph[2].add(4);
            graph[3].add(2); graph[3].add(4);
            graph[4].add(3); graph[4].add(2); graph[4].add(1);

            return graph;

        }

    }

