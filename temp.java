package Algo2;


import java.util.ArrayList;

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

}

    // Created by Or Kadrawi



    class _02_Floyd_Warshall_Binary {

        public static void main(String[] args) {

//		//without path
//		int m = 1; // first bottle
//		int n = 2; //second bottle
//		boolean[][] T = _01_Bottles_Problem.BottlesProblem(m,n);
//		floyd_warshall(T);
//		System.out.println("Bottle1 = " + m + ", Bottle2 = " + n);
//		System.out.println("\nFloyd Warshall Matrix:");
//
//		MyFunctions.Print(T);
//
//		System.out.println("\nIs the graph connected? : " + isConnected(T));
//
//		System.out.println("\nThere is : " + NumberOfComponents(T) + " components.");
//
//		String[] components = GetVertexInEachComponents(T);
//		for (int i = 0; i < components.length; i++) {
//			System.out.println("Component "+i+" is : " + components[i]);
//		}


//		//with path
            // int m = 1; // first bottle
            //  int n = 2; //second bottle
            // boolean[][] T = _01_Bottles_Problem.BottlesProblem(m,n);

//            boolean[][] mat2= {{false,true,true,false,false,false,false},
//                    {true,false,true,false,false,false,false},
//                    {true,true,false,false,false,false,false},
//                    {false,false,false,false,true,false,true},
//                    {false,false,false,true,false,true,false},
//                    {false,false,false,false,true,false,true},
//                    {false,false,false,true,false,true,false}};

//            int size = mat2.length;
//            String[][] path = InitPath(size, mat2, n);
//            floyd_warshall(mat2, path);
//            FixPath(path, n);
//            System.out.println("Bottle1 = " + m + ", Bottle2 = " + n);
//            IsExist(0,0,0,1,mat2,path,n);
//
//            System.out.println("\nAll paths:\n");
            //   PrintAllPath(mat2, path, n);


//		System.out.println("        V4");
//		System.out.println("        /\\");
//		System.out.println("       /  \\");
//		System.out.println("      /____\\");
//		System.out.println("     V0     V5");
//		System.out.println("    V6------V3");
//		System.out.println("      |     |");
//		System.out.println("      |_____|");
//		System.out.println("     V1     V2");
//		System.out.println();
//		boolean[][] T = Init01();
//		floyd_warshall(T);
//
//		System.out.println("Floyd Warshall Matrix:");
//		MyFunctions.Print(T);
//
//		System.out.println("Is the graph connected? : " + isConnected(T));

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

            int m = 1; // first bottle
            int n = 2; //second bottle
            boolean[][] T = _01_Bottles_Problem.BottlesProblem(m, n);
            floyd_warshall(T);
            printFW_mat(T);
            String[][] s=InitPath(6, T, 2);
//            int numCompone = NumberOfComponents(T);
//            System.out.println("number of Components is: " + numCompone);
//            String[] whichCompone = GetVertexInEachComponents(T);
//            for (int i = 0; i < whichCompone.length; i++) {
//                System.out.println("Component " + i + " is : " + whichCompone[i]);

            ReArrangeCols(T);
            Transpose(T);
            }







        //yes
        private static void floyd_warshall(boolean[][] T) { // מטריצת פרוילד וארשל- נכניס אליה מטריצה אחרי שעברה אתחול של מטריצת שכנויות
                                                    // פונקציה זו *מאתחלת* מטריצה כך שגם אם אין קשר ישיר בין שני קודקודים אך יש קשר עקיף יהיה T
            int size = T.length;
            for (int k = 0; k < size; k++) { // k: הוא הקודקוד המקשר
                // הוא קודקוד שבא לעזרת חבר- בעזרתו בודקים האם הוא מקשר בין שני קודקודים לא באופן ישיר
                for (int i = 0; i < size; i++) { // i מציין את השורה , כאשר כל שורה זה קודקוד עבורו בודקים קשר עקיף בינו לבין כל קודקודי הגרף
                    for (int j = 0; j < size; j++) { // j זה העמודות- בכל עמודה יש קודקוד שעבורו בודקים האם יש קשר עקיף עם הקודקוד שבשורה
                        T[i][j] = (T[i][k] && T[k][j]) || T[i][j];// put T if there is path between i and j
                                                                  // עושיפ בדיקה האם הוא קשר ישיר או קשר עקיף ע"י קודקוד k
                    }
                }
            }
        }

        //yes
        private static void floyd_warshallString_notDirectPath(boolean[][] T, String[][] paths) {
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



        //yes
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

        //yes
        public static boolean isConnected(boolean T[][]){

            for(int j=0; j<T[0].length; j++){
                if (T[0][j] == false)
                    return false;
            }
            return true;
        }

        //yes
        public static void printFW_mat(boolean[][] mat){
            for(int i=0; i<mat.length; i++){
                for(int j=0; j<mat[0].length; j++){
                    System.out.print(mat[i][j]+", ");
                }
                System.out.println();
            }
        }

        //yes
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

        //yes
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



        // yes
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


        //yes
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




    }














