package core;

/*import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;*/
import java.util.Vector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.lang.reflect.*;

public class Utilidades {
	public static void CLEAR() {
		System.out.println(new String(new char[50]).replace("\0", "\r\n"));
	}

	public static Vector<String> readDocument(String nomeArquivo) {
		// conferindo chamada de funcao através da pilha de execução
		String className = new Exception().getStackTrace()[1].getMethodName();
		String expectedCaller = "main";
		if (className != expectedCaller) {
			System.out.println("> Chamada invalida de funcao");
			System.out.println("\tCaller: " + className + "\n\tExpected: " + expectedCaller);
			return null;
		}

		// abrindo arquivo
		File file = new File(System.getProperty("user.dir") + "/" +nomeArquivo);
		String str;
		Vector<String> string_vector = new Vector<String>();

		try {
			BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
			while ((str = bufferedreader.readLine()) != null) { // enquanto ocorrer uma leitura
				str = str.replaceAll("[^a-zA-Z]\\s?", " ").toLowerCase();
				if (str != "") {
					string_vector.add(str);
				}
			}
			bufferedreader.close();

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("> Nao foi possivel ler o arquivo: " + "\"" +nomeArquivo + "\""
					+ " no caminho: " + "\"" +System.getProperty("user.dir") + "\"");
		}

		return string_vector;
	} // end readDocument();

	public static void rmStopWords(Vector<String> text, Vector<String> stopwords) {
		String[] array_text = text.toArray(new String[text.size()]); // operacao usando o vetor nao funcionava para
																		// casos muito grandes.
		for (int i = 0; i < array_text.length; i++) {
			String line = array_text[i];
			for (int j = 0; j < stopwords.size(); j++) {
				line = line.replaceAll("\\b" + stopwords.elementAt(j) + "\\b\\s*", "");
				line = line.replaceAll("\\b" + "[a-zA-Z]?" + "\\b", "");
				text.set(i, line);
			}
		}
	} // end rmStopWords();

	// ordenar "terms" usando Introspection
	public static void sortMap(Object obj) {
		if (obj instanceof TermFrequency) {		// conferindo classe do objeto
			TermFrequency ptr_obj = (TermFrequency) obj;
			ptr_obj.sortMap();
		}
		
		/*
		Poderia ter um comportamento para cada tipo de objeto, permitindo a alteração
		do fluxo do programa de acordo com o tipo do objeto (simples exemplo de Introspection).
		
		* Comportamento semelhante a de uma interface com diversas implementações de um mesmo método,
		* porém, usando Instropective não é necessário o "contrato" entre as classes aumentando a
		* liberdade do programador.
		*/
		else return;
		
		
		// Alterando diretamente o atributo usando Reflection:
		/*
		 * Map<String, Integer> sortedMap = new LinkedHashMap<>();
		 * 
		 * try { Field obj_terms = obj.getClass().getDeclaredField("terms"); //
		 * capturando campo do objeto obj_terms.setAccessible(true); // tornando-o
		 * acessivel
		 * 
		 * @SuppressWarnings("unchecked") // copia do conteudo de "terms" com cast
		 * Map<String, Integer> obj_unsortedMap = (Map<String, Integer>)
		 * obj_terms.get(obj);
		 * 
		 * // ordenando (decrescente)
		 * obj_unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(
		 * Comparator.reverseOrder())) .forEachOrdered(entry ->
		 * sortedMap.put(entry.getKey(), entry.getValue()));
		 * 
		 * obj_terms.set(obj, sortedMap); } catch (Exception e) { e.printStackTrace(); }
		 */
	}
}
