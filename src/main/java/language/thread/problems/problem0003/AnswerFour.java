package language.thread.problems.problem0003;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import ReentrantLock;

/**
 * demo ReentrantLock
 */
public class AnswerFour {

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        char[] chars = { 'a', 'b', 'c', 'd', 'e' };
        Lock canPrint = new ReentrantLock();
        Condition printNum = canPrint.newCondition();
        Condition printChar = canPrint.newCondition();
        new PrintNums(nums, canPrint, printNum, printChar).start();
        new PrintChars(chars, canPrint, printNum, printChar).start();
    }

    public static class PrintNums extends Thread {
        private int[] nums;
        private Condition printNum;
        private Condition printChar;
        private Lock canPrint;

        public PrintNums(int[] nums, Lock canPrint, Condition printNum, Condition printChar) {
            super();
            this.nums = nums;
            this.printNum = printNum;
            this.printChar = printChar;
            this.canPrint = canPrint;
        }

        public void run() {
            int count = 0;
            try {
                for (int n : nums) {
                    if (count == 2) {
                        canPrint.lock();
                        count = 0;
                        printChar.signal();
                        printNum.await();
                        canPrint.unlock();
                    }
                    System.out.print(n);
                    count++;
                }
                canPrint.lock();
                printChar.signal();
                canPrint.unlock();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static class PrintChars extends Thread {
        private char[] chars;
        private Condition printNum;
        private Condition printChar;
        private Lock canPrint;

        public PrintChars(char[] chars, Lock canPrint, Condition printNum, Condition printChar) {
            super();
            this.chars = chars;
            this.printNum = printNum;
            this.printChar = printChar;
            this.canPrint = canPrint;
        }

        public void run() {
            int count = 0;
            try {
                Thread.sleep(100);
                for (char n : chars) {
                    if (count == 1) {
                        canPrint.lock();
                        count = 0;
                        printNum.signal();
                        printChar.await();
                        canPrint.unlock();
                    }
                    System.out.print(n);
                    count++;
                }
                canPrint.lock();
                printNum.signal();
                canPrint.unlock();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
