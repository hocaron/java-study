package com.basestudy.functionalInterface;

public class CustomInterfaceTest {

    public static void main(String[] args) {
        CustomInterface<String> customInterface = () -> "Hello Custom";

        // abstract method
        String s = customInterface.myCall();
        System.out.println(s);

        // default method
        customInterface.printDefault();

        // static method
        CustomInterface.printStatic();
    }
}
