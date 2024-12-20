import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AoC24Day10 {
    public static ArrayList<String> map = new ArrayList<>();
    public static Set<String> nines = new HashSet<>();
    public static Set<String> routes = new HashSet<>();
    public static int mapSize = 0;

    public static void main(String[] args) throws IOException {
        int answer;
        int answer2;
        String thisLine;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day10input.txt"));
        while ((thisLine = br.readLine()) != null) {
            map.add(thisLine);
        }
        mapSize = map.size();
        for(int y = 0; y < mapSize; y ++){
            for(int x = 0; x < mapSize; x ++){
                if(map.get(y).charAt(x) == '0'){
                    String s = x + "," + y + "-";
                    explore(0, s, x, y, x, y);
                }
            }
        }
        answer = nines.size();
        answer2 = routes.size();
 //       System.out.println(routes);
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
    public static void explore(int val, String s, int x0, int y0, int x, int y){

        if(val == 9){
            String answer = "(" + x0 + "," + y0 + "-" + x + "," + y + ")";
            nines.add(answer);
            routes.add(s);

        } else {
            for (int scan = 1; scan < 5; scan++) {
                int xs = x + (scan % 2) * ( 2 - scan);
                int ys = y + ((scan + 1) % 2) * (3 - scan);
                 if (xs >= 0 && xs < mapSize && ys >= 0 && ys < mapSize) {
                    if (map.get(ys).charAt(xs) == (val + 49)) {
                        s += xs + "," + ys + "-";
                        explore(val + 1, s, x0, y0, xs, ys);
                    }
                }
            }
        }
    }
}
