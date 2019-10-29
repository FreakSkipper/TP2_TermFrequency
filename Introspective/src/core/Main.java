package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
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
				str = str.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
				if (str != "") {
					string_vector.add(str);
				}
			}
			bufferedreader.close();
			
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("> Nao foi possivel ler o arquivo: " + nomeArquivo);
		}
		
		return string_vector;
	} // end readDocument();
	
	
	public static void rmStopWords(Vector<String> text, Vector<String> stopwords) {
		
		for (int i = 0; i < text.size(); i++) {
			for (int j = 0; j < stopwords.size(); j++) {
				// using regex: \\b for word boundaries and \\s* to remove white spaces
				text.set(i, text.elementAt(i).replaceAll("\\b" +stopwords.elementAt(j) + "\\b\\s*", ""));
			}
		}
	} // end rmStopWords();

	
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
	} // end menu();
	
	
	public static void main(String[] args) {
		String nomeArquivoFonte, nomeArquivoStopWords;
		boolean stop = false;
		user_input = new Scanner(System.in);
		
		do {
			int opt = menu();
			
			if (opt == 1) {
				// Input nome arquivo
				System.out.println("> Insira o nome do arquivo a ser persquisado (SomeText.txt):");
				System.out.println(">> ");
				nomeArquivoFonte = user_input.nextLine();
				Vector<String> text = readDocument(nomeArquivoFonte);
				
				System.out.println("> Insira o nome do arquivo de stopwords (StopWords.txt):");
				System.out.println(">> ");
				nomeArquivoStopWords = user_input.nextLine();
				Vector<String> stopwords = readDocument(nomeArquivoStopWords);
				if (text.isEmpty() || stopwords.isEmpty()) continue;
				
				// removendo stopwords
				rmStopWords(text, stopwords);
				
				// calculando frequencia de termos
				TermFrequency tf = new TermFrequency();
				tf.termFreq(text);
				Field [] fields_tf = tf.getClass().getDeclaredFields();		// pegando membros da classe "TermFrequency"
				
				try {
					Field f_terms = tf.getClass().getDeclaredField("terms");		// acessando atributo "terms"
					f_terms.setAccessible(true);									// tornando atributo privado acessivel (perigoso)
//					Map<String, Integer> copy_terms = (zMap<String, Integer>) f_terms.get(tf);
					Iterator<String> it = ((Map<String, Integer>) f_terms.get(tf)).keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
//						System.out.printf("> Palavra: %-20s | Frequencia: %-8s\n", key, ((Field) f_terms.get(tf)).get(key));
						System.out.println(f_terms.get(tf));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				
				// printando
				tf.printTerms();
				
				/*
				 * // Input termo a ser procurado
				 * System.out.println("> Insira o termo a ser procurado:");
				 * System.out.print(">> ");
				 * 
				 * String termo = user_input.nextLine(); System.out.println(termo); // caso o
				 * usuario ensira mais de 1 palavra, soh pega a 1 String[] first_word =
				 * termo.split(" ");
				 * 
				 * TermFrequency tf = new TermFrequency(); // frequencia do termo
				 * tf.specificTermFreq(text, first_word[0]); // acessando atributo usando
				 * introspection Field[] fields_tf = tf.getClass().getDeclaredFields();
				 * 
				 * if (fields_tf.length > 0) { Field field = fields_tf[0];
				 * field.setAccessible(true); try {
				 * System.out.println("> A frequencia do termo " + "\"" +first_word[0] + "\"" +
				 * " eh " +field.get(tf) + "."); } catch(Exception e) { e.printStackTrace(); } }
				 * else { System.out.println("> Erro ao acessar atributos de " +tf.getClass());
				 * }
				 */
			} // end opt == 1;
			else if (opt == 2) stop = true;
		} while(!stop);
		
		user_input.close();
	} // end main();
}
