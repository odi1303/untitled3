package org.example;

import java.util.regex.Pattern;

public class ArithmeticApp {
    public static String main(int base, String expression) {
        expression=expression.replace(" ","");
        // Check for division by 0
        if (base != 2) {
            if (expression.contains("/0")) {

                return "Error: trying to divide by 0 (evaluated: \"0\")";
            }
        }
        else {
            if (expression.contains("/00000000")) {
                return "Error: trying to divide by 0 (evaluated: \"0\")";
            }
        }
        String result="";//the string for the result
        if(!legalExpression(expression,base)) {
            return "Error: invalid expression";
        }
        if(expression.indexOf('^')!=-1 || expression.indexOf('&')!=-1 ||expression.indexOf('~')!=-1
                || expression.indexOf('|')!=-1){
            result=binaricOperation(expression);
        }
        if(isArithmetic(expression)){
            result = arithmeticSolver(base,expression);
        }

        return result;

    }
    //input: the given base output:true if the base is legal and false otherwise
    public static boolean checkBase(int base){
        if (base == 2 || base == 8 || base == 10 || base == 16)
            return true;
        return false;
    }

    public static boolean legalExpression(String expression, int base) {
        if ( expression.trim().isEmpty()|| expression.isBlank()) {
            return false;
        }
        String pattern;
        if (base == 10) {
            // Base 10: Decimal digits (0-9)
            pattern = "[\\s]*[+-]?\\d+([\\s]*[+\\-*/][\\s]*\\d+)*[\\s]*";
            if(Pattern.matches(pattern, expression))
                return true;
        }
        else if (base == 8) {
            // Base 8: Octal digits (0-7)
            pattern = "[\\s]*[+-]?[0-7]+([\\s]*[+\\-*/][\\s]*[0-7]+)*[\\s]*";
            if(Pattern.matches(pattern, expression))
                return true;
        }
        else if (base == 16) {
            // Base 16: Hexadecimal digits (0-9, A-F, a-f)
            pattern = "[\\s]*[+-]?[0-9A-F]+([\\s]*[+\\-*/][\\s]*[0-9A-F]+)*[\\s]*";
            if(Pattern.matches(pattern, expression))
                return true;
        }
        else if (base == 2) {
            String pattern1="([01]{8})(\\s*[&|^]\\s*~?[01]{8})*";
            pattern="([01]{8})(\\s*[/*+]\\s*-?[01]{8})*";
            if(!Pattern.matches(pattern1, expression) && !Pattern.matches(pattern, expression))
                return false;
            String[] tokens = expression.split("[+\\-*/|~^&\\s]+");
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].length()!=8)
                    return false;
            }
            return true;
        }
        else {
            // Unsupported base
            return false;
        }

        // Validate the expression
        return Pattern.matches(pattern, expression);


    }

    public static boolean isArithmetic(String expression){
        for (int i=0;i<expression.length();i++){
            char nextLeter = expression.charAt(i);
            if ((nextLeter == '*') || (nextLeter == '/') || (nextLeter == '+') || (nextLeter == '-')){
                return true;
            } else if ((nextLeter == '&') || (nextLeter == '|') || (nextLeter == '^') || (nextLeter == '~')) {
                return false;
            }
        }
        return true;
    }

    public static String arithmeticSolver(int base, String expression){
        String newExpression= expression.replace("*", "#*#");
        newExpression= newExpression.replace("/", "#/#");
        newExpression= newExpression.replace("+", "#+#");
        newExpression= newExpression.replace("-", "#-#");
        String[] unCleanSplitedExpretion = newExpression.split("#");
        String[] splitedExpretion = new String[unCleanSplitedExpretion.length-1];
        if(unCleanSplitedExpretion[0].isEmpty()){
            System.arraycopy(unCleanSplitedExpretion,1,splitedExpretion,0,unCleanSplitedExpretion.length-1);
        }else{
            splitedExpretion = unCleanSplitedExpretion;
        }
        String[] newList = new String[splitedExpretion.length];
        int j = 0;
        for(int i=0; i<splitedExpretion.length; i++){
            if((!splitedExpretion[i].equals("*")) && (!splitedExpretion[i].equals("/"))){
                newList[j] = splitedExpretion[i];
                j++;
            }else{
                int minusOrPlus = 0;
                if(splitedExpretion[i+1].equals("-") || splitedExpretion[i+1].equals("+")){
                    minusOrPlus = 1;
                }
                int isMinus = 1;
                if (splitedExpretion[i+1].equals("-")){
                    isMinus = -1;
                }
                if(splitedExpretion[i].equals("*")) {
                    if(base == 2){
                        newList[j - 1] = Integer.toString(binariToInt(newList[j - 1]) * binariToInt(splitedExpretion[i+1+minusOrPlus])*isMinus, base);
                    }else{
                        newList[j - 1] = Integer.toString(Integer.parseInt(newList[j - 1], base) * Integer.parseInt(splitedExpretion[i+1+minusOrPlus], base)*isMinus,base);
                    }
                } else{
                    if(base == 2){
                        newList[j - 1] = Integer.toString(binariToInt(newList[j - 1]) / binariToInt(splitedExpretion[i+1+minusOrPlus])*isMinus, base);
                    }else{
                        newList[j - 1] = Integer.toString(Integer.parseInt(newList[j - 1], base) / Integer.parseInt(splitedExpretion[i+1+minusOrPlus], base)*isMinus,base);
                    }

                }
                i++;
                if(minusOrPlus == 1){
                    i++;
                }
            }
        }
        int totalSum = 0;
        if (newList[0].equals("-")){
            totalSum = -Integer.parseInt(newList[1],base);
        } else if (newList[0].equals("+")) {
            totalSum = Integer.parseInt(newList[1],base);
        }else{
            totalSum = Integer.parseInt(newList[0],base);
        }

        for(int i = 1; i < j;i++){ //can skip zero becouse we added that string to the titalSum.
            if(newList[i].equals("+")){
                totalSum += Integer.parseInt(newList[i+1],base);
            }
            if(newList[i].equals("-")){
                totalSum -= Integer.parseInt(newList[i+1],base); // make sure there are no -- occasions like --12;
            }
        }
        return (Integer.toString(totalSum, base)).toUpperCase();

    }

    public static int binariToInt(String word){
        String newWord = word;
        if(word.charAt(0) == 1){
            newWord = twosComplement(word);
        }
        return Integer.parseInt(newWord,2);
    }

    public static String binaricOperation(String expression){
        //first we will handle the not ~
        int first_index=expression.indexOf('~');
        String binary_number="";
        while (first_index != -1) {
            binary_number = expression.substring(first_index + 1, first_index + 9);
            String toReplace = "~" + binary_number; // The string to be replaced
            String replacement = not(binary_number); // The replacement string
            expression = expression.replaceFirst(toReplace, replacement);
            // Find the next occurrence of '~'
            first_index = expression.indexOf('~');
        }
        //after all the ~'s had been handled, we will give and & the priority
        first_index=expression.indexOf('&');
        binary_number="";
        String binary_number2="";
        while(first_index!=-1){
            binary_number=expression.substring(first_index-8,first_index);
            binary_number2=expression.substring(first_index+1,first_index+9);
            expression=expression.replace(binary_number+'&'+binary_number2,and(binary_number,binary_number2));
            first_index=expression.indexOf('&');
        }
        //after all the &'s had been handled, we will give and ^ the priority
        first_index=expression.indexOf('^');
        binary_number="";
        binary_number2="";
        while(first_index!=-1){
            binary_number=expression.substring(first_index-8,first_index);
            binary_number2=expression.substring(first_index+1,first_index+9);
            expression=expression.replace(binary_number+'^'+binary_number2,xor(binary_number,binary_number2));
            first_index=expression.indexOf('^');
        }
        //lastly, we will handle |
        first_index=expression.indexOf('|');
        binary_number="";
        binary_number2="";
        while(first_index!=-1){
            binary_number=expression.substring(first_index-8,first_index);
            binary_number2=expression.substring(first_index+1,first_index+9);
            expression=expression.replace(binary_number+'|'+binary_number2,or(binary_number,binary_number2));
            first_index=expression.indexOf('|');
        }
        return expression;
    }

    //doing or to 2 given binary numbers
    public static String or(String binary1,String binary2){
        return not(and(not(binary1),not(binary2)));
    }

    //doing xor to 2 given binary numbers
    public static String xor(String binary1, String binary2){
        return not(and(not(and(not(binary1), binary2)), not(and(binary1, not(binary2)))));
    }

    //doing and to 2 given binary numbers
    public static String and(String binary1,String binary2){
        String result="";
        for (int i=0;i<binary1.length();i++){
            if (binary1.charAt(i)=='1' && binary2.charAt(i)=='1'){
                result+='1';
            }
            else {
                result+='0';
            }
        }
        return result;
    }

    //doing not to a given string meaning turning 0's to 1's and 1's to 0's
    public static String not(String binary_number){
        String minus_binary_number=""; //turning 0's to 1's and 1's to 0's
        for (int j=0;j<binary_number.length();j++){
            if (binary_number.charAt(j)=='0'){
                minus_binary_number+='1';
            }
            else {
                minus_binary_number+='0';
            }
        }
        return minus_binary_number;
    }
    public static String twosComplement(String binary_number){
        String minus_binary_number=not(binary_number); //turning 0's to 1's and 1's to 0's
        int carry = 1; // Start with carry since we add 1
        String final_number = ""; // Result string for the two's complement
        for (int i = minus_binary_number.length() - 1; i >= 0; i--) {
            if (minus_binary_number.charAt(i) == '0' && carry == 1) {
                final_number = '1' + final_number;
                carry = 0; // No carry needed anymore
            } else if (minus_binary_number.charAt(i) == '1' && carry == 1) {
                final_number = '0' + final_number; // 1 + 1 = 10, so keep 0 and carry 1
            } else {
                final_number = minus_binary_number.charAt(i) + final_number; // No carry, just copy the bit
            }
        }
        //If carry is still 1, prepend it to the result
        if (carry == 1) {
            final_number = '1' + final_number;
        }
        return final_number;
    }

}


