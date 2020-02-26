package edu.stevens.cs570.assignments.tests;

import edu.stevens.cs570.assignments.Cacheable;
import edu.stevens.cs570.assignments.SimpleFakePersister;

/**
 * Created by Ravi Varadarajan on 4/15/2018.
 */
public class TestFakePersister<K, T extends Cacheable<K>> extends SimpleFakePersister<K,T> {

    private int persistCnt = 0;
    private int retrieveCnt = 0;
    private int removeCnt = 0;

    @Override
    public void persistItem(T value) {
        super.persistItem(value);
        persistCnt += 1;
    }

    @Override
    public T getItem(K key) {
        T item = super.getItem(key);
        if (item != null) {
            retrieveCnt += 1;
        }
        return super.getItem(key);
    }

    @Override
    public T removeItem(K key) {
        removeCnt += 1;
        return super.removeItem(key);
    }

    public int getPersistCnt() {
        return persistCnt;
    }

    public int getRetrieveCnt() {
        return retrieveCnt;
    }

    public int getRemoveCnt() {
        return removeCnt;
    }

}

