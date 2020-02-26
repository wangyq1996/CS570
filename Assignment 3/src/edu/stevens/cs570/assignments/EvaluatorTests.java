package edu.stevens.cs570.assignments;

import java.util.Scanner;

public class EvaluatorTests {
    public void Test1(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("11+3**2");
            assert Test1.evaluate() == 20;
            System.out.println("Test1 Passed");
        }catch (Throwable e){
            System.out.println("Test1 Failed");
        }
    }
    public void Test2(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("(12.34+34.56*0.66)**0.8/123");
            //System.out.println(Test1.evaluate());
            assert Test1.evaluate() == 0.140226856440992;
            System.out.println("Test2 Passed");
        }catch (Throwable e){
            System.out.println("Test2 Failed");
        }
    }
    public void Test3(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("123*32/0");
        }catch (IllegalArgumentException e) {
            try {
                assert e.getMessage() == "Divisor is Zero";
                System.out.println("Test3 passed");
            }catch (Throwable e1) {
                System.out.println("Test3 failed");
            }
        }
    }
    public void Test4(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("123*32/0.1");
            assert Test1.evaluate() == 39360;
            System.out.println("Test4 passed");
        }catch (IllegalArgumentException e){
            System.out.println("Test4 failed");
        } catch (Throwable e1){
            System.out.println("Test4 failed");
        }
    }
    public void Test5(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("-12*2");
            System.out.println(Test1.evaluate());
        }catch (IllegalArgumentException e){
            try{
                assert e.getMessage() == "Invalid Input";
                System.out.println("Test5 passed");
            }catch (Throwable e1){
                System.out.println("Test5 failed");
            }
        }
    }
    public void Test6(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("123*4r4-2");
        }catch (IllegalArgumentException e){
            try{
                assert e.getMessage() == "Invalid Char";
                System.out.println("Test6 passed");
            } catch (Throwable e1) {
                System.out.println("Test6 failed");
            }
        }
    }
    public void Test7(){
        try{
            ExpressionEvaluator Test1 = new ExpressionEvaluator("((123*44)-2");
        }catch (IllegalArgumentException e){
            try{
                assert e.getMessage() == "Brackets Wrong";
                System.out.println("Test7 passed");
            }catch (Throwable e1){
                System.out.println("Test7 failed");
            }
        }
    }
    public void Test8() {
        try {
            ExpressionEvaluator Test1 = new ExpressionEvaluator("(1.234**(+12.3)/(-3.54))");
            //System.out.println(Test1.evaluate());
            assert Test1.evaluate() == -3.7512257614709226;
            System.out.println("Test8 passed");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Test8 failed1");
        } catch (Throwable e1) {
            System.out.println("Test8 failed2");
        }
    }
    public void Test9() {
        try {
            ExpressionEvaluator Test1 = new ExpressionEvaluator("2**3**4");
            //System.out.println(Test1.evaluate());
            assert Test1.evaluate() == 2.4178516392292583E24;
            System.out.println("Test9 passed");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Test9 failed1");
        } catch (Throwable e1) {
            System.out.println("Test9 failed2");
        }
    }
    public static void main(String []args) {
        EvaluatorTests test = new EvaluatorTests();
        test.Test1();
        test.Test2();
        test.Test3();
        test.Test4();
        test.Test5();
        test.Test6();
        test.Test7();
        test.Test8();
        test.Test9();
    }
}

