import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoC24Day4 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer = 0;
        int answer2 = 0;
        int dim = 140;
        int row = 0;
        char[] mas = {'M', 'A', 'S'};
        String[] wsLines = new String[dim];
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day4input.txt"));
        while ((thisLine = br.readLine()) != null) {
            wsLines[row] = thisLine;
            row ++;
        }
        for(int y = 0; y < dim; y ++){
            for(int x = 0; x < dim; x ++){
                if(wsLines[y].charAt(x) == 'X'){
                    for(int d = 0; d < 8; d ++){
                        int xd = 0;
                        int yd = 0;
                        if(!((d > 0 && d < 4 && y > dim - 4) || (d > 4 && y < 3) || (d > 2 && d < 6 && x < 3) ||
                                ((d < 2 || d > 6) && x > dim - 4 ))){
                            if(d > 0 && d < 4) yd = 1;
                            if(d > 4) yd = -1;
                            if(d > 2 && d < 6) xd = -1;
                            if(d < 2 || d > 6) xd = 1;
                            boolean xmasTrue = true;
                            for(int sc = 1; sc < 4; sc ++){
                                if (wsLines[y + (yd * sc)].charAt(x + (xd * sc)) != mas[sc - 1]) {
                                    xmasTrue = false;
                                    break;
                                }
                            }
                            if(xmasTrue) {
                                answer ++;
                            }
                        }
                    }
                }
            }
        }
        for(int y = 1; y < dim - 1; y ++){
            for(int x = 1; x < dim - 1; x ++){
                if(wsLines[y].charAt(x) == 'A') {
                    StringBuilder ms = new StringBuilder();
                    for(int d = 1; d < 8; d += 2){
                        int xd = (2 - d % 4) * (1 - 2 * (d / 4));
                        int yd = (1 - 2 * (d / 4));
                        char c = wsLines[y + yd].charAt(x + xd);
                        ms.append(c);
                    }
                    String sm = ms.toString();
                    if(sm.equals("SMMS") || sm.equals("SSMM") || sm.equals("MSSM") || sm.equals("MMSS")) {
                        answer2 ++;
                     }
                }
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
}
