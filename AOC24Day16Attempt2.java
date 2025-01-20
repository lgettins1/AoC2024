import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class AOC24Day16Attempt2 {
    public static ArrayList<String> maze = new ArrayList<>();
    public static ArrayList<String> histories = new ArrayList<>();
    public static int answer = 999999999;
    public static int endX = 0;
    public static int endY = 0;
    public static class Step {
        String xy;
        int direction;
        int score;
        public Step(String xy, int direction, int score){
            this.xy = xy;
            this.direction = direction;
            this.score =score;
        }
    }
    public static ArrayList <Step> bigHistory = new ArrayList<>();
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
        String startXY = startX + "," + startY;
        Step thisStep = new Step(startXY, 0,0);
        bigHistory.add(thisStep);
        navigate(startX, startY, 0, 0, startXY + "-");
        HashSet<String> locations = new HashSet<>();
        for(String hh : histories){
            String[] hhh = hh.split("-");
            Collections.addAll(locations, hhh);
        }
        int answer2 = locations.size();
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);
    }

    public static void navigate(int rdX, int rdY, int dir, int steps, String history){
         if(rdX == endX && rdY == endY && steps <= answer){
            bestAnswer(steps, history);
        } else {
            int[] choices = new int [4];
            int choiceCount = 1;
            while(choiceCount == 1){
                choiceCount = 0;
                for(int d = 0; d < 4; d = (2 *  d) + 1) {
                    choices[d] = 0;
                    if (maze.get(rdY + deltas[(dir + d) % 4][1]).charAt(rdX + deltas[(dir + d) % 4][0]) != '#') choices[d] = 1;
                    choiceCount += choices[d];
                }
                if(choiceCount == 1) {
                    int option = choices[1] + 3 * choices[3];
                    dir = (dir + option) % 4;
                    steps += 1 + 1000 * (option % 2);
                    rdX += deltas[dir][0];
                    rdY += deltas[dir][1];
                    if(checkOn(rdX, rdY, dir, steps) == 0) {
                        choiceCount = 0;
                        break;
                    }
                    history += rdX + "," + rdY + "-";
                    if(rdX == endX && rdY == endY && steps <= answer) {
                        bestAnswer(steps, history);
                        choiceCount = 0;
                    }
                }
            }
            if(choiceCount > 1){
                for(int d = 0; d < 4; d = (2 * d) + 1){
                    if(choices[d] == 1){
                        int ndir = (dir + d) % 4;
                        int nrdX = rdX + deltas[ndir][0];
                        int nrdY = rdY + deltas[ndir][1];
                        int nSteps = steps + 1 + 1000 * (d % 2);
                        String nHistory = history + nrdX + "," + nrdY + "-";
                        int scenario = checkOn (nrdX, nrdY, ndir, nSteps);
                        if(scenario > 0) {
                            navigate(nrdX, nrdY, ndir, nSteps, nHistory);
                        }
                    }
                }
            }
        }
    }

    public static int checkOn(int rdX, int rdY, int dir, int steps){
        String ts = (rdX + "," + rdY);
        int scenario = 1;
        for(Step thisStep : bigHistory){
            if(thisStep.xy.equals(ts) && thisStep.direction == dir){
                if(thisStep.score < steps){
                    scenario = 0;
                } else {
                    int index = bigHistory.indexOf(thisStep);
                    thisStep.score = steps;
                    bigHistory.set(index, thisStep);
                    scenario = 2;
                }
                break;
            }
        }
        if(scenario == 1) {
            Step s = new Step (rdX + "," + rdY, dir, steps);
            bigHistory.add(s);
        }
        return scenario;
    }
    public static void bestAnswer(int steps, String history){
        if(steps < answer){
            answer = steps;
            histories.clear();
        }
        histories.add(history);
    }
}
