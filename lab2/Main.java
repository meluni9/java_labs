package lab2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Test MyArrayList ===");
        MyList arrayList = new MyArrayList();
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add(1, "C++");
        System.out.println(arrayList);
        System.out.println("Get index 1: " + arrayList.get(1));
        arrayList.remove(0);
        System.out.println("After remove(0): " + arrayList);

        System.out.println("\n=== Test MyLinkedList ===");
        MyList linkedList = new MyLinkedList();
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);
        linkedList.set(1, 99);
        System.out.println(linkedList); 
        
        System.out.println("\n=== Test MyLinkedHashSet ===");
        MyLinkedHashSet set = new MyLinkedHashSet();
        set.add("Apple");
        set.add("Banana");
        set.add("Apple"); 
        set.add("Cherry");
        System.out.println(set); 

        System.out.println("\n=== Test MyCache ===");
        MyCache cache = new MyCache(2); 
        
        cache.put("User1", "Oleg", 1000); 
        cache.put("User2", "Ivan", 5000); 
        System.out.println(cache);
        
        cache.put("User3", "Maria", 5000); 
        System.out.println("After adding User3 (eviction expected): " + cache);

        System.out.println("Waiting for expiry (1.5 sec)...");
        Thread.sleep(1500);
        System.out.println("Get User1 (should be null/expired): " + cache.get("User1"));
        System.out.println("Get User2 (should exist): " + cache.get("User2"));
    }
}
