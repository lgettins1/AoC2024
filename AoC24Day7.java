import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class AoC24Day7 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        BigDecimal answer = new BigDecimal("0");
        BigDecimal answer2 = new BigDecimal("0");
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day7input.txt"));
        while ((thisLine = br.readLine()) != null) {
            String[] cSplit = thisLine.split(": ");
            double testValue = Double.parseDouble(cSplit[0]);
            String[] oSplit = cSplit[1].split(" ");
            int[] vals = new int[oSplit.length];
            for (int op = 0; op < oSplit.length; op++) {
                vals[op] = Integer.parseInt(oSplit[op]);
            }
            boolean solved = false;
            boolean solved2 = false;
            for (int a = 0; a < Math.pow(2, (vals.length - 1)); a++) {
                double thisTotal = getThisTotal(a, vals);
                if (thisTotal == testValue && !solved) {
                    solved = true;
                    answer = answer.add(BigDecimal.valueOf(thisTotal));
                }
            }

            for (int a = 0; a < Math.pow(3, (vals.length - 1)); a++) {
                BigDecimal thisTotal = getThisTotal2(a, vals);
                if (thisTotal.compareTo(BigDecimal.valueOf(testValue)) == 0 && !solved2) {
                    solved2 = true;
                    answer2 = answer2.add(thisTotal);
                }
            }
        }

        System.out.println("The answer to part 1 is " +  answer);
        System.out.println("The answer to part 2 is " + answer2);

    }

    private static double getThisTotal(int a, int[] vals) {
        StringBuilder bin = new StringBuilder(Integer.toBinaryString(a));
        while(bin.length() < vals.length - 1){
            bin.insert(0, "0");
        }
        double thisTotal = vals[0];
        for(int b = 1; b < vals.length; b ++){
            if(bin.charAt(b - 1) == '0'){
                thisTotal += vals[b];
            }  else {
                thisTotal = thisTotal * vals[b];
            }
        }
        return thisTotal;
    }
    private static BigDecimal getThisTotal2(int a, int[] vals){
        StringBuilder base3 = new StringBuilder(asBase3(a));
        while(base3.length() < vals.length - 1){
            base3.insert(0, "0");
        }

        BigDecimal thisTotal = BigDecimal.valueOf(vals[0]);
        for(int b = 1; b < vals.length; b ++){
            switch(base3.charAt(b - 1)){
                case '0':
                    thisTotal = thisTotal.add(BigDecimal.valueOf(vals[b]));
                    break;
                case '1':
                    thisTotal = thisTotal.multiply(BigDecimal.valueOf(vals[b]));
                    break;
                case '2':
                    String[] totalInt = thisTotal.toString().split("\\.");
                    String concat = totalInt[0] + vals[b];
                    thisTotal = BigDecimal.valueOf(Long.parseLong(concat));
            }
        }
        return thisTotal;
    }
    public static String asBase3(int num) {
        long ret = 0;
        long factor = 1;
        while(num > 0){
            ret += num % 3 * factor;
            num /= 3;
            factor *= 10;
        }
        return Long.toString(ret);
    }
}