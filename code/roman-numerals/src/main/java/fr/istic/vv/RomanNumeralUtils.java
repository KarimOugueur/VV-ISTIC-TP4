package fr.istic.vv;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNumeralUtils {

    /**
     * Check if a roman numeral is valid
     *
     * @param value : value of roman numeral
     * @return true if it is valid else false
     */
    public static boolean isValidRomanNumeral(String value) {
        if (value.isBlank()) return false;
        // pattern matching
        String regexp = "^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * Convert roman to integer
     *
     * @param numeral : roman numeral
     * @return the value of the roman numeral in integer
     */
    public static int parseRomanNumeral(String numeral) {
        // init a map with all possibles characters and values
        Map<Character, Integer> mapRomanInt = new HashMap<>();
        mapRomanInt.put('I', 1);
        mapRomanInt.put('V', 5);
        mapRomanInt.put('X', 10);
        mapRomanInt.put('L', 50);
        mapRomanInt.put('C', 100);
        mapRomanInt.put('D', 500);
        mapRomanInt.put('M', 1000);

        int result = 0;

        // Walk into the string
        for (int i = 0; i < numeral.length(); i++) {
            char ch = numeral.charAt(i);

            // Check if we add or substract
            if (i > 0 && mapRomanInt.get(ch) > mapRomanInt.get(numeral.charAt(i - 1))) {
                result += mapRomanInt.get(ch) - 2 * mapRomanInt.get(numeral.charAt(i - 1));
            } else
                result += mapRomanInt.get(ch);
        }

        return result;
    }

    /**
     * Convert integer to roman numeral
     *
     * @param number : the number to convert
     * @return roman string
     */
    public static String toRomanNumeral(int number) {
        int[] integers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumeral = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < integers.length; i++) {
            while (number >= integers[i]) {
                number -= integers[i];
                sb.append(romanNumeral[i]);
            }
        }
        return sb.toString();
    }

}
