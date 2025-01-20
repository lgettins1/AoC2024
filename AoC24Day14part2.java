import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day14part2 {

    public static void main(String[] args) throws IOException {
        int answer2 = 0;
        int maxX = 101;
        int maxY = 103;
        String thisLine;
        ArrayList<int[]> robots = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day14input.txt"));
        while ((thisLine = br.readLine()) != null) {
            String[] a = thisLine.split("[=, ]");
            robots.add(new int[]{Integer.parseInt(a[1]), Integer.parseInt(a[2]),
                    Integer.parseInt(a[4]), Integer.parseInt(a[5])});
        }
        for(int loop = 1; loop < 10000; loop ++) {
            for (int[] robot : robots) {
                int x = robot[0] + robot[2];
                x = x % maxX;
                if (x < 0) x += maxX;
                int y = robot[1] + robot[3];
                y = y % maxY;
                if (y < 0) y += maxY;
                robot[0] = x;
                robot[1] = y;
            }
            int a =showArea(robots, maxX, maxY, loop);
            if( a > 0 ) answer2 = a;
        }
        System.out.println("The answer to part 2 is " + answer2);
    }
    public static int showArea(ArrayList<int[]> robots, int maxX, int maxY, int loop) {
        int[][] hallway = new int[maxX + 1][maxY + 1];
        int answer = 0;
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                hallway[x][y] = 0;
            }
        }
        for (int[] robot : robots) {
            hallway[robot[0]][robot[1]]++;
        }
        boolean couldBeTree = false;
        for (int y = 0; y < maxY; y++) {
            StringBuilder thisRow = new StringBuilder();
            for (int x = 0; x < maxX; x++) {
                thisRow.append(hallway[x][y]);
            }
            String thisRowString = thisRow.toString();
            if (thisRowString.contains("11111111")) couldBeTree = true;
        }
        if (couldBeTree) {
            answer = loop;
            for (int y = 0; y < maxY; y++) {
                for (int x = 0; x < maxX; x++) {
                    System.out.print(hallway[x][y]);
                }
                System.out.println();
            }
         }
        return answer;
    }
}
