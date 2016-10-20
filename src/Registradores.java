
public class Registradores {
	
	//DECLARA플O DOS REGISTRADORES
	private int[] reg;	//ARRAY PARA ADICIONAR OS 32 REGISTRADORES DO MIPS
	private long regHi;	//REGISTRADOR HI
	private long regLo;	//REGISTRADOR LO
	private int PC;		//PC - PROGRAM COUNTER

	public Registradores() {
		this.reg = new int[32];
		this.regHi = 0;
		this.regLo = 0;
		this.PC = 0;
	}
	
	//GET DA INFORMA플O EXISTENTE EM UM DOS 32 REGISTRADORES
	public int getReg(int index) {
		return reg[index];
	}
	
	//GET DA INFORMA플O DO REGISTRADOR LO PARA A FUN플O MFLO
	public long getLo() {
		return regLo;
	}

	//GET DA INFORMA플O DO REGISTRADOR LO PARA A FUN플O MFHI
	public long getHi() {
		return regHi;
	}
	
	//SET DO REGISTRADOR COM INDICE E VALOR COMO PARAMETROS
	public void setReg(int index, int value) {
		if(index != 0)	//REGISTRADOR ZERO N홒 PODE SER ALTERADO
			this.reg[index] = value;
	}
	
	//SET DO REGISTRADOR HI PARA A INSTRU플O MULT E MULTU, DIV E DIVU
	public void setRegHi(int value) {
		this.regHi = value;
	}

	//SET DO REGISTRADOR LO PARA A INSTRU플O MULT E MULTU, DIV E DIVU
	public void setRegLo(int value) {
		this.regLo = value;
	}
	
	//GET E SET DO PC
	public int getPC() {
		return PC;
	}

	public void setPC(int PC) {
		this.PC = PC;
	}
}
