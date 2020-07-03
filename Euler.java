package Algo2;
import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;


    class Edge{
    int begin, end;

    public Edge(int b, int e){
        begin = b;
        end = e;
    }

    public String toString(){
        return "(" + begin + "," + end + ")";
    }
}

    class EulerCycle { // inner class
    ArrayList<Integer>[] graph;//input graph
    ArrayList<Integer> C; // C is the Euler cycle
    Stack<Integer> S; // S present our path on the graph, if we closed cycle so we push the last vertex to the
                     // ArrayList C that present the Cycle
    int degrees[]; // the degree of each vertex in the graph
    int numOfVertexes; // how much vertexes there is in the graph

    // constructor
    public EulerCycle(ArrayList<Integer>[] graph) {
        S = new Stack<Integer>();
        C = new ArrayList<Integer>();
        this.graph = graph;
        numOfVertexes = this.graph.length;
        degrees = new int[numOfVertexes];
        for (int i = 0; i < graph.length; i++) { // in this for loop we -
            degrees[i] = graph[i].size(); // init the degree of each vertex in the graph
        }
    }

        /**
         * This function check if the graph have an Euler Cycle
         * There is an Euler Cycle  if and only if all the degrees of the vertexes are even
         * @return True= there is a Cycle , False= there is no Cycle
         */
        public boolean checkIfEulerGraph() {
            boolean ans = true;
            for (int i = 0; ans && i < graph.length; i++) {
                if (graph[i].size() % 2 != 0) ans = false; // check if the degree of some vertex is odd
            }
            return ans; // if all the degrees are even so the graph have an Euler Cycle
        }


        /**
         * This function build the Euler Cycle
         * @return False if there is no Euler Cycle,
         * else- return True if there is Euler Cycle and also build the Euler cycle of the graph
         */
    public boolean BuildEulerCycle() {

        boolean ans = checkIfEulerGraph();
        if (!ans) {  System.out.println("The Graph has no Euler Cycle"); }
        else {
            int u , v0 = 0;
            S.push(v0); // v0 is the vertex that we choose to start with it
            while (!S.empty()) {//O(|E|)
                v0 = S.peek();//returns the object at the top of this stack without removing it from the stack.
                if (degrees[v0] == 0) { // if degree of v0 is 0 so we closed a Cycle caz we delete all his nei
                    C.add(v0);// add v0 to Euler Cycle C ,complex-O(1)
                    S.pop();// remove v0 from the stack path S, complex-O(1)
                }
                else { // we have not closed a Cycle yet
                    u = graph[v0].get(0); // looking for nei of v0 that will be u
                    S.add(u); // push the nei u to the stack path S
                    degrees[v0]--; // degree[v0] = remove edge, and if we got degree=0 its means that we stack And we
                                  // have nowhere to go

                    degrees[u]--;
                    graph[v0].remove((Integer) u); // complex- O(|V|)
                    graph[u].remove((Integer) v0); // complex- O(|V|)
                }
            }
        }
        return ans;
    }

    public void printEulerCycle() {
        System.out.println("The Euler Cycle:");
        System.out.println(C.toString());
    }

    public void printGraph() {
        System.out.println("The Graph:");
        System.out.println(Arrays.toString(graph));
    }

    public void addEdges() {
        ArrayList<Edge> eg = new ArrayList<Edge>();
        int i = 0, beg = 0, end = 0;
        while (i < graph.length) {
            if (graph[i].size() % 2 != 0) {
                beg = i++;
                boolean flag = true;
                while (flag && i < graph.length) {
                    if (graph[i].size() % 2 != 0) {
                        eg.add(new Edge(beg, i));
                        flag = false;
                        graph[beg].add(i);
                        degrees[beg]++;
                        graph[i].add(beg);
                        degrees[i]++;
                    } else i++;
                }
            }
            i++;
        }
        System.out.println(eg);
        System.out.println(Arrays.toString(graph));
    }


    //----------------------------------init graphs and main------------------------------------------------

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
            EulerCycle ec1 = new EulerCycle(InitGraphs.init1());
            //		ec.addEdges();
            //		ec.printGraph();
            ec1.BuildEulerCycle();
            ec1.printEulerCycle();
            ////
            EulerCycle ec2 = new EulerCycle(InitGraphs.init2());
            ec2.BuildEulerCycle();
            ec2.printEulerCycle();
            ////
            EulerCycle ec3 = new EulerCycle(InitGraphs.init3());
            ec3.BuildEulerCycle();
            ec3.printEulerCycle();
            ////
            EulerCycle ec4 = new EulerCycle(InitGraphs.init4());
            ec4.BuildEulerCycle();
            ec4.printEulerCycle();
            ////
            EulerCycle ec5 = new EulerCycle(InitGraphs.init5());
            ec5.BuildEulerCycle();
            ec5.printEulerCycle();
            ////
            EulerCycle ec6 = new EulerCycle(InitGraphs.init6());
            ec6.BuildEulerCycle();
            ec6.printEulerCycle();
            ////
            EulerCycle ec7 = new EulerCycle(InitGraphs.init7());
            ec7.BuildEulerCycle();
            ec7.printEulerCycle();

        }
    }
}
/** init1: The Euler Cycle:  [0, 2, 4, 3, 2, 1, 0]
 init2: The Euler Cycle:  [0, 4, 3, 2, 4, 1, 3, 0, 2, 1, 0]
 init3: The Euler Cycle:  [0, 8, 7, 0, 6, 5, 0, 4, 3, 0, 2, 1, 0]
 init4: The Graph has no Euler Cycle, The Euler Cycle: []
 init5: The Euler Cycle: [0, 3, 2, 1, 0]
 init6: The Euler Cycle: [0, 4, 5, 3, 4, 2, 3, 0, 2, 1, 0]
 init7: The Euler Cycle: [0, 2, 1, 0]

 */