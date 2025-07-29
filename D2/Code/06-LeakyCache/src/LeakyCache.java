import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class LeakyCache {
    private final Map<String, byte[]> cache = new WeakHashMap<>();

    public void put(String key, byte[] value) {
        cache.put(key, value);
    }

    public byte[] get(String key) {
        return cache.get(key);
    }
}
