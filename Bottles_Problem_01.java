package Algo2;

import java.util.ArrayList;

public class Bottles_Problem_01 {
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
    public static boolean[][] buildMatrix_Neighbors(int n, int m) { // יצירת מטריצת שכנויות
        int size = (n + 1) * (m + 1);
        boolean[][] mat = new boolean[size][size];
		int row;
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < m+1; j++) {
				row = findIndex(i,j,m);
				mat[row][findIndex(0,j,m)] = true; // empty side left
				mat[row][findIndex(i,0,m)] = true; // empty side right
				mat[row][findIndex(n,j,m)] = true; // full side left
				mat[row][findIndex(i,m,m)] = true; // full side right
				mat[row][findIndex(i+j-(Math.min(i+j, m)),Math.min(i+j, m),m)] = true;//Pour from bottle2 to bottle1
                mat[row][findIndex(Math.min(i+j, n),i+j-Math.min(i+j, n),m)] = true;//Pour from bottle1 to bottle2

            }
		}
            return mat;
        }

    /**
     * print the Neighbors matrix
     * @param mat is the Neighbors mat
     */
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
     * @param mat is a Neighbors matrix
     */
    public static void FWBoolean(boolean[][] mat){
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



    public static   void main(String[] args) {
        int n = 1; // first bottle
        int m = 2; // second bottle
        boolean[][] mat1 = buildMatrix_Neighbors(n,m);//first method
        boolean[][] mat2= {{false,true,true,false,false,false,false},
                           {true,false,true,false,false,false,false},
                           {true,true,false,false,false,false,false},
                           {false,false,false,false,true,false,true},
                           {false,false,false,true,false,true,false},
                           {false,false,false,false,true,false,true},
                           {false,false,false,true,false,true,false}};
        PrintNeighbors_mat(mat2);
        //FWB(mat2);
        System.out.println("_______________");
        //printFW_mat(mat2);

        System.out.println("_______________");

    }













}