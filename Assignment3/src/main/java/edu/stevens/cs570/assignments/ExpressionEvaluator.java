package edu.stevens.cs570.assignments;

import java.util.EmptyStackException;
import java.util.Stack;

public class ExpressionEvaluator {
    private String str;
    private Stack<Character> stackChar;
    private Stack<Double> stackNum;

    /**
     * String Pretreatment
     * @param s
     * @return
     */
    private String preTreatment(String s){
        s=s.replaceAll("\\s+","");
        s=s.replace("**","^");
        return s;
    }

    /**
     *  Check Exceptions in String
     * @param s
     * @return
     */
    private int scanString(String s){
        s = preTreatment(s);
        // Brackets Wrong
        int count = 0;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '('){
                count++;
            }
            if(s.charAt(i) == ')'){
                count--;
                if(count < 0){
                    return -1;
                }
            }
        }
        if(count != 0){
            return -1;
        }
        // Invalid Input
        if(priority(s.charAt(0)) * priority(s.charAt(s.length()-1)) != 1){
            return -2;
        }
        for(int i=0;i<s.length()-1;i++){
            // Invalid Char
            if(priority(s.charAt(i)) == -2){
                return -3;
            }
            // Invalid Input
            if((priority(s.charAt(i)) != 1 && priority(s.charAt(i)) != 2)
                    && (s.charAt(i+1) == ')' || priority(s.charAt(i+1)) !=1)){
                return -2;
            }
            // Divisor is Zero
            if(s.charAt(i) == '/' && s.charAt(i+1) == '0'){
                int temp = i+1;
                while(s.charAt(temp) == '0' && temp<s.length()-1){
                    if(s.charAt(temp+1) == '.'){
                        return 0;
                    }
                    if(s.charAt(temp+1) == ')'){
                        return -4;
                    }
                    temp++;
                }
                return -4;
            }
        }
        return 0;
    }

    /**
     * Constructor
     *
     * @param s
     * @throws //IllegalArugumentException
     */
    public ExpressionEvaluator(String s) throws IllegalArgumentException {
        if(scanString(s) == -1){
            throw new IllegalArgumentException("Brackets Wrong");
        }
        if(scanString(s) == -2){
            throw new IllegalArgumentException("Invalid Input");
        }
        if(scanString(s) == -3){
            throw new IllegalArgumentException("Invalid Char");
        }
        if(scanString(s) == -4){
            throw new IllegalArgumentException("Divisor is Zero");
        }
        else{
            this.str = preTreatment(s);
            this.stackChar = new Stack<>();
            this.stackNum = new Stack<>();
        }
    }

    /**
     * priority of operands
     *
     * @param c
     * @return
     */
    private int priority(char c) {
        switch (c) {
            case '.':
                return 0;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '(':
            case ')':
                return 1;
            case '+':
            case '-':
                return 2;
            case '*':
            case '/':
                return 3;
            case '^':
                return 4;
        }
        return -2;
    }

    /**
     * calculate
     * @return
     */
    private double stackCharPop() throws IllegalArgumentException {
        double temp1 = 0, temp2 = 0, result=0.0;
        char c = stackChar.pop();
        boolean token =true;
        try{
            temp1=stackNum.pop();
            temp2=stackNum.pop();
        }catch (EmptyStackException e){
            token = false;
        }
        if(!token){
            throw new IllegalArgumentException("Invalid Input");
        }
        if(c == '+'){
            result = temp1+temp2;
        }
        if(c == '-'){
            result = temp2-temp1;
        }
        if(c == '*'){
            result = temp1*temp2;
        }
        if(c == '/'){
            if(temp1 == 0){
                throw new java.lang.IllegalArgumentException("Divisor is Zero");
            }
            result = temp2/temp1;
        }
        if(c == '^'){
            result = Math.pow(temp2, temp1);
        }
        stackNum.push(result);
        return result;
    }

    /**
     * @return result of evaluation
     */
    public double evaluate() throws IllegalArgumentException {
        double result = 0.0;
        double temp;
        char c;
        for (int i=0;i<str.length();i++) {
            c=str.charAt(i);
            if (Character.isDigit(c) ||
                    (priority(c) == 2 &&
                            (str.charAt(i-1) == '(' &&
                                    Character.isDigit(str.charAt(i+1))))) {
                String strTemp = new String();
                if(priority(c)==2){
                    strTemp += c;
                    i++;
                }
                while(i < str.length() && (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.')){
                    strTemp += str.charAt(i);
                    if(i == str.length()-1) break;
                    if(Character.isDigit(str.charAt(i+1))|| str.charAt(i+1) == '.'){
                        i++;
                    }else{
                        break;
                    }
                }
                boolean token = true;
                try{
                    Double.parseDouble(strTemp);
                }catch (NumberFormatException e){
                    token=false;
                }
                if(token){
                    temp = Double.valueOf(strTemp);
                    stackNum.push(temp);
                }
                else{
                    throw new IllegalArgumentException("Invalid Input");
                }
            }
            else if (c == '(') {
                stackChar.push(c);
            }
            else if (c == ')') {
                while (stackChar.peek() != '(') {
                    stackCharPop();
                }
                stackChar.pop();
            }
            else{
                while(!stackChar.empty() && priority(c) < priority(stackChar.peek())) {
                    stackCharPop();
                }
                stackChar.push(c);
            }
        }
        while (!stackChar.empty()) {
            result = stackCharPop();
        }
        while(!stackNum.empty()){
            result = stackNum.pop();
        }
        return result;
    }
     public static void main(String []args){
        ExpressionEvaluator test = new ExpressionEvaluator(".0+.4");
         System.out.println(test.evaluate());
    }
}
