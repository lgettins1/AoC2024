import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class AoC24Day13 {
    public static void main(String[] args) throws IOException {
        BigInteger answer = BigInteger.valueOf(0);
        BigInteger answer2 = BigInteger.valueOf(0);
        ArrayList<ArrayList<BigInteger>> prizes = getArrayLists();
        BigInteger part2 = new BigInteger("10000000000000");
        for (ArrayList<BigInteger> tp : prizes) {
            BigInteger tp4 = tp.get(4).add(part2);
            BigInteger tp5 = tp.get(5).add(part2);
            BigInteger a = (tp.get(4).multiply(tp.get(3))).subtract(tp.get(5).multiply(tp.get(2)));
            BigInteger a2 = (tp4.multiply(tp.get(3))).subtract(tp5.multiply(tp.get(2)));
            BigInteger b = (tp.get(0).multiply(tp.get(3))).subtract(tp.get(1).multiply(tp.get(2)));
            BigInteger c = (tp.get(4).multiply(tp.get(1))).subtract(tp.get(5).multiply(tp.get(0)));
            BigInteger c2 = (tp4.multiply(tp.get(1))).subtract(tp5.multiply(tp.get(0)));
            BigInteger d = (tp.get(1).multiply(tp.get(2))).subtract(tp.get(0).multiply(tp.get(3)));
            BigDecimal aa = new BigDecimal(a).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
            BigDecimal aa2 = new BigDecimal(a2).divide(new BigDecimal(b), 2, RoundingMode.HALF_UP);
            BigDecimal bb = new BigDecimal(c).divide(new BigDecimal(d), 2, RoundingMode.HALF_UP);
            BigDecimal bb2 = new BigDecimal(c2).divide(new BigDecimal(d), 2, RoundingMode.HALF_UP);

            if (isIntegerValue(aa) && isIntegerValue(bb)) {
                BigInteger aaa = aa.toBigInteger();
                BigInteger bbb = bb.toBigInteger();
                answer = answer.add((aaa.multiply(BigInteger.valueOf(3))).add(bbb));
            }
            if (isIntegerValue(aa2) && isIntegerValue(bb2)) { 
                BigInteger aaa2 = aa2.toBigInteger();
                BigInteger bbb2 = bb2.toBigInteger();
                answer2 = answer2.add((aaa2.multiply(BigInteger.valueOf(3))).add(bbb2));
            }
        }
        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }

    private static ArrayList<ArrayList<BigInteger>> getArrayLists() throws IOException {
        String thisLine;
        int row = 0;
        ArrayList<ArrayList<BigInteger>> prizes = new ArrayList<>();
        ArrayList<BigInteger> record = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day13input.txt"));
        while ((thisLine = br.readLine()) != null) {
            if (row % 4 < 3) {
                String[] a = thisLine.split("[=+]");
                String[] b = a[1].split(",");
                BigInteger x = BigInteger.valueOf(Long.parseLong(b[0]));
                BigInteger y = BigInteger.valueOf(Long.parseLong(a[2]));
                record.add(x);
                record.add(y);
            } else {
                ArrayList<BigInteger> cl = new ArrayList<>(record);
                prizes.add(cl);
                record.clear();
            }
            row++;
        }
        ArrayList<BigInteger> cl = new ArrayList<>(record);
        prizes.add(cl);
        return prizes;
    }

    private static boolean isIntegerValue(BigDecimal bd) {
        return bd.stripTrailingZeros().scale() <= 0;
    }
}

