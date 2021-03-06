import java.io.PrintWriter;

/**
 * SUBTITLE (1) May cause overflow exception 
 * (2) SignExtImm = { 16{immediate[15]}, immediate } 
 * (3) ZeroExtImm = { 16{1b�0}, immediate } 
 * (4) BranchAddr = { 14{immediate[15]}, immediate, 2�b0 } 
 * (5) JumpAddr = {PC+4[31:28], address, 2�b0 } 
 * (6) Operands considered unsigned numbers (vs.* 2�s comp.) 
 * (7) Atomic test & set pair; R[rt] = 1 if pair atomic, 0 if not atomic
 * 
 * @author Ikaro Alef e Kenedy Felipe
 * 
 */

public class Executor {

	Registradores reg;

	public Executor() {
		this.reg = new Registradores();
	}

	public static String complementoBits(String bin, int bits) {
		int tam = bin.length();
		while (tam < bits) {
			bin = "0" + bin;
			tam++;
		}
		return bin;
	}

	// add R[rd] = R[rs] + R[rt] (1)
	public void add(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) + this.reg.getReg(rt));
	}

	// sub R[rd] = R[rs] - R[rt] (1)
	public void sub(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) - this.reg.getReg(rt));
	}

	// slt R[rd] = (R[rs] < R[rt]) ? 1 : 0
	public void slt(int rd, int rs, int rt) {
		if (this.reg.getReg(rs) < this.reg.getReg(rt))
			this.reg.setReg(rd, 1);
		else
			this.reg.setReg(rd, 0);
	}

	// and R[rd] = R[rs] & R[rt]
	public void and(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) & this.reg.getReg(rt));
	}

	// or R[rd] = R[rs] | R[rt]
	public void or(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) | this.reg.getReg(rt));
	}

	// xor R[rd] = R[rs] ^ R[rt]
	public void xor(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) ^ this.reg.getReg(rt));
	}

	// nor R[rd] = ~(R[rs] | R[rt])
	public void nor(int rd, int rs, int rt) {
		this.reg.setReg(rd, ~(this.reg.getReg(rs) | this.reg.getReg(rt)));
	}

	// jr PC = R[rs]
	public void jr(int rs) {
		this.reg.setPC(this.reg.getReg(rs));
	}

	// mfhi R[rd] = Hi
	public void mfhi(int rd) {
		this.reg.setReg(rd, (int) this.reg.getHi());
	}

	// mflo R[rd] = Lo
	public void mflo(int rd) {
		this.reg.setReg(rd, (int) this.reg.getLo());
	}

	// addu R[rd] = R[rs] + R[rt]
	public void addu(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) + this.reg.getReg(rt));
	}

	// subu R[rd] = R[rs] - R[rt]
	public void subu(int rd, int rs, int rt) {
		this.reg.setReg(rd, this.reg.getReg(rs) - this.reg.getReg(rt));
	}

	// mult {Hi,Lo} = R[rs] * R[rt]
	public void mult(int rs, int rt) {
		long mult = (long) this.reg.getReg(rs) * this.reg.getReg(rt);
		int maskLo = 0xFFFFFFFF;
		long hi = mult >> 32;
		int lo = (int) (mult & maskLo);
		this.reg.setRegHi((int) hi);
		this.reg.setRegLo(lo);
	}

	// multu {Hi,Lo} = R[rs] * R[rt]
	// METODO UTILIZADO PARA VERIFICA��O DO UNSIGNED
	public void multu(int rs, int rt) {

		// REGISTRADOR RS
		String hex = Integer.toBinaryString(this.reg.getReg(rs));
		hex = complementoBits(hex, 32);
		long regRs = Long.parseUnsignedLong(hex, 2);

		// REGISTRADOR RT
		hex = Integer.toBinaryString(this.reg.getReg(rt));
		hex = complementoBits(hex, 32);
		long regRt = Long.parseUnsignedLong(hex, 2);

		// MULTIPLICA��O
		long mult = (long) regRs * regRt;

		// CONVERS�O DO VALOR DA MULTIPLICA��O PARA BIN�RIO
		hex = Long.toBinaryString(mult);
		hex = complementoBits(hex, 64);

		// SEPARA��O DOS BITS 32 MAIS SIGNIFICATIVOS EM HI E OS 32 MENOS SIGNIFICATIVOS EM LO
		String Hi = hex.substring(0, 32);
		String Lo = hex.substring(32, 64);

		// NEGA��O PARA QUANDO HOUVER A CONVERS�O PARA INT NO REGISTRADOR ELE ESTEJA SEM SINAL
		long hi = Long.parseUnsignedLong(Hi, 2);
		long lo = Long.parseUnsignedLong(Lo, 2);

		this.reg.setRegHi((int) hi);
		this.reg.setRegLo((int) lo);
	}

	// div Lo = R[rs]/R[rt]; Hi = R[rs]%R[rt]
	public void div(int rs, int rt) {
		if (this.reg.getReg(rt) != 0) {
			this.reg.setRegHi(this.reg.getReg(rs) % this.reg.getReg(rt));
			this.reg.setRegLo(this.reg.getReg(rs) / this.reg.getReg(rt));
		}
	}

	// divu Lo=R[rs]/R[rt]; Hi=R[rs]%R[rt]
	public void divu(int rs, int rt) {
		if (this.reg.getReg(rt) != 0) {

			String hex = Integer.toBinaryString(this.reg.getReg(rt));
			hex = complementoBits(hex, 32);
			long regRT = Long.parseUnsignedLong(hex, 2);

			hex = Integer.toBinaryString(this.reg.getReg(rs));
			hex = complementoBits(hex, 32);
			long regRS = Long.parseUnsignedLong(hex, 2);

			this.reg.setRegHi((int) (regRS % regRT));
			this.reg.setRegLo((int) (regRS / regRT));
		}
	}

	// sll R[rd] = R[rt] << shamt
	public void sll(int rd, int rt, int shamt) {
		this.reg.setReg(rd, (this.reg.getReg(rt) << shamt));
	}

	// srl R[rd] = R[rt] >> shamt
	public void srl(int rd, int rt, int shamt) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >> shamt));
	}

	// sra R[rd] = R[rt] >>> shamt
	public void sra(int rd, int rt, int shamt) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >>> shamt));
	}

	// sllv R[rd] = R[rt] << R[rs]
	public void sllv(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) << this.reg.getReg(rs)));
	}

	// srlv R[rd] = R[rt] >> R[rs]
	public void srlv(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >> this.reg.getReg(rs)));
	}

	// srav R[rd] = R[rt] >>> R[rs]
	public void srav(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >>> this.reg.getReg(rs)));
	}

	// syscall
	public void syscall() {
	}

	// lui R[rt] = {imm, 16�b0}
	public void lui(int rt, int immed) {
		int value = (immed << 16);
		this.reg.setReg(rt, (value));
	}

	// addi R[rt] = R[rs] + SignExtImm
	public void addi(int rt, int rs, int immed) {
		this.reg.setReg(rt, this.reg.getReg(rs) + immed);
	}

	// slti R[rt] = (R[rs] < SignExtImm)? 1 : 0
	public void slti(int rt, int rs, int immed) {
		if (this.reg.getReg(rs) < immed)
			this.reg.setReg(rt, 1);
		else
			this.reg.setReg(rt, 0);
	}

	// andi R[rt] = R[rs] & ZeroExtImm (3)
	public void andi(int rt, int rs, int immed) {
		this.reg.setReg(rt, (this.reg.getReg(rs) & immed));
	}

	// ori R[rt] = R[rs] | ZeroExtImm (3)
	public void ori(int rt, int rs, int immed) {
		this.reg.setReg(rt, (this.reg.getReg(rs) | immed));
	}

	// xori R[rt] = R[rs] ^ ZeroExtImm (3)
	public void xori(int rt, int rs, int immed) {
		this.reg.setReg(rt, (this.reg.getReg(rs) & immed));
	}

	// lw R[rt] = M[R[rs]+SignExtImm]
	public void lw(int rt, int immed, int rs) throws Exception {
		this.reg.setReg(rt, this.reg.getMemory(this.reg.getReg(rs)+immed));
	}

	// sw M[R[rs]+SignExtImm] = R[rt]
	public void sw(int rt, int immed, int rs) throws Exception {
		this.reg.addMemory(this.reg.getReg(rs) + immed, this.reg.getReg(rt));
	}

	// j PC = JumpAddr (5)
	public void j(int JumpAddr) {
		this.reg.setPC(JumpAddr);
	}

	// bltz if(R[rs] < ZERO) PC = Label
	public boolean bltz(int rs, int immediate) {
		if (this.reg.getReg(rs) < 0){
			this.reg.setPC((this.reg.getPC() + immediate));
			return true;
		}
		return false;
	}

	// beq if(R[rs]==R[rt]) PC=PC+4+BranchAddr (4)
	public boolean beq(int rt, int rs, int BranchAddr) {
		if (this.reg.getReg(rs) == this.reg.getReg(rt)) {
			this.reg.setPC(this.reg.getPC() + 4 + BranchAddr);
			return true;
		}
		return false;
	}

	// bne if(R[rs]!=R[rt]) PC=PC+4+BranchAddr (4)
	public boolean bne(int rt, int rs, int BranchAddr) {
		if (this.reg.getReg(rs) != this.reg.getReg(rt)) {
			this.reg.setPC(this.reg.getPC() + 4 + BranchAddr);
			return true;
		}
		return false;
	}

	// addiu R[rt] = R[rs] + SignExtImm (2)
	public void addiu(int rt, int rs, int immed) {
		this.reg.setReg(rt, this.reg.getReg(rs) + immed);
	}

	// lb 
	public void lb(int rt, int immed, int rs) throws Exception {
		int memory = this.reg.getMemory(this.reg.getReg(rs)+immed);
		if((memory & 0x80) != 128)
			this.reg.setReg(rt, (memory & 0x000000FF));
		else
			this.reg.setReg(rt, (memory | 0xFFFFFF00));
	}

	// lbu R[rt]={24�b0,M[R[rs] + SignExtImm](7:0)}
	public void lbu(int rt, int immed, int rs) throws Exception {
		this.reg.setReg(rt, (this.reg.getMemory((this.reg.getReg(rs)+immed)) & 0x000000FF));
	}

	// sb M[R[rs]+SignExtImm](7:0) = R[rt](7:0)
	public void sb(int rt, int immed, int rs) throws Exception {
		int index = (this.reg.getReg(rs) + immed);
		int value = this.reg.getReg(rt) & 0x000000FF;
		int memory = this.reg.getMemory(index);
		
		value = value << immed*8; // DESLOCAMENTO EM BITS
		value = value | memory;
		this.reg.addMemory(index, value);
	}

	// jal R[31]=PC+8;PC=JumpAddr
	public void jal(int JumpAddr) {
		this.reg.setReg(31, (this.reg.getPC()+4));
		this.reg.setPC(JumpAddr);
	}

	// IMPRESS�O DOS REGISTRADORES
	public void printReg(PrintWriter out) {
		for (int i = 0; i < 32; i++){
			//if(this.reg.getReg(i) != 0)
				out.print("$" + i + "=" + this.reg.getReg(i) + ";");
		}
		//out.print("$Hi=" + this.reg.getHi() + ";");
		//out.println("$Lo=" + this.reg.getLo() + ";");
	}
}
