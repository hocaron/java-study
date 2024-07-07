package com.basestudy;

import java.util.Collection;
import java.util.Stack;

public class GenericStack<T> extends Stack<T> {
	public void pushAll(Collection<? extends T> source) {
		for (T elem : source) {
			push(elem);
		}
		String s1 = "Hello";
		String s2 = "Hello";

		System.out.println(s1 == s2); // 주소 비교 false
		System.out.println(s1.equals(s2)); // 값 비교 true
	}
}
