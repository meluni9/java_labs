package lab4;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. Generics Type Safety ===");
        MyList<String> stringList = new MyArrayList<>();
        stringList.add("Hello");
        stringList.add("Generics");
        // String val = stringList.get(0);
        System.out.println("String List: " + stringList);


        System.out.println("\n=== 2. Wildcard Usage (? extends E) ===");
        MyList<Number> numberList = new MyArrayList<>();
        numberList.add(10.5); 
        numberList.add(100);

        MyList<Integer> integerList = new MyLinkedList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        numberList.addAll(integerList); 

        System.out.println("Number List after adding Integers: " + numberList);

        System.out.println("\n=== 3. Generic Cache <Integer, String> ===");
        MyCache<Integer, String> userCache = new MyCache<>(2);
        
        userCache.put(1, "Oleg", 1000);
        userCache.put(2, "Ivan", 5000);

        System.out.println(userCache);
        
        Thread.sleep(1500);
        System.out.println("Get ID 1 (expired): " + userCache.get(1));
        System.out.println("Get ID 2 (valid): " + userCache.get(2));
    }
}
