package core;

import java.util.Vector;

public class TermFrequency {
	private int term_quantity;

	public TermFrequency() {
		term_quantity = 0;
	}

	// Frequencia do "termo" fornecido no texto.
	public void termFreq(Vector<String> text, String term) {
		int term_freq = 0;

		for (String line : text) {
			String[] words;
			words = line.split(" ");

			for (String single_word : words) {
				if (single_word.toLowerCase().contains(term))
					term_freq++;
			}
		}

		term_quantity = term_freq;
	}


	public static boolean hasTerm(String line, String term) {
		if (line.contains(term))
			return true;

		return false;
	}

}
