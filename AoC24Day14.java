import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day14 {
    public static void main(String[] args) throws IOException {
        int answer = 1;
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
        for(int[] robot : robots) {
            int x = robot[0] + 100 * robot[2];
            x = x % maxX;
            if( x < 0) x += maxX;
            int y = robot[1] + 100 * robot[3];
            y = y % maxY;
            if( y < 0) y += maxY;
            robot[0] = x;
            robot[1] = y;
        }
        int[][]hallway = showArea(robots, maxX, maxY);
        int quadW = (maxX - 1) / 2;
        int quadH = (maxY - 1) / 2;
        int[] results = {0, 0, 0, 0};
        for(int quad = 0; quad < 4; quad ++){
            int xi = (quad % 2) * (quadW + 1);
            int yi = (quad / 2) * (quadH + 1);
            for(int sy = yi; sy < yi + quadH; sy ++){
                for(int sx = xi; sx < xi + quadW; sx ++){
                    results[quad] += hallway[sx][sy];
                }
            }
            answer *= results[quad];
        }
        System.out.println("The answer to part 1 is " + answer);

    }
    public static int[][] showArea(ArrayList<int[]> robots, int maxX, int maxY) {
        int[][] hallway = new int[maxX + 1][maxY + 1];
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                hallway[x][y] = 0;
            }
        }
        for (int[] robot : robots) {
            hallway[robot[0]][robot[1]]++;
        }
        return hallway;
    }
}