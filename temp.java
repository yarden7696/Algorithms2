// Created by Or Kadrawi
package Algo2;
import java.util.ArrayList;

// Created by Or Kadrawi

 class _01_Bottles_Problem {

    public static void main(String[] args) {
        int m = 1; // first bottle
        int n = 2; // second bottle
        boolean[][] mat = BottlesProblem(m,n);//first method
        Print(mat);
    }

    public static boolean[][] BottlesProblem(int m, int n) {
        int size = (m + 1) * (n + 1);
        boolean[][] mat = new boolean[size][size];

        // first method
/*		int k;
		for (int i = 0; i < m+1; i++) {
			for (int j = 0; j < n+1; j++) {
				k = getIndex(i,j,n);
				mat[k][getIndex(0,j,n)] = true;
				mat[k][getIndex(i,0,n)] = true;
				mat[k][getIndex(m,j,n)] = true;
				mat[k][getIndex(i,n,n)] = true;
				mat[k][getIndex(i+j-(Math.min(i+j, n)),Math.min(i+j, n),n)] = true;
				mat[k][getIndex(Math.min(i+j, m),i+j-Math.min(i+j, m),n)] = true;
			}
		}*/

        // second method
        int i,j;
        for (int k = 0; k < mat.length; k++) {
            i = getI(k,n);
            j = getJ(k,n);
            mat[k][getIndex(0,j,n)] = true;
            mat[k][getIndex(i,0,n)] = true;
            mat[k][getIndex(m,j,n)] = true;
            mat[k][getIndex(i,n,n)] = true;
            mat[k][getIndex(i+j-(Math.min(i+j, n)),Math.min(i+j, n),n)] = true;
            mat[k][getIndex(Math.min(i+j, m),i+j-Math.min(i+j, m),n)] = true;
        }
        return mat;
    }

    public static int getIndex(int i, int j, int n)
    {
        return (n+1)*i + j;
    }

    public static int getI(int k, int n)
    {
        return k / (n+1);
    }

    public static int getJ(int k, int n)
    {
        return k % (n+1);
    }

    public static void Print(boolean[][] mat)
    {
        int rows = mat.length;
        int cols = mat[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(mat[i][j]+"\t");
            }
            System.out.println();
        }
    }


















     // Created by Or Kadrawi

     public static class _03_Floyd_Warshall_weights {

         static int inf = 1000000;

         public static void main(String[] args) {

             //Q1 basic floyd warshall to find minimum price
             int[][] mat = {{0, 4, inf, 3},
                     {4, 0, 15, inf},
                     {inf, 15, 0, 1},
                     {3, inf, 1, 0}};
             floyd_warshall(mat);
             Print(mat);

             //Q2 floyd warshall to find minimum price with example of path
             int[][] mat2 = {{0, 4, inf, 3},
                     {4, 0, 15, inf},
                     {inf, 15, 0, 1},
                     {3, inf, 1, 0}};
             String[][] path = InitPath(mat.length, mat2);
             floyd_warshall_initAllPaths(mat2, path);
             FixPath(path);
             Print(mat2);
             System.out.println();
             PrintAllPath(path);

             //Q3 floyd warshall on weights on the vertices
             int[][] mat3 = {{0,1,1,inf},
                     {1,0,inf,1},
                     {1,inf,0,1},
                     {inf,1,1,0}};
             int[] arr = {1,8,5,10};
             int[][] H = InitE(mat3, arr);
             floyd_warshall(H);
             fixE(H, arr);
             Print(H);
         }// end main




         //Q1
         /**
          * explain on page 7. remove the infinity from the mat.
          */
         private static void floyd_warshall(int[][] mat) {// פה נקבל מטריצת *משקלים* שכבר יש באלכסון הראשי 0,ומס כלשהו
                                                         // בין שתי צלעות עם קשר ישיר
             int size = mat.length;
             for (int k = 0; k < size; k++) {
                 for (int i = 0; i < size; i++) {
                     for (int j = 0; j < size; j++) {
                         if (mat[i][k]!= inf &&  mat[k][j]!= inf)
                             if (mat[i][k] + mat[k][j] < mat[i][j])//If yes:check if the current value is greater
                                 mat[i][j] = mat[i][k] + mat[k][j];
                     }
                 }
             }
         }
         private static void Print(int[][] mat)
         {
             for (int i = 0; i < mat.length; i++) {
                 for (int j = 0; j < mat[i].length; j++) {
                     System.out.print(mat[i][j]+"\t");
                 }
                 System.out.println();
             }
             System.out.println("-------------------------");
         }

         //Q2
         /**
          * init the paths between 2 vertexes if there is direct connection.
          */
         private static String[][] InitPath(int size, int[][] mat) {
             String[][] path = new String[size][size];
             for (int i = 0; i < path.length; i++) {
                 for (int j = 0; j < path.length; j++) {
                     if (mat[i][j] != inf) // if there is direct connection between 2 vertex
                         path[i][j] = (i+1) + ""; // add the path vertex to its own תא. i+1 caz v1=(i=0)
                 }
             }
             return path;
         }
         /**
          * init the all paths between 2 vertexes עקיפים.
          */
         private static void floyd_warshall_initAllPaths(int[][] mat, String[][] path) {
             int size = mat.length;
             for (int k = 0; k < size; k++) {
                 for (int i = 0; i < size; i++) {
                     for (int j = 0; j < size; j++) {
                         if (mat[i][k]!= inf &&  mat[k][j]!= inf)
                             if (mat[i][k] + mat[k][j] < mat[i][j])
                             {
                                 mat[i][j] = mat[i][k] + mat[k][j];
                                 path[i][j] = path[i][k] + "->" + path[k][j];
                             }
                     }
                 }
             }
         }

         /**
          * add the last vertex to the string path (like bottle problem 2).
          * without this function the path between 2->4 will be : 2->1, but with this function
          * it will be  2->4:	2->1->4 (with the last vertex 4).
          */
         private static void FixPath(String[][] path) {
             for (int i = 0; i < path.length; i++) {
                 for (int j = 0; j < path.length; j++) {
                     path[i][j] += "->"+(j+1);
                 }
             }
         }
         private static void PrintAllPath(String[][] path) {
             for (int i = 0; i < path.length; i++) {
                 for (int j = 0; j < path.length; j++) {
                     System.out.print((i+1)+"->"+(j+1)+":\t");
                     if (path[i][j] != null)
                         System.out.println(path[i][j]);
                     else
                         System.out.println("NO..");
                 }
             }
             System.out.println("-------------------------");
         }

         //Q3
         /**
          * המרה ממטריצה של משקלים על קודקודים למשקלים על צלעות, מתבצעת המרה לא מלאה- יש צורך בתיקון המטריצה,
          *
          * param v : is the Neighbor matrix for the graph.
          * direct connection = 1, indirect connection = infinity, connection between node and itself = 0
          * param orr: is an array of the values of each vertex  בהתאמה.
          * בעצם הפונקציה הזו מטפלת *אך ורק* ב1ים, כלומר אם יש קשר ישיר אזי במקום הדיפולט שהוא 1 יהיה את הערך האמיתי בין שני קודקודים ישירים.
          */
         private static int[][] InitE(int[][] v, int[] arr) {
             int size = v.length;
             int[][] E = new int[size][size]; // create a new mat for משקלים על צלעות

             for (int i = 0; i < E.length; i++) {
                 for (int j = 0; j < E[i].length; j++) {
                     if (v[i][j] == 1) { // if there is direct connection between 2 vertexes
                         E[i][j] = arr[i] + arr[j]; // so we add the value of each תא in the array(page 9 in yellow).
                     }
                     else { E[i][j] = inf; }
                 }
             }
             for (int i = 0; i < E.length; i++) {// לשאול למה שם באלכסון הראשי בכל תא את ערך הקודקוד?
                 E[i][i] = arr[i];
             }
             return E;
         } // אחרי שסיימנו עם האלגוריתם הזה נכניס את המטריצה הזו לפרוליד וארשל ולאחר מכן נפעיל עליה את אלגוריתם fixE
         //  מטפל בבעית האינסוף  floyd_warshall


         /**
          * this function get an mat after initE and floyd_warshall algorithms.
          * תפקידה של הפונקציה fixE הוא לטפל בבעיה של הספירה פעמיים(הסבר בעמוד 10)
          */
         private static void fixE(int[][] E, int[] arr) {
             for (int i = 0; i < E.length; i++) {
                 for (int j = 0; j < E[i].length; j++) {
                     if (i != j) // like on page 10: i=1, j=4.
                         E[i][j] = (E[i][j] + arr[i] + arr[j] ) / 2;
                                   // E[1][4]=9 and arr[1]=4 ,arr[4]=5 -->> 9+4+5/2= 18:2=9
                 }
             }
         }




     }

}

class _02_Floyd_Warshall_Binary {

    private static _01_Bottles_Problem MyFunctions;

    public static void main(String[] args) {

		//without path
        System.out.println("without path");
		int m = 1; // first bottle
		int n = 2; //second bottle
		boolean[][] T = _01_Bottles_Problem.BottlesProblem(m,n);
		floyd_warshall(T);
		System.out.println("Bottle1 = " + m + ", Bottle2 = " + n);
		System.out.println("\nFloyd Warshall Matrix:");

		MyFunctions.Print(T);

		System.out.println("\nIs the graph connected? : " + isConnected(T));

		System.out.println("\nThere is : " + NumberOfComponents(T) + " components.");

		String[] components = GetVertexInEachComponents(T);
		for (int i = 0; i < components.length; i++) {
			System.out.println("Component "+i+" is : " + components[i]);
		}

        System.out.println("_________________________________________________________________________________");
//		//with path
        System.out.println("with path: ");
        int m1 = 3; // first bottle
        int n1 = 5; //second bottle
        boolean[][] T1 = _01_Bottles_Problem.BottlesProblem(m1,n1);
        int size = T1.length;
        String[][] path = InitPath(size, T1, n1);
        floyd_warshall(T1, path);
        FixPath(path, n1);
        System.out.println("Bottle1 = " + m1 + ", Bottle2 = " + n1);

        IsExist(0,0,0,4,T1,path,n1);

        System.out.println("\nAll paths:\n");
        PrintAllPath(T1, path, n1);


		System.out.println("        V4");
		System.out.println("        /\\");
		System.out.println("       /  \\");
		System.out.println("      /____\\");
		System.out.println("     V0     V5");
		System.out.println("    V6------V3");
		System.out.println("      |     |");
		System.out.println("      |_____|");
		System.out.println("     V1     V2");
		System.out.println();
		boolean[][] T2 = Init01();
		floyd_warshall(T2);

		System.out.println("Floyd Warshall Matrix:");
		MyFunctions.Print(T2);

		System.out.println("Is the graph connected? : " + isConnected(T2));

		System.out.println("\nThere is : " + NumberOfComponents(T2) + " components.");

		String[] components1 = GetVertexInEachComponents(T2);
		for (int i = 0; i < components.length; i++) {
			System.out.println("Component "+i+" is : " + components[i]);
		}


		ReArrangeMat(T2);
		System.out.println("\nReArrange Matrix:");
		MyFunctions.Print(T2);

    }


    private static void floyd_warshall(boolean[][] T) {
        int size = T.length;
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    T[i][j] = (T[i][k] && T[k][j]) || T[i][j];
                }
            }
        }
    }

    private static void floyd_warshall(boolean[][] T, String[][] paths) {
        int size = T.length;
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (T[i][j] == false && ((T[i][k] && T[k][j])==true))
                        paths[i][j] = paths[i][k] + "->" + paths[k][j];
                    T[i][j] = (T[i][k] && T[k][j]) || T[i][j];
                }
            }
        }
    }

    private static void FixPath(String[][] path, int n) {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                path[i][j] += "->("+_01_Bottles_Problem.getI(j, n)+","+_01_Bottles_Problem.getJ(j, n)+")";
            }
        }
    }


    private static void PrintAllPath(boolean[][] T, String[][] path, int n) {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                System.out.print("("+_01_Bottles_Problem.getI(i, n)+","+_01_Bottles_Problem.getJ(i, n)+")->("+_01_Bottles_Problem.getI(j, n)+","+_01_Bottles_Problem.getJ(j, n)+"):\t");
                if (T[i][j] == true)
                    System.out.println(path[i][j]);
                else
                    System.out.println("NO..");
            }
        }
    }

    private static void IsExist(int i1, int j1, int i2, int j2, boolean[][] T, String[][] paths, int n) {
        int i = _01_Bottles_Problem.getIndex(i1, j1, n);
        int j = _01_Bottles_Problem.getIndex(i2, j2, n);
        System.out.println("Is Exist path from ("+i1+","+j1+") to ("+i2+","+j2+")?");
        if (T[i][j] == true)
            System.out.println("YES! "+paths[i][j]);
        else
            System.out.println("NO..");
    }

    public static boolean isConnected(boolean T[][]){

        for(int j=0; j<T[0].length; j++){
            if (T[0][j] == false)
                return false;
        }
        return true;
    }

    private static int NumberOfComponents(boolean[][] T) {
        int size = T.length;
        ArrayList<Integer> Seen = new ArrayList<Integer>();
        ArrayList<Integer> UnSeen = new ArrayList<Integer>();
        for (int i = 0; i < T.length; i++) {
            UnSeen.add(i);
        }

        int counter = 0;
        while (UnSeen.isEmpty() == false) {
            int vertex = UnSeen.get(0);
            counter++;
            for (int j = 0; j < size; j++) {
                if (T[vertex][j]==true)
                {
                    Seen.add(j);
                }
            }
            UnSeen = Substract(UnSeen, Seen);
        }
        return counter;
    }

    private static String[] GetVertexInEachComponents(boolean[][] T) {
        int size = T.length, counter = 0;
        int[] components = new int[size];

        for (int i = 0; i < components.length; i++) {
            if (components[i] == 0)
            {
                counter++;
                components[i] = counter;
                for (int j = i+1; j < components.length; j++) {
                    if (components[j] == 0 && T[i][j] == true)
                        components[j] = counter;
                }
            }
        }

        String[] vertexInComponent = new String[counter];
        for (int i = 0; i < vertexInComponent.length; i++) {
            vertexInComponent[i] = "";
        }
        for (int i = 0; i < components.length; i++) {
            vertexInComponent[components[i]-1] += i + "\t";
        }

        return vertexInComponent;
    }
    private static void ReArrangeMat(boolean[][] T) {
        ReArrangeCols(T);
        Transpose(T);
        ReArrangeCols(T);
    }

    private static void Transpose(boolean[][] T) {
        boolean temp;
        for (int i = 0; i < T.length; i++) {
            for (int j = i+1; j < T.length; j++) {
                temp = T[i][j];
                T[i][j] = T[j][i];
                T[j][i] = temp;
            }
        }
    }

    private static void ReArrangeCols(boolean[][] T) {
        int start = 0, end , row = 0;
        while (start<T.length-1)
        {
            row = start;
            end = T.length-1;
            while(start<end)
            {
                while(start < T.length && T[row][start]==true)
                    start++;
                if (start == T.length)
                {
                    return;
                }
                while(T[row][end]==false)
                    end--;
                for (int i = 0; i < T.length; i++) {
                    SwapCols(T, i, start, end);

                }
                start++;
                end--;
            }
        }
    }

    private static void SwapCols(boolean[][] T, int i, int start, int end) {
        boolean temp;
        temp = T[i][start];
        T[i][start] = T[i][end];
        T[i][end] = temp;
    }

    private static ArrayList<Integer> Substract(ArrayList<Integer> UnSeen, ArrayList<Integer> Seen) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for (int i = 0; i < UnSeen.size(); i++) {
            if (Seen.contains(UnSeen.get(i)) == false)
            {
                ans.add(UnSeen.get(i));
            }
        }
        return ans;
    }


    //        V4
    //        /\
    //       /  \
    //      /____\
    //     V0     V5
    //    V6------V3
    //      |     |
    //	    |_____|
    //     V1     V2
    //
    private static boolean[][] Init01() {
        boolean[][] T= {{false,false,false,false,true,true,false},
                {false,false,true,false,false,false,true},
                {false,true,false,true,false,false,false},
                {false,false,true,false,false,false,true},
                {true,false,false,false,false,true,false},
                {true,false,false,false,true,false,false},
                {false,true,false,true,false,false,false}};
        return T;
    }
    private static String[][] InitPath(int size, boolean[][] T, int n) {
        String[][] path = new String[size][size];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                if (T[i][j] == true)
                    path[i][j] = "("+_01_Bottles_Problem.getI(i, n)+","+_01_Bottles_Problem.getJ(i, n)+")";

            }
        }
        return path;
    }


}

































































//package Algo2;
//
//
//import java.util.ArrayList;
//
//class _01_Bottles_Problem {
//
//    public static void main(String[] args) {
//        int m = 1; // first bottle
//        int n = 2; // second bottle
//        boolean[][] mat = BottlesProblem(m,n);//first method
//        Print(mat);
//
//    }
//
//    public static boolean[][] BottlesProblem(int m, int n) {
//        int size = (m + 1) * (n + 1);
//        boolean[][] mat = new boolean[size][size];
//
//        // first method
///*		int k;
//		for (int i = 0; i < m+1; i++) {
//			for (int j = 0; j < n+1; j++) {
//				k = getIndex(i,j,n);
//				mat[k][getIndex(0,j,n)] = true;
//				mat[k][getIndex(i,0,n)] = true;
//				mat[k][getIndex(m,j,n)] = true;
//				mat[k][getIndex(i,n,n)] = true;
//				mat[k][getIndex(i+j-(Math.min(i+j, n)),Math.min(i+j, n),n)] = true;
//				mat[k][getIndex(Math.min(i+j, m),i+j-Math.min(i+j, m),n)] = true;
//			}
//		}*/
//
//        // second method
//        int i,j;
//        for (int k = 0; k < mat.length; k++) {
//            i = getI(k,n);
//            j = getJ(k,n);
//            mat[k][getIndex(0,j,n)] = true;
//            mat[k][getIndex(i,0,n)] = true;
//            mat[k][getIndex(m,j,n)] = true;
//            mat[k][getIndex(i,n,n)] = true;
//            mat[k][getIndex(i+j-(Math.min(i+j, n)),Math.min(i+j, n),n)] = true;
//            mat[k][getIndex(Math.min(i+j, m),i+j-Math.min(i+j, m),n)] = true;
//        }
//        return mat;
//    }
//
//    public static int getIndex(int i, int j, int n)
//    {
//        return (n+1)*i + j;
//    }
//
//    public static int getI(int k, int n)
//    {
//        return k / (n+1);
//    }
//
//    public static int getJ(int k, int n)
//    {
//        return k % (n+1);
//    }
//
//    public static void Print(boolean[][] mat)
//    {
//        int rows = mat.length;
//        int cols = mat[0].length;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(mat[i][j]+"\t");
//            }
//            System.out.println();
//        }
//    }
//
//}
//
//    // Created by Or Kadrawi
//
//
//
//    class _02_Floyd_Warshall_Binary {
//
//        public static void main(String[] args) {
//
////		//without path
////		int m = 1; // first bottle
////		int n = 2; //second bottle
////		boolean[][] T = _01_Bottles_Problem.BottlesProblem(m,n);
////		floyd_warshall(T);
////		System.out.println("Bottle1 = " + m + ", Bottle2 = " + n);
////		System.out.println("\nFloyd Warshall Matrix:");
////
////		MyFunctions.Print(T);
////
////		System.out.println("\nIs the graph connected? : " + isConnected(T));
////
////		System.out.println("\nThere is : " + NumberOfComponents(T) + " components.");
////
////		String[] components = GetVertexInEachComponents(T);
////		for (int i = 0; i < components.length; i++) {
////			System.out.println("Component "+i+" is : " + components[i]);
////		}
//
//
////		//with path
//            // int m = 1; // first bottle
//            //  int n = 2; //second bottle
//            // boolean[][] T = _01_Bottles_Problem.BottlesProblem(m,n);
//
////            boolean[][] mat2= {{false,true,true,false,false,false,false},
////                    {true,false,true,false,false,false,false},
////                    {true,true,false,false,false,false,false},
////                    {false,false,false,false,true,false,true},
////                    {false,false,false,true,false,true,false},
////                    {false,false,false,false,true,false,true},
////                    {false,false,false,true,false,true,false}};
//
////            int size = mat2.length;
////            String[][] path = InitPath(size, mat2, n);
////            floyd_warshall(mat2, path);
////            FixPath(path, n);
////            System.out.println("Bottle1 = " + m + ", Bottle2 = " + n);
////            IsExist(0,0,0,1,mat2,path,n);
////
////            System.out.println("\nAll paths:\n");
//            //   PrintAllPath(mat2, path, n);
//
//
//		System.out.println("        V1");
//		System.out.println("        /\\");
//		System.out.println("       /  \\");
//		System.out.println("      /____\\");
//		System.out.println("     V4     V6");
//		System.out.println("    V5------V2");
//		System.out.println("      |     |");
//		System.out.println("      |_____|");
//		System.out.println("     V3     V7");
//		System.out.println();
//		boolean[][] T = Init01();
//		floyd_warshall(T);
//
//		System.out.println("Floyd Warshall Matrix:");
//		MyFunctions.Print(T);
//
//		System.out.println("Is the graph connected? : " + isConnected(T));
//
//		System.out.println("\nThere is : " + NumberOfComponents(T) + " components.");
//
//		String[] components = GetVertexInEachComponents(T);
//		for (int i = 0; i < components.length; i++) {
//			System.out.println("Component "+i+" is : " + components[i]);
//		}
//
//		ReArrangeMat(T);
//		System.out.println("\nReArrange Matrix:");
//		MyFunctions.Print(T);
//
////            int m = 1; // first bottle
////            int n = 2; //second bottle
////            boolean[][] T = _01_Bottles_Problem.BottlesProblem(m, n);
////            floyd_warshall(T);
////            printFW_mat(T);
////            String[][] s=InitPath(6, T, 2);
////            int numCompone = NumberOfComponents(T);
////            System.out.println("number of Components is: " + numCompone);
////            String[] whichCompone = GetVertexInEachComponents(T);
////            for (int i = 0; i < whichCompone.length; i++) {
////                System.out.println("Component " + i + " is : " + whichCompone[i]);
//
//
//            }
//
//
//
//
//
//
//
//        //yes
//        private static void floyd_warshall(boolean[][] T) { // מטריצת פרוילד וארשל- נכניס אליה מטריצה אחרי שעברה אתחול של מטריצת שכנויות
//                                                    // פונקציה זו *מאתחלת* מטריצה כך שגם אם אין קשר ישיר בין שני קודקודים אך יש קשר עקיף יהיה T
//            int size = T.length;
//            for (int k = 0; k < size; k++) { // k: הוא הקודקוד המקשר
//                // הוא קודקוד שבא לעזרת חבר- בעזרתו בודקים האם הוא מקשר בין שני קודקודים לא באופן ישיר
//                for (int i = 0; i < size; i++) { // i מציין את השורה , כאשר כל שורה זה קודקוד עבורו בודקים קשר עקיף בינו לבין כל קודקודי הגרף
//                    for (int j = 0; j < size; j++) { // j זה העמודות- בכל עמודה יש קודקוד שעבורו בודקים האם יש קשר עקיף עם הקודקוד שבשורה
//                        T[i][j] = (T[i][k] && T[k][j]) || T[i][j];// put T if there is path between i and j
//                                                                  // עושיפ בדיקה האם הוא קשר ישיר או קשר עקיף ע"י קודקוד k
//                    }
//                }
//            }
//        }
//
//        //yes
//        private static void floyd_warshallString_notDirectPath(boolean[][] T, String[][] paths) {
//            int size = T.length;
//            for (int k = 0; k < size; k++) {
//                for (int i = 0; i < size; i++) {
//                    for (int j = 0; j < size; j++) {
//                        if (T[i][j] == false && ((T[i][k] && T[k][j])==true))
//                            paths[i][j] = paths[i][k] + "->" + paths[k][j];
//                        T[i][j] = (T[i][k] && T[k][j]) || T[i][j];
//                    }
//                }
//            }
//        }
//
//        private static void FixPath(String[][] path, int n) {
//            for (int i = 0; i < path.length; i++) {
//                for (int j = 0; j < path.length; j++) {
//                    path[i][j] += "->("+_01_Bottles_Problem.getI(j, n)+","+_01_Bottles_Problem.getJ(j, n)+")";
//                }
//            }
//        }
//
//
//
//        //yes
//        private static void PrintAllPath(boolean[][] T, String[][] path, int n) {
//            for (int i = 0; i < path.length; i++) {
//                for (int j = 0; j < path.length; j++) {
//                    System.out.print("("+_01_Bottles_Problem.getI(i, n)+","+_01_Bottles_Problem.getJ(i, n)+")->("+_01_Bottles_Problem.getI(j, n)+","+_01_Bottles_Problem.getJ(j, n)+"):\t");
//                    if (T[i][j] == true)
//                        System.out.println(path[i][j]);
//                    else
//                        System.out.println("NO..");
//                }
//            }
//        }
//
//        private static void IsExist(int i1, int j1, int i2, int j2, boolean[][] T, String[][] paths, int n) {
//            int i = _01_Bottles_Problem.getIndex(i1, j1, n);
//            int j = _01_Bottles_Problem.getIndex(i2, j2, n);
//            System.out.println("Is Exist path from ("+i1+","+j1+") to ("+i2+","+j2+")?");
//            if (T[i][j] == true)
//                System.out.println("YES! "+paths[i][j]);
//            else
//                System.out.println("NO..");
//        }
//
//        //yes
//        public static boolean isConnected(boolean T[][]){
//
//            for(int j=0; j<T[0].length; j++){
//                if (T[0][j] == false)
//                    return false;
//            }
//            return true;
//        }
//
//        //yes
//        public static void printFW_mat(boolean[][] mat){
//            for(int i=0; i<mat.length; i++){
//                for(int j=0; j<mat[0].length; j++){
//                    System.out.print(mat[i][j]+", ");
//                }
//                System.out.println();
//            }
//        }
//
//        //yes
//        private static int NumberOfComponents(boolean[][] T) {
//            int size = T.length;
//            ArrayList<Integer> Seen = new ArrayList<Integer>();
//            ArrayList<Integer> UnSeen = new ArrayList<Integer>();
//            for (int i = 0; i < T.length; i++) {
//                UnSeen.add(i);
//            }
//
//            int counter = 0;
//            while (UnSeen.isEmpty() == false) {
//                int vertex = UnSeen.get(0);
//                counter++;
//                for (int j = 0; j < size; j++) {
//                    if (T[vertex][j]==true)
//                    {
//                        Seen.add(j);
//                    }
//                }
//                UnSeen = Substract(UnSeen, Seen);
//            }
//            return counter;
//        }
//
//        //yes
//        private static String[] GetVertexInEachComponents(boolean[][] T) {
//            int size = T.length, counter = 0;
//            int[] components = new int[size];
//
//            for (int i = 0; i < components.length; i++) {
//                if (components[i] == 0)
//                {
//                    counter++;
//                    components[i] = counter;
//                    for (int j = i+1; j < components.length; j++) {
//                        if (components[j] == 0 && T[i][j] == true)
//                            components[j] = counter;
//                    }
//                }
//            }
//
//            String[] vertexInComponent = new String[counter];
//            for (int i = 0; i < vertexInComponent.length; i++) {
//                vertexInComponent[i] = "";
//            }
//            for (int i = 0; i < components.length; i++) {
//                vertexInComponent[components[i]-1] += i + "\t";
//            }
//
//            return vertexInComponent;
//        }
//
//
//
//        // yes
//        private static String[][] InitPath(int size, boolean[][] T, int n) {
//            String[][] path = new String[size][size];
//            for (int i = 0; i < path.length; i++) {
//                for (int j = 0; j < path.length; j++) {
//                    if (T[i][j] == true)
//                        path[i][j] = "("+_01_Bottles_Problem.getI(i, n)+","+_01_Bottles_Problem.getJ(i, n)+")";
//
//                }
//            }
//            return path;
//        }
//
//
//
//        private static void ReArrangeMat(boolean[][] T) {
//            ReArrangeCols(T);
//            Transpose(T);
//            ReArrangeCols(T);
//        }
//
//        private static void Transpose(boolean[][] T) {
//            boolean temp;
//            for (int i = 0; i < T.length; i++) {
//                for (int j = i+1; j < T.length; j++) {
//                    temp = T[i][j];
//                    T[i][j] = T[j][i];
//                    T[j][i] = temp;
//                }
//            }
//        }
//
//        private static void ReArrangeCols(boolean[][] T) {
//            int start = 0, end , row = 0;
//            while (start<T.length-1)
//            {
//                row = start;
//                end = T.length-1;
//                while(start<end)
//                {
//                    while(start < T.length && T[row][start]==true)
//                        start++;
//                    if (start == T.length)
//                    {
//                        return;
//                    }
//                    while(T[row][end]==false)
//                        end--;
//                    for (int i = 0; i < T.length; i++) {
//                        SwapCols(T, i, start, end);
//
//                    }
//                    start++;
//                    end--;
//                }
//            }
//        }
//
//        private static void SwapCols(boolean[][] T, int i, int start, int end) {
//            boolean temp;
//            temp = T[i][start];
//            T[i][start] = T[i][end];
//            T[i][end] = temp;
//        }
//
//
//        //yes
//        private static ArrayList<Integer> Substract(ArrayList<Integer> UnSeen, ArrayList<Integer> Seen) {
//            ArrayList<Integer> ans = new ArrayList<Integer>();
//            for (int i = 0; i < UnSeen.size(); i++) {
//                if (Seen.contains(UnSeen.get(i)) == false)
//                {
//                    ans.add(UnSeen.get(i));
//                }
//            }
//            return ans;
//        }
//
//
//        //        V4
//        //        /\
//        //       /  \
//        //      /____\
//        //     V0     V5
//        //    V6------V3
//        //      |     |
//        //	    |_____|
//        //     V1     V2
//        //
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
