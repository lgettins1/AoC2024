import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.abs;

public class AoC24Day1 {
    public static void main(String[]args) throws IOException {
        String thisLine;
        int[] col1 = new int[1000];
        int[] col2 = new int[1000];
        int row  = 0;
        int answer = 0;
        int answer2 = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day1input.txt"));
        while ((thisLine = br.readLine()) != null) {
            String[] sp = thisLine.split(" {3}");
            col1[row] = Integer.parseInt(sp[0].trim());
            col2[row] = Integer.parseInt(sp[1].trim());
            row ++;
        }
        Arrays.sort(col1);
        Arrays.sort(col2);
       for(int x = 0; x < row; x ++){
           answer += (abs(col1[x] - col2[x]));
           int appearances = 0;
           for(int y = 0; y < row; y ++){
               if(col1[x] == col2[y]) appearances ++;
           }
           answer2 += col1[x] * appearances;
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
}

