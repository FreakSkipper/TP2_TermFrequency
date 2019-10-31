package core;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.lang.reflect.Field;
/*import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;*/

public class Main {
	private static Scanner user_input; // destinado ao input do usuario (https://stackoverflow.com/q/13042008)

	public static int menu() {
		int opt = -1;

		do {
			// CLEAR();
			System.out.println("############################ Term Frequency ############################");
			System.out.println("> 1 - Calcular a frequencia de um termo");
			System.out.println("> 2 - Sair");
			System.out.print(">> ");

			try {
				String readString = user_input.nextLine();
				opt = Integer.parseInt(readString);
			} catch (NumberFormatException | NoSuchElementException e) {
				System.out.println("> Valor inserido incorreto! Tente novamente.");
			}
		} while (opt != 1 && opt != 2);

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
				Vector<String> text = Utilidades.readDocument(nomeArquivoFonte);

				System.out.println("> Insira o nome do arquivo de stopwords (StopWords.txt):");
				System.out.println(">> ");
				nomeArquivoStopWords = user_input.nextLine();
				Vector<String> stopwords = Utilidades.readDocument(nomeArquivoStopWords);

				if (text.isEmpty() || stopwords.isEmpty())
					continue;
				else
					System.out.println("> Aguarde.");

				// removendo stopwords
				Utilidades.rmStopWords(text, stopwords);

				// calculando frequencia de termos
				TermFrequency tf = new TermFrequency();
				tf.termFreq(text);
				// tf.sortMap(); // ordenando normalmente
				Utilidades.sortMap(tf); // ordenando usando Introspective

				try {
					// Acessando elementos usando Introspection (Reflection API)
					Field f_terms = tf.getClass().getDeclaredField("terms"); // acessando atributo "terms"
					f_terms.setAccessible(true); // tornando atributo privado acessivel (perigoso)

					// Printando termos e frequencia correspondente
					@SuppressWarnings("unchecked")
					// ponteiro (copia) para o objeto da classe "TermFrequency"
					Map<String, Integer> ptr_terms = (Map<String, Integer>) f_terms.get(tf);
					Iterator<String> it = ptr_terms.keySet().iterator();
					int contador = 0;
					while (it.hasNext()) {
						contador++;
						String key = it.next();
						System.out.printf("> Palavra: %-20s | Frequencia: %-8s\n", "*" + key + "*", ptr_terms.get(key));
					}
					System.out.println("> A quantidade de palavras eh: " + contador);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}

			} // end opt == 1;
			else if (opt == 2)
				stop = true;
		} while (!stop);

		user_input.close();
	} // end main();
}
