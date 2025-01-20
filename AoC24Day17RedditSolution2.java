import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class AoC24Day17RedditSolution2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("c:/users/lance/documents/AoC24Day17input.txt"));
        long[] registers = new long[3];
        for (int i = 0; i < 3; i++) {
            registers[i] = Integer.parseInt(br.readLine().split(": ")[1]);
        }
        br.readLine();
        String[] pStrings = (br.readLine().split(": ")[1]).split(",");
        int[] program = new int[pStrings.length];
        for (int i = 0; i < pStrings.length; i++) {
            program[i] = Integer.parseInt(pStrings[i]);
        }

        List<Integer> out = simulate(registers, program);
        System.out.println("p1: " + String.join(",", out.stream().map(String::valueOf).toList()));

        // For Part 2, I disassembled and analyzed my input program. It turns out that the first output
        // depends only on the rightmost 10 bits of the initial value of register 'a'. So first, look for all
        // 10-bit numbers that output program[0].
        List<Long> possibilities = new ArrayList<>();
        for (long aCandidate = 0; aCandidate < 1024; aCandidate++) {
            registers[0] = aCandidate;
            registers[1] = 0;
            registers[2] = 0;
            out = simulate(registers, program);
            if (out.get(0) == program[0]) {
                possibilities.add(aCandidate);
            }
        }

        // Now, iteratively find longer possible numbers that produce the first offset+1 numbers of program.
        // We always have the possible numbers (that produce the first offset numbers of program) in
        // possibilities. The next possibilities are then dependent on the next 3 bits on the left. So try all
        // possible firstThree with all current possibilities, simulate and see which ones are now possible.
        for (int offset = 1; offset < program.length; offset++) {
            List<Long> newPoss = new ArrayList<>();
            for (long initial : possibilities) {
                for (long firstThree = 0; firstThree < 8; firstThree++) {
                    int leftShift = 10 + 3 * (offset - 1);
                    long aCandidate = initial + (firstThree << leftShift);

                    registers[0] = aCandidate;
                    registers[1] = 0;
                    registers[2] = 0;
                    out = simulate(registers, program);
                    boolean isPossible = isIsPossible(offset, program, out);
                    if (isPossible) newPoss.add(aCandidate);
                }
            }
            possibilities = newPoss;
        }

        Collections.sort(possibilities);
        System.out.println("p2: " + possibilities.get(0));
    }

    private static boolean isIsPossible(int offset, int[] program, List<Integer> out) {
        boolean isPossible;
        // for the last iteration only, check that the output is not longer than program; for the
        // previous iterations, longer output may be modified by later iterations, so they remain
        // possible.
        if (offset < program.length - 1) {
            isPossible = out.size() > offset && out.get(offset) == program[offset];
        } else {
            isPossible = out.size() == program.length && out.get(offset) == program[offset];
        }
        return isPossible;
    }

    private static List<Integer> simulate(long[] registers, int[] program) {
        List<Integer> out = new ArrayList<>();
        int pc = 0;
        while (pc < program.length) {
            int opcode = program[pc];
            int operand = program[pc + 1];
               switch (opcode) {
                case 1:
                    /* The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's literal operand, then stores the result in register B. */
                    registers[1] = registers[1] ^ operand;
                    break;
                case 2:
                    /* The bst instruction (opcode 2) calculates the value of its combo operand modulo 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register. */
                    registers[1] = combo(operand, registers) % 8;
                    break;
                case 3:
                    /* The jnz instruction (opcode 3) does nothing if the A register is 0. However, if the A register is not zero, it jumps by setting the instruction pointer to the value of its literal operand; if this instruction jumps, the instruction pointer is not increased by 2 after this instruction. */
                    if (registers[0] != 0) {
                        pc = operand;
                        continue;
                    }
                case 4:
                    /* The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.) */
                    registers[1] = registers[1] ^ registers[2];
                    break;
                case 5:
                    /* The out instruction (opcode 5) calculates the value of its combo operand modulo 8, then outputs that value. (If a program outputs multiple values, they are separated by commas.) */
                    out.add((int) (combo(operand, registers) % 8));
                    break;
                case 0:
                case 6:
                case 7:
                    int reg = opcode == 0 ? 0 : opcode - 5;
                    int denominator = 1 << combo(operand, registers);
                    registers[reg] = registers[0] / denominator;
                    break;
                default:
                    throw new IllegalStateException("unknown opcode: " + opcode);
            }
            pc += 2;
        }
        return out;
    }

    private static long combo(int operand, long[] registers) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4, 5, 6 -> registers[operand - 4];
            default -> throw new IllegalArgumentException("unknown " + operand);
        };
    }
}
