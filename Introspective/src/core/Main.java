package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Main {
	private static Scanner user_input;			// destinado ao input do usuario (https://stackoverflow.com/q/13042008)
	
	
	public static void CLEAR() {
		System.out.println(new String(new char[50]).replace("\0", "\r\n"));
	}
	
	
	public static Vector<String> readDocument(String nomeArquivo) {
		// conferindo chamada de funcao
		String className = new Exception().getStackTrace()[1].getMethodName();
		String expectedCaller = "main";
		if (className != expectedCaller) {
			System.out.println("> Chamada invalida de funcao");
			System.out.println("\tCaller: " +className + "\n\tExpected: " +expectedCaller);
			return null;
		}
		
		// abrindo arquivo
		File file = new File(System.getProperty("user.dir") + "/Introspective/src/" + nomeArquivo);
		String str;
		Vector<String> string_vector = new Vector<String>();
		
		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
			while ((str = bufferedreader.readLine()) != null) { // enquanto ocorrer uma leitura
				string_vector.add(str);
			}
			bufferedreader.close();
			
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("> Nao foi possivel ler o arquivo: " + nomeArquivo);
		}
		
		return string_vector;
	}

	
	public static int menu() {
		int opt = -1;
		
		do {
			// CLEAR();
			System.out.println("############################ Term Frequency ############################");
			System.out.println("> 1 - Calcular a frequencia de um termo");
			System.out.println("> 2 - Sair");
			System.out.print(">> ");
			
			String readString = user_input.nextLine();
			if (!readString.isEmpty()) opt = Integer.parseInt(readString);
		} while(opt != 1 && opt != 2);
				
		return opt;
	}
	
	
	public static void main(String[] args) {
		
		boolean stop = false;
		user_input = new Scanner(System.in);
		
		do {
			int opt = menu();
			
			if (opt == 1) {
				// Input nome arquivo
				System.out.println("> Insira o nome do arquivo a ser persquisado (SomeText.txt):");
				System.out.println(">> ");

				String nomeArquivo = user_input.nextLine();
				Vector<String> text = readDocument(nomeArquivo);
				if (text.isEmpty()) continue;
				
				// Input termo a ser procurado
				System.out.println("> Insira o termo a ser procurado:");
				System.out.print(">> ");
				
				String termo = user_input.nextLine();
				System.out.println(termo);
				// caso o usuario ensira mais de 1 palavra, soh pega a 1
				String[] first_word = termo.split(" ");		
				
				TermFrequency tf = new TermFrequency();			// frequencia do termo
				tf.termFreq(text, first_word[0]);
				// acessando atributo usando introspection
				Field[] fields_tf = tf.getClass().getDeclaredFields();
				
				if (fields_tf.length > 0) {
					Field field = fields_tf[0];
					field.setAccessible(true);
					try {
						System.out.println("> A frequencia do termo " + "\"" +first_word[0] + "\"" + " eh " +field.get(tf) + ".");
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("> Erro ao acessar atributos de " +tf.getClass());
				}
			} // end opt == 1;
			else if (opt == 2) stop = true;
		} while(!stop);
		
		user_input.close();
	}
}
