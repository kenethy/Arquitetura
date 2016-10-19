
public class Teste {
	public static void main(String[] args){
		int jump = Integer.parseUnsignedInt("11111111111111111111111111111111", 2) & 0xFFFFFFFF;
		Integer.parseInt("1", 2);
		System.out.println(jump);
	}
}
