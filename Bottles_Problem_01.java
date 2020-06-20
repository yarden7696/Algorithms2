package Algo2;

import java.util.ArrayList;

// יש פה עד תירגול 3 כולל

public class Bottles_Problem_01 {
    private static _01_Bottles_Problem MyFunctions;
///////////////////////////////////כל המימושים פה יכולים להופיע כשאלה כללית בלי קשר לבעיית הבקבוקים//////////////////////////////
    //כמו לדוגמא-מיהם רכיבי קשירות של הגרף, האם קיים מסלול בין i לj... אז אפשר להתייחס לקריאה של mat[i][j] כמו תא רגיל, i שורות וj עמודות
    // כאשר בין כל שורה i ועמודה j נבדוק האם יש מסלול ישיר או עקיף... ככה אפשר להתייחס לזה בצורה כללית ולא להסתכל על זה כבעיית הבקבוקים
    // המימוש בהמשך קשור יותר לבעיית הבקבוקים ואז מסלול בין i לj יהיה האם ניתן להגיע ממצב i=(,)=מצב של בקבוק, למצב אחר j=(,)=המצב שנרצה להגיע אליו
    /**
     * build the Neighbors matrix
     * @param n is max size of the first bottle
     * @param m is max size of the second bottle
     * @return a boolean matrix
     */
    // bottle problem- Neighbors matrix = גרף הבקבוקים= האם יש קשר ישיר בין 2 קודקודים
    public static boolean[][] buildMatrix_Neighbors(int n, int m) { // יצירת מטריצת שכנויות
        int size = (n + 1) * (m + 1);
        boolean[][] mat = new boolean[size][size];
		int row;
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < m+1; j++) {
				row = findIndex(i,j,m); // (i,j)bottle A , m is bottle B
				mat[row][findIndex(0,j,m)] = true; // empty side left
				mat[row][findIndex(i,0,m)] = true; // empty side right
				mat[row][findIndex(n,j,m)] = true; // full side left
				mat[row][findIndex(i,m,m)] = true; // full side right
				mat[row][findIndex(i+j-(Math.min(i+j, m)),Math.min(i+j, m),m)] = true;//Pour from bottle1 to bottle2
                mat[row][findIndex(Math.min(i+j, n),i+j-Math.min(i+j, n),m)] = true;//Pour from bottle2 to bottle1

            }
		}
            return mat;
        }

    /**
     * print the Neighbors matrix
     * @param mat is the Neighbors mat
     */
    // bottle problem- print Neighbors matrix
    public static void PrintNeighbors_mat(boolean[][] mat)// just for me- מדפיסה מטריצת שכנויות
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

    public static int findIndex(int i, int j, int m)
    {
        return (m+1)*i + j;
    }

    public static int getI(int k, int m)
    {
        return k / (m+1);
    }

    public static int getJ(int k, int m)
    {
        return k % (m+1);
    }

    /**
     * this function build a matrix that if there is no direct connect between 2 vertex but
     * ///**there is עקיף connect///*** between 2 vertex so we put T !!!
     * @param **mat** is a Neighbors matrix!!!
     */
    public static void FWBoolean(boolean[][] mat){ // מצריטה זו נקראת מטריצת המסלולים- היא בודקת האם קיים מסלול *עקיף* בין 2 קודקודים
        int n = mat.length;
        for (int k = 0; k<n; k++){// k: הוא הקודקוד המקשר
                                 // הוא קודקוד שבא לעזרת חבר- בעזרתו בודקים האם הוא מקשר בין שני קודקודים לא באופן ישיר
            for (int i = 0; i<n; i++){  // (stam) מציין את השורה , כאשר כל שורה זה קודקוד עבורו בודקים קשר עקיף בינו לבין כל קודקודי הגרף i
                for (int j = 0; j<n; j++){ // line זה העמודות- בכל עמודה יש קודקוד שעבורו בודקים האם יש קשר עקיף עם הקודקוד שבשורה j
                    mat[i][j] = mat[i][j] || (mat[i][k] && mat[k][j]);// put T if there is path between i and j
                    // עושיפ בדיקה האם הוא קשר ישיר או קשר עקיף ע"י קודקוד k
                }
            }
        }
    }

    /**
     * this function print the froyld warshell mat
     * @param mat id the froyld warshell mat
     */
    public static void printFW_mat(boolean[][] mat){
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[0].length; j++){
                System.out.print(mat[i][j]+", ");
            }
            System.out.println();
        }
    }


    /**
     * This function takes a ***neighboring matrix*** and with it creates a string matrix.
     * If there is a direct relationship between two vertices,if(mat[i][j] == true) then path[i][j] = the string where
     * we gave 'T'. explained of that function on page 13.
     * @param size size the mat
     * @param mat is a ***neighboring matrix***
     * @param m max size of buttle 2
     * @return a string matrix that every תא there is direct connection between 2 vertex.
     */
    private static String[][] Init_DirectPath(int size, boolean[][] mat, int m) {
        String[][] path = new String[size][size];
        for (int i = 0; i < path.length; i++) { //i=(i,j). i is the lines when (i,j) is a situation of bottle
            for (int j = 0; j < path.length; j++) {// j=j. j is the columns. explained on page 13.
                if (mat[i][j] == true)
                    path[i][j] = "("+_01_Bottles_Problem.getI(i, m)+","+_01_Bottles_Problem.getJ(i, m)+")";
            }
        }
        return path;
    }

    // באופן כללי הפונק בודקת האם יש מסלול בין שני קודקודים בגרף (מסלול ישיר או עקיף)
    /**
     * this function checks if there is מסלול between 2 modes of 2 bottles
     * explanation on page 2 about the findIndex(i,j) function.
     * findIndex(i,j) function gets situation of bottle and turn it to a number of row.
     * T היא מטריצת המסלולים(מסלולים עקיפים וישירים)
     */
    private static void IsExist(int i1, int j1, int i2, int j2, boolean[][] T, String[][] paths, int m) {
        int i = findIndex(i1, j1, m); //make vertex (i1,j1) = row number
        int j = findIndex(i2, j2, m); //make vertex (i2,j2) = column number
        System.out.println("Is Exist path from ("+i1+","+j1+") to ("+i2+","+j2+")?");
        if (T[i][j] == true)
            System.out.println("YES! "+paths[i][j]);
        else
            System.out.println("NO..");
    }


    /**
     * this function checks if the graph is connected. if the all first line of the matrix is 'T'
     * then the graph is connected.
     * @param mat is a boolean matrix after froyld warshell algorithm
     * @return true if the graph is connected, else return false.
     */
    public static boolean isConnected(boolean mat[][]){

        for(int j=0; j<mat[0].length; j++){
            if (mat[0][j] == false)
                return false;
        }
        return true;
    }


    /**
     * This function returns how many Number Of Components graph has.
     * explained of that function on page 14.
     * @param fwMat is a boolean matrix after froyld warshell matrix
     * @return the Number Of Components of the graph.
     */
    private static int NumberOfComponents(boolean[][] fwMat) {
        int size = fwMat.length;
        ArrayList<Integer> Seen = new ArrayList<Integer>();
        ArrayList<Integer> UnSeen = new ArrayList<Integer>();
        for (int i = 0; i < fwMat.length; i++) {
            UnSeen.add(i);
        }

        int counter = 0;
        while (UnSeen.isEmpty() == false) {
            int vertex = UnSeen.get(0);
            counter++;
            for (int j = 0; j < size; j++) {
                if (fwMat[vertex][j]==true)
                {
                    Seen.add(j);
                }
            }
            UnSeen = Substract(UnSeen, Seen);
        }
        return counter;
    }


    /**
     * Helper function that compares the two seen and unseen arrays.
     * The function checks which תא in the array we haven't visited.
     * @param UnSeen
     * @param Seen
     * @return aמ Updated unseen array.
     */
    private static ArrayList<Integer> Substract(ArrayList<Integer> UnSeen, ArrayList<Integer> Seen) {
        ArrayList<Integer> newUnseen = new ArrayList<Integer>();
        for (int i = 0; i < UnSeen.size(); i++) {
            if (Seen.contains(UnSeen.get(i)) == false)
            {
                newUnseen.add(UnSeen.get(i));
            }
        }
        return newUnseen;
    }


    /**
     *This function returns which vertexes are in every Components.
     * explained of that function on page 15.
     * @param fwMat is a boolean matrix after froyld warshell matrix
     * @return the array that in every תא there is a string of the all vertex that in the *same* component.
     */
    private static String[] GetVertexInEachComponents(boolean[][] fwMat) {
        int size = fwMat.length, counter = 0;
        int[] arrComponents = new int[size];// create array in size that equal the number of vertexes

        for (int i = 0; i < arrComponents.length; i++) // running on the "lines" of the matrix
        {
            if (arrComponents[i] == 0) // if that equal to 0 its mean that its a new Component
            {
                counter++; // its a new Component so we need to add 1
                arrComponents[i] = counter;
                for (int j = i+1; j < arrComponents.length; j++)//running on the "columns" of the matrix
                {// every line=i=vertex we compare it to the columns=j and check if there is 'T'.
                    if (arrComponents[j] == 0 && fwMat[i][j] == true)// if both of theme are 'T' so-
                        arrComponents[j] = counter; // we put the counter on this תאים that represent by j.
                }
            }
        }
        //so far i create an array that it initialize with numbers, that each number is a component.

        String[] vertexInCommonComponent = new String[counter];// create an array of string with size of counter
        for (int i = 0; i < vertexInCommonComponent.length; i++) //running on this array and init it with : ""
        {
            vertexInCommonComponent[i] = "";
        }

        for (int i = 0; i < arrComponents.length; i++)//running on the Initialized array with the "counters"=components.
        {
       vertexInCommonComponent[arrComponents[i]-1] += i + "\t";// in every component we put the common vertexes to this component
        }

        return vertexInCommonComponent; // return the array that in every תא there is a string of the all vertex
        // that in the *same* component.
    }


    /**
     *This function initialize paths of string that there is no direct connect between 2 vertex.
     * @param neiborMat
     * @param directPath_String_mat
     */
    private static void floyd_warshall_initNotDirectPath(boolean[][] neiborMat, String[][] directPath_String_mat) {
        int size = neiborMat.length;
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (neiborMat[i][j] == false && ((neiborMat[i][k] && neiborMat[k][j])==true))
                        directPath_String_mat[i][j] = directPath_String_mat[i][k] + "->" + directPath_String_mat[k][j];
                    neiborMat[i][j] = (neiborMat[i][k] && neiborMat[k][j]) || neiborMat[i][j];
                }
            }
        }
    }

//    Is Exist path from (0,0) to (0,4)?
//    YES! (0,0)->(3,0)->(0,3)->(3,3)->(1,5)->(1,0)->(0,1)->(3,1)->(0,4)

    /**
     *this function fix the all paths by that that if i did not use this function the path between :
     * (0,0) to (0,4) : look like that : (0,0)->(3,0)->(0,3)->(3,3)->(1,5)->(1,0)->(0,1)->(3,1) , (no(0,4))
     * without the (0,4) at the end. and after we usr that function the path between (0,0) to (0,4)
     * will look like: (0,0)->(3,0)->(0,3)->(3,3)->(1,5)->(1,0)->(0,1)->(3,1)->(0,4) (with(0,4)).
     */
          private static void FixPath(String[][] path, int m) {
                 for (int i = 0; i < path.length; i++) {
                     for (int j = 0; j < path.length; j++) {
                         path[i][j] += "->("+getI(j, m)+","+getJ(j, m)+")";
                     }
                 }
             }

    /**
     * print all paths from each vertex to each vertex
     * @param fwMat is a boolean fw matrix
     * @param fullPath_Mat is a string matrix after floyd_warshall_initNotDirectPath function
     * @param m is the max size of bottle 2
     */
    private static void PrintAllPath(boolean[][] fwMat, String[][] fullPath_Mat, int m) {
        for (int i = 0; i < fullPath_Mat.length; i++) {
            for (int j = 0; j < fullPath_Mat.length; j++) {
                System.out.print("("+getI(i, m)+","+getJ(i, m)+")->("+getI(j, m)+","+getJ(j, m)+"):\t");
                if (fwMat[i][j] == true)
                    System.out.println(fullPath_Mat[i][j]);
                else
                    System.out.println("NO..");
            }
        }
    }

    /**
     * מסדר את המטריצה כך שתראה יפה, הבדלים בעמוד 4- בין מטריצה "יפה" למטריצה "לא יפה" (כמובן ששתי המטריצות עם אותן
     * קודקודים רק עם סדר שונה של הקודקודים.
     * @param T
     */
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


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // continue Bottle problem, now the numbers on the vertex! (not on the edge like above).

    //   תירגול 3 - "ברשימה שלאור מה צריך לעשות חסר את -"מציאת מטריצת המרחקים עם משקלים על הצלעות"
    // כי פה אנחנו כבר מקבלים מטריצת משקלים(מרחקים) מלאה במרחקים שיש בין שני קודקודים ישירים ואנחנו רק מעיפים את האינסוף...
    // אז צריכה גם את הפונק שבונה את מטריצת המשקלים בין 2 קודקודים ישירים

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
        // לבדוק איך אני בונה את המטריצה שאני מקבלת בפונק זו, כלומר איך אני בונה את המטריצה שבע"מ 7 עם 2 הריבועים הירוקים, מטריצה עם מספרים המשיעים על קשר ישיר בין 2 קודקודים

        private static void floyd_warshall(int[][] mat) {// פה נקבל מטריצת *משקלים* שכבר יש באלכסון הראשי 0,ומס כלשהו
                                                        // בין שתי צלעות עם קשר ישיר
            int size = mat.length;
            for (int k = 0; k < size; k++) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (mat[i][k]!= inf &&  mat[k][j]!= inf) //If the two green squares different from infinity
                            if (mat[i][k] + mat[k][j] < mat[i][j])//If yes:check if the current value is greater than the two greens
                                mat[i][j] = mat[i][k] + mat[k][j];//if yes put instead the current value the 2 greens.
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
         *  mat is the mat with numbers with the direct connection between 2 vertexes , path is a string mat between 2 direct vertexes
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
         * המרה ממטריצה של משקלים על קודקודים למשקלים על צלעות(כי פיתרון הבעיה של משקלים על צלעות אנחנו כבר יודעים לפתור), מתבצעת המרה לא מלאה- יש צורך בתיקון המטריצה,
         *
         * param v : is the Neighbor matrix for the graph = מטריצת הקודקודים.
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










    public static void main(String[] args) {
//        int n = 1; // first bottle
//        int m = 2; // second bottle
//        boolean[][] mat1 = buildMatrix_Neighbors(n,m);//first method
        boolean[][] mat2= {{false,false,false,true,false,true,false},
                           {false,false,false,false,true,false,true},
                           {false,false,false,false,true,false,true},
                           {true,false,false,false,false,true,false},
                           {false,true,true,false,false,false,false},
                           {true,false,false,true,false,false,false},
                           {false,true,true,false,false,false,false}};
        PrintNeighbors_mat(mat2);
        FWBoolean(mat2);
        System.out.println("_______________");
        printFW_mat(mat2);

        System.out.println("_______________");
        ReArrangeMat(mat2);
        System.out.println("\nReArrange Matrix:");
       	MyFunctions.Print(mat2);
    }

}
