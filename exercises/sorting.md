# Sorting algorithms

Implement [Bubble sort](https://en.wikipedia.org/wiki/Bubble_sort), [Quicksort](https://en.wikipedia.org/wiki/Quicksort) and [Merge sort](https://en.wikipedia.org/wiki/Merge_sort) in the `Sorting` class as indicated below. The three methods return a sorted version of the original array. The comparison between the elements of the arrays is specified with an instance of `Comparator`.

```java
class Sorting {

    public static T[] bubblesort(T[] array, Comparator<T> comparator) { ... }

    public static T[] quicksort(T[] array, Comparator<T> comparator)  { ... }

    public static T[] mergesort(T[] array, Comparator<T> comparator) { ... }

}
```

Using [jqwik](https://jqwik.net/) create a differential fuzzing strategy to test the three sorting algorithms at the same time. Create the test before any sorting implementation. Document any bug you find with the help of your tests.


**NOTE:** 
- Do not use any existing implementation, write your own code. 
- Use the provided project template as a starting point.
- In the project you can launch the tests with `mvn test`.

**Answer**

Dans un premier temps, nous avons crée un générateur d'entier (pour commencer) aléatoire entre -100 et 100.
Ce tableau sera de taille 1 à 100;
    
    @Provide
    Arbitrary<Integer[]> arrayGenerator() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-100, 100);
        return integerArbitrary
        .array(Integer[].class).ofMinSize(1).ofMaxSize(30);
    }

Puis, nous avons créé une méthode qui vérifie si le tableau est trié ou non :

    public boolean checkIsSortedObjectArrayForCustomSort(
    Integer[] array, Comparator<Integer> comparator) {
        if (comparator == null) {
        throw new IllegalArgumentException("Comparator should not be null.");
        }
        System.out.println(array);

        if (array == null || array.length <= 1) {
            return true;
        }
       return IntStream.range(0, array.length - 1)
                .noneMatch(i -> comparator.compare(array[i], array[i + 1]) > 0);
    }

Ensuite, nous avons testé chaque test qui biensûr ne passeront pas au début car les méthodes ne sont pas 
développées.


**Merge sort**

Nous avons commencé par le tri fusion, qui est un tri que nous connaissions relativement bien. Sur celui-ci, nous n'avons pas eu réellement de problème étant donné que nous l'avions déjà travaillé il y a peu de temps.
Nous n'avons donc pas trouvé de bug suite à cette méthode.

**Bubble sort**

Pour le tri bulles, nous le connaissions également hors nous avions fait une petite erreur lors de l'implémentation qui nous a provoqué des NPE et donc plusieurs minutes de déboguage. Nous avions mis :

    for (int i = array.length; i>0 ; i++) {

au lieu de 

    for (int i = array.length; i>0 ; i--) {

C'était une erreur d'inattention comme nous n'avions pas l'habitude de faire des boucles qui décrémentent.
Une autre erreur que nous avons commise est sur l'appel de la méthode changeElements(). Nous avions appelé la méthode de la manière suivante :

    changeElements(array, j+1, j, comparator);

or cet appel n'était pas correct puisque cela triait le tableau de manière décroissante ! L'appel correct est celui-ci :

     changeElements(array, j, j+1, comparator);

Pour le QuickSort, nous avons détecté plusieurs erreurs que nous avons commises.
- la première concerne l'appel de la méthode à quicksort, nous avions mis :

    
    **return quicksort(array, 0, array.length, comparator);** 

au lieu de

    **return quicksort(array, 0, array.length - 1, comparator);** 

Cela générait des erreurs.
Enfin il y avait également les appels de méthode à changeElements qui provoquait des erreurs car
elle refait une comparaison dans la méthode. Nous avons donc décidé cet appel de méthode en un échange simple
avec un temporaire. 

    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
