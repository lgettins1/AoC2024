import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day15 {
    public static ArrayList<String> maze = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        int answer = 0;
        String thisLine;
        int section = 0;
        ArrayList<Character> steps = new ArrayList<>();
        int mazeH = 0;
        int robotX = 0;
        int robotY = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day15input.txt"));
        while ((thisLine = br.readLine()) != null) {
            if(thisLine.isEmpty()) section = 1;
            if(section == 0){
                if(thisLine.contains("@")){
                    robotY = mazeH;
                    robotX = thisLine.indexOf("@");
                }
                maze.add(thisLine);
                mazeH ++;
            } else {
                for(int a = 0; a < thisLine.length(); a ++){
                    steps.add(thisLine.charAt(a));
                }
            }
        }
        int curStep = 0;
        int xd = 0;
        int yd = 0;
        while(curStep < steps.size()){
            char cs = steps.get(curStep);
            yd = switch (cs) {
                case '<' -> {
                    xd = -1;
                    yield 0;
                }
                case '>' -> {
                    xd = 1;
                    yield 0;
                }
                case '^' -> {
                    xd = 0;
                    yield -1;
                }
                case 'v' -> {
                    xd = 0;
                    yield 1;
                }
                default -> yd;
            };
            boolean open = testCoordinate(robotX, robotY, xd, yd);
            if(open){
                int bCount = 1;
                while(maze.get(robotY + yd * bCount).charAt(robotX + xd * bCount) != '.') bCount ++;
                for(int moving = bCount; moving >= 1; moving --){
                    String nLine = maze.get(robotY + yd * moving);
                    String nLine1 = nLine.substring(0, robotX + xd * moving);
                    String nLine2 = nLine.substring(robotX + 1 + xd * moving);
                    String ch = String.valueOf(maze.get(robotY + yd * (moving - 1)).charAt(robotX + xd * (moving - 1)));
                    maze.set(robotY + yd * moving, nLine1 + ch + nLine2);
                }
                maze.set(robotY, maze.get(robotY).substring(0, robotX) + "." + maze.get(robotY).substring(robotX + 1));
                robotX += xd;
                robotY += yd;
            }
               curStep ++;
        }
        for(int a = 0; a < maze.size(); a ++){
            for(int b = 0; b < maze.get(a).length(); b ++){
                if(maze.get(a).charAt(b) == 'O'){
                    answer += 100 * a + b;
                }
            }
        }

        System.out.println("The answer to part 1 is " + answer);
    }
    public static boolean testCoordinate(int x, int y, int xd, int yd){
        boolean open;
        char c = maze.get(y + yd).charAt(x + xd);
        open = switch (c) {
            case '#' -> false;
            case 'O' -> testCoordinate(x + xd, y + yd, xd, yd);
            default -> true;
        };
        return open;
    }
}
