package edu.stevens.cs570.assignments.tests;

import edu.stevens.cs570.assignments.Cacheable;
import edu.stevens.cs570.assignments.LRUCache;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/18/19
 */
public class LRUCacheTests2 {
    private static int passCnt = 0;
    private static int points = 0;

    private static class CacheItem implements Cacheable<String> {

        private final String key, value;
        public CacheItem(String key, String value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String getKey() {
            return key;
        }
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof CacheItem)) {
                return false;
            }
            CacheItem other = (CacheItem) obj;
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
        @Override
        public String toString() {
            return "key: " + key + ",value:" + value;
        }
    }

    private static int [] scores = new int[11];

    /**
     *  Check if adding an item works when cache is not full (5 pts)
     */
    private static void test1() {
        try {
            edu.stevens.cs570.assignments.tests.TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jake Doe", "employee2");
            lruCache.putItem(item2);
            assert fakePersister.getPersistCnt() == 2;
            assert lruCache.getItem("John Doe").equals(item1);
            assert lruCache.getItem("Jake Doe").equals(item2);
            assert lruCache.getFaultRatePercent() == 0.0;
            assert fakePersister.getRemoveCnt() == 0;
            passCnt += 1;
            points += 5;
            System.out.println("\nTest1 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest1 failed\n");
        } finally {
            scores[0] = points;
        }
    }

    /**
     *  Check if adding an item works when cache is full (7 pts)
     */
    private static void test2() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(1, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            assert fakePersister.getPersistCnt() == 2;
            assert lruCache.getItem("Jane Doe").equals(item2);
            System.out.println(fakePersister.getRetrieveCnt());
            assert fakePersister.getRetrieveCnt() == 0;
            assert lruCache.getItem("John Doe").equals(item1);
            System.out.println(fakePersister.getRetrieveCnt());
            assert fakePersister.getRemoveCnt() == 0;
            try {
                System.out.println(lruCache.getFaultRatePercent());
                assert lruCache.getFaultRatePercent() > 0;
                System.out.println(fakePersister.getRetrieveCnt());
                assert fakePersister.getRetrieveCnt() == 1;
                points += 4;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            assert lruCache.getFaultRatePercent() == 50.0;
            points += 3;
            System.out.println("\nTest2 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest2 failed\n");
            passed = false;
        } finally {
            scores[1] = points - scores[0];
        }
        passCnt += passed ? 1 : 0;
    }

    /**
     *  Check if removing an item works when item is in cache (8 pts)
     */
    private static void test5() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            assert lruCache.getItem("Jane Doe").equals(item2);
            assert lruCache.getItem("Chandra Sekhar").equals(item3);
            assert lruCache.getItem("John Doe").equals(item1);
            lruCache.removeItem("John Doe");
            try {
                assert fakePersister.getRemoveCnt() == 1;
                points += 4;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            assert lruCache.getItem("John Doe") == null;
            points += 4;
            System.out.println("\nTest5 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest5 failed\n");
            passed = false;
        } finally {
            scores[2] = points - scores[0] - scores[1];
        }
        passCnt += passed ? 1 : 0;
    }

    /**
     *  Check if removing an item works when item is not in cache (7 pts)
     */
    private static void test6() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            CacheItem item4 = new CacheItem("Jane Liu", "employee4");
            lruCache.putItem(item4);
            assert lruCache.getItem("Jane Doe").equals(item2);
            assert lruCache.getItem("Chandra Sekhar").equals(item3);
            assert lruCache.getItem("John Doe").equals(item1);
            assert lruCache.getItem("Jane Liu").equals(item4);
            lruCache.removeItem("Jane Doe");
            try {
                assert fakePersister.getRemoveCnt() == 1;
                points += 4;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            assert lruCache.getItem("Jane Doe") == null;
            points += 3;
            System.out.println("\nTest6 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest6 failed\n");
            passed = false;
        } finally {
            scores[3] = points - scores[0] - scores[1] - scores[2];
        }
        passCnt += passed ? 1 : 0;;
    }

    private static boolean sameAsArray(Iterator<String> iter, String [] arr) {
        List<String> iterItems = new ArrayList<>();
        while (iter.hasNext()) {
            iterItems.add(iter.next());
        }
        return Arrays.equals(arr, iterItems.toArray());
    }

    // check if cache key iterator works correctly when items are just added (8 pts)
    private static void test7() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            int retrievalCnt = fakePersister.getRetrieveCnt();
            try {
                assert sameAsArray(lruCache.getCacheKeys(), new String[]{"Chandra Sekhar", "Jane Doe", "John Doe"});
                assert fakePersister.getRetrieveCnt() == retrievalCnt;
                points += 4;
            }  catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            CacheItem item4 = new CacheItem("Jane Liu", "employee4");
            lruCache.putItem(item4);
            retrievalCnt = fakePersister.getRetrieveCnt();
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Liu", "Chandra Sekhar", "Jane Doe"});
            assert fakePersister.getRetrieveCnt() == retrievalCnt;
            points += 4;
            System.out.println("\nTest7 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest7 failed\n");
            passed = false;
        } finally {
            scores[4] = points - scores[0] - scores[1] - scores[2] - scores[3];
        }
        passCnt += passed ? 1 : 0;
    }

    // check if cache key iterator works correctly when items are modified (8 pts)
    private static void test8() {
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            CacheItem item4 = new CacheItem("Jane Liu", "employee4");
            lruCache.putItem(item4);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Liu", "Chandra Sekhar", "Jane Doe"});
            CacheItem item5 = new CacheItem("John Doe", "employee5");
            lruCache.putItem(item5);
            int retrievalCnt = fakePersister.getRetrieveCnt();
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"John Doe", "Jane Liu", "Chandra Sekhar"});
            assert fakePersister.getRetrieveCnt() == retrievalCnt;
            passCnt += 1;
            points += 8;
            System.out.println("\nTest8 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest8 failed\n");
        } finally {
            scores[5] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4];
        }
    }

    // check if cache key iterator works correctly when items are retrieved ( 6 pts)
    private static void test9() {
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            lruCache.getItem("Jane Doe");
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Doe", "Chandra Sekhar", "John Doe"});
            lruCache.getItem("Chandra Sekhar");
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Chandra Sekhar", "Jane Doe", "John Doe"});
            passCnt += 1;
            points += 6;
            System.out.println("\nTest9 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest9 failed\n");
        } finally {
            scores[6] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4] -scores[5];
        }
    }

    // check if cache key iterator works correctly when items are removed (7 pts)
    private static void test10() {
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Chandra Sekhar", "Jane Doe", "John Doe"});
            lruCache.removeItem("Jane Doe");
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Chandra Sekhar", "John Doe"});
            passCnt += 1;
            points += 7;
            System.out.println("\nTest10 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest10 failed\n");
        } finally {
            scores[7] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4]- scores[5] - scores[6];
        }
    }

    // check if reset stats works correctly (9 pts)
    private static void test11() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(2, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Chandra Sekhar", "employee3");
            lruCache.putItem(item3);
            lruCache.getItem("Jane Doe");
            lruCache.getItem("John Doe");
            try {
                assert lruCache.getFaultRatePercent() > 0;
                points += 3;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            try {
                assert lruCache.getFaultRatePercent() == 50.0;
                points += 1;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            lruCache.resetFaultRateStats();
            lruCache.getItem("Chandra Sekhar");
            lruCache.getItem("Jane Doe");
            try {
                assert lruCache.getFaultRatePercent() > 0;
                points += 3;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            try {
                assert lruCache.getFaultRatePercent() == 100.0;
                points += 2;
            } catch (Throwable t) {
                t.printStackTrace();
                passed = false;
            }
            System.out.println("\nTest11 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest11 failed\n");
            passed = false;
        } finally {
            scores[8] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4]- scores[5] - scores[6] - scores[7];
        }
        passCnt += passed ? 1 : 0;
    }

    // check if modify size works correctly when size increases (3 pts)
    private static void test12() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(2, fakePersister);
            CacheItem item1 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("Jane Doe", "employee1");
            lruCache.putItem(item2);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Doe", "John Doe"});
            lruCache.modifySize(3);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Doe", "John Doe"});
            points += 3;
            System.out.println("\nTest12 passed\n");
        } catch (Throwable t) {
            t.printStackTrace();
            passed = false;
            System.out.println("\nTest12 failed\n");
        } finally {
            scores[9] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4]- scores[5] - scores[6] - scores[7] -scores[8];
        }
        passCnt += passed ? 1 : 0;
    }

    // check if modify size works correctly when size decreases ( 7 pts)
    private static void test13() {
        boolean passed = true;
        try {
            TestFakePersister<String, CacheItem> fakePersister = new TestFakePersister<>();
            LRUCache<String, CacheItem> lruCache = new LRUCache<>(3, fakePersister);
            CacheItem item1 = new CacheItem("Jake Doe", "employee1");
            lruCache.putItem(item1);
            CacheItem item2 = new CacheItem("John Doe", "employee1");
            lruCache.putItem(item2);
            CacheItem item3 = new CacheItem("Jane Doe", "employee2");
            lruCache.putItem(item3);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Doe", "John Doe", "Jake Doe"});
            lruCache.modifySize(2);
            assert sameAsArray(lruCache.getCacheKeys(), new String [] {"Jane Doe", "John Doe"});
            points += 7;
            System.out.println("\nTest13 passed\n");
        } catch (Throwable t) {
            t.printStackTrace();
            passed = false;
            System.out.println("\nTest13 failed\n");
        } finally {
            scores[10] = points - scores[0] - scores[1] - scores[2] - scores[3] - scores[4]- scores[5] - scores[6] - scores[7] - scores[8] - scores[9];
        }
        passCnt += passed ? 1 : 0;
    }


    public static void main(String [] args) {
        test1();
        test2();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
        test13();
        System.out.println(String.format("%d tests out of 11 passed",passCnt));
        System.out.println(String.format("total test points=%d",points));
        System.out.println("Total Scores : " + Arrays.toString(new int [] {5,7,8,7,8,8,6,7,9,3,7}));
        System.out.println("Scores : " +Arrays.toString(scores));
    }
}

