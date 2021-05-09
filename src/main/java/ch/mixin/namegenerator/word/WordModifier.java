package ch.mixin.namegenerator.word;

import java.util.ArrayList;

public class WordModifier {
	public static String capital(String word) {
		ArrayList<String> capitalParts = new ArrayList<>();

		for (String part : word.split(" ")) {
			if (part.length() == 0)
				continue;

			capitalParts.add(part.substring(0, 1).toUpperCase() + part.substring(1));
		}

		word = String.join(" ", capitalParts);
		capitalParts = new ArrayList<>();

		for (String part : word.split("-")) {
			if (part.length() == 0)
				continue;

			capitalParts.add(part.substring(0, 1).toUpperCase() + part.substring(1));
		}

		word = String.join("-", capitalParts);
		return word;
	}

	public static ArrayList<String> capital(ArrayList<String> words) {
		ArrayList<String> results = new ArrayList<>();
		for (String word : words) {
			results.add(capital(word));
		}
		return results;
	}

	public static String plural(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if ("sx".contains(word.substring(length - 1)) || (length >= 3 && "tch".equals(word.substring(length - 3)))) {
			word += "es";
		} else if ("y".equals(word.substring(length - 1))
				&& (length == 1 || !"aeiou".contains(word.substring(length - 2, length - 1)))) {
			word = word.substring(0, length - 1) + "ies";
		} else {
			word += "s";
		}

		return word;
	}

	public static String comparative(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if (length >= 7) {
			return "more " + word;
		}

		if ("y".equals(word.substring(length - 1))) {
			word = word.substring(0, length - 1) + "ier";
		} else if ("e".equals(word.substring(length - 1))) {
			word += "r";
		} else {
			word += "er";
		}

		return word;
	}

	public static String superlative(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if (length >= 7) {
			return "most " + word;
		}

		if ("y".equals(word.substring(length - 1))) {
			word = word.substring(0, length - 1) + "iest";
		} else if ("e".equals(word.substring(length - 1))) {
			word += "st";
		} else {
			word += "est";
		}

		return word;
	}

	public static String thirdPerson(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if ("sx".contains(word.substring(length - 1)) || (length >= 3 && "tch".equals(word.substring(length - 3)))) {
			word += "es";
		} else if ("y".equals(word.substring(length - 1))
				&& (length == 1 || !"aeiou".contains(word.substring(length - 2, length - 1)))) {
			word = word.substring(0, length - 1) + "ies";
		} else {
			word += "s";
		}

		return word;
	}

	public static String pastParticiple(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if (length >= 2 && "ee".equals(word.substring(length - 2))) {
			word = word.substring(0, length - 1) + "d";
		} else if ("e".equals(word.substring(length - 1))) {
			word += "d";
		} else if ("y".equals(word.substring(length - 1))) {
			word = word.substring(0, length - 1) + "ied";
		} else {
			word += "ed";
		}

		return word;
	}

	public static String presentParticiple(String word) {
		Integer length = word.length();

		if (length == 0) {
			return "";
		}

		if ("e".equals(word.substring(length - 1)) && (length == 1 || !"ee".equals(word.substring(length - 2)))) {
			word = word.substring(0, length - 1) + "ing";
		} else if ("mnp".contains(word.substring(length - 1))
				&& (length == 1 || (!word.substring(length - 1).equals(word.substring(length - 2, length - 1))
						&& "aeiou".contains(word.substring(length - 2, length - 1))))) {
			word += word.substring(length - 1) + "ing";
		} else {
			word += "ing";
		}

		return word;
	}

	public static String list(ArrayList<String> words) {
		String text = "";
		for (int i = 0; i < words.size(); i++) {
			text += words.get(i);
			if (i + 2 == words.size()) {
				text += " and ";
			} else if (i + 1 < words.size()) {
				text += ", ";
			}
		}
		return text;
	}
}
