package core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class TermFrequency {
	private int term_quantity;
	private Map<String, Integer> terms;

	
	public TermFrequency() {
		this.term_quantity = 0;
		this.terms = new HashMap<>();
	}
	
	
	// Classe que para armazenar uma palavra e sua frequencia
	public class termStruct {
		public String word;
		public int quantity;
	}
	

	// Frequencia do "termo" fornecido no texto.
	public void specificTermFreq(Vector<String> text, String term) {
		int term_freq = 0;

		for (String line : text) {
			String[] words;
			words = line.split(" ");

			for (String single_word : words) {
				if (single_word.contains(term))
					term_freq++;
			}
		}

		term_quantity = term_freq;
	} // end specificTermFreq();
	
	// Calcula a frequencia de cada palavra em "text"
	public void termFreq(Vector<String> text) {
		for (String line : text) {
			String [] words = line.split("\\s");		// \s white space character
			
			for (String single_word : words) {
				if (single_word != "\\s" && single_word.length() > 0) {
					if (terms.containsKey(single_word)) {
						terms.put(single_word, terms.get(single_word) + 1);
					}
					else {	// nao contem a chave
						terms.put(new String(single_word), new Integer(1));
					}					
				}
			}
		}
	} // end termFreq();
	

	public static boolean hasTerm(String line, String term) {
		if (line.contains(term))
			return true;

		return false;
	} // end hasTerm();
	
	
	public void printTerms( ) {
		Iterator<String> it = terms.keySet().iterator();
		int contador = 0;
		while (it.hasNext()) {
			contador++;
			String key = it.next();
			System.out.printf("> Palavra: %-20s | Frequencia: %-8s\n", key, terms.get(key));
		}
		System.out.println("> Quantidade de palavras: " +contador);
	} // printTerms();

}
