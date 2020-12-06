import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BigShell {

    private static final int LIMIT = 10;

    private BigShell() {
    }

    public static void main(String[] args) throws IOException {
        Scanner inputScn = new Scanner(new File("src/main/java/input.txt")).useDelimiter("");
        List<String> chunks = new ArrayList<>();
        int chunkNum = 0;
        while (inputScn.hasNext()) {
            List<String> chunk = new ArrayList<>();
            for (int i = 0; i < LIMIT && inputScn.hasNext(); i++) {
                chunk.add(inputScn.next());
            }
            sort(chunk);
            String chunkFile = String.format("src/main/java/chunks/chunk-%d.txt", chunkNum++);
            FileWriter myWriter = new FileWriter(chunkFile);
            myWriter.append(String.join("", chunk));
            myWriter.close();
            chunks.add(chunkFile);
        }
        inputScn.close();
        PriorityQueue<Pair<Scanner, String>> chunkScns = new PriorityQueue<>(Comparator.comparing(Pair::getValue));
        chunks.stream()
                .map(s -> {
                    try {
                        return new Scanner(new File(s));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .peek(scanner -> scanner.useDelimiter(""))
                .filter(Scanner::hasNext)
                .map(scanner -> new Pair<>(scanner, scanner.next()))
                .forEach(chunkScns::offer);
        FileWriter outputWrt = new FileWriter("src/main/java/output.txt");
        while (!chunkScns.isEmpty()) {
            String sym = getMin(chunkScns);
            outputWrt.append(sym);
        }
        outputWrt.close();
    }

    private static String getMin(PriorityQueue<Pair<Scanner, String>> chunkScns) {
        Pair<Scanner, String> poll = chunkScns.poll();
        if (poll.getKey().hasNext()) {
            chunkScns.offer(new Pair<>(poll.getKey(), poll.getKey().next()));
        }
        return poll.getValue();
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

