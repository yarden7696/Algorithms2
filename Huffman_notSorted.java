package Algo2;
import java.util.PriorityQueue;


public class Huffman_notSorted {

    public static class Node implements Comparable<Node>{
        /** @param:
         * number - a letter number
         * freq - a letter frequency (probability)
         */
        private final int nil = -1;
        int letterNumber, freq, parent;
        int left, right;


        // CONSTRUCTOR
        public Node(int letterNumber, int key, int left, int right){
            this.letterNumber = letterNumber;
            this.freq = key;
            this.parent = nil;
            this.left = left;
            this.right = right;
        }

        public Node(int letterNumber, int freq){
            this.letterNumber = letterNumber;
            this.freq = freq;
            this.parent = nil;
            this.left = nil;
            this.right = nil;
        }
        public void setParent(int parent) { this.parent = parent; }

        /** convert parameters - to string */
        public String toString(){
            return "("+letterNumber+","+ freq +" "+","+left+","+right+","+parent +" )";
        }
        @Override
        public int compareTo(Node n) {
            int ans = this.freq - n.freq;
            return ans;
        }
    }


    public static class HuffmanAlgorithmHeap {//O(nlog2(n))
         Node nodes[];
        int numOfLeaves, numNodes, len;
        final int nil = -1;
        String codes[];
        PriorityQueue<Node> queue;

        // CONSTRUCTOR
        public HuffmanAlgorithmHeap(int freq[]) {   //O(n)+O(n)+O(nlog2(n))
            numOfLeaves = freq.length;
            numNodes = numOfLeaves*2-1;
            nodes = new Node[numNodes];
            codes = new String[numOfLeaves];
            len = numOfLeaves;

            for (int i=0; i<numOfLeaves; i++){  //O(n) init tree
                nodes[i] = new Node(i,freq[i]);
            }
            queue = new PriorityQueue<Node>(numOfLeaves);
            for (int i = 0; i < numOfLeaves; i++) {//O(nlog2(n))
                queue.add(nodes[i]);
            }
            for (int i = 0; i < numOfLeaves; i++) {//O(n)
                this.codes[i] = new String();
            }
        }


        public void HuffmanAlgorithm(){//O(n)
            for(int i=0; i<numOfLeaves-1; i++){//O(n)
                Node n1 = queue.poll();//O(1)
                Node n2 = queue.poll();//O(1)
                Node node = new Node(len, n1.freq +n2.freq, n1.letterNumber, n2.letterNumber);
                n1.setParent(len);
                n2.setParent(len);
                queue.add(node);//O(1)
                nodes[len] = node;
                len++;
            }
            //// build the Huffman's Code for all letters
            for (int i=0; i<numOfLeaves; i++){//O(2n-1)
                Node child = nodes[i];
                Node parent = nodes[child.parent];
                while(child.parent != nil){
                    if (parent.left==child.letterNumber) codes[i] = "0" + codes[i];
                    else codes[i] = "1" + codes[i];
                    child = parent;
                    if (child.parent != nil) parent = nodes[child.parent];
                }
            }
        }
        public void printCode(){
            for (int i=0; i<numOfLeaves; i++){
                System.out.print((char)('a'+i)+": "+codes[i] + ";  ");
            }
        }
        public static void main(String[] args) {
//            int freq1[] = {12,40,15,8,25};
            int freq2[] = {45,13,12,16,9,5};
            HuffmanAlgorithmHeap hh =new HuffmanAlgorithmHeap(freq2);
            hh.HuffmanAlgorithm();
            hh.printCode();
        }

    }
/* 	freq1:  a: 1111;  b: 0;  c: 110;  d: 1110;  e: 10;
	freq2:  a: 0;  b: 101;  c: 100;  d: 111;  e: 1101;  f: 1100;
*/


}
