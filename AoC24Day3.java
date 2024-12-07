import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoC24Day3 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer = 0;
        int answer2 = 0;
        boolean mulEnabled = true;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day3input.txt"));
        while ((thisLine = br.readLine()) != null) {
            int lp = 0;
            while(lp < thisLine.length()){
                switch (thisLine.charAt(lp)){
                    case 'd':
                        if(thisLine.startsWith("do()", lp)){
                            mulEnabled = true;
                            lp += 2;
                            break;
                        }
                        if(thisLine.startsWith("don't()", lp)){
                            mulEnabled = false;
                            lp += 5;
                            break;
                        }
                    case 'm':
                        if(thisLine.startsWith("mul(", lp)) {
                            boolean valid = true;
                            int lp2 = lp + 4;
                            boolean foundComma = false;
                            StringBuilder firstNumber = new StringBuilder();
                            StringBuilder secondNumber = new StringBuilder();
                            while (thisLine.charAt(lp2) != ')') {
                                if (thisLine.charAt(lp2) > 47 && thisLine.charAt(lp2) < 58) {
                                    if (foundComma) {
                                        secondNumber.append(thisLine.charAt(lp2));
                                    } else {
                                        firstNumber.append(thisLine.charAt(lp2));
                                    }
                                } else {
                                    if (thisLine.charAt(lp2) == 44) {
                                        foundComma = true;
                                    } else {
                                        valid = false;
                                    }
                                }
                                lp2++;
                            }
                            if (valid) {
                               int a = Integer.parseInt(firstNumber.toString());
                               int b = Integer.parseInt(secondNumber.toString());
                               answer += a * b;
                               if(mulEnabled) answer2 += a * b;
                               lp += firstNumber.length() + secondNumber.length() + 4;
                            }
                        }

                }
               lp ++;
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);
    }
}
