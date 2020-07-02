package Algo2;

public class Super_Best {

        /**
         * 'Best' algorithm for matrix - the best rectangle with maximum sum
         * Using BEST algorithm
         * Complexity: O(n*m^2)
         * @param mat is the matrix we get in the question to find her max sum
         * @return [max sum, first i_index, first j_index, last i_index, last j_index]
         */
        public static int[] bestMatrix(int[][] mat) {

            int si_index = -1, ei_index = -1, sj_index = -1, ej_index = -1;

            int rows = mat.length;
            int cols = mat[0].length;
            int max = Integer.MIN_VALUE;

            int[][] help = new int[rows][cols+1];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    help[i][j+1] = help[i][j] + mat[i][j];
                }
            }
            for (int i = 0; i < cols; i++) {
                for (int j = i; j < cols; j++) {
                    int[] temp = new int[rows];
                    for (int k = 0; k < rows; k++) {
                        temp[k] = help[k][j+1] - help[k][i];
                    }
                    int[] best = best(temp);
                    if(best[0] > max) {
                        max = best[0];
                        si_index = best[1];
                        ei_index = best[2];
                        sj_index = i;
                        ej_index = j;
                    }
                }
            }
            return new int[] {max, si_index, sj_index, ei_index, ej_index};
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

    }

