package Algo2;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class huffman {


    /**
     * Huffman algorithm - using two queue - assumption: the given freq array is sorted
     * Complexity: O(n)
     */
    public static class Huffman { // שדות
        private static final int weight = 0, left = 1, right = 2, parent = 3;
        private char[] chars;
        private int[] freq; // קיבלנו את המערך הזה ממויין
        private int[][] tree; // מטריצה של העץ
        private ArrayBlockingQueue<Integer> q1, q2; // הצהרה על שני תורים
        private String[] code;
        private int n;


        public Huffman(char[] chars, int[] freq) { // בנאי
            n = chars.length;
            this.chars = chars;
            this.freq = freq;
            tree = new int[2 * n - 1][4]; // 2n-1 כמס קודקודי העץ
            code = new String[n]; // מערך של סטרינגים של הקידודים
            q1 = new ArrayBlockingQueue<Integer>(n);
            q2 = new ArrayBlockingQueue<Integer>(n);
            for (int i = 0; i < n; i++) {
                tree[i][weight] = freq[i];// מאתחלים את מטריצת העץ עם המשקלים(האחוזים) של כל תו
                q1.add(i); // מוסיפים לתור את הקודקוד
            }
            createTree();
            buildCode("", 2 * n - 2);
        }

        private void createTree() {
            int k = n; // k מאותחל בהתחלה כגודל של אן(התו באנגלית)
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
            if (i < n) {
                this.code[i] = code;
                return;
            }
            buildCode(code + "0", tree[i][left]);
            buildCode(code + "1", tree[i][right]);
        }

        public String getCode() { // לבדוק האם מה שהודפס נכון
            String ans = "";
            for (int i = 0; i < n; i++) {
                ans += chars[i] + ": " + code[i] + "\n";
            }
            return ans;
        }

        // print the table -לבדוק האם מה שהודפס נכון
        public void printMat(){
            for (int i=0; i<n*2-1; i++){
                for (int j=0; j<4; j++){
                    System.out.print(tree[i][j]+" ");
                }
                System.out.println();
            }
        }
    }


    public static void main(String[] args) {
        //Integer freq2[] = {5,9,12,13,16,45};
        //char letter2[] = {'f','e','c','b','d','a'};
        int freq2[] = {16, 13, 12, 9, 5, 45};
        char letter2[] = {'d', 'b', 'c', 'e', 'f', 'a'};

        Huffman h = new Huffman(letter2, freq2);
        System.out.println(h.getCode());

        h.printMat();

    }
}






























