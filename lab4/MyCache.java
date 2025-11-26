package lab4;

import java.io.Serializable;

public class MyCache<K, V> {

    private static class Entry<K, V> implements Serializable {
        K key;
        V value;
        long creationTime;
        long ttl;

        Entry(K key, V value, long ttl) {
            this.key = key;
            this.value = value;
            this.ttl = ttl;
            this.creationTime = System.currentTimeMillis();
        }

        boolean isExpired() {
            return (System.currentTimeMillis() - creationTime) > ttl;
        }
    }

    private final int capacity;
    private final MyLinkedList<Entry<K, V>> entries;

    public MyCache(int capacity) {
        this.capacity = capacity;
        this.entries = new MyLinkedList<>();
    }

    public void put(K key, V value, long ttl) {
        if (key == null || value == null) {
            throw new NullPointerException("Key or Value cannot be null");
        }

        remove(key);

        if (entries.size() >= capacity) {
            entries.remove(0);
            System.out.println("Eviction: Cache full, oldest entry removed.");
        }

        entries.add(new Entry<>(key, value, ttl));
    }

    public V get(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        for (int i = 0; i < entries.size(); i++) {
            Entry<K, V> entry = entries.get(i);
            
            if (key.equals(entry.key)) {
                if (entry.isExpired()) {
                    entries.remove(i);
                    System.out.println("Expiry: Key '" + key + "' expired.");
                    return null;
                }
                return entry.value;
            }
        }
        return null;
    }

    public void remove(K key) {
        if (key == null) throw new NullPointerException("Key cannot be null");
        
        for (int i = 0; i < entries.size(); i++) {
            Entry<K, V> entry = entries.get(i);
            if (key.equals(entry.key)) {
                entries.remove(i);
                return;
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cache{");
        for (int i = 0; i < entries.size(); i++) {
            Entry<K, V> e = entries.get(i);
            if (!e.isExpired()) {
                sb.append(e.key).append("=").append(e.value);
                if (i < entries.size() - 1) sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
