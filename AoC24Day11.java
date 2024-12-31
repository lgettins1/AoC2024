import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;

public class AoC24Day11 {
    public static void main(String[] args) throws IOException {
        String thisLine;
        BigInteger answer = new BigInteger("0");
        BigInteger answer2 = new BigInteger("0");
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day11input.txt"));
        thisLine = br.readLine();
        String[]s = thisLine.split(" ");
        ArrayList<BigInteger[]>stones = new ArrayList<>();
        for (String string : s) {
            BigInteger[] b = {BigInteger.valueOf(Long.parseLong(string)), BigInteger.valueOf(1)};
            stones.add(b);
        }
        for(int blink = 0; blink < 75; blink ++){
            for(int ss = 0; ss < stones.size(); ss ++){
                BigInteger thisStone = stones.get(ss)[0];
                String thisStoneString = thisStone.toString();
                BigInteger nn = stones.get(ss)[1];
                if(Objects.equals(thisStone, BigInteger.valueOf(0))){
                    BigInteger[] b = {BigInteger.valueOf(1), nn};
                    stones.set(ss, b);
                } else {

                    if(thisStoneString.length() % 2 == 0){
                        String s1 = thisStoneString.substring(0, thisStoneString.length() / 2);
                        String s2 = thisStoneString.substring(thisStoneString.length() / 2);
                        BigInteger s1bi = BigInteger.valueOf(Long.parseLong(s1));
                        BigInteger s2bi = BigInteger.valueOf(Long.parseLong(s2));
                        BigInteger[] s1ss = {s1bi, nn};
                        BigInteger[] s2ss = {s2bi, nn};
                        stones.set(ss, s1ss);
                        ss ++;
                        stones.add(ss, s2ss);
                    } else {

                        thisStone = thisStone.multiply(BigInteger.valueOf(2024));
                        BigInteger[] b = {thisStone, nn};
                        stones.set(ss, b);
                    }
                }
            }
            for(int a = 0 ; a < stones.size() - 1; a ++){
                BigInteger thisStone = stones.get(a)[0];
                BigInteger nn = stones.get(a)[1];
                for(int b = a + 1; b < stones.size(); b ++){
                    if(Objects.equals(stones.get(b)[0], thisStone)){
                        nn = nn.add(stones.get(b)[1]);
                        BigInteger[] bi = {thisStone, nn};
                        stones.set(a, bi);
                        stones.remove(b);
                        b--;
                    }
                }
            }
            if(blink == 24){
                for (BigInteger[] stone : stones) {
                    answer = answer.add(stone[1]);
                }
            }
        }
        for (BigInteger[] stone : stones) {
            answer2 = answer2.add(stone[1]);
        }

        System.out.println("The answer to part 1 is " + answer);
        System.out.println("The answer to part 2 is " + answer2);

    }
}
