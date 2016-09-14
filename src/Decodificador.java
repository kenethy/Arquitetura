import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Decodificador {

	private static PrintWriter out;
	private static BufferedReader reader;

	// Complemento de 0 a frente do array binário para a quantidade de 32 bits
	public static String complemento32bits(String bin) {
		int tam = bin.length();

		while (tam < 32) {
			bin = "0" + bin;
			tam++;
		}
		return bin;
	}

	public static void main(String[] args) throws IOException {
		//FileReader in = new FileReader("in.txt");
		FileReader in = new FileReader("CODE.txt");
		reader = new BufferedReader(in);
		FileWriter writer = new FileWriter("out.txt");
		out = new PrintWriter(new PrintWriter(writer));
		
		String linha = reader.readLine();

		do {
			linha = new BigInteger(linha, 16).toString(2);
			linha = complemento32bits(linha);
			
			String opcode = linha.substring(0, 6);
			String rs = linha.substring(6, 11);
			String rt = linha.substring(11, 16);
			String rd = linha.substring(16, 21);
			String shamt = linha.substring(21, 26);
			String funct = linha.substring(26, 32);
			String immediate = linha.substring(16, 32); // o complemento a 2 é feito com o cast (short) na
														// conversao de imediato para int nos cases abaixo
			String address = linha.substring(6, 32);
			String branchAddr = ""; // BranchAddr = { 14{immediate[15]}, immediate, 2’b0 }

			switch (opcode) {
			// OPCODE = 0
			case "000000":
				switch (funct) {
				case "100000": // add
					out.print("add" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100010": // sub
					out.print("sub" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "101010": // slt
					out.print("slt" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100100": // and
					out.print("and" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100101": // or
					out.print("or" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100110": // xor
					out.print("xor" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100111": // nor
					out.print("nor" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "001000": // jr
					out.print("jr" + " $" + Integer.parseInt(rs, 2));
					break;
				case "010000": // mfhi
					out.print("mfhi" + " $" + Integer.parseInt(rd, 2));
					break;
				case "010010": // mflo
					out.print("mflo" + " $" + Integer.parseInt(rd, 2));
					break;
				case "100001": // addu
					out.print("addu" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "100011": // subu
					out.print("subu" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					break;
				case "011000": // mult
					out.print("mult" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					break;
				case "011001": // multu
					out.print("multu" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					break;
				case "011010": // div
					out.print("div" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					break;
				case "011011": // divu
					out.print("divu" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					break;
				case "000000": // sll
					out.print("sll" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					break;
				case "000010": // srl
					out.print("srl" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					break;
				case "000011": // sra
					out.print("sra" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					break;
				case "000100": // sllv
					out.print("sllv" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					break;
				case "000110": // srlv
					out.print("srlv" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					break;
				case "000111": // srav
					out.print("srav" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					break;
				case "001100": // syscall
					out.print("syscall");
					break;
				}
				break;
			case "001111": // lui
				out.print("lui" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2));
				break;
			case "001000": // addi
				out.print("addi" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "001010": // slti
				out.print("slti" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "001100": // andi
				out.print("andi" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "001101": // ori
				out.print("ori" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "001110": // xori
				out.print("xori" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "100011": // lw
				out.print("lw" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				break;
			case "101011": // sw
				out.print("sw" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				break;
			case "000010": // j
				out.print("j " + Integer.parseInt(address, 2));
				break;
			case "000001": // bltz
				out.print("bltz" + " $" + Integer.parseInt(rs, 2) + ", " + (short)Integer.parseInt(address, 2));
				break;
			case "000100": // beq
				out.print("beq" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
						+ (short)Integer.parseInt(address, 2));
				break;
			case "000101": // bne
				out.print("bne" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
						+ (short)Integer.parseInt(address, 2));
				break;
			case "001001": // addiu
				out.print("addiu" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				break;
			case "100000": // lb
				out.print("lb" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				break;
			case "100100": // lbu
				out.print("lbu" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				break;
			case "101000": // sb
				out.print("sb" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				break;
			case "000011": // jal
				out.print("jal " + Integer.parseInt(address, 2));
				break;
			}
			out.println();
			
			linha = reader.readLine();
		} 
		while(linha != null);

		out.close();
	}
}
