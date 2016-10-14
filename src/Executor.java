import java.io.PrintWriter;

/** SUBTITLE
 * (1) May cause overflow exception 
 * (2) SignExtImm = { 16{immediate[15]}, immediate } 
 * (3) ZeroExtImm = { 16{1b�0}, immediate } 
 * (4) BranchAddr = {14{immediate[15]}, immediate, 2�b0 } 
 * (5) JumpAddr = { PC+4[31:28], address, 2�b0 } 
 * (6) Operands considered unsigned numbers (vs. 2� s comp.) 
 * (7) Atomic test&set pair; R[rt] = 1 if pair atomic, 0 if not atomic
 * 
 * @author Kenedy
 * 
 */

public class Executor {

	Registradores reg;

	public Executor() {
		this.reg = new Registradores();
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

	}

	// mfhi R[rd] = Hi
	public void mfhi(int rd) {
		this.reg.setReg(rd, this.reg.getHi());
	}

	// mflo R[rd] = Lo
	public void mflo(int rd) {
		this.reg.setReg(rd, this.reg.getLo());
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

	}

	// multu {Hi,Lo} = R[rs] * R[rt]
	public void multu(int rs, int rt) {

	}

	// div Lo = R[rs]/R[rt]; Hi = R[rs]%R[rt]
	public void div(int rs, int rt) {
		this.reg.setRegHi(this.reg.getReg(rs) % this.reg.getReg(rt));
		this.reg.setRegLo(this.reg.getReg(rs) / this.reg.getReg(rt));
	}

	// divu Lo=R[rs]/R[rt]; Hi=R[rs]%R[rt]
	public void divu(int rs, int rt) {
		this.reg.setRegHi(this.reg.getReg(rs) % this.reg.getReg(rt));
		this.reg.setRegLo(this.reg.getReg(rs) / this.reg.getReg(rt));
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

	// R[rd] = R[rt] << R[rs]
	public void sllv(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) << this.reg.getReg(rs)));
	}

	// R[rd] = R[rt] >> R[rs]
	public void srlv(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >> this.reg.getReg(rs)));
	}

	// R[rd] = R[rt] >>> R[rs]
	public void srav(int rd, int rt, int rs) {
		this.reg.setReg(rd, (this.reg.getReg(rt) >>> this.reg.getReg(rs)));
	}

	// syscall
	public void syscall() {
	}

	// lui R[rt] = {imm, 16�b0}
	public void lui(int rt, int immed) {

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
		this.reg.setReg(rt, (this.reg.getReg(rs) & immed));
	}

	// xori R[rt] = R[rs] ^ ZeroExtImm (3)
	public void xori(int rt, int rs, int immed) {
		this.reg.setReg(rt, (this.reg.getReg(rs) & immed));
	}

	// lw R[rt] = M[R[rs]+SignExtImm]
	public void lw(int rt, int immed, int rs) {

	}

	// sw M[R[rs]+SignExtImm] = R[rt]
	public void sw(int rt, int immed, int rs) {

	}

	// j PC = JumpAddr (5)
	public void j(int address) {

	}

	// bltz if(R[rs] < ZERO) PC = Label
	public void bltz(int rs, int adress) {
		if (this.reg.getReg(rs) < 0)
			;
		// PC = address;
	}

	// beq if(R[rs]==R[rt]) PC=PC+4+BranchAddr (4)
	public void beq(int rt, int rs, int immed) {
		if (this.reg.getReg(rs) == this.reg.getReg(rt))
			;
		// PC = PC + 4 + BranchAddr
	}

	// bne if(R[rs]!=R[rt]) PC=PC+4+BranchAddr (4)
	public void bne(int rt, int rs, int immed) {
		if (this.reg.getReg(rs) != this.reg.getReg(rt))
			;
		this.reg.setPC(this.reg.getPC() + 4 + immed);
	}

	// addiu R[rt] = R[rs] + SignExtImm (2)
	public void addiu(int rt, int rs, int immed) {
		this.reg.setReg(rt, this.reg.getReg(rs) + immed);
	}

	// lb R[rt]={24�b0,M[R[rs] + SignExtImm](7:0)}
	public void lb(int rt, int immed, int rs) {

	}

	// lbu R[rt]={24�b0,M[R[rs] + SignExtImm](7:0)}
	public void lbu(int rt, int immed, int rs) {

	}

	// sb M[R[rs]+SignExtImm](7:0) = R[rt](7:0)
	public void sb(int rt, int immed, int rs) {

	}

	// jal R[31] = PC + 8; PC = JumpAddr
	public void jal(int address) {
		this.reg.setReg(31, this.reg.getPC() + 8);
	}

	public void printReg(PrintWriter out) {
		for (int i = 0; i < 32; i++)
			out.print("$" + i + "=" + this.reg.getReg(i) + ";");
	}
}