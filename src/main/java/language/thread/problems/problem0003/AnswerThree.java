package language.thread.problems.problem0003;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport(直接等待和恢复)
 */
public class AnswerThree {

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        char[] chars = { 'a', 'b', 'c', 'd', 'e' };
        PrintNums t1 = new PrintNums(nums);
        PrintChars t2 = new PrintChars(chars);
        t1.setPrintChars(t2);
        t2.setPrintNums(t1);
        t1.start();
        t2.start();

    }

    public static class PrintNums extends Thread {
        private int[] nums;
        private PrintChars printChars;

        public PrintNums(int[] a1) {
            super();
            this.nums = a1;
        }

        public void setPrintChars(PrintChars printChars) {
            this.printChars = printChars;
        }

        public void run() {
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                if(count==2){
                    count = 0;
                    // 恢复 printChars线程：
                    LockSupport.unpark(printChars);
                    // 暂停当前线程
                    LockSupport.park();
                }
                System.out.print(nums[i]);
                count++;
            }
            LockSupport.unpark(printChars);
        }
    }

    public static class PrintChars extends Thread {
        private char[] chars;
        private PrintNums printNums;

        public PrintChars(char[] chars) {
            super();
            this.chars = chars;
        }

        public void setPrintNums(PrintNums printNums) {
            this.printNums = printNums;
        }

        public void run() {
            LockSupport.park();
            int count = 0;
            for (int i = 0; i < chars.length; i++) {
                if(count==1){
                    count = 0;
                    LockSupport.unpark(printNums);
                    LockSupport.park();
                }
                System.out.print(chars[i]);
                count++;
            }
            LockSupport.unpark(printNums);
        }
    }

}
