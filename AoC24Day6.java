import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day6 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer2 = 0;
        int cr = 1;
        int guardX = 0;
        int guardY = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day6input.txt"));
        thisLine = br.readLine();
        String[] maze = new String [thisLine.length()];
        maze[0] = thisLine;
        while ((thisLine = br.readLine()) != null) {
            maze[cr] = thisLine;
            if(thisLine.contains("^")){
                guardY = cr;
                for(int a = 0; a < thisLine.length(); a ++){
                    if(thisLine.charAt(a) == '^') guardX = a;
                }
            }
            cr ++;
        }
        ArrayList<int[]> visited;
        ArrayList<int[]> visited2;

        String[] mazeCopy = new String[maze.length];
        visited = exitMap(guardX, guardY, maze);
        System.out.println("The answer to part 1 is " + visited.size());
        for(int a = 1; a < visited.size(); a ++){
            System.arraycopy(maze, 0, mazeCopy, 0, maze.length);
            String mazeLine = mazeCopy[visited.get(a)[1]].substring(0, visited.get(a)[0]) + "#" + mazeCopy[visited.get(a)[1]].substring(visited.get(a)[0] + 1);
            mazeCopy[visited.get(a)[1]] = mazeLine;
            visited2 = exitMap(guardX, guardY, mazeCopy);
            if(visited2.isEmpty()) answer2 ++;
        }

        System.out.println("The answer to part 2 is " + answer2);

    }
    public static ArrayList<int[]> exitMap (int guardX, int guardY, String[] maze){
        int dim = maze[0].length();
        int xd = 0;
        int yd = -1;
        int dir = 0;
        ArrayList<int[]> visited = new ArrayList<>();
        visited.add(new int[] {guardX, guardY, dir});

        while(guardX > 0 && guardY > 0 && guardX < dim - 1 && guardY < dim - 1){
            char a = maze[guardY + yd].charAt(guardX + xd);
            if(a == '.' || a == '^'){
                guardX += xd;
                guardY += yd;
                int[] b = {guardX, guardY, dir};
                boolean unique = true;
                boolean loop = false;
                for (int[] ints : visited) {
                    if (ints[0] == guardX && ints[1] == guardY) {
                        unique = false;
                        if (ints[2] == dir) loop = true;
                    }
                }
                if(loop) {
                    visited.clear();
                    return visited;
                }
                if(unique) {
                    visited.add(b.clone());
                }
            } else {
                dir = (dir + 1) % 4;
                xd = (2 - dir) % 2;
                yd = (dir - 1) % 2;
            }
        }
        return visited;
    }
}
