package com.basestudy.functionalInterface;

public class CustomInterfaceTest {

    public static void main(String[] args) {
        com.basestudy.functionalInterface.CustomInterface<String> customInterface = () -> "Hello Custom";

        // abstract method
        String s = customInterface.myCall();
        System.out.println(s);

        // default method
        customInterface.printDefault();

        // static method
        com.basestudy.functionalInterface.CustomInterface.printStatic();
    }
}
