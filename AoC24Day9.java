import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AoC24Day9 {
    public static void main(String[] args) throws IOException {
        BigDecimal answer = new BigDecimal("0");
        BigDecimal answer2 = new BigDecimal("0");
        ArrayList <int[]> cBlocks = new ArrayList<>();
        ArrayList <Integer> gaps = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day9input.txt"));
        String thisLine = br.readLine();
        int iLength = thisLine.length();
        int dLength = 0;
        for (int a = 0; a < iLength; a++) {
            dLength += Integer.parseInt(thisLine.substring(a, a + 1));
        }
        String[] blocks = new String[dLength];
        String[] blocks2 = new String[dLength];
        int bPos = 0;
        for (int a = 0; a < iLength; a += 2) {
            int i = Integer.parseInt(thisLine.substring(a, a + 1));
            int[] thisBlock = {a / 2, i};
            cBlocks.add(thisBlock);
            for (int b = 0; b < i; b ++) {
                blocks[bPos + b] = Integer.toString(a / 2);
            }
            bPos += i;
            if (a < iLength - 2) {
                int i1 = Integer.parseInt(thisLine.substring(a + 1, a + 2));
                gaps.add(i1);
                for (int b = 0; b < i1; b ++) {
                    blocks[bPos + b] = ".";
                }
                bPos += i1;
            }
        }
        int feb = 0;
        int lfb = blocks.length - 1;
        while (feb < lfb) {
            while (!blocks[feb].equals(".")) {
                feb++;
            }
            while (blocks[lfb].equals(".")) {
                lfb--;
            }
            if (lfb > feb) {
                blocks[feb] = blocks[lfb];
                blocks[lfb] = ".";
            }
        }
        for (int a = 0; a < blocks.length; a++) {
            if (!blocks[a].equals(".")) {
                answer = answer.add(BigDecimal.valueOf((long) a * Integer.parseInt(blocks[a])));
            }
        }
        //part 2
        int sfb = cBlocks.size() - 1;
        int lp = cBlocks.size();
        while(lp > 0){
            boolean moved = false;
            if(cBlocks.get(sfb)[0] == lp - 1) {
                lp --;
                for (int sg = 0; sg < sfb; sg++) {
                    if (gaps.get(sg) >= cBlocks.get(sfb)[1]  && !moved) {
                        moved = true;
                        int ng = gaps.get(sg) - cBlocks.get(sfb)[1];
                        gaps.set(sg, 0);
                        gaps.add(sg + 1, ng);
                        if (sfb < gaps.size() - 1) {
                            gaps.set(sfb, gaps.get(sfb) + cBlocks.get(sfb)[1] + gaps.get(sfb + 1));
                            gaps.remove(sfb + 1);
                        } else {
                            gaps.set(sfb, gaps.get(sfb) + cBlocks.get(sfb)[1]);
                        }
                        cBlocks.add(sg + 1, cBlocks.get(sfb));
                        cBlocks.remove(sfb + 1);
                    }
                }
            }
            if(!moved) sfb --;
        }
        bPos = 0;
        for(int a = 0; a < cBlocks.size(); a ++){
             for(int addBlock = 0; addBlock < cBlocks.get(a)[1]; addBlock ++ ){
                blocks2[bPos] = Integer.toString(cBlocks.get(a)[0]);
                bPos ++;
            }
            if(a < gaps.size() && gaps.get(a) > 0){
                for(int b = 0; b < gaps.get(a); b ++){
                    blocks2[bPos] = ".";
                    bPos ++;
                }
            }
        }
        for (int a = 0; a < blocks2.length; a++) {
            if (!blocks2[a].equals(".")) {
                answer2 = answer2.add(BigDecimal.valueOf((long) a * Integer.parseInt(blocks2[a])));
            }
        }
        System.out.println("The answer to part 1 is "+answer);
        System.out.println("The answer to part 2 is "+answer2);
    }
}
