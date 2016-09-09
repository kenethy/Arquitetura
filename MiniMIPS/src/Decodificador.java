import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Decodificador {
	
	public static ArrayList<String> lerArq() throws IOException{ //ler um arquivo "in.txt", converte o hexa para binario em um ArrayList e retorna o arrayList.
		FileReader arq = new FileReader("in.txt");
		BufferedReader buffer = new BufferedReader(arq);
		ArrayList<String> retorno = new ArrayList<String>();
		String linha = buffer.readLine(); 
		while (linha!=null) {
			retorno.add((new BigInteger(linha, 16)).toString(2));
			linha = buffer.readLine(); // lê da segunda até a última linha
		}
		
		arq.close();
		return retorno;
	}
	
	//public static void 
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> ola = lerArq();
		
		System.out.println(ola.get(1));
	}
}
