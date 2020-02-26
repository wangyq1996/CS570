package edu.stevens.cs570.assignments;

//import edu.stevens.cs570.assignments.SlotMachine;
import edu.stevens.cs570.assignments.SlotMachine.Symbol;

/**
 * Created by Ravi Varadarajan on 2/6/2018.
 */
public class SlotMachineTest {

    private static int passCnt = 0;
    private static int score = 0;

    // test getSymbolForAReel for non-probabilistic case
    private static void Test1()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 25;
            double [] odds = new double [] { 0.0, 1.0, 0.0, 0.0, 0.0};
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            assert sm.getSymbolForAReel() == SlotMachine.Symbol.FLOWERS;
            passCnt++;
            score += 5;
            System.out.println("\nTest1 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest1 failed\n");
        }
    }

    // test getSymbolForAReel for probabilistic case
    private static void Test2()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 25;
            double [] odds = new double [] { 0.6, 0.1, 0.1, 0.1, 0.1};
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            int [] freqCnt = new int[SlotMachine.Symbol.values().length];
            int nTrials = 1000;
            for (int i=0; i < nTrials; i++) {
                freqCnt[sm.getSymbolForAReel().ordinal()] += 1;
            }
            double allowedError = 0.05;
            for (int i=0; i < Symbol.values().length; i++) {
                assert Math.abs(((double)freqCnt[i])/nTrials - odds[i]) <= allowedError;
            }
            passCnt++;
            score += 12;
            System.out.println("\nTest2 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest2 failed\n");
        }
    }

    // test 0 payout case
    private static void Test3()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 25;
            double [] odds = new double [] { 0.2, 0.2, 0.2, 0.2, 0.2 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            Symbol [] symbols = new Symbol [] { Symbol.FLOWERS,
                    Symbol.FLOWERS, Symbol.BELLS, Symbol.FRUITS, Symbol.HEARTS};
            assert sm.calcPayout(symbols, 5) == 0;
            passCnt++;
            score += 5;
            System.out.println("\nTest3 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest3 failed\n");
        }
    }

    // test payout for at least 1/2 of matched symbols for quarter machine
    private static void Test4()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 25;
            int wagerUnits = 4;
            double [] odds = new double [] { 0.3, 0.2, 0.2, 0.2, 0.1 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            Symbol [] symbols = new Symbol [] { Symbol.HEARTS,
                    Symbol.FLOWERS, Symbol.BELLS, Symbol.BELLS, Symbol.BELLS};
            long val = sm.calcPayout(symbols, wagerUnits * wagerUnitValue);
            assert val == 10 * wagerUnitValue * wagerUnits;
            passCnt++;
            score += 10;
            System.out.println("\nTest4 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest4 failed\n");
        }
    }

    // test payout for all matched symbols for half-dollar machine
    private static void Test5()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 50;
            int wagerUnits = 8;
            double [] odds = new double [] { 0.3, 0.2, 0.2, 0.2, 0.1 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            Symbol [] symbols = new Symbol [] { Symbol.FRUITS,
                    Symbol.FRUITS, Symbol.FRUITS, Symbol.FRUITS, Symbol.FRUITS};
            assert sm.calcPayout(symbols, wagerUnits * wagerUnitValue) == 3 * 2 * wagerUnitValue * wagerUnits;
            passCnt++;
            score += 8;
            System.out.println("\nTest5 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest5 failed\n");
        }
    }

    // test pay out percent for one lever pull
    private static void Test6()  {
        try {
            int numReels = 5;
            int wagerUnitValue = 50;
            int wagerUnits = 8;
            double [] odds = new double [] { 0.0, 0.0, 0.0, 0.0, 1.0 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            sm.pullLever(wagerUnits);
            assert sm.getPayoutPercent() == 2 * Symbol.SPADES.getPayoutFactor() * 100;
            passCnt++;
            score += 10;
            System.out.println("\nTest6 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest6 failed\n");
        }
    }

    // test pay out percent for multiple lever pulls
    private static void Test7()  {
        try {
            int numReels = 4;
            int wagerUnitValue = 50;
            int wagerUnits = 8;
            double [] odds = new double [] { 0.0, 0.0, 0.0, 0.8, 0.2 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            int nTrials = 5000;
            for (int i=0; i < nTrials; i++) {
                sm.pullLever(wagerUnits);
            }
            double heartsPayoutFactor = Symbol.HEARTS.getPayoutFactor();
            double spadesPayoutFactor = Symbol.SPADES.getPayoutFactor();
            // use binomial probabilities
            double expectedPayoutFactor = Math.pow(0.2,4) * 2 * spadesPayoutFactor +
                    4 * Math.pow(0.2,3) * 0.8 * spadesPayoutFactor +
                    4 * Math.pow(0.8,3) * 0.2 * heartsPayoutFactor +
                    Math.pow(0.8,4) * 2 * heartsPayoutFactor;
            double allowedError = 5;
            System.out.println("Payout % = "+sm.getPayoutPercent());
            System.out.println("Expected Payout % ="+ expectedPayoutFactor * 100);
            assert Math.abs(sm.getPayoutPercent() - expectedPayoutFactor * 100 ) <= allowedError;
            passCnt++;
            score += 15;
            System.out.println("\nTest7 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest7 failed\n");
        }
    }

    // test reset
    private static void Test8()  {
        try {
            int numReels = 4;
            int wagerUnitValue = 50;
            int wagerUnits = 8;
            double [] odds = new double [] { 0.0, 0.0, 0.0, 0.8, 0.2 };
            SlotMachine sm = new SlotMachine(numReels, odds, wagerUnitValue);
            int nTrials = 5000;
            for (int i=0; i < nTrials; i++) {
                sm.pullLever(wagerUnits);
            }
            double heartsPayoutFactor = Symbol.HEARTS.getPayoutFactor();
            double spadesPayoutFactor = Symbol.SPADES.getPayoutFactor();
            double expectedPayoutFactor = Math.pow(0.2,4) * 2 * spadesPayoutFactor +
                    4 * Math.pow(0.2,3) * 0.8 * spadesPayoutFactor +
                    4 * Math.pow(0.8,3) * 0.2 * heartsPayoutFactor +
                    Math.pow(0.8,4) * 2 * heartsPayoutFactor;
            double allowedError = 5;
            System.out.println("Payout % = "+sm.getPayoutPercent());
            System.out.println("Expected Payout % ="+ expectedPayoutFactor * 100);
            // assert Math.abs(sm.getPayoutPercent() - expectedPayoutFactor * 100 ) <= allowedError;
            sm.reset();
            assert sm.getPayoutPercent() == 0;
            for (int i=0; i < nTrials; i++) {
                sm.pullLever(wagerUnits);
            }
            //assert Math.abs(sm.getPayoutPercent() - expectedPayoutFactor * 100 ) <= allowedError;
            passCnt++;
            score += 15;
            System.out.println("\nTest8 passed\n");
        } catch(Throwable t) {
            t.printStackTrace();
            System.out.println("\nTest8 failed\n");
        }
    }


    public static void main(String [] args) throws Exception {
        Test1();
        Test2();
        Test3();
        Test4();
        Test5();
        Test6();
        Test7();
        Test8();
        System.out.println(passCnt + " tests passed");
        System.out.println("Total score="+score);
    }
}

