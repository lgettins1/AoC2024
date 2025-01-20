import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day15part2 {
    public static ArrayList<String> maze = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        int answer2 = 0;
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
                    robotX = 2 * thisLine.indexOf("@");
                }
                StringBuilder thisLine2 = new StringBuilder();
                for(int a = 0; a < thisLine.length(); a ++){
                    switch(thisLine.charAt(a)){
                        case '#':
                            thisLine2.append("##");
                            break;
                        case 'O':
                            thisLine2.append("[]");
                            break;
                        default:
                            thisLine2.append(thisLine.charAt(a)).append(".");
                    }
                }
                maze.add(thisLine2.toString());
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
                if(yd == 0) {
                    int bCount = 1;
                    while (maze.get(robotY).charAt(robotX + xd * bCount) != '.') bCount++;
                    for (int moving = bCount; moving >= 1; moving--) {
                        String nLine = maze.get(robotY);
                        String nLine1 = nLine.substring(0, robotX + xd * moving);
                        String nLine2 = nLine.substring(robotX + 1 + xd * moving);
                        String ch = String.valueOf(maze.get(robotY).charAt(robotX + xd * (moving - 1)));
                        maze.set(robotY, nLine1 + ch + nLine2);
                    }
                    maze.set(robotY, maze.get(robotY).substring(0, robotX) + "." + maze.get(robotY).substring(robotX + 1));
                } else {
                    verticalMove(robotX, robotY, yd);
                }
                robotX += xd;
                robotY += yd;
            }
            curStep ++;
        }
        for(int a = 0; a < maze.size(); a ++){
            for(int b = 0; b < maze.get(a).length(); b ++){
                if(maze.get(a).charAt(b) == '['){
                    answer2 += 100 * a + b;
                }
            }
        }

        System.out.println("The answer to part 2 is " + answer2);
    }
    public static boolean testCoordinate(int x, int y, int xd, int yd){
        boolean open = false;
        char c = maze.get(y + yd).charAt(x + xd);
        switch (c) {
            case '#' -> {
            }
            case '.' -> open = true;
            default -> {
                if (yd == 0) {
                    open = testCoordinate(x + xd, y + yd, xd, yd);
                } else {
                    boolean open1 =testCoordinate(x + xd, y + yd, xd, yd);
                    boolean open2;
                    if(c == '['){
                        open2 = testCoordinate(x + xd + 1, y + yd, xd, yd);
                    } else {
                        open2 = testCoordinate(x + xd - 1, y + yd, xd, yd);
                    }
                    if(open1 && open2) open= true;
                }
            }
        }
        return open;
    }

    public static void verticalMove(int robotX, int robotY, int yd){
        while(maze.get(robotY + yd).charAt(robotX) != '.'){
            if(maze.get(robotY + yd).charAt(robotX) == ']'){
                verticalMove(robotX - 1, robotY + yd, yd);
            } else {
                verticalMove(robotX + 1, robotY + yd, yd);
            }
            verticalMove(robotX, robotY + yd, yd);

        }
        String l = maze.get(robotY + yd).substring(0, robotX);
        String l2 = maze.get(robotY + yd).substring(robotX + 1);
        String l3 = l + maze.get(robotY).charAt(robotX) + l2;
        maze.set(robotY + yd, l3);
        l =maze.get(robotY).substring(0, robotX);
        l2 = maze.get(robotY).substring(robotX + 1);
        l3 = l + "." + l2;
        maze.set(robotY, l3);
    }
}
