
public class Registradores {
	
	private int[] reg;
	private int regHi;
	private int regLo;
	private int PC;

	public Registradores() {
		this.reg = new int[32];
		this.regHi = 0;
		this.regLo = 0;
		this.setPC(0);
	}

	public int getReg(int index) {
		return reg[index];
	}
	
	public int getLo() {
		return regLo;
	}

	public int getHi() {
		return regHi;
	}

	public void setReg(int index, int value) {
		if(index != 0)
			this.reg[index] = value;
	}
	
	public void setRegHi(int value) {
		this.regHi = value;
	}

	public void setRegLo(int value) {
		this.regLo = value;
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int PC) {
		this.PC = PC;
	}
}
