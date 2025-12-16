package lab8;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelMonteCarloPi {
    
    private static final long TOTAL_ITERATIONS = 1_000_000_000L; 
    private static final AtomicLong pointsInCircle = new AtomicLong(0);

    public static void main(String[] args) {
        int numThreads = 1;

        if (args.length > 0) {
            try {
                numThreads = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Аргумент має бути цілим числом. Використовується 1 потік.");
            }
        }

        long iterationsPerThread = TOTAL_ITERATIONS / numThreads;

        Thread[] threads = new Thread[numThreads];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                long localInCircle = 0;
                ThreadLocalRandom random = ThreadLocalRandom.current();

                for (long j = 0; j < iterationsPerThread; j++) {
                    double x = random.nextDouble();
                    double y = random.nextDouble();

                    if (x * x + y * y <= 1.0) {
                        localInCircle++;
                    }
                }
                pointsInCircle.addAndGet(localInCircle);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        double pi = 4.0 * pointsInCircle.get() / TOTAL_ITERATIONS;

        System.out.println("PI is " + pi);
        System.out.println("THREADS " + numThreads);
        System.out.println("ITERATIONS " + TOTAL_ITERATIONS);
        System.out.println("TIME " + (endTime - startTime) + "ms");
    }
}
