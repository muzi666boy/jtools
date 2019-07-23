// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.jdk8;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * The LambdaTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class LambdaTest {

    @Test
    public void testSubclass() {
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();
    }

    @Test
    public void testComplexSubclass() {
        new Thread(() -> {
            System.out.println("In Java8, Lambda expression rocks !!");
            System.out.println("In Java8, Lambda expression rocks !!");
        }).start();
    }

    @Test
    public void testComplexSubclass2() throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 1; i++) {
                System.out.println("In Java8, Lambda expression rocks !!" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        t.join();
    }

    List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API", "Lambdas");
    List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);

    @Test
    public void testList() {
        features.forEach((f) -> System.out.println(f));
    }

    @Test
    public void testPredicate() {
        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str) -> str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str) -> str.endsWith("a"));

        System.out.println("Print all languages :");
        filter(languages, (str) -> true);

        System.out.println("Print no language : ");
        filter(languages, (str) -> false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str) -> str.length() > 4);

        System.out.println("filter and: ");
        filterAnd(languages);
    }

    public static void filter(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }

    public void filterAnd(List<String> names) {
        // 甚至可以用and()、or()和xor()逻辑函数来合并Predicate，
        // 例如要找到所有以J开始，长度为四个字母的名字，你可以合并两个Predicate并传入
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;
        names.stream().filter(startsWithJ.and(fourLetterLong))
                .forEach((n) -> System.out.print("nName, which starts with 'J' and four letter long is : " + n));
    }

    @Test
    public void testMap() {
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);
    }

    @Test
    public void testStreamMapReduce() {
        // 新方法：
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // 新方法：
        double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("bill : " + bill);
    }

    @Test
    public void testStreamFilter() {
        List<String> filtered = features.stream().filter(x -> x.length()> 12).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", features, filtered);
    }

    @Test
    public void testStreamMap() {
        String filtered = features.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.printf("Original List : %s, map list : %s %n", features, filtered);
    }

    @Test
    public void testStreamDistinct() {
        List<String> filtered = features.stream().map(x -> x.toUpperCase()).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s, map list : %s %n", features, filtered);
        List<Integer> filtered2 = numbers.stream().distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s, map list : %s %n", numbers, filtered2);
    }

    @Test
    public void testMR() {
        Integer result = numbers.stream().map(x -> x * x).map(x -> x * 2).reduce((sum, cost) -> sum + cost).get();
        System.out.printf("mr result: %d", result);
    }

    @Test
    public void testNumber() {
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }

    public static void  printValur(String str){
        System.out.println("print value : "+str);
    }

    @Test
    public void testMethod() {
        System.out.println("----------------普通的写法-----------------------");
        List<String> al = Arrays.asList("a","b","c","d");
        for (String a: al) {
            printValur(a);
        }
        System.out.println("----------------JDK双冒号--------------------------");
        //JDK8中有双冒号的用法，就是把方法当做参数传到stream内部，使stream的每个元素都传入到该方法里面执行一下
        Consumer<String> stringConsumer = LambdaTest::printValur;
        System.out.println("----------------方式1--------------------------");
        al.forEach(stringConsumer);
        System.out.println("----------------方式2--------------------------");
        al.forEach(x -> stringConsumer.accept(x));
        System.out.println("----------------方式3--------------------------");
        al.forEach(LambdaTest::printValur);
    }

}
