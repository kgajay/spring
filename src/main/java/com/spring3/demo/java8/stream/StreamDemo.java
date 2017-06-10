package com.spring3.demo.java8.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ajay.kg created on 10/06/17.
 */
public class StreamDemo {

    public static void main(String[] args) {

        // Count empty strings
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println("List: " +strings);
        long count = getCountEmptyStringUsingJava7(strings);
        System.out.println("Java 7 Empty Strings count: " + count);

        count = strings.parallelStream().filter(string->string.isEmpty()).count();
        System.out.println("Java 8 Empty Strings count: " + count);

        count = getCountLength3UsingJava7(strings);
        System.out.println("Java 7 Strings of length 3: " + count);

        count = strings.stream().filter(s -> s.length()==3).count();
        System.out.println("Java 8 Strings of length 3: " + count);


        //Eliminate empty string
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        System.out.println("Java 7 Filtered List: " + filtered);

        filtered = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        System.out.println("Java 8 Filtered List: " + filtered);

        //Eliminate empty string and join using comma.
        String mergedString = getMergedStringUsingJava7(strings,", ");
        System.out.println("Java 7 Merged String: " + mergedString);

        mergedString = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Java 8 Merged String: " + mergedString);

        //get list of square of distinct numbers
        System.out.println("Using Java 7");
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = getSquares(numbers);
        System.out.println("Java 7 Squares List: " + squaresList);
        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);

        System.out.println("Java 7 List: " +integers);
        System.out.println("Java 7 Highest number in List : " + getMax(integers));
        System.out.println("Java 7 Lowest number in List : " + getMin(integers));
        System.out.println("Java 7 Sum of all numbers : " + getSum(integers));
        System.out.println("Java 7 Average of all numbers : " + getAverage(integers));
        System.out.println("Java 7 Random Numbers: ");

        //print ten random numbers
        Random random = new Random();

        for(int i=0; i < 10; i++){
            System.out.println(random.nextInt());
        }

        System.out.println("Using java 8");
        squaresList = numbers.stream().map(integer -> integer*integer).distinct().collect(Collectors.toList());
        System.out.println("Java 8 Squares List: " + squaresList);
        System.out.println("Java 8 List: " +integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) ->x).summaryStatistics();

        System.out.println("Java 8 Highest number in List : " + stats.getMax());
        System.out.println("Java 8 Lowest number in List : " + stats.getMin());
        System.out.println("Java 8 Sum of all numbers : " + stats.getSum());
        System.out.println("Java 8 Average of all numbers : " + stats.getAverage());
        System.out.println("Java 8 Random Numbers: ");

        random.ints().limit(10).sorted().forEach(System.out::println);

    }


    private static int getCountEmptyStringUsingJava7(List<String> strings) {
        int count = 0;

        for(String string: strings){

            if(string.isEmpty()){
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings) {
        int count = 0;
        for(String string : strings) {
            if (string.length() == 3) {
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings){
        List<String> filteredList = new ArrayList<String>();

        for(String string: strings){

            if(!string.isEmpty()){
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings, String separator){
        StringBuilder stringBuilder = new StringBuilder();

        for(String string: strings){

            if(!string.isEmpty()){
                stringBuilder.append(string);
                stringBuilder.append(separator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length()-2);
    }

    private static List<Integer> getSquares(List<Integer> numbers){
        List<Integer> squaresList = new ArrayList<Integer>();

        for(Integer number: numbers){
            Integer square = new Integer(number.intValue() * number.intValue());

            if(!squaresList.contains(square)){
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers){
        int max = numbers.get(0);

        for(int i=1;i < numbers.size();i++){

            Integer number = numbers.get(i);

            if(number.intValue() > max){
                max = number.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers){
        int min = numbers.get(0);

        for(int i=1;i < numbers.size();i++){
            Integer number = numbers.get(i);

            if(number.intValue() < min){
                min = number.intValue();
            }
        }
        return min;
    }

    private static int getSum(List numbers){
        int sum = (int)(numbers.get(0));

        for(int i=1;i < numbers.size();i++){
            sum += (int)numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers){
        return getSum(numbers) / numbers.size();
    }
}
