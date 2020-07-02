package Algo2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

    public class BFS {

        int[][] mat;
        int[] color;
        int[] distance;
        int[] father;
        int V; //numberOfVertecies

        int components[]; // vadim
        int source ; // vadim
        int numComps; // vadim


        public BFS(int[][] matrix)
        {
            mat = matrix;
            V = mat.length;
            color = new int[V];
            distance = new int[V];
            father = new int[V];
            components = new int[V];
            source = 0;
            numComps = 0;
        }

        private void Run_BFS(int s) {
            Queue<Integer> q = new LinkedList<>();

            Initialize();

            q.add(s);
            color[s] = 1; // check if 1 is gray
            distance[s] = 0;
            father[s] = -1;

            int v;
            while (q.isEmpty() == false)
            {
                v = q.poll();
                for (int u = 0; u < V; u++)
                {
                    if (color[u] == 0 && mat[v][u] == 1) // 0 is white
                    {
                        color[u] = 1;
                        distance[u] = distance[v]+1;
                        father[u] = v;
                        q.add(u);
                    }
                }
                color[v] = 2; // v is black now , go out of the game
            }
        }

        private void Initialize() {
            for (int v = 0; v < V; v++) {
                color[v] = 0;
                distance[v] = Integer.MAX_VALUE; // היה במקום האינסוף 0, לבדוק אם בסדר
                father[v] = -1;
            }
        }

        public boolean IsConnected()
        {
            for (int v = 0; v < V; v++)
            {
                if (color[v] == 0) // if there is some white vertex --> the meaning is that the graph not connected
                    return false;
            }
            return true; // if all the vertexes of the graph is black so the graph is connected
        }

        public boolean IsTherePathBetween_v_and_u(int v, int u)
        {
            Run_BFS(v);
            if (color[u] == 2)
                return true;
            return false;
        }

        //after checking IsTherePathBetween_v_and_u
        public ArrayList<Integer> GetPathBetween_v_and_u(int v, int u)
        {
            ArrayList<Integer> path = new ArrayList<Integer>();

            int vertex = u;
            while(father[vertex] != -1)
            {
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

        // את זה אני הוספתי -לבדוק אם נכון
///////////////////////////////////////////////////////////////////////////////////////////////////////
        private void connectedComponents(){
            while (hasNextComponent()){
                numComps++;
                Run_BFS(source);
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
/////////////////////////////////////////////////////////////////////////////////////////////////

        public static void main(String[] args) {
            int[][] graph = {{0,0,0,1,0,0},
                    {0,0,0,1,1,1},
                    {0,0,0,1,1,1},
                    {1,1,1,0,0,0},
                    {0,1,1,0,0,0},
                    {0,1,1,0,0,0}};

            BFS bfs = new BFS(graph);
            System.out.println(bfs.IsTherePathBetween_v_and_u(5,0));
            System.out.println(bfs.GetPathBetween_v_and_u(5, 0));
            System.out.println(bfs.getAllComponents());
            System.out.println(bfs.numComps);
          //  System.out.println(bfs.V);
        }
    }

























