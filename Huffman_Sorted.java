package Algo2;
import java.util.concurrent.ArrayBlockingQueue;

public class Huffman_Sorted {

    /**
     * Huffman algorithm - using two queue - assumption: the given freq array is sorted
     * Complexity: O(n)
     */
    public static class Huffman { // שדות
        private static final int weight = 0, left = 1, right = 2, parent = 3;
        private char[] chars;
        private int[] freq; // sorted array of frequency
        private int[][] tree; // mat of the tree
        private ArrayBlockingQueue<Integer> q1, q2;
        private String[] code;
        private int len;

        // CONSTRUCTOR
        public Huffman(char[] chars, int[] freq) {
            len = chars.length;
            this.chars = chars;
            this.freq = freq;
            tree = new int[2 * len - 1][4]; // 2n-1 like the number vertexes of the tree
            code = new String[len]; // the codes of the tree
            q1 = new ArrayBlockingQueue<Integer>(len);
            q2 = new ArrayBlockingQueue<Integer>(len);
            for (int i = 0; i < len; i++) {
                tree[i][weight] = freq[i];// מאתחלים את מטריצת העץ עם המשקלים(האחוזים) של כל תו
                q1.add(i); // מוסיפים לתור את הקודקוד
            }
            createTree();
            buildCode("", 2 * len - 2);
        }

        private void createTree() {
            int k = len; // k מאותחל בהתחלה כגודל של אן(התו באנגלית)
            while (q1.size() + q2.size() > 1) { // כל עוד שני התורים מלאים והסכום שלהם גדול מ-1
                int l = getMin(); // שולפים את המינימום לילד שיהיה ילד שמאלי (l זה בעצם מס הקודקוד- כלומר אינדקס הקודקוד)
                int r = getMin(); // שולפים את המינימום שיהיה ילד ימני (r זה בעצם מס הקודקוד -כלומר אינדקס הקודקוד)
                tree[l][parent] = k; // עידכון מיהו האבא של l
                tree[r][parent] = k; // עידכון מיהו האבא של r
                tree[k][weight] = tree[l][weight] + tree[r][weight]; // עידכון המשקל של האבא
                tree[k][left] = l; // עידכון הבן השמאלי של האבא
                tree[k][right] = r; // עידכון הבן הימני של האבא
                q2.add(k); // הוספת האבא k לתור q2 שהוא תור של האבות המדומים
                k++; // מקדמים את k כך שיהיה אבא חדש
            }
        }

        private int getMin() {
            if (q1.isEmpty() && q2.isEmpty()) return -1;
            if (q1.isEmpty()) return q2.poll();
            if (q2.isEmpty()) return q1.poll();
            if (tree[q1.peek()][weight] > tree[q2.peek()][weight]) return q2.poll();
            return q1.poll();
        }

        private void buildCode(String code, int i) { // i הוא מס קודקוד בעץ שלו נרצה למצור קידוד
            if (i < len) {
                this.code[i] = code;
                return;
            }
            buildCode(code + "0", tree[i][left]);
            buildCode(code + "1", tree[i][right]);
        }

        public String getCode() { // לבדוק האם מה שהודפס נכון
            String ans = "";
            for (int i = 0; i < len; i++) {
                ans += chars[i] + ": " + code[i] + "\n";
            }
            return ans;
        }

        // print the table
        public void printMat(){
            for (int i = 0; i< len *2-1; i++){
                for (int j=0; j<4; j++){
                    System.out.print(tree[i][j]+" ");
                }
                System.out.println();
            }
        }
    }


    public static void main(String[] args) {
        int freq2[] = {5,9,12,13,16,45};
        char letter2[] = {'f','e','c','b','d','a'};

//        int freq2[] = {16, 13, 12, 9, 5, 45};
//        char letter2[] = {'d', 'b', 'c', 'e', 'f', 'a'};

        Huffman h = new Huffman(letter2, freq2);
        System.out.println(h.getCode());

        System.out.println("the mat is:");
        h.printMat();

    }
}



























