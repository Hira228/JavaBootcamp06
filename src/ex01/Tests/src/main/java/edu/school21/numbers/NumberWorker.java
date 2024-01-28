package edu.school21.numbers;

import edu.school21.numbers.exceptions.IllegalNumberException;

public class NumberWorker {

    public boolean isPrime(int number) {

        if (number < 2) {
            throw new IllegalNumberException("Incorrect number!");
        }
        for(int i = 2; i <= Math.sqrt(number); ++i) {
            if(number % i == 0) return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        return String.valueOf(number)
                .chars()
                .map(x -> x - '0')
                .sum();
    }


}
