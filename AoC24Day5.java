import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AoC24Day5 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer = 0;
        int answer2 = 0;
        int section = 1;
        ArrayList<int[]> rules = new ArrayList<>();
        int[] thisRule = new int[2];
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day5input.txt"));
        while ((thisLine = br.readLine()) != null) {
            if (thisLine.isEmpty()) {
                section++;
            } else {
                if (section == 1) {
                    String[] rr = thisLine.split("\\|");
                    thisRule[0] = Integer.parseInt(rr[0]);
                    thisRule[1] = Integer.parseInt(rr[1]);
                    rules.add(thisRule.clone());
                } else {
                    String[] rUpdate = thisLine.split(",");
                    int[] input = new int[rUpdate.length];
                    int midVal = 0;
                    for (int a = 0; a < rUpdate.length; a++) {
                        input[a] = Integer.parseInt(rUpdate[a]);
                        if (a == (rUpdate.length - 1) / 2) midVal = input[a];
                    }
                    int[] response = isCorrect(input, rules);
                    if (Arrays.equals(response, new int[]{0, 0})) {
                        answer += midVal;
                    } else {
                        while(!Arrays.equals(response, new int[]{0, 0})) {
                            for (int a = 0; a < input.length; a++) {
                                if (input[a] == response[0]) {
                                    input[a] = response[1];
                                } else {
                                    if (input[a] == response[1]) input[a] = response[0];
                                }
                            }
                            response = isCorrect(input,rules);
                        }
                        int midVal2 = input[(input.length - 1) / 2];
                        answer2 += midVal2;
                    }
                }
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);
    }

    private static int[] isCorrect(int[] input, ArrayList<int[]> rules) {
        int[] response = {0,0};
        for(int b = 0; b < input.length; b ++){
            for (int[] rule : rules) {
                if (rule[0] == input[b] && b > 0) {
                    for (int d = 0; d < b; d++) {
                        if (rule[1] == input[d]) {
                            response = rule;
                            break;
                        }
                    }
                }
                if (rule[1] == input[b] && b < input.length - 2) {
                    for (int d = b; d < input.length; d++) {
                        if (rule[0] == input[d]) {
                            response = rule;
                            break;
                        }
                    }
                }
            }
        }
        return response;
    }
}
