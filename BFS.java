package Algo2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * find the min distance from source vertex to other vertexes
 */
    public class BFS {

        int[][] mat;
        int[] color; // 0=white , 1=gray , 2=black
        int[] distance;
        int[] father;
        int numV; //numberOfVertecies
        int components[]; // vadim
        int source ; // vadim
        int numComps; // vadim


        //constructor
        public BFS(int[][] matrix) {
            mat = matrix;
            numV = mat.length;
            color = new int[numV];
            distance = new int[numV];
            father = new int[numV];
            components = new int[numV];
            source = 0;
            numComps = 0;
        }

    /**
     * This function is BFS algorithm
     * @param s is the source vertex
     */
    private void BFS_Algo(int s) {
            Queue<Integer> q = new LinkedList<>();

            Initialize(); // init graph
            q.add(s);

            color[s] = 1; // colors source vertex to gray
            distance[s] = 0; // set distance from himself to 0
            father[s] = -1; // he is the source so he did not have father

            int v;
            while (q.isEmpty() == false){ // while there is vertexes in the queue :

                v = q.poll();
                for (int u = 0; u < numV; u++) {
                    if (color[u] == 0 && mat[v][u] == 1){  // here we check the nei of v,all u's are nei
                           // caz they in the col of v. if u is white and there is path between u and v so -
                        color[u] = 1; // colors u with gray
                        distance[u] = distance[v]+1; //  set his distance
                        father[u] = v; // set his father
                        q.add(u); // u is gray so we put hi, in the queue
                    }
                }
                color[v] = 2; // v is black now , out of the game
            }
        }

        private void Initialize() {
            for (int v = 0; v < numV; v++) {
                color[v] = 0; // set all vertexes to white
                distance[v] = Integer.MAX_VALUE; // init dist to 0
                father[v] = -1; // they have not father yet
            }
        }

        // check if the graph is connected
        public boolean IsConnected() {
            for (int v = 0; v < numV; v++) {
                if (color[v] == 0) // if there is some white vertex --> the meaning is that the graph not connected
                       return false;
            }
            return true; // if all the vertexes of the graph is black so the graph is connected
        }

        public boolean IsTherePathBetween_v_and_u(int v, int u) {
            BFS_Algo(v);
            if (color[u] == 2)
                return true;
            return false;
        }

        //after checking IsTherePathBetween_v_and_u we return the path between them
        public ArrayList<Integer> GetPathBetween_v_and_u(int v, int u) {

            ArrayList<Integer> path = new ArrayList<Integer>();

            int vertex = u;
            while(father[vertex] != -1) {
                path.add(vertex);
                vertex = father[vertex];
            }
            path.add(vertex);

            //reverse the list
            int temp;
            for (int i = 0; i < path.size()/2; i++) {
                temp = path.get(i);
                path.set(i, path.get(path.size()-1-i));
                path.set(path.size()-1-i, temp);
            }
            return path;
        }



      // my edition to BSF Algorithm
///////////////////////////////////////////////////////////////////////////////////////////////////////
        private void connectedComponents(){
            while (hasNextComponent()){
                numComps++;
                BFS_Algo(source);
                for (int i = 0; i < components.length; i++) {
                    if (distance[i]!=Integer.MAX_VALUE) components[i] = numComps;
                }
            }
        }
        private boolean hasNextComponent(){
            boolean ans = false;
            for (int i = 0; !ans && i < components.length; i++) {
                if(components[i] == 0) {
                    ans = true;
                    source = i;
                }
            }
            return ans;
        }

        public String getAllComponents(){
            connectedComponents();
            ArrayList<Integer>[] compsList = new ArrayList[numComps];
            for (int i = 0; i < compsList.length; i++) {
                compsList[i] = new ArrayList<Integer>();
            }
            for (int i = 0; i < components.length; i++) {
                int n = components[i];
                compsList[n-1].add(i);
            }
            String ans = new String();
            for (int i = 0; i < compsList.length; i++) {
                ans = ans + compsList[i] + "\n";
            }
            return ans;
        }


    public int numberComponents(){
        connectedComponents();
        ArrayList<Integer>[] compsList = new ArrayList[numComps];
        for (int i = 0; i < compsList.length; i++) {
            compsList[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < components.length; i++) {
            int n = components[i];
            compsList[n-1].add(i);
        }
        return compsList.length;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////

        public static void main(String[] args) {
            int[][] graph = {{0,1,1,0,0,0,0},
                             {1,0,1,0,0,0,0},
                             {1,1,0,0,0,0,0},
                             {0,0,0,0,1,0,1},
                             {0,0,0,1,0,1,0},
                             {0,0,0,0,1,0,1},
                             {0,0,0,1,0,1,0}};

            BFS bfs = new BFS(graph);
            System.out.println("there is path between u and v ? ");
            System.out.println(bfs.IsTherePathBetween_v_and_u(5,0));
            System.out.println(" ");
            System.out.println("the path between u and v : ");
            System.out.println(bfs.GetPathBetween_v_and_u(5, 0));
            System.out.println(" ");
            System.out.println("which vertex in each component : ");
            System.out.println(bfs.getAllComponents());
            System.out.println(" ");
            System.out.println("number of Components in the graph  : ");
            System.out.println(bfs.numberComponents());

        }
    }






























