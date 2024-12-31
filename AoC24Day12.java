import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AoC24Day12 {
    public static String[] garden = new String[141];
    public static int[][] visited = new int[141][141];
    public static ArrayList<int[]> fence = new ArrayList<>();
    public static int area;
    public static int row;
    public static void main(String[] args) throws IOException {
        int answer = 0;
        int answer2 = 0;
        String thisLine;
        row = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day12input.txt"));
        while ((thisLine = br.readLine()) != null) {
            garden[row] = thisLine;
            row ++;
        }
        for(int y = 0; y < row; y ++){
            for(int x = 0; x < row; x ++){
                visited[x][y] = 0;
            }
        }
        for(int y = 0; y < row; y ++) {
            for (int x = 0; x < row; x++) {
                fence.clear();
                area = 0;
                flood(x, y);
                answer += (fence.size() * area);
                int fence2 = fence.size();
                for(int d = 0; d < 4; d ++){
                    int[][] filter = new int[fence.size()][2];
                    for(int init = 0; init < fence.size(); init ++){
                        filter[init][0] = -6;
                        filter[init][1] = -6;
                    }
                    int filterSize = 0;
                    for (int[] section : fence) {
                        if (section[2] == d) {
                            filter[filterSize][0] = section[0];
                            filter[filterSize][1] = section[1];
                            filterSize ++;
                        }
                    }
                    if(filterSize > 1){
                        int firstSort = d % 2;
                        int secondSort = 1 - firstSort;
                        Arrays.sort(filter, (o1, o2) -> {
                            if(o1[firstSort] != o2[firstSort]){
                                return Integer.compare(o1[firstSort], o2[firstSort]);
                            } else {
                                return Integer.compare(o1[secondSort], o2[secondSort]);
                            }
                        });
                        for(int b = 1; b < filter.length; b ++){
                            if(filter[b][firstSort] == filter [b - 1][firstSort] &&
                                        filter[b][secondSort] == filter[b - 1][secondSort] + 1) fence2 --;
                        }
                    }
                }
                answer2 += (fence2 * area);
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
    public static void flood(int x, int y){
        if(visited[x][y] == 0){
            visited[x][y] = 1;
            area ++;
            for(int dir = 0; dir < 4; dir ++){
                int xs = x + (1 - dir) * ((dir + 1) % 2);
                int ys = y + (2 - dir) * (dir % 2);
                if(xs < 0 || xs == row || ys < 0 || ys == row || garden[y].charAt(x) != garden[ys].charAt(xs)){
                    fence.add(new int[]{xs, ys, dir});
                } else {
                    if(visited[xs][ys] == 0){
                        flood(xs, ys);
                    }
                }
            }
        }
    }
}
