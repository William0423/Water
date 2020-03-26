package language.thread.problems.problem0001;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Java5.0新特性:

 用Lock替换synchronized
 */
public class AnswerTwo {
	public static void main(String[] args) {
		Bunsiness bunsiness = new Bunsiness();
		for (int i =1;i<=10;i++) {
			final int k = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					bunsiness.child(k);
				}
			}).start();
			
			bunsiness.parent(i);
		}
		
	}
	
	static class Bunsiness {
		
		Lock lock = new ReentrantLock();
		// Java在过去很长一段时间只能通过synchronized关键字来实现互斥，它有一些缺点。
		// 比如你不能扩展锁之外的方法或者块边界，尝试获取锁时不能中途取消等。
		// Java 5 通过Lock接口提供了更复杂的控制来解决这些问题。 
		// ReentrantLock 类实现了 Lock，它拥有与 synchronized 相同的并发性和内存语义且它还具有可扩展性。
		Condition condition = lock.newCondition();
		private boolean isSubTurn = true;
		
		public void child(int i) {
			lock.lock();
			try {
				while (!isSubTurn) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
	            for (int j = 1; j <= 10; j++) {  
	                System.out.println("sub run " + j + " in round " + i);  
	            }  
	            isSubTurn = false;
	            condition.signal();
			} finally {
				lock.unlock();
			}
		}
		
		
		public void parent(int i) {
			lock.lock(); // 如果没有这句，会抛什么错误？
            try {  
                while (isSubTurn) {  
                    try {  
                        condition.await();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                for (int j = 1; j <= 100; j++) {  
                    System.out.println("main run " + j + " in round " + i);  
                }  
                isSubTurn = true;  
                condition.signal();  
            } finally {  
                lock.unlock();  
            } 
		}
		
	}

}
