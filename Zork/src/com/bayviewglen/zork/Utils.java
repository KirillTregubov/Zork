package com.bayviewglen.zork;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/** "Utils" Class - a class that contains various utilities used by the rest of the code.
 * 
 *  Authors: 		Kirill Tregubov, Zacharia Burrafato, Andrew Douglas, Alim Halani
 *  Code Version:	0.3-alpha
 *  Published Date:	May 2018
 */

public class Utils {
	
	private static final int NUM_CHARS = 80;
	
	/** 
	 * Print, but put spaces after a specific character amount
	 */
	public static void formattedPrint(boolean includeLineBreak, String inputString) {
		if (includeLineBreak)
			System.out.println("\n" + formatStringForPrinting(inputString));
		else System.out.println(formatStringForPrinting(inputString));
	}

	public static String formatStringForPrinting(String inputString) {
		StringBuilder sb = new StringBuilder(inputString);
		int i = 0;
		int oldi = 0;
		
		while ((i = sb.indexOf(" ", i + NUM_CHARS)) != -1 && oldi < sb.length()) {
			// double \n
			if (sb.substring(oldi,i).contains("\n\n")) {
				oldi = sb.indexOf("\n", oldi)+4;
				i = sb.indexOf(" ", oldi + NUM_CHARS);
				// double \n
				if (sb.substring(oldi,i).contains("\n\n")) {
					oldi = sb.indexOf("\n", oldi)+4;
					i = sb.indexOf(" ", oldi + NUM_CHARS);
					// single \n
					if (sb.substring(oldi,i).contains("\n")) {
						oldi = sb.indexOf("\n", oldi)+2;
						i = sb.indexOf(" ", oldi + NUM_CHARS);
						sb.replace(i, i + 1, "\n");	
					} else {
						sb.replace(i, i + 1, "\n");
					}
					// single \n
				} else if (sb.substring(oldi,i).contains("\n")) {
					oldi = sb.indexOf("\n", oldi)+2;
					i = sb.indexOf(" ", oldi + NUM_CHARS);
					sb.replace(i, i + 1, "\n");	
					// normal \n
				} else {
					sb.replace(i, i + 1, "\n");
				}
				// single \n
			} else if (sb.substring(oldi,i).contains("\n")) {
				oldi = sb.indexOf("\n", oldi)+2;
				i = sb.indexOf(" ", oldi + NUM_CHARS);
				sb.replace(i, i + 1, "\n");
			}
			// normal \n
			else {
				sb.replace(i, i + 1, "\n");
				oldi = i;
			}
		}

		return sb.toString();
	}

	/** 
	 * Find index of the specified occurrence of a character
	 */
	public static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

	public static boolean containsCompareBoth(String str, String searchStr) {
		return containsIgnoreCase(str, searchStr) || containsIgnoreCase(searchStr, str);
	}

	/** 
	 * Check if str contains searchStr (regardless of case)
	 */
	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null)
			return false;

		final int length = searchStr.length();
		if (length == 0)
			return true;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return true;
		}
		return false;
	}

	public static int containsIgnoreCaseStart(String str, String searchStr) {
		if (str == null || searchStr == null)
			return -1;

		final int length = searchStr.length();
		if (length == 0)
			return -1;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return i;
		}
		return -1;
	}

	/** 
	 * Check if str contains searchStr (regardless of case)
	 */
	public static int containsFindIndex(String str, String searchStr) {
		if (str == null || searchStr == null)
			return -1;

		final int length = searchStr.length();
		if (length == 0)
			return -1;

		for (int i = str.length() - length; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, length))
				return i;
		}
		return -1;
	}

	public static String removeSubstring(String string, String substring) {
		int index = string.indexOf(substring);

		String returnString = "";
		returnString += string.substring(0, index);
		try {
			if (string.charAt(index-1) == ' ' && string.charAt(index + substring.length()) == ' ') returnString += string.substring(index + substring.length()+1);
			else returnString += string.substring(index + substring.length());
		} catch (Exception e) {
			if (!(string.charAt(index + substring.length()) == ' ')) returnString += string.substring(index + substring.length(),index + substring.length()+1);
			returnString += string.substring(index + substring.length()+1);
		}

		return returnString;
	}

	public static String removeBeforeSubstring(String string, String substring) {
		try {
			int index = string.toLowerCase().indexOf(substring);
			if (string.charAt(index + substring.length()) == ' ') return string.substring(index + substring.length()+1);
			else return string.substring(index + substring.length());
		} catch (Exception e) {
			return string;
		}
	}

	/** 
	 * Words to Numbers
	 */
	final static List<String> allowedStrings = Arrays.asList("and", "zero", "one", "two", "three", "four", "five",
			"six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety", "hundred", "thousand", "million", "billion", "trillion");

	public static String detectNumbers(String inputString) {
		return convertTextualNumbersInDocument(inputString);
	}

	/**
	 * Main driver method. Converts textual numbers (e.g. twenty five) to
	 * integers (e.g. 25)
	 * 
	 * Does not currently cater for decimal points. e.g. "five point two"
	 * 
	 * @param inputText
	 */
	public static String convertTextualNumbersInDocument(String inputText) {

		// splits text into words and deals with hyphenated numbers. Use linked
		// list due to manipulation during processing
		List<String> words = new LinkedList<String>(cleanAndTokenizeText(inputText));

		// replace all the textual numbers
		words = replaceTextualNumbers(words);

		// put spaces back in and return the string. Should be the same as input
		// text except from textual numbers
		return wordListToString(words);
	}

	/**
	 * Does the replacement of textual numbers, processing each word at a time
	 * and grouping them before doing the conversion
	 * 
	 * @param words
	 * @return
	 */
	private static List<String> replaceTextualNumbers(List<String> words) {

		// holds each group of textual numbers being processed together. e.g.
		// "one" or "five hundred and two"
		List<String> processingList = new LinkedList<String>();

		int i = 0;
		while (i < words.size() || !processingList.isEmpty()) {

			// caters for sentences only containing one word (that is a number)
			String word = "";
			if (i < words.size()) {
				word = words.get(i);
			}

			// strip word of all punctuation to make it easier to process
			String wordStripped = word.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();

			// 2nd condition: skip "and" words by themselves and at start of num
			if (allowedStrings.contains(wordStripped) && !(processingList.size() == 0 && wordStripped.equals("and"))) {
				words.remove(i); // remove from main list, will process later
				processingList.add(word);
			} else if (processingList.size() > 0) {
				// found end of group of textual words to process

				//if "and" is the last word, add it back in to original list
				String lastProcessedWord = processingList.get(processingList.size() - 1);
				if (lastProcessedWord.equals("and")) {
					words.add(i, "and");
					processingList.remove(processingList.size() - 1);
				}

				// main logic here, does the actual conversion
				String wordAsDigits = String.valueOf(convertWordsToNum(processingList));

				wordAsDigits = retainPunctuation(processingList, wordAsDigits);
				words.add(i, String.valueOf(wordAsDigits));

				processingList.clear();
				i += 2;
			} else {
				i++;
			}
		}

		return words;
	}

	/**
	 * Retain punctuation at the start and end of a textual number.
	 * 
	 * e.g. (seventy two) -> (72)
	 * 
	 * @param processingList
	 * @param wordAsDigits
	 * @return
	 */
	private static String retainPunctuation(List<String> processingList, String wordAsDigits) {

		String lastWord = processingList.get(processingList.size() - 1);
		char lastChar = lastWord.trim().charAt(lastWord.length() - 1);
		if (!Character.isLetter(lastChar)) {
			wordAsDigits += lastChar;
		}

		String firstWord = processingList.get(0);
		char firstChar = firstWord.trim().charAt(0);
		if (!Character.isLetter(firstChar)) {
			wordAsDigits = firstChar + wordAsDigits;
		}

		return wordAsDigits;
	}

	/**
	 * Splits up hyphenated textual words. e.g. twenty-two -> twenty two
	 * 
	 * @param sentence
	 * @return
	 */
	private static List<String> cleanAndTokenizeText(String sentence) {
		List<String> words = new LinkedList<String>(Arrays.asList(sentence.split(" ")));

		// remove hyphenated textual numbers
		for (int i = 0; i < words.size(); i++) {
			String str = words.get(i);
			if (str.contains("-")) {
				List<String> splitWords = Arrays.asList(str.split("-"));

				// just check the first word is a textual number. Caters for
				// "twenty-five," without having to strip the comma
				if (splitWords.size() > 1 && allowedStrings.contains(splitWords.get(0))) {
					words.remove(i);
					words.addAll(i, splitWords);
				}
			}

		}

		return words;
	}

	/**
	 * Creates string including spaces from a list of words
	 * 
	 * @param list
	 * @return
	 */
	private static String wordListToString(List<String> list) {
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			if (i == 0 && str != null) {
				result.append(list.get(i));
			} else if (str != null) {
				result.append(" " + list.get(i));
			}
		}

		return result.toString();
	}

	/**
	 * Logic for taking a textual number string and converting it into a number
	 * e.g. twenty five -> 25
	 * 
	 * This relies on there only being one textual number being processed. Steps
	 * prior to this deal with breaking a paragraph down into individual textual
	 * numbers, which could consist of a number of words.
	 * 
	 * @param input
	 * @return
	 */
	private static long convertWordsToNum(List<String> words) {
		long finalResult = 0;
		long intermediateResult = 0;
		for (String str : words) {
			// clean up string for easier processing
			str = str.toLowerCase().replaceAll("[^a-zA-Z\\s]", "");
			if (str.equalsIgnoreCase("zero")) {
				intermediateResult += 0;
			} else if (str.equalsIgnoreCase("one")) {
				intermediateResult += 1;
			} else if (str.equalsIgnoreCase("two")) {
				intermediateResult += 2;
			} else if (str.equalsIgnoreCase("three")) {
				intermediateResult += 3;
			} else if (str.equalsIgnoreCase("four")) {
				intermediateResult += 4;
			} else if (str.equalsIgnoreCase("five")) {
				intermediateResult += 5;
			} else if (str.equalsIgnoreCase("six")) {
				intermediateResult += 6;
			} else if (str.equalsIgnoreCase("seven")) {
				intermediateResult += 7;
			} else if (str.equalsIgnoreCase("eight")) {
				intermediateResult += 8;
			} else if (str.equalsIgnoreCase("nine")) {
				intermediateResult += 9;
			} else if (str.equalsIgnoreCase("ten")) {
				intermediateResult += 10;
			} else if (str.equalsIgnoreCase("eleven")) {
				intermediateResult += 11;
			} else if (str.equalsIgnoreCase("twelve")) {
				intermediateResult += 12;
			} else if (str.equalsIgnoreCase("thirteen")) {
				intermediateResult += 13;
			} else if (str.equalsIgnoreCase("fourteen")) {
				intermediateResult += 14;
			} else if (str.equalsIgnoreCase("fifteen")) {
				intermediateResult += 15;
			} else if (str.equalsIgnoreCase("sixteen")) {
				intermediateResult += 16;
			} else if (str.equalsIgnoreCase("seventeen")) {
				intermediateResult += 17;
			} else if (str.equalsIgnoreCase("eighteen")) {
				intermediateResult += 18;
			} else if (str.equalsIgnoreCase("nineteen")) {
				intermediateResult += 19;
			} else if (str.equalsIgnoreCase("twenty")) {
				intermediateResult += 20;
			} else if (str.equalsIgnoreCase("thirty")) {
				intermediateResult += 30;
			} else if (str.equalsIgnoreCase("forty")) {
				intermediateResult += 40;
			} else if (str.equalsIgnoreCase("fifty")) {
				intermediateResult += 50;
			} else if (str.equalsIgnoreCase("sixty")) {
				intermediateResult += 60;
			} else if (str.equalsIgnoreCase("seventy")) {
				intermediateResult += 70;
			} else if (str.equalsIgnoreCase("eighty")) {
				intermediateResult += 80;
			} else if (str.equalsIgnoreCase("ninety")) {
				intermediateResult += 90;
			} else if (str.equalsIgnoreCase("hundred")) {
				intermediateResult *= 100;
			} else if (str.equalsIgnoreCase("thousand")) {
				intermediateResult *= 1000;
				finalResult += intermediateResult;
				intermediateResult = 0;
			} else if (str.equalsIgnoreCase("million")) {
				intermediateResult *= 1000000;
				finalResult += intermediateResult;
				intermediateResult = 0;
			} else if (str.equalsIgnoreCase("billion")) {
				intermediateResult *= 1000000000;
				finalResult += intermediateResult;
				intermediateResult = 0;
			} else if (str.equalsIgnoreCase("trillion")) {
				intermediateResult *= 1000000000000L;
				finalResult += intermediateResult;
				intermediateResult = 0;
			}
		}

		finalResult += intermediateResult;
		intermediateResult = 0;
		return finalResult;
	}
}
