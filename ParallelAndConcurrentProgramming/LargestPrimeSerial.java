import java.math.BigInteger;
import java.time.Clock;
import java.util.Random;

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
 *
 * Example runs: Serial:
 * 341550071728321341550071728321341550071728321341550071728321341550071728321341550071728321321341550071728321341550071728321341550071755809
 * ForkJoin:
 * 341550071728321341550071728321341550071728321341550071728321341550071728321341550071728321321341550071728321341550071728321341550071759853
 */
public class LargestPrimeSerial {
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

        while (true) {
            // Starting n at an odd number, we ensure all numbers we check are odd by incrementing by 2
            n = n.add(BigInteger.valueOf(2));

            // Runtime calculated from start time
            runtime = clock.millis() - start;

            // If the n we are testing is prime, store it
            if (probablyPrime(n, k)) {
                largestPrime = n;
                System.out.println("Prime found... " + largestPrime.toString());
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

    private static boolean probablyPrime(BigInteger n, int k) {
        // Make sure the input is greater than 3 and is odd
        // compareTo returns -1 for < val and 0 if == val
        if (n.compareTo(BigInteger.valueOf(3)) <= 0) {
            if (n.compareTo(BigInteger.ZERO) <= 0) {
                System.out.println("Non-positive n passed to prime test.");
                System.exit(0);
            }
            if (n.equals(BigInteger.ONE)) {
                return false;
            }
            return true;
        }
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            return false;
        }

        // n = 2^r * d + 1 with d odd (by factoring out powers of 2 from n-1)
        BigInteger d = n.subtract(BigInteger.ONE);
        BigInteger r = BigInteger.ZERO;
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            d = d.shiftRight(1);
            r = r.add(BigInteger.ONE);
        }

        // Witness loop
        // This repeats k times
        boolean skipOuter;
        for (int i = 0; i < k; i++) {
            // Generate a random BigInt between 2 and n - 2
            BigInteger maxLimit = n.subtract(BigInteger.valueOf(2));
            BigInteger minLimit = BigInteger.valueOf(2);
            BigInteger bigInteger = maxLimit.subtract(minLimit);
            Random randNum = new Random();
            int len = maxLimit.bitLength();
            BigInteger a = new BigInteger(len, randNum);
            if (a.compareTo(minLimit) < 0)
                a = a.add(minLimit);
            if (a.compareTo(bigInteger) >= 0)
                a = a.mod(bigInteger).add(minLimit);

            // x ← a^d mod n
            BigInteger x = a.modPow(d, n);

            // If x = 1 or x = n − 1 then
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }
            skipOuter = false;

            // Repeat r - 1 times
            for (BigInteger j = BigInteger.ZERO; j.compareTo(r.subtract(BigInteger.ONE)) < 0; j = j.add(BigInteger.ONE)) {
                // x ← x^2 mod n
                x = x.modPow(BigInteger.valueOf(2), n);
                // If x = n − 1 then
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    skipOuter = true;
                    break;
                }
            }

            if (skipOuter) {
                continue;
            }

            // Composite found
            return false;
        }

        // Probably prime found
        return true;
    }
}