import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shell {
    private Shell() {
    }

    public static void main(String[] args) throws IOException {
        Scanner inputScn = new Scanner(new File("src/main/java/input.txt")).useDelimiter("");
        ArrayList<String> syms = new ArrayList<>();
        while (inputScn.hasNext()) {
            syms.add(inputScn.next());
        }
        Shell.sort(syms);
        FileWriter myWriter = new FileWriter("src/main/java/output.txt");
        myWriter.write(String.join("", syms));
        myWriter.close();
    }

    public static void sort(List<String> a) {
        int n = a.size();
        // 3x+1
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a.get(j), a.get(j - h)); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(List<String> a, int i, int j) {
        String swap = a.get(i);
        a.set(i, a.get(j));
        a.set(j, swap);
    }
}

