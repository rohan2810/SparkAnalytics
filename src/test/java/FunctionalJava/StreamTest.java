package FunctionalJava;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class StreamTest {

    /*
    Custom collector
     */

//    Collector<String, ?, LinkedList<String>> toLinkedList =
//            Collector.of(LinkedList::new, LinkedList::add,
//                    (first, second) -> {
//                        first.addAll(second);
//                        return first;
//                    });
//
//    LinkedList<String> linkedListOfPersons =
//            productList.stream().collect(toLinkedList);

    private Map<String, String> books;

    @BeforeTest
    public void setup() {
        books = new HashMap<>();
        books.put("978-0201633610", "Design patterns : elements of reusable object-oriented software");
        books.put("978-1617291999", "Java 8 in Action: Lambdas, Streams, and functional-style programming");
        books.put("978-0134685991", "Effective Java");
    }

    @Test
    public void singleOptionalEntry() {
        Optional<String> optionalIsbn = books.entrySet().stream()
                .filter(e -> "Effective Java".equals(e.getValue()))
                .map(Map.Entry::getKey).
                        findFirst();

        assertEquals("978-0134685991", optionalIsbn.get());
    }


    @Test
    public void multipleEntries() {
        books.put("978-0321356680", "Effective Java: Second Edition");

        List<String> isbnCodes = books.entrySet().stream()
                .filter(e -> e.getValue().startsWith("Effective Java"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        assertTrue(isbnCodes.contains("978-0321356680"));
        assertTrue(isbnCodes.contains("978-0134685991"));
    }


    @Test
    public void streamFilter() throws Exception {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> result = list.stream().
                filter(num -> num < 4).
                findAny();
        assertTrue(result.isPresent());
    }

    @Test
    public void forloop() {

        List<Integer> numbers
                = Arrays.asList(11, 22, 33, 44,
                55, 66, 77, 88,
                99, 100);
        //for loop
        System.out.println("regular for loop");
        for (Integer integer : numbers) {
            System.out.print(integer + 1 + " ");
        }
        System.out.println();
        System.out.println("using for each");
//        // functional
        numbers.forEach(number -> System.out.print(number + 1 + " "));

        System.out.println();
        System.out.println("using stream");
//        //using stream
        numbers.stream().map(number -> number + 1).forEach(number -> System.out.print(number + " "));


    }
}
