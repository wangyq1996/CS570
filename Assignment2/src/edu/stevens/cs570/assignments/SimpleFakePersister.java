package edu.stevens.cs570.assignments;

import java.util.HashMap;
import java.util.Map;


public class SimpleFakePersister<K,T extends Cacheable<K>> implements Persister<K,T> {

    private Map<K,T> localMap = new HashMap<>();

    @Override
    public void persistItem(T item) {
        localMap.put(item.getKey(), item);
    }

    @Override
    public T getItem(K key) {
        return localMap.get(key);
    }

    @Override
    public T removeItem(K key) {
        return localMap.remove(key);
    }
}
