import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day16 {
    public static ArrayList<String> maze = new ArrayList<>();
    public static int answer = 999999999;
    public static int endX = 0;
    public static int endY = 0;
    public static ArrayList <String> bigHistory = new ArrayList<>();
    public static ArrayList <Integer> bigHistorySteps = new ArrayList<>();
    public static int[][] deltas = {{ 1, 0}, { 0, 1}, { -1, 0}, { 0, -1}};

    public static void main(String[] args) throws IOException {

        String thisLine;
        int startX = 0;
        int startY = 0;
         int row = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day16input.txt"));
        while ((thisLine = br.readLine()) != null) {
            maze.add(thisLine);
            if(thisLine.contains("E")){
                endY = row;
                endX = thisLine.indexOf("E");
            }
            if(thisLine.contains("S")){
                startY = row;
                startX = thisLine.indexOf("S");
            }
            row ++;
        }
        String history = startX + "," + startY + "-0- ";
        bigHistory.add(startX +"," + startY);
        bigHistorySteps.add(0);
        navigate(startX, startY, 0, 0, 0, history);
        System.out.println("The answer to part 1 is " + answer);
    }
    public static void navigate(int rdX, int rdY, int dir, int steps, int depth, String history){
        System.out.println(rdX +"," + rdY + " d" + dir + " steps=" + steps + " depth " + depth + " best=" + answer);
 //       System.out.println(history);
        System.out.println(bigHistory.size());
        if(rdX == endX && rdY == endY && steps < answer){
            answer = steps;
            System.out.println(answer);
        } else {
            if(maze.get(rdY + deltas[dir][1]).charAt(rdX + deltas[dir][0]) != '#'){
                String a = (rdX + deltas[dir][0]) + "," + (rdY + deltas[dir][1]) + "-" + dir + "- ";
                String element = (rdX + deltas[dir][0]) + "," + (rdY + deltas[dir][1]);
                if(!history.contains(a) && steps + 1 < answer){
                    int st = 0;
                    if(bigHistory.contains(element)){
                        st = bigHistorySteps.get(bigHistory.indexOf(element));
                        if(st > steps + 1) bigHistorySteps.set(bigHistory.indexOf(element),steps + 1);
                    }
                    if(st == 0 || st > steps + 1) {
                        if(st == 0) {
                            bigHistory.add(element);
                            bigHistorySteps.add(steps + 1);
                        }
                        int block = 1;
                        boolean keepOn = true;
                        int nrdX = rdX + deltas[dir][0];
                        int nrdY = rdY + deltas[dir][1];
                        int nSteps = steps + 1;
                        while(keepOn){
                            boolean op = (maze.get(rdY + (block + 1) * deltas[dir][1]).charAt(rdX + (block + 1) * deltas[dir][0]) != '#');
                            boolean lw = (maze.get(rdY + deltas[(dir + 1) % 4][1] + block * deltas[dir][1] ).
                                    charAt(rdX + deltas[(dir + 1) % 4][0] + block * deltas[dir][0]) == '#');
                            boolean rw = (maze.get(rdY + deltas[(dir + 3) % 4][1] + block * deltas[dir][1] ).
                                    charAt(rdX + deltas[(dir + 3) % 4][0] + block * deltas[dir][0]) == '#');
                            block ++;
                            if(op && lw && rw){
                                   nrdX += deltas[dir][0];
                                   nrdY += deltas[dir][1];
                                   nSteps ++;
                            } else {
                                keepOn = false;
                            }
                        }
                        a = nrdX  + "," + nrdY + "-" + dir + "- ";


                        navigate(nrdX, nrdY, dir, nSteps, depth + 1, history + a);
                    }
                }
            }
            String a = rdX + "," + rdY  + "-" + (dir + 1) % 4 + "- ";
            String b = rdX + "," + rdY + "-" + (dir + 3) % 4 + "- ";
            if(maze.get(rdY + deltas[(dir + 1) % 4][1]).charAt(rdX + deltas[(dir + 1) % 4][0]) == '.'){
                if(!history.contains(a) && !history.contains(b) && steps + 1000 < answer) {
                   navigate(rdX, rdY, (dir + 1) % 4, steps + 1000, depth + 1,history + a);
                }
            }
            if(maze.get(rdY + deltas[(dir + 3) % 4][1]).charAt(rdX + deltas[(dir + 3) % 4][0]) == '.') {
                if (!history.contains(a) && !history.contains(b) && steps + 1000 < answer) {
                    navigate(rdX, rdY, (dir + 3) % 4, steps + 1000, depth + 1,history + b);
                }
            }

        }
    }
}
