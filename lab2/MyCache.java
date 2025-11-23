package lab2;

public class MyCache {
    private static class CacheEntry {
        Object key;
        Object value;
        long createdAt;
        long ttl; // time to live in millis

        CacheEntry(Object key, Object value, long ttl) {
            this.key = key;
            this.value = value;
            this.ttl = ttl;
            this.createdAt = System.currentTimeMillis();
        }

        boolean isExpired() {
            return (System.currentTimeMillis() - createdAt) > ttl;
        }
    }

    private final int capacity;
    // Використовуємо наш MyLinkedList для зберігання записів
    private MyLinkedList entries; 

    public MyCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.entries = new MyLinkedList();
    }

    public void put(Object key, Object value, long ttlMillis) {
        if (key == null || value == null) {
            throw new NullPointerException("Key or Value cannot be null in Cache");
        }

        // 1. Перевіряємо, чи існує ключ, і видаляємо його (оновлення)
        remove(key);

        // 2. Перевіряємо розмір (Eviction policy)
        if (entries.size() >= capacity) {
            // Видаляємо найперший елемент (найстаріший доданий - FIFO eviction для спрощення)
            // Для LRU треба було б переміщати елементи при get()
            entries.remove(0);
            System.out.println("Eviction: Cache full, removed oldest entry.");
        }

        // 3. Додаємо новий
        entries.add(new CacheEntry(key, value, ttlMillis));
    }

    public Object get(Object key) {
        if (key == null) throw new NullPointerException("Key cannot be null");

        for (int i = 0; i < entries.size(); i++) {
            CacheEntry entry = (CacheEntry) entries.get(i);
            
            // Використовуємо equals для порівняння ключів
            if (key.equals(entry.key)) {
                if (entry.isExpired()) {
                    entries.remove(i); // Expiry policy
                    System.out.println("Expiry: Entry for '" + key + "' expired.");
                    return null;
                }
                // (Опціонально) Для LRU тут треба перемістити entry в кінець списку
                return entry.value;
            }
        }
        return null;
    }

    public void remove(Object key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        for (int i = 0; i < entries.size(); i++) {
            CacheEntry entry = (CacheEntry) entries.get(i);
            if (key.equals(entry.key)) {
                entries.remove(i);
                return;
            }
        }
    }

    public int size() {
        // Очистка прострочених елементів при запиті розміру (lazy cleanup)
        cleanup();
        return entries.size();
    }

    // Метод для очистки прострочених записів
    public void cleanup() {
        for (int i = entries.size() - 1; i >= 0; i--) {
            CacheEntry entry = (CacheEntry) entries.get(i);
            if (entry.isExpired()) {
                entries.remove(i);
            }
        }
    }
    
    @Override
    public String toString() {
        cleanup();
        StringBuilder sb = new StringBuilder("Cache content: {");
        for (int i = 0; i < entries.size(); i++) {
             CacheEntry entry = (CacheEntry) entries.get(i);
             sb.append(entry.key).append("=").append(entry.value);
             if (i < entries.size() - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
