package com.spring3.demo.java8.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ajay.kg created on 10/06/17.
 */
public class StreamJavaMapTest {

    public static void main(String[] args) {

        List<String> items = Arrays.asList("apple", "apple", "banana", "apple", "orange", "banana", "papaya");
        Map<String, Long> result = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Result " + result);

        Map<String, Long> finalMap = new LinkedHashMap<>();
        result.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue()
                .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println("Final Map " + finalMap);


        List<Staff> list = new ArrayList<>();
        list.add( new Staff("mkyong", 30, new BigDecimal(10000)));
        list.add(new Staff("jack", 27, new BigDecimal(20000)));
        list.add(new Staff("lawrence", 33, new BigDecimal(30000)));
        list.add(new Staff("aws.amazon.com", 44, new BigDecimal(200000)));
        list.add(new Staff("mkyong.com", 34, new BigDecimal(400000)));

        Map<Integer, String> result1 = list.stream().filter(staff -> staff.getSalary().compareTo(new BigDecimal(10000))>0).collect(Collectors.toMap(Staff::getAge, Staff::getName));
        System.out.println("Map result 1 " + result1);

        Map<Integer, Staff> result2 = list.stream().collect(Collectors.toMap(Staff::getAge, Function.identity()));
        System.out.println("Map result 1 " + result2);

        Map<Integer, String> HOSTING = new HashMap<>();
        HOSTING.put(1, "linode.com");
        HOSTING.put(2, "heroku.com");
        HOSTING.put(3, "digitalocean.com");
        HOSTING.put(4, "aws.amazon.com");

        // Map -> Stream -> Filter -> String
        String resultStr = HOSTING.entrySet().stream()
                .filter(map -> "aws.amazon.com".equals(map.getValue()))
                .map(map -> map.getValue())
                .collect(Collectors.joining());

        System.out.println("With Java 8 : " + resultStr);


        //Map -> Stream -> Filter -> Map
        Map<Integer, String> collect = HOSTING.entrySet().stream()
                .filter(map -> map.getKey() >= 2)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        System.out.println(collect); //output : {2=heroku.com}
    }
}
