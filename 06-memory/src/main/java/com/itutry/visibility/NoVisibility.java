package com.itutry.visibility;

import com.itutry.util.Sleeper;

public class NoVisibility {
    private static boolean ready = false;
    private static int number = 0;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield(); //交出CPU让其它线程工作
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();

        Sleeper.sleep(1);
        number = 42;
        ready = true;
    }
}
