package Algo2;
import java.util.ArrayList;


public class Prim {

        HeapMin Q;
        ArrayList<Node>[] graph;
        int [] key, parents;
        boolean [] visited;
        int numOfVertexes, nEdges, root;
        final int infinity = Integer.MAX_VALUE, NIL = -1;
        Edge T[];
        public Prim(ArrayList<Node>[] G, int root){
            graph = G;
            numOfVertexes = graph.length;
            this.root = root;
            key = new int[numOfVertexes];
            parents = new int[numOfVertexes];
            visited = new boolean[numOfVertexes];
            T = new Edge[numOfVertexes-1];
            nEdges =  0;
            for (int i=0; i<numOfVertexes; i++) {
                key[i] = infinity;
                parents[i] = NIL;
                visited[i] = false;
            }
            key[root] = 0;
            Q = new HeapMin();
            Q.minHeapInsert(new Node(root,0));
            for (int i=1; i<numOfVertexes; i++){
                Q.minHeapInsert(new Node(i,infinity));
            }
        }
        public void MST_Prim(){
            while(!Q.isEmpty() && nEdges<numOfVertexes-1){
                int u = Q.heapExtractMin().vertex;
                for (int i=0; i<graph[u].size(); i++){
                    Node n = graph[u].get(i);
                    int v = n.vertex;
                    int uvWeight = n.key;
                    if (!visited[v] &&  uvWeight < key[v]){
                        parents[v] = u;
                        key[v] = uvWeight;
                        Q.heapDecreaseKey(n);
                    }
                }
                visited[u] = true;
                Node x =  Q.heapGetMin();
                T[nEdges++] = new Edge(parents[x.vertex], x.vertex, x.key);
            }
        }
        void PrintMST(){
            int sumWeight = 0;
            for (int i = 0; i < key.length; i++) {
                sumWeight = sumWeight +key[i];
            }
            System.out.println("MSP Prim Summary Weight: "+sumWeight);
            System.out.print("MSP Prim Weights :  ");
            for (int i = 0; i < key.length; i++) {
                System.out.print((key[i])+", ");
            }
            System.out.println();
            System.out.print("MSP Prim parents :  ");
            for (int i = 0; i < parents.length; i++) {
                System.out.print((parents[i])+", ");
            }
            System.out.println();
            for (int i = 0; i < T.length; i++) {
                System.out.print(T[i] + ", ");
            }
        }





    public static class Node{
        int key;// the key of the vertex for adjacency table
        int vertex; // the vertex number
        final int WHITE = 0, NIL = -1;
        public Node(int v, int key){
            this.key = key;
            this.vertex = v;
        }
        public Node(){
            this.key = 0;
            this.vertex = -1;
        }
        public Node(Node node) {
            this(node.vertex, node.key);
        }
        public String toString(){
            //return "(w: " + key + ", "+vertex + ")";
            return "(" + vertex+ ", w: " + key + ")";
        }
    }








    public class HeapMin {

        int _positiveInfinity = Integer.MAX_VALUE;
        final int INIT_SIZE = 10;
        private Node _a[];
        private int _size;
        public HeapMin(Node arr[]){
            _size = arr.length;
            _a = new Node[_size];
            for (int i=0; i<_size; i++){
                _a[i]=arr[i];
            }
        }
        public HeapMin(){
            _a = new Node[0];
        }
        /** returns the heap size*/
        public int getSize(){return _size;}
        /** returns the heap array */
        public Node[] getA(){ return _a;}

        /** parent returns the parent of vertex  i*/
        private  int parent(int i){return (i-1)/2;}

        /** leftChild returns the left child of vertex  i*/
        private  int leftChild(int i){return 2*i+1;}
        /** rightChild returns the right child of vertex  i*/
        private  int rightChild(int i){return 2*i+2;}
        /** returns the heap minimum */
        public Node heapMinimum(){return _a[0];}
        /** returns true if the heap is empty, otherwise false */
        public boolean isEmpty(){
            boolean ans = false;
            if (_size == 0) ans = true;
            return ans;
        }
        /** the minHeapfy function maintains the min-heap property */
        private void minHeapify(int v, int heapSize){
            int smallest;
            int left = leftChild(v);
            int right = rightChild(v);
            if (left<heapSize && _a[left].key<_a[v].key){
                smallest = left;
            }
            else{
                smallest = v;
            }
            if (right<heapSize && _a[right].key<_a[smallest].key){
                smallest = right;
            }
            if (smallest!=v){
                exchange(v, smallest);
                minHeapify(smallest, heapSize);
            }
        }

        /** the heap minimum element extraction */
        public Node heapExtractMin(){
            int min = _positiveInfinity; // infinity
            Node node=null;
            if (!isEmpty()){
                node = _a[0];
                min = node.key;
                _a[0]=_a[_size-1];
                _size = _size-1;
                minHeapify(0, _size);
            }
            return node;
        }
        public Node heapGetMin(){
            return _a[0];
        }
        /** the heapDecreaseKey implements the Decrease Key operation*/
        public void heapDecreaseKey(Node node){
            int v = node.vertex;
            int i = 0;
            while (i<_size && v!=_a[i].vertex) i++;
            if (node.key <_a[i].key){
                _a[i] = node;
                while (i>0 && _a[parent(i)].key>_a[i].key){
                    exchange(i, parent(i));
                    i = parent(i);
                }
            }
        }
        /** minHeapInsert function implements the Insert-Key operation*/
        public void minHeapInsert(Node node){//O(log2(n))
            resize(1);
            _a[_size-1] = new Node(node);
            _a[_size-1].key = _positiveInfinity;
            heapDecreaseKey(node);
        }
        /** increment an array*/
        private void resize(int increment){
            Node temp[] = new Node[_size+increment];
            for (int i=0; i<_size; i++){
                temp[i]=_a[i];
            }
            _a = temp;
            _size = _size+increment;
        }
        /** exchange two array elements*/
        private void exchange(int i, int j){
            Node t = _a[i];
            _a[i] = _a[j];
            _a[j] = t;
        }

    }





    class Edge{
        int vertexA, vertexB;
        int weight;
        // Constructor
        public Edge(int vertexA, int vertexB, int weight){
            this.vertexA = vertexA;
            this.vertexB = vertexB;
            this.weight = weight;
        }
        public String toString(){
            return "(" + vertexA + "," + vertexB + ",w:" + weight+")";
        }
    }


















        @SuppressWarnings("unchecked")
        public static ArrayList<Node>[] init1(){
            int numOfVertexes = 9;
            ArrayList<Node>[] graph = new ArrayList[numOfVertexes];
            for (int i=0; i<numOfVertexes; i++) {
                graph[i] = new ArrayList<Node>();
            }
            // vertex: (adjacency vertex, key)
            // a - first vertex:
            graph[0].add(new Node(1,4)); graph[0].add(new Node(7,8));
            // b - second vertex:
            graph[1].add(new Node(0,4)); graph[1].add(new Node(2,8)); graph[1].add(new Node(7,11));
            // c - third vertex:
            graph[2].add(new Node(1,8)); graph[2].add(new Node(3,7)); graph[2].add(new Node(5,4)); graph[2].add(new Node(8,2));
            // d - 4-th vertex:
            graph[3].add(new Node(2,7)); graph[3].add(new Node(4,9)); graph[3].add(new Node(5,14));
            // e - 5-th vertex:
            graph[4].add(new Node(3,9)); graph[4].add(new Node(5,10));
            // f - 6-th vertex:
            graph[5].add(new Node(2,4)); graph[5].add(new Node(3,14)); graph[5].add(new Node(4,10)); graph[5].add(new Node(6,2));
            // f - 7-th vertex:
            graph[6].add(new Node(5,2)); graph[6].add(new Node(7,1)); graph[6].add(new Node(8,6));
            // f - 8-th vertex:
            graph[7].add(new Node(0,8)); graph[7].add(new Node(1,11)); graph[7].add(new Node(6,1)); graph[7].add(new Node(8,7));
            // f - 9-th vertex:
            graph[8].add(new Node(2,2)); graph[8].add(new Node(6,6)); graph[8].add(new Node(7,7));
            return graph;
        }
        @SuppressWarnings("unchecked")
        public static ArrayList<Node>[] init2(){
            int numOfVertexes = 3;
            ArrayList<Node>[]graph = new ArrayList[numOfVertexes];
            for (int i=0; i<numOfVertexes; i++) {
                graph[i] = new ArrayList<Node>();
            }
            // vertex: (adjacency vertex, key)
            // a - first vertex:
            graph[0].add(new Node(1,3)); graph[0].add(new Node(2,4));
            // b - second vertex:
            graph[1].add(new Node(0,3)); graph[1].add(new Node(2,4));
            // c - third vertex:
            graph[2].add(new Node(0,4)); graph[2].add(new Node(1,4));
            return graph;
        }
        @SuppressWarnings("unchecked")
        public static ArrayList<Node>[] init3(){
            int numOfVertexes = 4;
            ArrayList<Node>[] graph = new ArrayList[numOfVertexes];
            for (int i=0; i<numOfVertexes; i++) {
                graph[i] = new ArrayList<Node>();
            }
            graph[0].add(new Node(1,5)); graph[0].add(new Node(2,4)); graph[0].add(new Node(3,2));
            graph[1].add(new Node(0,5)); graph[1].add(new Node(3,1));
            graph[2].add(new Node(0,4));
            graph[3].add(new Node(0,2));  graph[3].add(new Node(1,1));
            // vertex: (adjacency vertex, key)
            return graph;
        }
        public static void main(String[] args) {
            Prim p = new Prim(init2(), 0);
            p.MST_Prim();
            p.PrintMST();

        }

    }
/**	init1
 MSP Prim Summary Weight: 37
 MSP Prim Weights :  0, 4, 4, 7, 9, 2, 1, 8, 2,
 MSP Prim parents :  -1, 0, 5, 2, 3, 6, 7, 0, 2,
 (0,1,w:4), (0,7,w:8), (7,6,w:1), (6,5,w:2), (5,2,w:4), (2,8,w:2), (2,3,w:7), (3,4,w:9),
 init2
 MSP Prim Summary Weight: 7
 MSP Prim Weights :  0, 3, 4,
 MSP Prim parents :  -1, 0, 0,
 (0,1,w:3), (0,2,w:4),
 init3
 MSP Prim Summary Weight: 7
 MSP Prim Weights :  0, 1, 4, 2,
 MSP Prim parents :  -1, 3, 0, 0,
 (0,3,w:2), (3,1,w:1), (0,2,w:4), */

