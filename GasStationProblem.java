package Algo2;

public class GasStationProblem {

    /**
     * @param fill_station - the gas station capacity
     * @param waste_inWay - the fuel that we waste in the way
     * @return if there is enough gas to complete the road
     * Complexity: O(n)
     */
    public static boolean isWay(int[] fill_station, int[] waste_inWay) {
        int len = fill_station.length;
        int[] c = new int[len]; // c save the sub of fill_station[i]-waste_inWay[i]
        for (int i = 0; i < len; i++) { // init c array
            c[i] = fill_station[i] - waste_inWay[i];
        }
        int[] best = bestCycle(c); // best on c array to find the sub array with the max value
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += c[(best[1] + i) % len];
            if (sum < 0) return false;
        }
        return true;
    }


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
        int max_neg = origin_sum + best_negative[0]; // max_neg is the max in negative array
        if (best_origin[0] < 0 || best_origin[0] >= max_neg) { // checking where is the max
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

        int [] fill = {3,6,2,8};
        int [] waste = {5,4,3,4};
        boolean b=isWay(fill,waste);
        System.out.println(b);

}
    }

