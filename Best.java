package Algo2;

import java.util.ArrayList;
import java.util.Arrays;


public class Best {
    /**
     * 'Best' algorithm for one dimension array - cycle
     * Using best algorithm
     * Complexity: O(n)
     * @param origin_arr
     * @return [max sum, first index, last index]
     * Proof on page :
     */
    public static int[] bestCycle(int[] origin_arr) {

        int len = origin_arr.length;
        int origin_sum = 0;
        int[] neg_arr = new int[len];

        for (int i = 0; i < len; i++) {
            origin_sum += origin_arr[i]; // sum all the element in the original array
            neg_arr[i] = -origin_arr[i]; // put '-' on each value in original array
        }
        // the maximum can be in 2 options: cycle array / normal array so here we check the 2 options.
        int[] best_origin = best(origin_arr); // best on original array
        int[] best_negative = best(neg_arr); // // best on negative array
        int max_neg= origin_sum + best_negative[0]; // max_neg is the max in negative array
        if (best_origin[0] < 0 || best_origin[0] >=max_neg) { // checking where is the max
            return best_origin;
        } else {
            return new int[]{origin_sum + best_negative[0], (best_negative[2] + 1) % len, best_negative[1] - 1};
        }
    }


    /**
     * * The problem is to find Continuous sub array with maximum value
     * @param arr is the array with the numbers
     * @return an array of max value, start index and end index
     * complex : o(n)
     * Proof on page :
     */
    private static int[] best(int[] arr) {
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        int s_i = 0, sIndx = -1, eIndx = -1;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += arr[i];
            if (sum > max) { // if we get sum bigger than before so-
                max = sum; // update max
                sIndx = s_i; // same start like it was before
                eIndx = i; // update end index to i caz it is the current index we add
            }
            if (sum < 0) { //If adding the value in index i caused sum<0 , so-
                sum = 0; // init sum to 0 caz we get sum<0
                s_i = i + 1; // update start index to the next index(i+1)
            }
        }
        return new int[]{max, sIndx, eIndx};

    }

    public static void main(String[] args) {
       // int [] b=  new int[]{1, 7, 2, 3, -13, 2, 1, 10,-2,1,-20};
        //int [] b=  new int[]{7, -9, 2, 1};
        int [] b=  new int[]{-7, -9, -2, -1};
        System.out.println("before best cycle : ");
        System.out.println(Arrays.toString(best(b)));
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("after best cycle : ");
        System.out.println(Arrays.toString(bestCycle(b)));

    }
}


//------------------------------------------------------------------------------------------------------

    class NumberOfBests {

        public static class Node {
            private int begin, end, sum;

            public Node(int b, int e, int s) {
                begin = b;
                end = e;
                sum = s;
            }

            public int begin() {
                return begin;
            }

            public int end() {
                return end;
            }

            public int sum() {
                return sum;
            }

            public String toString() {
                return "begin: " + begin + ", end: " + end + ", sum: " + sum;
            }
        }


        public static void longestBest(int[] a) {
            int i = 0;
            while (i < a.length && a[i] < 0) {
                i++;
            }
            if (i == a.length) throw new RuntimeException("array contasins negative numbers only! ");
            int sum = 0, maxSum = a[i], beginMax = i, begin = i, endMax = i + 1, maxLen = 0;
            while (i < a.length) {
                sum = sum + a[i];
                if (sum < 0) {
                    sum = 0;
                    begin = i + 1;
                } else if (sum > maxSum) {
                    maxSum = sum;
                    endMax = i + 1;
                    beginMax = begin;
                    maxLen = endMax - beginMax;
                } else if (sum == maxSum && maxLen < i + 1 - begin) {
                    endMax = i + 1;
                    beginMax = begin;
                    maxLen = endMax - beginMax;
                }
                i = i + 1;
            }
            System.out.println("begin=" + beginMax + ", end=" + endMax + ", sum=" + maxSum + ", maxLen=" + maxLen);
            for (int j = beginMax; j < endMax; j++) {
                System.out.print(a[j] + ", ");
            }
            System.out.println();
        }

        public static void shortestBest(int[] a) {
            int i = 0;
            while (i < a.length && a[i] < 0) {
                i++;
            }
            if (i == a.length) throw new RuntimeException("array contasins negative numbers only! ");
            int sum = 0, maxSum = a[i], beginMax = i, begin = i, endMax = i + 1, minLen = a.length;
            while (i < a.length) {
                sum = sum + a[i];
                if (sum <= 0) {
                    sum = 0;
                    begin = i + 1;
                } else if (sum > maxSum) {
                    maxSum = sum;
                    endMax = i + 1;
                    beginMax = begin;
                    minLen = endMax - beginMax;
                } else if (sum == maxSum && minLen > i + 1 - begin) {
                    endMax = i + 1;
                    beginMax = begin;
                    minLen = endMax - beginMax;
                }
                i = i + 1;
            }
            System.out.println("begin=" + beginMax + ", end=" + endMax + ", sum=" + maxSum + ", minLen=" + minLen);
            for (int j = beginMax; j < endMax; j++) {
                System.out.print(a[j] + ", ");
            }
            System.out.println();
        }

        // check this func
        public static void alltBests(int[] a) {
            int i = 0;
            while (i < a.length && a[i] < 0) {
                i++;
            }
            if (i == a.length) throw new RuntimeException("array contasins negative numbers only! ");
            int sum = 0, maxSum = a[i], beginMax = i, begin = i, endMax = i + 1;
            ArrayList<Node> v = new ArrayList<Node>();
            while (i < a.length) {
                sum = sum + a[i];
                if (sum < 0) {
                    sum = 0;
                    begin = i + 1;
                } else if (sum > maxSum) {
                    v.clear();
                    maxSum = sum;
                    endMax = i + 1;
                    beginMax = begin;
                    v.add(new Node(beginMax, endMax, maxSum));
                } else if (sum == maxSum) {
                    v.add(new Node(begin, i + 1, sum));
                }
                i = i + 1;
            }
            for (int j = 0; j < v.size(); j++) {
                Node n = v.get(j);
                for (int k = n.begin(); k < n.end(); k++) {
                    System.out.print(a[k] + ", ");
                }
                System.out.println();
            }
        }

            public static void main(String[] args) {
                //int []a = {1,-1, 3};
                int[] b = {1, 7, 2, 3, -13, 2, 1, 10,-2,1,-20};
                //int a[] = {10,2,-5,8,-100,3,50,-80,1,2,3}; //sum=53
                //int a[] = {10,2,8,1,2,3}; //sum=53
                System.out.println(Arrays.toString(b));
                System.out.println("longestBest:");
                longestBest(b);
                System.out.println("\nshortestBest:");
                shortestBest(b);
                System.out.println("\nalltBests:");
                alltBests(b);
            }
        }















