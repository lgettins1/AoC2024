import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AoC24Day2 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        int answer = 0;
        int answer2 = 0;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day2input.txt"));
        while ((thisLine = br.readLine()) != null) {
            String [] lineList = thisLine.split(" ");
            int[] numList = new int[lineList.length];
            for(int i = 0; i < lineList.length; i ++){
                numList[i] = Integer.parseInt(lineList[i]);
            }
            boolean passes = checkSafe(numList);
            if(passes) {
                answer ++;
                answer2 ++;
            } else {
                boolean passesDamper = false;
                for (int badLevel = 0; badLevel < numList.length; badLevel ++){
                    if (!passesDamper){
                        int[] newList = new int [numList.length - 1];
                        int position = 0;
                        for (int x = 0; x < numList.length; x ++){
                            if(x != badLevel){
                                newList[position] = numList[x];
                                position ++;
                            }
                        }
                        boolean checkNewList = checkSafe(newList);
                        if(checkNewList) passesDamper = true;
                    }
                }
                if(passesDamper) answer2 ++;
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
    public static boolean checkSafe(int []numList){
        boolean passes = true;
        int chk1 = numList[numList.length - 1] - numList [0];
        int sumOfALs = 0;
        for(int i = 0; i < numList.length - 1; i ++){
            int p = Math.abs(numList[i + 1] - numList [i]);
            if(p < 1 || p > 3){
                passes = false;
                break;
            }
            sumOfALs += p;
        }
        if(sumOfALs != Math.abs(chk1)) passes = false;
        return passes;
    }
}
