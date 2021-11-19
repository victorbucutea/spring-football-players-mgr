package com.webappsecurity;

import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BruteForcer {

    public static AtomicLong count = new AtomicLong(0);

    public static void main(String[] args) throws NoSuchAlgorithmException, ExecutionException, InterruptedException {
        String original = "QtH7XWr4OjW81Jzg12oAoSCqC9sVYGNKi2/8CyAq5H8="; //byu678

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        char[] alphabet2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        StopWatch t = new StopWatch();
        t.start();
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        executorService.submit(new BruteIterator(0, alphabet, original, t));
        executorService.submit(new BruteIterator(2, alphabet, original, t));
        executorService.submit(new BruteIterator(4, alphabet, original, t));
        executorService.submit(new BruteIterator(6, alphabet, original, t));
        executorService.submit(new BruteIterator(8, alphabet, original, t));
        executorService.submit(new BruteIterator(10, alphabet, original, t));
        executorService.submit(new BruteIterator(12, alphabet, original, t));
        executorService.submit(new BruteIterator(14, alphabet, original, t));
        executorService.submit(new BruteIterator(16, alphabet, original, t));

        executorService.submit(new BruteIterator(18, alphabet, original, t));
        executorService.submit(new BruteIterator(20, alphabet, original, t));
        executorService.submit(new BruteIterator(22, alphabet, original, t));
        executorService.submit(new BruteIterator(24, alphabet, original, t));
        executorService.submit(new BruteIterator(26, alphabet, original, t));
        executorService.submit(new BruteIterator(28, alphabet, original, t));
        executorService.submit(new BruteIterator(30, alphabet, original, t));
        executorService.submit(new BruteIterator(32, alphabet, original, t));
        executorService.submit(new BruteIterator(34, alphabet, original, t));

    }

    private static class BruteIterator implements Runnable {
        private final int start;
        private final char[] alphabet;
        private final MessageDigest digest;
        private final String original;
        private final StopWatch t;
        private int stop;

        public BruteIterator(int start, char[] alphabet, String original, StopWatch stopWatch) throws NoSuchAlgorithmException {
            this.start = start;
            this.stop = start + 3;
            if (this.stop > alphabet.length) {
                stop = alphabet.length;
            }
            this.alphabet = alphabet;
            this.t = stopWatch;
            this.original = original;
            this.digest = MessageDigest.getInstance("SHA-256");
        }

        @Override
        public void run() {
            for (int i = start; i < stop; i++) {
//                System.out.println(Thread.currentThread().getName() + " - " + alphabet[i]);
                for (char c : alphabet) {
                    for (char value : alphabet) {
                        for (char item : alphabet) {
                            for (char element : alphabet) {
                                for (char c1 : alphabet) {
                                    String val = String.valueOf(new char[]{
                                            alphabet[i],
                                            c,
                                            value,
                                            item,
                                            element,
                                            c1
                                    });

                                    byte[] digest1 = digest.digest(val.getBytes(StandardCharsets.UTF_8));
                                    BruteForcer.count.getAndIncrement();

                                    if (BruteForcer.count.get() % 10_000_000 == 0) {
                                        t.stop();
                                        System.out.println("Executed " +
                                                BruteForcer.count.get() + " combinations in " + t.getTotalTimeSeconds());
                                        t.start();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
