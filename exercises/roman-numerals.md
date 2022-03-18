# Roman numerals

Any natural number between 0 and 3999 can be represented in *roman numerals* using the following rules:

1. Only symbols *M*, *D*, *C*, *X*, *V*, *I* can be used. Their values are shown below:

    |   M  |  D  |  C  | L  |  X | V | I |
    |------|-----|-----|----|----|---|---|
    | 1000 | 500 | 100 | 50 | 10 | 5 | 1 |

2. Symbols M, C, X, I can be repeated consecutively up to three times.
3. Symbols D, L and V can not be repeated.
4. When a symbol of lower value of appears to the right of a symbol of equal or higher value, all symbol values are added.
5. When a symbols of lower value appears to the left of a symbols of higher value, the lower value is subtracted from the higher value. Only symbols C, X, V can be subtracted. Each symbol can be subtracted only once. The subtracted symbol must be one fifth or one tenth of the larger.

*Examples:*

-    1 = I
-    4 = IV
-    8 = VIII
-    9 = IX
-   14 = XIV
-   16 = XVI
-   19 = XIX
-   99 = XCIX
-  105 = CV
- 1001 = MI
- 2289 = MMCCLXXXIX

Implement the following methods in the `RomanNumeralUtils` class:

```java
class RomanNumeralUtils {

    public static boolean isValidRomanNumeral(String value) { ... }

    public static int parseRomanNumeral(String numeral) { ... }

    public static String toRomanNumeral(int number) { ... }

}
```

Use [jqwik](https://jqwik.net/) to create property based tests verifying these three methods. Create the tests before implementing the methods. Document any bugs you found with the help of these tests during the process.

**NOTE:** 
- Do not use any existing implementation, write your own code. 
- Use the provided project template as a starting point.
- In the project you can launch the tests with `mvn test`.

**Answer** 

Afin de déterminer les caractères romains numériques valides, nous avons décider d'utiliser
les regexp. Cela permet d'avoir plus de contrôle sur les input. Globalement, nous avions confiance 
au **isValid** mais nous l'avons quand même testé avec :

- **testIsValidRomanNOKWithMultipleDDorMMorVV()** : il ne doit pas y avoir 2D, 2M ou 2V d'affilé,
- **testIsValidRomanNOKWith4MorCorXorI()** : il ne doit pas y avoir 4M ou 4C ou 4I d'affilé

Cela ne nous a pas permis de détecter la moindre erreur.

Cependant, le test de **toRomanNumeral()** avec **testIsValidRomanNumeralAfterConverting()** 
nous a fait détecter des erreurs : IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
pour un énorme nombre. Cela est dû à notre map :


        mapRomanNumerals.put(1000, "M");
        mapRomanNumerals.put(900, "CM");
        mapRomanNumerals.put(500, "D");
        mapRomanNumerals.put(400, "CD");
        mapRomanNumerals.put(100, "C");
        mapRomanNumerals.put(90, "XC");
        mapRomanNumerals.put(50, "L");
        mapRomanNumerals.put(40, "XL");
        mapRomanNumerals.put(10, "X");
        mapRomanNumerals.put(9, "IX");
        mapRomanNumerals.put(5, "V");
        mapRomanNumerals.put(4, "IV");
        mapRomanNumerals.put(1, "I");


Nous avons transformé cette map en 2 tableaux et ensuite mis à jour le code en conséquence
(parcours de map en parcours d'array) :

    int[] integers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] romanNumeral = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

