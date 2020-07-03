package Algo2;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

    public class TreesFire {

        /**
         * MyVector is an inner class that present nei list. each vector is an array that the first element
         * in the array is the vertex himself and the rest of the array is the nei of the first element
         */
        public static class MyVector{
            int[] data; // nei list of 1 vertex
            int size; // the data in the array

            public MyVector(int size){ // constructor
                data = new int[size];
                this.size = 0;
            }

            public void add(int val) { data[size++] = val; }
            public int get(int index) { return data[index]; }
            public void set(int index, int val) { data[index] = val; }
            public int size() { return size; }
            public void remove(int x){ }

            public String toString(){
                String ans = "";
                if (size == 1) ans = ans + data[0];
                else {
                    for (int i = 0; i < size; i++) {
                        ans = ans + data[i] + ", ";
                    }
                }
                return ans;
            }
        }

        public static void TreesFire_Algo(MyVector[] tree){

            int len = tree.length; // length of the tree is the num of the vertexes
            Queue<Integer> leavesQ = new ArrayBlockingQueue<>(len); // create an empty queue that will be leaves in it,
            // leaves has degree that equal to 1, ArrayBlockingQueue is a queue that orders elements FIFO (first-in-first-out).

            int radius = 0, diameter = 0, numCenters = 0;
            int[] degrees = new int[len]; // create an array of degrees
            int[] levels = new int[len]; //// create an array of

            // queue initialization, len - number of vertices
            for (int i = 0; i<len; i++){ // init the queue with the all leaves
                // tree[i].size() is like in tirgul 9 page 3- for example v2- its size 3 so in degrees[2] will be 3
                degrees[i] = tree[i].size();
                if (degrees[i] == 1) leavesQ.add(i); // if there is degree 1 so its a leave so we add the leave to the queue
            }

            int leaf = 0, neigh = 0, maxLevel = 0;

            while (!leavesQ.isEmpty()){ //O(n)- while the queue still full
                leaf = leavesQ.poll(); // poll removes and returns the head of the queue, leaf is the head of the queue
                degrees[leaf] = 0; // remove the leaf from the tree by remove him from the the array degrees תירגול 9 בסופו-מסבירה
                for (int j=0; j<tree[leaf].size(); j++){ // in this for loop we running of all the neigh of this leaf
                                                       // and remove the leaf from its neigh
                    neigh = tree[leaf].get(j); //O(1) - neigh is the neighbor of this leaf
                    if(degrees[neigh] > 0) degrees[neigh]--; // if neigh not a leave so we reduce -- from array degrees,
                                                            // we remove his neigh(leaf) from the array's degrees
                    if(degrees[neigh] == 1) { // the neigh is leave after reduce --1 so-
                        leavesQ.add(neigh); // add the neigh to the queue
                        levels[neigh] = levels[leaf] + 1; // levels is an array that say how much centers there is
                        maxLevel = Math.max(maxLevel, levels[neigh]); // maxLevel could be 0/1/2
                    }
                }
            }
            ArrayList<Integer> centers = new ArrayList<>(2);
            for (int i = 0; i < len; i++) {
                if (levels[i] == maxLevel) centers.add(i); // checking how center there is
            }
            // printing result
            numCenters = centers.size();
            diameter = 2*maxLevel + numCenters - 1;
            radius = (diameter + 1)/2;
            System.out.println("radius = " + radius + ", diameter = " + diameter + ", centers: " + centers
                    +", numCenters = "+numCenters);
        }
        
        //////////////////////////////////init trees and main////////////////////////////////////////////////////

            public static class InitTrees {
                public static MyVector[] initTree1() {
                    int n = 7;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    ////////////
                    tree[1].add(0);
                    tree[1].add(2);
                    tree[1].add(4);
                    ///////////
                    tree[2].add(1);
                    tree[2].add(3);
                    /////////////
                    tree[3].add(2);
                    ////////////
                    tree[4].add(1);
                    tree[4].add(5);
                    ////////////
                    tree[5].add(4);
                    tree[5].add(6);
                    ////////////
                    tree[6].add(5);
                    return tree;
                }

                public static MyVector[] initTree2() {//Graph: 0<->1<->2<->3<->4
                    int n = 5;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    ////////////
                    tree[1].add(0);
                    tree[1].add(2);
                    ///////////
                    tree[2].add(1);
                    tree[2].add(3);
                    /////////////
                    tree[3].add(2);
                    tree[3].add(4);
                    ////////////
                    tree[4].add(3);
                    return tree;
                }

                public static MyVector[] initTree3() {//Graph: 0<->1<->2<->3<->4<->5<->6<->7
                    int n = 8;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    ////////////
                    tree[1].add(0);
                    tree[1].add(2);
                    ///////////
                    tree[2].add(1);
                    tree[2].add(3);
                    /////////////
                    tree[3].add(2);
                    tree[3].add(4);
                    ////////////
                    tree[4].add(3);
                    tree[4].add(5);
                    ////////////
                    tree[5].add(4);
                    tree[5].add(6);
                    ////////////
                    tree[6].add(5);
                    tree[6].add(7);
                    ////////////
                    tree[7].add(6);
                    return tree;
                }

                public static MyVector[] initTree4() {
                    int n = 8;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    /////
                    tree[1].add(0);
                    tree[1].add(2);
                    ////////////
                    tree[2].add(1);
                    tree[2].add(3);
                    tree[2].add(5);
                    ///////////
                    tree[3].add(2);
                    tree[3].add(4);
                    /////////////
                    tree[4].add(3);
                    ////////////
                    tree[5].add(2);
                    tree[5].add(6);
                    ////////////
                    tree[6].add(5);
                    tree[6].add(7);
                    ////////////
                    tree[7].add(6);
                    return tree;
                }

                public static MyVector[] initTree5() {
                    int n = 7;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    ////////////
                    tree[1].add(0);
                    tree[1].add(2);
                    ///////////
                    tree[2].add(1);
                    tree[2].add(4);
                    tree[2].add(6);
                    /////////////
                    tree[3].add(5);
                    ////////////
                    tree[4].add(2);
                    tree[4].add(5);
                    ////////////
                    tree[5].add(4);
                    tree[5].add(3);
                    ////////////
                    tree[6].add(2);
                    return tree;
                }

                public MyVector[] initTree6() {
                    int n = 7;
                    MyVector[] tree = new MyVector[n];//graph (tree)array of vectors
                    for (int i = 0; i < n; i++) {
                        tree[i] = new MyVector(n);
                    }
                    tree[0].add(1);
                    tree[0].add(2);
                    tree[0].add(3);
                    tree[0].add(4);
                    tree[0].add(5);
                    tree[0].add(6);
                    tree[1].add(0);
                    tree[2].add(0);
                    tree[3].add(0);
                    tree[4].add(0);
                    tree[5].add(0);
                    tree[6].add(0);
                    return tree;
                }
            }


        public static void main(String[] args) {
            TreesFire_Algo(InitTrees.initTree1());//radius = 3, diameter = 5, centers: 1, 4
            TreesFire_Algo(InitTrees.initTree2());//radius = 2, diameter = 4, centers: 2
            TreesFire_Algo(InitTrees.initTree3());//radius = 4, diameter = 7, centers: 3, 4
            TreesFire_Algo(InitTrees.initTree4());//radius = 3, diameter = 5, centers: 2, 5
            TreesFire_Algo(InitTrees.initTree5());//radius = 3, diameter = 5, centers: 2, 4
          //  fire2(InitTrees.initTree6());//radius = 1, diameter = 2, centers: 0

        }

    }

