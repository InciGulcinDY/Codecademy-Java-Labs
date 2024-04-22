import java.math.BigInteger;
import java.time.Clock;
import java.util.concurrent.ForkJoinPool;

/**
 * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test Applying
 * the Miller-Rabin test. Accuracy: the Miller-Rabin test can declare a
 * composite n probably prime with a probability of at most 4^-k. A k of 160 will
 * be chosen for this project. However, for large n numbers, this probability is
 * closer to the bound of 8^-k. This means that it can declare a number prime
 * with a 8^-k chance of it being inaccurate. Input #1: n > 3, an odd integer to
 * be tested for primality Input #2: k, the number of rounds of testing to
 * perform Output: “composite” if n is found to be composite, “probably prime”
 * otherwise
 */
public class LargestPrimeForkJoin {
    // Stopping time limit in milliseconds
    static int timeLimit = 5 * 1000;

    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        long start, runtime;

        // Initial variables
        BigInteger largestPrime = BigInteger.valueOf(-1);
        BigInteger n = new BigInteger(
                "341550071728321341550071728321341550071728321341550071728321341550071728321341550071728321321341550071728321341550071728321341550071728321");
        int k = 160;
        start = clock.millis();

        int nThreads = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(nThreads);

        while (true) {
            // Starting n at an odd number, we ensure all numbers we check are odd by incrementing by 2
            n = n.add(BigInteger.valueOf(2));

            // Runtime calculated from start time
            runtime = clock.millis() - start;

            RecursivePrime rPrime = new RecursivePrime(n, k);
            pool.invoke(rPrime);
            boolean result = rPrime.result;

            if(result) {
                System.out.println("Prime found " + n);
                largestPrime = n;
            }


            // Stopping criteria
            if (runtime > timeLimit) {
                break;
            }
        }
        if (largestPrime.compareTo(BigInteger.ZERO) < 0) {
            System.out.println("No primes found.");
        } else {
            System.out.println("Largest prime found: " + largestPrime.toString());
        }
    }


}