
public class Registradores {

	// DECLARAÇÃO DOS REGISTRADORES
	private int[] reg; // ARRAY PARA ADICIONAR OS 32 REGISTRADORES DO MIPS
	private long regHi; // REGISTRADOR HI
	private long regLo; // REGISTRADOR LO
	private int PC; // PC - PROGRAM COUNTER
	private int[] memory;
	private final static int base = 8192;

	public Registradores() {
		this.reg = new int[32];
		this.regHi = 0;
		this.regLo = 0;
		this.PC = 0;
		this.memory = new int[8194];
	}

	// GET DA INFORMAÇÃO EXISTENTE EM UM DOS 32 REGISTRADORES
	public int getReg(int index) {
		return reg[index];
	}

	// GET DA INFORMAÇÃO DO REGISTRADOR LO PARA A FUNÇÃO MFLO
	public long getLo() {
		return regLo;
	}

	// GET DA INFORMAÇÃO DO REGISTRADOR LO PARA A FUNÇÃO MFHI
	public long getHi() {
		return regHi;
	}

	// SET DO REGISTRADOR COM INDICE E VALOR COMO PARAMETROS
	public void setReg(int index, int value) {
		if (index != 0) // REGISTRADOR ZERO NÃO PODE SER ALTERADO
			this.reg[index] = value;
	}

	// SET DO REGISTRADOR HI PARA A INSTRUÇÃO MULT E MULTU, DIV E DIVU
	public void setRegHi(int value) {
		this.regHi = value;
	}

	// SET DO REGISTRADOR LO PARA A INSTRUÇÃO MULT E MULTU, DIV E DIVU
	public void setRegLo(int value) {
		this.regLo = value;
	}

	// GET E SET DO PC
	public int getPC() {
		return PC;
	}

	public void setPC(int PC) {
		this.PC = PC;
	}

	public void addMemory(int index, int value) {
		//if (index % base == 0) {
			//index /= base;
			memory[index] = value;
		//}
	}

	public void setMemory(int index, int value) {
		//if (index % base == 0) {
			index /= base;
			memory[index] = value;
		//}
	}

	public int getMemory(int index) throws Exception{
		//if(index % base == 0){
		for (int i =0; i<8193; i++){
			System.out.println(memory[i]);
		}
			//index /= base;
			return memory[index]; 
		//}
		//else throw new Exception("Erro na Memória");
	}
}
