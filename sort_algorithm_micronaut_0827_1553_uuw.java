// 代码生成时间: 2025-08-27 15:53:41
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import java.util.Arrays;
import java.util.List;

@Controller("/api/sort")
public class SortAlgorithmMicronaut {

    /**
     * Sorts a list of integers using the natural ordering.
     *
     * @param unsortedList The list of integers to be sorted.
     * @return A sorted list of integers.
     */
    @Get("/sort")
    public List<Integer> sort(@QueryValue List<Integer> unsortedList) {
        if (unsortedList == null) {
            throw new IllegalArgumentException("The unsorted list cannot be null");
        }
        return sortList(unsortedList);
    }

    /**
     * Sorts a list of integers using Arrays.sort method.
     *
     * @param list The list of integers to be sorted.
     * @return A sorted list of integers.
     */
    private List<Integer> sortList(List<Integer> list) {
        return Arrays.stream(list).sorted().toList();
    }

    /**
     * Test endpoint to demonstrate the sorting functionality.
     *
     * @return A message indicating the success of the operation.
     */
    @Get("/test")
    public String testSort() {
        List<Integer> testList = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        List<Integer> sortedTestList = sortList(testList);
        System.out.println("Sorted List: " + sortedTestList);
        return "Sorting successful! Sorted list: " + sortedTestList;
    }
}
