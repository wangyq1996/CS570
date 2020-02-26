package edu.stevens.cs570.assignments;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class LRUCache<K,T extends Cacheable<K>> {

    private final List<T> lruList;
    private int size;
    private Persister persister;
    private double hit, all,faultRate;


    private class CacheKeyIterator<K> implements Iterator<K> {
        int count = 0;

        @Override
        public boolean hasNext() {
            return (count<lruList.size());
        }

        @Override
        public K next() {
            K key=(K) lruList.get(count).getKey();
            count++;
            return key;
        }
    }

    public LRUCache(int size, Persister<? extends K, ? extends T> persister) {
        lruList = new LinkedList<>();
        this.size = size;
        this.persister = persister;
        hit =0.0;
        all =0.0;
        faultRate = 0.0;
    }

    /**
     *
     * Worst Case:O(n);
     * Best Case:O(1);
     */
    public void modifySize(int newSize) {
        if(newSize < 0){
            return;
        }
        if(newSize >= this.size){
            this.size=newSize;
        }
        else{while(newSize < lruList.size()){
            lruList.remove(lruList.size()-1);
            }
            this.size=newSize;
        }
    }

    /**
     * Worst Case:O(n^2)
     * Best Case:O(1)
     */
    public T getItem(K key) {
        T item = null;
        Iterator<K> ck=getCacheKeys();
        int count =0;
        //Read Hit
        while(ck.hasNext()){
            count++;
            if(ck.next().equals(key)){
                item= lruList.get(count-1);
                lruList.remove(count-1);
                lruList.add(0,item);
                hit++;
                all++;
                return item;
            }
        }
        //Read Not Hit
        if(item == null ){
            item = (T)persister.getItem(key);
            putItemInCache(item);
            all++;
        }
        return item;
    }

    /**
     * Worst Case:O(n^2)
     * Best Case:O(1)
     */
    public void putItem(T item) {
        if(item == null){
            return;
        }
        K key = item.getKey();
        Iterator<K> ck=getCacheKeys();
        //Set New
        if(persister.getItem(key) == null){
            persister.persistItem(item);
            putItemInCache(item);
        }
        //Not New
        else{
                int count=0;
                // Write Hit
                while(ck.hasNext()){
                    count++;
                    if(ck.next().equals(key)){
                            lruList.remove(count-1);
                            lruList.add(0,item);
                            persister.removeItem(key);
                            persister.persistItem(item);
                            hit++;
                            all++;
                            return;
                        }
                    }
                //Write not hit
                persister.removeItem(key);
                persister.persistItem(item);
                putItemInCache(item);
                all++;
        }
    }

    private void putItemInCache(T item){
        if (lruList.size() < size) {
            lruList.add(0,item);
        }
        else{
            lruList.remove(size-1);
            lruList.add(0,item);
        }
    }//Personal Function

    /**
     * Worst Case:O(n^2)
     * Best Case:O(1)
     */
    public T removeItem(K key) {
        T item = null;
        Iterator<K> ck= getCacheKeys();
        int count=0;
        while(ck.hasNext()) {
            count++;
            if (ck.next().equals(key)) {
                item = lruList.get(count-1);
                lruList.remove(count-1);
                persister.removeItem(key);
            }
        }
        if (item == null && persister.getItem(key) != null) {
            item = (T) persister.getItem(key);
            persister.removeItem(key);
        }
        return item;
    }

    public Iterator<K> getCacheKeys() {
        return new CacheKeyIterator<>();
    }

    public double getFaultRatePercent() {
        if(all == 0.0){
            faultRate = 0.0;
        }
        faultRate = (all-hit)*100/all;
        return faultRate;//exception
    }

    public void resetFaultRateStats() {
        hit=0.0;
        all=0.0;
        faultRate = 0.0;
    }

}
