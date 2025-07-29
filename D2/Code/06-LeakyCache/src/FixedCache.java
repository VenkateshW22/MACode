// FixedCache.java
import java.util.Map;
import java.util.WeakHashMap;

public class FixedCache {
    // Use a WeakHashMap to allow GC to collect unreferenced keys.
    private final Map<String, byte[]> cache = new WeakHashMap<>();

    public void put(String key, byte[] value) {
        cache.put(key, value);
    }

    public byte[] get(String key) {
        return cache.get(key);
    }
}