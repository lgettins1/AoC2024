import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AoC24Day8 {
    public static Set<Integer> antinodes = new HashSet<>();
    public static Set<Integer> antinodes2 = new HashSet<>();
    public static int mazeDim = 50;

    public static void main(String[] args) throws IOException {
        String thisLine;
        int cr = 0;
        String[][] antennae = new String[2000][3];
        int aCount = 0;
        Set<Character> aTypes = new HashSet<>();
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day8input.txt"));
        while ((thisLine = br.readLine()) != null) {
            for (int a = 0; a < thisLine.length(); a++) {
                if (thisLine.charAt(a) != '.') {
                    antennae[aCount][0] = thisLine.substring(a, a + 1);
                    antennae[aCount][1] = Integer.toString(a);
                    antennae[aCount][2] = Integer.toString(cr);
                    aTypes.add(thisLine.charAt(a));
                    aCount++;
                }
            }
            cr++;
        }
        for (Character aType : aTypes) {
            ArrayList<int[]> pairs = new ArrayList<>();
            for (String[] row : antennae) {
                if (row[0] != null && row[0].startsWith(Character.toString(aType))) {
                    int[] match = new int[2];
                    match[0] = Integer.parseInt(row[1]);
                    match[1] = Integer.parseInt(row[2]);
                    pairs.add(match.clone());
                }
            }
            for (int l1 = 0; l1 < pairs.size(); l1++) {
                for (int l2 = l1 + 1; l2 < pairs.size(); l2++) {
                    int xd = pairs.get(l1)[0] - pairs.get(l2)[0];
                    int yd = pairs.get(l1)[1] - pairs.get(l2)[1];
                    determine(pairs.get(l1)[0] + xd, pairs.get(l1)[1] + yd);
                    determine(pairs.get(l2)[0] - xd, pairs.get(l2)[1] - yd);
                    determine2(pairs.get(l1)[0], pairs.get(l1)[1], xd, yd);
                }
            }
        }
        int answer = antinodes.size();
        int answer2 = antinodes2.size();

        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);


    }

    public static void determine(int x1, int y1) {
        if (x1 > -1 && x1 < mazeDim && y1 > -1 && y1 < mazeDim) {
            antinodes.add((y1 * mazeDim) + x1);
        }
    }

    public static void determine2(int x1, int y1, int xd, int yd) {
        int sx = x1;
        int sy = y1;
        while (sx > -1 && sx < mazeDim && sy > -1 && sy < mazeDim) {
            antinodes2.add((sy * mazeDim) + sx);
            sx += xd;
            sy += yd;
        }
        sx = x1;
        sy = y1;
        while (sx > -1 && sx < mazeDim && sy > -1 && sy < mazeDim) {
            antinodes2.add((sy * mazeDim) + sx);
            sx -= xd;
            sy -= yd;
        }
    }
}