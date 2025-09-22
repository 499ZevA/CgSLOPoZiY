// 代码生成时间: 2025-09-22 23:10:33
package com.example.sort;

import io.micronaut.core.annotation.Introspected;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Introspected
public class SortService {

    /**
     * Sorts a list of integers using the given comparator.
     *
     * @param numbers The list of integers to sort.
     * @param comparator The comparator to use for sorting.
     * @return A sorted list of integers.
     * @throws IllegalArgumentException If the input list is null.
     */
    public List<Integer> sortNumbers(List<Integer> numbers, Comparator<Integer> comparator) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input list cannot be null.");
        }
        return numbers.stream()
                .sorted(comparator)
                .toList();
    }

    /**
     * Sorts a list of integers in ascending order.
     *
     * @param numbers The list of integers to sort.
     * @return A sorted list of integers in ascending order.
     * @throws IllegalArgumentException If the input list is null.
     */
    public List<Integer> sortNumbersAscending(List<Integer> numbers) {
        return sortNumbers(numbers, Comparator.naturalOrder());
    }

    /**
     * Sorts a list of integers in descending order.
     *
     * @param numbers The list of integers to sort.
     * @return A sorted list of integers in descending order.
     * @throws IllegalArgumentException If the input list is null.
     */
    public List<Integer> sortNumbersDescending(List<Integer> numbers) {
        return sortNumbers(numbers, Comparator.reverseOrder());
    }

    // Additional sorting methods can be added here for different types or custom comparators.

    // Test the sorting functionality
    public static void main(String[] args) {
        SortService sortService = new SortService();
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 5, 6);

        System.out.println("Original list: " + numbers);
        System.out.println("Sorted in ascending order: " + sortService.sortNumbersAscending(numbers));
        System.out.println("Sorted in descending order: " + sortService.sortNumbersDescending(numbers));
    }
}
