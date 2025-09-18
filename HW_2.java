import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class HW_2 {

    private static int lineNum = 1;

    public static class Instruction {
        private String name;
        private int opcode;
        private String type;

        public Instruction(String name, int opcode, String type) {
            this.name = name;
            this.opcode = opcode;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public int getOpcode() {
            return opcode;
        }

        public String getType() {
            return type;
        }
        public int getOpcodeLength(){
            if(type == "R"){
                return 11;
            } else if(type == "I"){
                return 10;
            } else if(type=="D"){
                return 11;
            } else if(type=="B"){
                return 6;
            } else{
                return 8;
            }
        }
    }

    public static class Instructions {
        public static final Instruction[] INSTRUCTION_SET = {
                new Instruction("ADD", 0b10001011000, "R" ),
                new Instruction("ADDI", 0b1001000100, "I"),
                new Instruction("ADDIS", 0b1011000100, "I"),
                new Instruction("ADDS", 0b10101011000, "R"),
                new Instruction("AND", 0b10001010000, "R"),
                new Instruction("ANDI", 0b1001001000, "I"),
                new Instruction("ANDIS", 0b1111001000, "I"),
                new Instruction("ANDS", 0b1110101000, "R"),
                new Instruction("B", 0b000101, "B"),
                new Instruction("B.cond", 0b01010100, "CB"),
                new Instruction("BL", 0b100101, "B"),
                new Instruction("BR", 0b11010110000, "R"),
                new Instruction("CBNZ", 0b10110101, "CB"),
                new Instruction("CBZ", 0b10110100, "CB"),
                new Instruction("DUMP", 0b11111111110, "R"),
                new Instruction("EOR", 0b11001010000, "R"),
                new Instruction("EORI", 0b1101001000, "I"),
                new Instruction("FADDD", 0b00011110011, "R"),
                new Instruction("FADDS", 0b00011110001, "R"),
                new Instruction("FCMPD", 0b00011110011, "R"),
                new Instruction("FCMPS", 0b00011110001, "R"),
                new Instruction("FDIVD", 0b00011110011, "R"),
                new Instruction("FDIVS", 0b00011110001, "R"),
                new Instruction("FMULD", 0b00011110011, "R"),
                new Instruction("FMULS", 0b00011110001, "R"),
                new Instruction("FSUBD", 0b00011110011, "R"),
                new Instruction("FSUBS", 0b00011110001, "R"),
                new Instruction("HALT", 0b11111111111, "R"),
                new Instruction("LDUR", 0b11111000010, "D"),
                new Instruction("LDURB", 0b00111000010, "D"),
                new Instruction("LDURD", 0b11111100010, "R"),
                new Instruction("LDURH", 0b01111000010, "D"),
                new Instruction("LDURS", 0b10111100010, "R"),
                new Instruction("LDURSW", 0b10111000100, "D"),
                new Instruction("LSL", 0b11010011011, "R"),
                new Instruction("LSR", 0b11010011010, "R"),
                new Instruction("MUL", 0b10011011000, "R"),
                new Instruction("ORR", 0b10101010000, "R"),
                new Instruction("ORRI", 0b1011001000, "I"),
                new Instruction("PRNL", 0b11111111100, "R"),
                new Instruction("PRNT", 0b11111111101, "R"),
                new Instruction("SDIV", 0b10011010110, "R"),
                new Instruction("SMULH", 0b10011011010, "R"),
                new Instruction("STUR", 0b11111000000, "D"),
                new Instruction("STURB", 0b00111000000, "D"),
                new Instruction("STURD", 0b11111100000, "R"),
                new Instruction("STURH", 0b01111000000, "D"),
                new Instruction("STURS", 0b10111100000, "R"),
                new Instruction("STURSW", 0b10111000000, "D"),
                new Instruction("SUB", 0b11001011000, "R"),
                new Instruction("SUBI", 0b1101000100, "I"),
                new Instruction("SUBIS", 0b1111000100, "I"),
                new Instruction("SUBS", 0b11101011000, "R"),
                new Instruction("UDIV", 0b10011010110, "R"),
                new Instruction("UMULH", 0b10011011110, "R")
        };
    }

    public static void convertBinaryLineToAssembly(String chunk) {
        // Perform processing on the chunk here
    long binaryInstruction = Long.parseLong(chunk, 2);

    Instruction in = getInstructionType(binaryInstruction);

    if (in != null) {
        if (in.type.equals("R")) {
            convertRTypeInstruction(binaryInstruction, in);
        } else if (in.type.equals("I")) {
            convertITypeInstruction(binaryInstruction, in);
        } else if (in.type.equals("D")) {
            convertDTypeInstruction(binaryInstruction, in);
        } else if (in.type.equals("B")) {
            convertBTypeInstruction(binaryInstruction, in);
        } else if (in.type.equals("CB")) {
            convertCBTypeInstruction(binaryInstruction, in);
        } else {
            convertNullType(binaryInstruction, in);
        }
    } else {
        // Handle null instruction
        System.out.println("Unknown instruction: " + binaryInstruction);
    }
    }

    // For PRNL, PRNT, DUMP, or HALT
    private static void convertNullType(long binaryNumber, HW_2.Instruction in) {
        System.out.println("This is a " + in.type + " type instruction");
        System.out.println(in.getName());
    }

    // For CBZ and CBNZ
    private static void convertCBTypeInstruction(long binaryNumber, HW_2.Instruction in) {
        // Extract the address (bits 23-5) and the rt field (bits 4-0)
        int address = (int) ((binaryNumber >> 5) & 0xFFFFF); // Address is bits 23-5
        long mask = (1L << 19) - 1; // Create a mask with 19 bits set to 1 (bits 5 to 23)
        long extractedBits = (binaryNumber >> 5) & mask; // Shift right by 5 to get rid of lower bits, then use the mask to keep only bits 5 to 23
        // String binaryString = Long.toBinaryString(extractedBits);
        // System.out.println(binaryString);
        // String binaryString1 = Long.toBinaryString(binaryNumber);
        // System.out.println(binaryString1);
        int rt = (int) (binaryNumber & 0x1F); // Rt field is bits 4-0
        String cond = "";

        if(rt == 0){
        cond = "EQ";
        }else if(rt == 1){
        cond = "NE";
        }else if(rt == 2){
        cond = "HS";
        }else if(rt == 3){
        cond = "LO";
        }else if(rt == 4){
        cond = "MI";
        }else if(rt == 5){
        cond = "PL";
        }else if(rt == 6){
        cond = "VS";
        }else if(rt == 7){
        cond = "VC";
        }else if(rt == 8){
        cond = "HI";
        }else if(rt == 9){
        cond = "LS";
        }else if(rt == 10){
        cond = "GE";
        }else if(rt == 11){
        cond = "LT";
        }else if(rt == 12){
        cond = "GT";
        }else{
        cond = "LE";
        }
        int des = lineNum + rt-6;
        int des2 = rt + 2;

        if(in.getName() == "B.cond"){
            
            System.out.println("L" + lineNum + ": " + "B." + cond + " L" + des2);
            lineNum += 1;
        }else{
            System.out.println("L" + lineNum + ": " + in.getName() + " X" + rt + " L" + des);
            lineNum += 1;
        }
        
    }

    private static void convertBTypeInstruction(long binaryNumber, HW_2.Instruction in) {
        // Extract the opcode (bits 31-26) and address (bits 25-0)
        // int opcode = (int) ((binaryNumber >> 26) & 0b111111);
        int address = (int) (binaryNumber & 0x3FFFFFF);

        // Check if the address is negative (MSB is 1)
        if ((address & 0x2000000) != 0) {
            // Convert the address to a negative value using two's complement
            address = (int) (address | 0xFC000000);
        }

        int des = lineNum + address;

        System.out.println("L" + lineNum + ": " + in.getName() + " L" + des);
        lineNum += 1;

    }

    private static void convertDTypeInstruction(long binaryNumber, HW_2.Instruction in) {
        int DTAddr = (int) ((binaryNumber >> 12) & 0b111111111); // Extract bits 12-20 as Rn

        // Gets MSB
        int MSB = (DTAddr >> 8);
        DTAddr = DTAddr & 0b011111111;

        if (MSB != 0) {
            DTAddr = -(DTAddr & 0b011111111);
        }

        int op = (int) ((binaryNumber >> 10) & 0b11); // Extract bits 10-11 as Rn
        int Rn = (int) ((binaryNumber >> 5) & 0b11111); // Extract bits 5-9 as Rn
        int Rt = (int) (binaryNumber & 0b11111); // Extract bits 0-4 as Rd

        System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rt + ", [" + Rn + ", #" + DTAddr + "]");
        lineNum += 1;
    }

    // For all I-type instructions
    private static void convertITypeInstruction(long binaryNumber, HW_2.Instruction in) {
        int ALU_immediate = (int) ((binaryNumber >> 10) & 0b111111111111); // Extract bits 10-21 as ALU_Immediate

        // Gets MSB
        int MSB = (ALU_immediate >> 11);
        ALU_immediate = ALU_immediate & 0b011111111111;

        if (MSB != 0) {
            ALU_immediate = -(ALU_immediate & 0b011111111);
        }

        int Rn = (int) ((binaryNumber >> 5) & 0b11111); // Extract bits 5-9 as Rn
        int Rd = (int) (binaryNumber & 0b11111); // Extract bits 0-4 as Rd

        System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rd + ", X" + Rn + ", #" + ALU_immediate);
        lineNum += 1;
    }

    // For all R-type instructions
    private static void convertRTypeInstruction(long binaryNumber, HW_2.Instruction in) {
        int Rm = (int) ((binaryNumber >> 16) & 0b11111); // Extract bits 16-20 as Rm
        int shamt = (int) ((binaryNumber >> 10) & 0b111111); // Extract bits 10-15 as shamt
        int Rn = (int) ((binaryNumber >> 5) & 0b11111); // Extract bits 5-9 as Rn
        int Rd = (int) (binaryNumber & 0b11111); // Extract bits 0-4 as Rd

        if (in.getName() == "BR") {
            int dis = lineNum + Rn;
            System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rn);
            lineNum += 1;
        } else if (in.getName() == "LSL") {
            System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rd + ", X" + Rn + ", #" + shamt);
            lineNum += 1;
        } else if (in.getName() == "LSR") {
            System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rd + ", X" + Rn + ", #" + shamt);
            lineNum += 1;
        } else if (in.getName() == "PRNT") {
            System.out.println(in.getName());
            lineNum += 1;
        } else if (in.getName() == "PRNL") {
            System.out.println(in.getName());
            lineNum += 1;
        } else if (in.getName() == "DUMP") {
            System.out.println(in.getName());
            lineNum += 1;
        } else if (in.getName() == "HALT") {
            System.out.println(in.getName());
            lineNum += 1;
        } else {
            System.out.println("L" + lineNum + ": " + in.getName() + " X" + Rd + ", X" + Rn + ", X" + Rm);
            lineNum += 1;
        }

    }

    public static byte[] loadBinaryFile(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] binaryData = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(binaryData);
        }

        return binaryData;
    }

    // Gets the instruction type given a a long that contains a 32bit binary number
    // (Integer)
    public static Instruction getInstructionType(long binaryInstruction) {
        for (Instruction instruction : Instructions.INSTRUCTION_SET) {
            int opcodeLength = instruction.getOpcodeLength();  
            long mask = (1L << opcodeLength) - 1; // Create a mask with the same length as the opcode
            long opcodeValue = (binaryInstruction >> (32 - opcodeLength)) & mask; // Extract the first 11 bits as the
                                                                                  // opcode
            int extractedOpcode = (int) opcodeValue;
            if (extractedOpcode == instruction.getOpcode()) {
                if (instruction.getOpcode() == 241) {
                    int shamt = (int) ((binaryInstruction >> 10) & 0b111111); // Extract bits 10-15 as shamt

                    if (shamt == 2) {
                        return getInstructionByName("FMULS");
                    } else if (shamt == 6) {
                        return getInstructionByName("FDIVS");
                    } else if (shamt == 8) {
                        return getInstructionByName("FCMPS");
                    } else if (shamt == 10) {
                        return getInstructionByName("FADDS");
                    } else if (shamt == 14) {
                        return getInstructionByName("FSUBS");
                    } else {
                        return null;
                    }
                } else if (instruction.getOpcode() == 243) {
                    int shamt = (int) ((binaryInstruction >> 10) & 0b111111); // Extract bits 10-15 as shamt

                    if (shamt == 2) {
                        return getInstructionByName("FMULD");
                    } else if (shamt == 6) {
                        return getInstructionByName("FDIVD");
                    } else if (shamt == 8) {
                        return getInstructionByName("FCMPD");
                    } else if (shamt == 10) {
                        return getInstructionByName("FADDD");
                    } else if (shamt == 14) {
                        return getInstructionByName("FSUBD");
                    } else {
                        return null;
                    }
                } else if (instruction.getOpcode() == 1238) {
                    int shamt = (int) ((binaryInstruction >> 10) & 0b111111); // Extract bits 10-15 as shamt

                    if (shamt == 2) {
                        return getInstructionByName("SDIV");
                    } else if (shamt == 3) {
                        return getInstructionByName("UDIV");
                    } else {
                        return null;
                    }

                } else {
                    return instruction; // Return the matching instruction
                }
            }
        }
        return null; // Return null if no matching instruction is found
    }

    public static Instruction getInstructionByName(String name) {
        for (Instruction instruction : Instructions.INSTRUCTION_SET) {
            if (instruction.getName().equals(name)) {
                return instruction;
            }
        }
        return null; // Return null if the instruction with the given name is not found
    }

    public static void readFile(byte[] binaryData) {
        StringBuilder chunkBuilder = new StringBuilder();

        for (byte b : binaryData) {
            String binaryString = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            chunkBuilder.append(binaryString);

            if (chunkBuilder.length() == 32) {
                convertBinaryLineToAssembly(chunkBuilder.toString());
                chunkBuilder.setLength(0); // Clear the StringBuilder for the next chunk
            }
        }

        // Process any remaining bits
        if (chunkBuilder.length() > 0) {
            convertBinaryLineToAssembly(chunkBuilder.toString());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the input file name: ");
        String fileName = scanner.nextLine();
        scanner.close();

        // Load binary file
        try {
            byte[] binaryData = loadBinaryFile(fileName);
            // Read binary data
            readFile(binaryData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
