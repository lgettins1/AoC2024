import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AoC24Day17 {
    public static void main(String[] args) throws IOException {

        String thisLine;
        int section = 0;
        int reg = 0;
        int[] registers = new int [3];
        ArrayList<Integer> program = new ArrayList<>();
        ArrayList<Integer> output;
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day17input.txt"));
        while ((thisLine = br.readLine()) != null) {
            if(thisLine.isEmpty()) {
                section ++;
            } else {
                String[] tmp = thisLine.split(":");
                if (section == 0) {
                    registers[reg] = Integer.parseInt(tmp[1].trim());
                    reg++;
                } else {
                    String [] tmp2 = tmp[1].split(",");
                    for(String t: tmp2){
                        program.add(Integer.parseInt(t.trim()));
                    }
                }
            }
        }
        output = runScenario(program, registers);
        System.out.println("The answer to part 1 is " + output);
        boolean success = false;
        int registerA = -1;
        while(!success){
            registerA ++;
            if(registerA % 10000 == 0)System.out.println(registerA);
            registers[0] = registerA;
            output = runScenario(program, registers);
            success = output.equals(program);
        }
        System.out.println("The answer to part 2 is " + registerA);
    }

    public static ArrayList<Integer> runScenario(ArrayList<Integer> program, int[] registers){
        ArrayList<Integer> output = new ArrayList<>();
        int pointer = 0;
        while(pointer < program.size()){
            int instruction = program.get(pointer);
            int lOperand = program.get(pointer + 1);
            int cOperand =lOperand;
            if(cOperand > 3 && cOperand < 7) cOperand = registers[cOperand  - 4];
            pointer += 2;
            switch(instruction){
                case 0:
                    registers[0] = registers[0] / ((int) Math.pow(2 , cOperand));
                    break;
                case 1:
                    registers[1] = registers[1] ^ lOperand;
                    break;
                case 2:
                    registers[1] = cOperand % 8;
                    break;
                case 3:
                    if(registers[0] > 0){
                        pointer = lOperand;
                    }
                    break;
                case 4:
                    registers[1] = registers[1] ^ registers[2];
                    break;
                case 5:
                    int out = cOperand % 8;
                    output.add(out);
                    break;
                case 6:
                    registers[1] = registers[0] / ((int) Math.pow(2 , cOperand));
                    break;
                case 7:
                    registers[2] = registers[0] / ((int) Math.pow(2 , cOperand));
                    break;
            }
        }
        return output;
    }
}
