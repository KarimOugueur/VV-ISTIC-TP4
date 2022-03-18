package fr.istic.vv;

import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.*;

public class RomanNumeralTest {

    // Check if we convert a roman numeral to integer, and this integer to roman, we have
    // the same value --> this test is not really efficient because it would be some
    // problem between the conversions and have the same value at the output
    @Property
    void testRomanToIntegerAndIntegerToRomanOK(@ForAll("stringRomanGenerator") String roman) {
        if(RomanNumeralUtils.isValidRomanNumeral(roman)) {
            int integer = RomanNumeralUtils.parseRomanNumeral(roman);
            String intToRoman = RomanNumeralUtils.toRomanNumeral(integer);
            assertThat(intToRoman).isEqualTo(roman);
        }
    }


    // Check after converting to roman that it is valid
    @Property
    public boolean testIsValidRomanNumeralAfterConverting(@ForAll("arrayIntegersGenerator") int valeur) {
        return RomanNumeralUtils.isValidRomanNumeral(RomanNumeralUtils.toRomanNumeral(valeur));
    }

    // Generate integers between 1 and 3999
    @Provide
    public Arbitrary<Integer> arrayIntegersGenerator() {
        return Arbitraries.integers().between(1, 3999);
    }

    @Property
    public void testToRomanNumeralsBetween0And4000(@ForAll("stringRomanGenerator") String romanNumerals) {
        // We get valid roman numerals --> we canno't create a filter
        // because it generates errors
        if (RomanNumeralUtils.isValidRomanNumeral(romanNumerals)) {
            int value = RomanNumeralUtils.parseRomanNumeral(romanNumerals);
            assertThat(value).isBetween(0, 4000);
        }
    }

    // Generate roman numerals
    @Provide
    Arbitrary<String> stringRomanGenerator() {
        return Arbitraries.strings().withChars('M','D','C','L','X','V','I').ofMaxLength(9);
    }

    // Test with string with more than 1 D or 1 L or 1 V
    @Property
    public boolean testIsValidRomanNOKWithMultipleDDorMMorVV(@ForAll("stringRomanGeneratorWithMultipleDDorMMorVV") String romanNumeral) {
        System.out.println(romanNumeral);
        return !RomanNumeralUtils.isValidRomanNumeral(romanNumeral);
    }

    // Generate string with more than 1 D or 1 L or 1 V
    @Provide
    public Arbitrary<String> stringRomanGeneratorWithMultipleDDorMMorVV() {
        return Arbitraries.strings().ofMaxLength(10).withChars('M','C','D','L','X','V','I').filter(string -> string.matches("[MDCLXVI]*(DD|LL|VV)+[MDCLXVI]*"));
    }

    // Test roman numeral with more than 3 M
    @Property
    public boolean testIsValidRomanNOKWith4MorCorXorI(@ForAll("stringRomanGeneratorWith4MorCorXorI") String romanNumeral) {
        return !RomanNumeralUtils.isValidRomanNumeral(romanNumeral);
    }

    // Generate roman numeral with more than 3 M
    @Provide
    public Arbitrary<String> stringRomanGeneratorWith4MorCorXorI() {
        return Arbitraries.strings().ofMaxLength(10).withChars('M','C','D','L','X','V','I')
                .filter(string -> string.contains("MMMM"));
    }


}
