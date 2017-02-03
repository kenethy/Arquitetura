import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;

public class Decodificador {

	private static PrintWriter out;
	private static BufferedReader reader;

	// COMPLEMENTO DE ZEROS A FRENTE DO ARRAY BINÃ�RIO PARA QUANTIDADE DE 32 BITS
	public static String complemento32bits(String bin) {
		int tam = bin.length();
		while (tam < 32) {
			bin = "0" + bin;
			tam++;
		}
		return bin;
	}

	public static void main(String[] args) throws NumberFormatException, Exception {
		
		FileReader in = new FileReader("entrada.txt");
		reader = new BufferedReader(in);
		FileWriter writer = new FileWriter("out.txt");
		out = new PrintWriter(new PrintWriter(writer));
		Executor execute = new Executor();
		String linha = reader.readLine();
		boolean jumper = false;
		ArrayList<String> instrucao = new ArrayList<String>();
		
		do {
			linha = new BigInteger(linha, 16).toString(2);
			linha = complemento32bits(linha);
			instrucao.add(linha);
			linha = reader.readLine();			
		} while (linha != null);

		do {
//			linha = new BigInteger(linha, 16).toString(2);
//			linha = complemento32bits(linha);

			String opcode = instrucao.get(execute.reg.getPC()/4).substring(0, 6);
			String rs = instrucao.get(execute.reg.getPC()/4).substring(6, 11);
			String rt = instrucao.get(execute.reg.getPC()/4).substring(11, 16);
			String rd = instrucao.get(execute.reg.getPC()/4).substring(16, 21);
			String shamt = instrucao.get(execute.reg.getPC()/4).substring(21, 26);
			String funct = instrucao.get(execute.reg.getPC()/4).substring(26, 32);
			String immediate = instrucao.get(execute.reg.getPC()/4).substring(16, 32); // COMPLEMENTO A 2 É FEITO COM CAST (SHORT) NA CONVERSÃO DO IMEDIATO
			String address = instrucao.get(execute.reg.getPC()/4).substring(6, 32);

			// ZeroExtImm = { 16{1b'0}, immediate }
			int ZeroExtImm = Integer.parseInt(immediate, 2);
			// SignExtImm = { 16{immediate[15]}, immediate }
			int SignExtImm = ((ZeroExtImm & 0x8000) == 0 ? ZeroExtImm : ZeroExtImm - 0x10000);
			// BranchAddr = { 14{immediate[15]}, immediate, 2'b0 }
			int branch = ((ZeroExtImm & 0x8000) == 0 ? ZeroExtImm : ZeroExtImm - 0x10000);
			int BranchAddr = (branch << 2);
			// JumpAddr = { PC+4[31:28], address, 2'b0 }
			int jump = Integer.parseUnsignedInt(instrucao.get(execute.reg.getPC()/4), 2) & 0x02FFFFFF;
			int JumpAddr = (execute.reg.getPC() & 0xF0000000) | (jump << 2);

			switch (opcode) {
			// OPCODE = 0
			case "000000":
				switch (funct) {
				case "100000": // add
					out.print("add" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.add(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100010": // sub
					out.print("sub" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.sub(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "101010": // slt
					out.print("slt" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.slt(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100100": // and
					out.print("and" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.and(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100101": // or
					out.print("or" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.or(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100110": // xor
					out.print("xor" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.xor(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100111": // nor
					out.print("nor" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.nor(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "001000": // jr
					out.print("jr" + " $" + Integer.parseInt(rs, 2));
					execute.jr(Integer.parseInt(rs, 2));
					jumper = true;
					break;
				case "010000": // mfhi
					out.print("mfhi" + " $" + Integer.parseInt(rd, 2));
					execute.mfhi(Integer.parseInt(rd, 2));
					jumper = false;
					break;
				case "010010": // mflo
					out.print("mflo" + " $" + Integer.parseInt(rd, 2));
					execute.mflo(Integer.parseInt(rd, 2));
					jumper = false;
					break;
				case "100001": // addu
					out.print("addu" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.addu(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "100011": // subu
					out.print("subu" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rs, 2) + ", $"
							+ Integer.parseInt(rt, 2));
					execute.subu(Integer.parseInt(rd, 2), Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "011000": // mult
					out.print("mult" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					execute.mult(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "011001": // multu
					out.print("multu" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					execute.multu(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "011010": // div
					out.print("div" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					execute.div(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "011011": // divu
					out.print("divu" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2));
					execute.divu(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2));
					jumper = false;
					break;
				case "000000": // sll
					out.print("sll" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					execute.sll(Integer.parseInt(rd, 2), Integer.parseInt(rt, 2), Integer.parseInt(shamt, 2));
					jumper = false;
					break;
				case "000010": // srl
					out.print("srl" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					execute.srl(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2), Integer.parseInt(shamt, 2));
					jumper = false;
					break;
				case "000011": // sra
					out.print("sra" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
							+ Integer.parseInt(shamt, 2));
					execute.sra(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2), Integer.parseInt(shamt, 2));
					jumper = false;
					break;
				case "000100": // sllv
					out.print("sllv" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					execute.sllv(Integer.parseInt(rd, 2), Integer.parseInt(rt, 2), Integer.parseInt(rs, 2));
					jumper = false;
					break;
				case "000110": // srlv
					out.print("srlv" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					execute.srlv(Integer.parseInt(rd, 2), Integer.parseInt(rt, 2), Integer.parseInt(rs, 2));
					jumper = false;
					break;
				case "000111": // srav
					out.print("srav" + " $" + Integer.parseInt(rd, 2) + ", $" + Integer.parseInt(rt, 2) + ", $"
							+ Integer.parseInt(rs, 2));
					execute.srav(Integer.parseInt(rd, 2), Integer.parseInt(rt, 2), Integer.parseInt(rs, 2));
					jumper = false;
					break;
				case "001100": // syscall
					out.print("syscall");
					execute.syscall();
					jumper = false;
					break;
				}
				break;
			case "001111": // lui
				out.print("lui" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2));
				execute.lui(Integer.parseInt(rt, 2), Integer.parseInt(immediate, 2));
				jumper = false;
				break;
			case "001000": // addi
				out.print("addi" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				execute.addi(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), SignExtImm);
				jumper = false;
				break;
			case "001010": // slti
				out.print("slti" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				execute.slti(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), SignExtImm);
				jumper = false;
				break;
			case "001100": // andi
				out.print("andi" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ ZeroExtImm);
				execute.andi(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), ZeroExtImm);
				jumper = false;
				break;
			case "001101": // ori
				out.print("ori" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ ZeroExtImm);
				execute.ori(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), ZeroExtImm);
				jumper = false;
				break;
			case "001110": // xori
				out.print("xori" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ ZeroExtImm);
				execute.xori(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), ZeroExtImm);
				jumper = false;
				break;
			case "100011": // lw
				out.print("lw" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				execute.lw(Integer.parseInt(rt, 2), SignExtImm, Integer.parseInt(rs, 2));
				jumper = false;
				break;
			case "101011": // sw
				out.print("sw" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				execute.sw(Integer.parseInt(rt, 2), SignExtImm, Integer.parseInt(rs, 2));
				jumper = false;
				break;
			case "000010": // j
				out.print("j " + Integer.parseInt(address, 2));
				execute.j(JumpAddr);
				jumper = true;
				break;
			case "000001": // bltz
				out.print("bltz" + " $" + Integer.parseInt(rs, 2) + ", " + (short) Integer.parseInt(address, 2));
				jumper = execute.bltz(Integer.parseInt(rs, 2), Integer.parseInt(immediate, 2));
				break;
			case "000100": // beq
				out.print("beq" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				jumper = execute.beq(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2), BranchAddr);
				break;
			case "000101": // bne
				out.print("bne" + " $" + Integer.parseInt(rs, 2) + ", $" + Integer.parseInt(rt, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				jumper = execute.bne(Integer.parseInt(rs, 2), Integer.parseInt(rt, 2), BranchAddr);
				break;
			case "001001": // addiu
				out.print("addiu" + " $" + Integer.parseInt(rt, 2) + ", $" + Integer.parseInt(rs, 2) + ", "
						+ (short) Integer.parseInt(immediate, 2));
				execute.addiu(Integer.parseInt(rt, 2), Integer.parseInt(rs, 2), SignExtImm);
				jumper = false;
				break;
			case "100000": // lb
				out.print("lb" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				execute.lb(Integer.parseInt(rt, 2), (SignExtImm & 0x000000FF), Integer.parseInt(rs, 2));
				jumper = false;
				break;
			case "100100": // lbu
				out.print("lbu" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				execute.lbu(Integer.parseInt(rt, 2), (SignExtImm & 0x000000FF), Integer.parseInt(rs, 2));
				jumper = false;
				break;
			case "101000": // sb
				out.print("sb" + " $" + Integer.parseInt(rt, 2) + ", " + (short) Integer.parseInt(immediate, 2) + "($"
						+ Integer.parseInt(rs, 2) + ")");
				execute.sb(Integer.parseInt(rt, 2), (SignExtImm & 0x000000FF), Integer.parseInt(rs, 2));
				jumper = false;
				break;
			case "000011": // jal
				out.print("jal " + Integer.parseInt(address, 2));
				execute.jal(JumpAddr);
				jumper = true;
				break;
			}
			
			if (!jumper)
				execute.reg.setPC(execute.reg.getPC()+4);
			
//			System.out.println(execute.reg.getPC());
					
			out.println();
			execute.printReg(out);
			if(execute.reg.getPC() < (instrucao.size()*4))
				out.println();			
			
//			System.out.println("Instrução: "+ instrucao.size());
			
		} while (execute.reg.getPC() < (instrucao.size()*4));
		out.close();
	}
}
