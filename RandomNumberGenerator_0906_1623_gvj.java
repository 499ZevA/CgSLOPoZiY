// 代码生成时间: 2025-09-06 16:23:04
import java.util.concurrent.ThreadLocalRandom;
import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Singleton;

/**
 * Random number generator service.
 * This class generates random numbers within a specified range.
 */
@Singleton
@Introspected
public class RandomNumberGenerator {

    /**
     * Generates a random number between the specified range.
     *
     * @param min Minimum value of the range (inclusive)
     * @param max Maximum value of the range (exclusive)
     * @return A random number within the specified range
     * @throws IllegalArgumentException If min is greater than max
     */
    public int generateRandomNumber(int min, int max) {
        // Check if the minimum value is greater than the maximum value
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than the maximum value");
        }

        // Generate a random number within the specified range
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
