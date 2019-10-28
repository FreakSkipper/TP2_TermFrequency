package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;


public class TermFrequency {
	private static Scanner user_input;			// destinado ao input do usuario (https://stackoverflow.com/q/13042008)
	
	public static void CLEAR() {
		System.out.println(new String(new char[50]).replace("\0", "\r\n"));
	}
	
	public static Vector<String> readDocument() {
		String nome_arquivo = "SomeText.txt";
		File file = new File(System.getProperty("user.dir") + "/src/" + nome_arquivo);
		String str;
		Vector<String> string_vector = new Vector<String>();
		
		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
			while ((str = bufferedreader.readLine()) != null) { // enquanto ocorrer uma leitura
				string_vector.add(str);
			}
			bufferedreader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Não foi possível ler o arquivo: " + nome_arquivo);
		}
		
		return string_vector;
	}

	public static boolean hasTerm(String line, String term) {
		if (line.contains(term)) return true;
		
		return false;
	}
	
	public static int termFreq(Vector<String> text, String term) {
		int term_freq = 0;
		
		for (String line : text) {
			String[] words;
			words = line.split(" ");
			
			for (String single_word : words) {
				if (single_word.toLowerCase().contains(term)) term_freq++;
			}
		}
		return term_freq;
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
			opt = Integer.parseInt(readString);
		} while(opt != 1 && opt != 2);
				
		return opt;
	}
	
	public static void main(String[] args) {
		Vector<String> text = readDocument();
		boolean stop = false;
		user_input = new Scanner(System.in);
		
		do {
			int opt = menu();
			
			if (opt == 1) {						// frequencia do termo				
				System.out.println("> Insira o termo a ser procurado:");
				System.out.print(">> ");
				
				String termo = user_input.nextLine();
				System.out.println(termo);
				String[] first_word = termo.split(" ");
				
				int term_freq = termFreq(text, first_word[0]);
				
				System.out.println("> A frequencia do termo " + "\"" +first_word[0] + "\"" + " é " +term_freq + ".");
			}
		} while(stop);
		
		user_input.close();
	}
}
