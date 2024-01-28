package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    private final NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 11, 13, 17, 23})
        public void isPrimeForPrimes(int num) {
            assertTrue(numberWorker.isPrime(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 10, 12, 14, 16, 18, 20})
        public void isPrimeForNotPrimes(int num) {
        assertFalse(numberWorker.isPrime(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, -7, -1, -2, -3, -4, -5, -6})
        public void isPrimeForIncorrectNumbers(int num) {
        IllegalNumberException thrown = assertThrows(
                IllegalNumberException.class,
                () -> numberWorker.isPrime(num)
        );
        assertTrue(thrown.getMessage().contains("Incorrect number!"));
    }

    @ParameterizedTest(name = "test values: {0}")
    @CsvFileSource(resources = "/data.csv", delimiter = ' ')
        public void isSumDigitVariousTests(int num, int res) {
        assertEquals(numberWorker.digitsSum(num), res);
    }

}
