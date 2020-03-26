package language.thread.problems.problem0001;


import java.util.concurrent.BlockingQueue;

/**
 * 子线程循环10次,接着主线程循环100次,接着又回到子线程循环10次, 接着再回到主线程又循环100次,如此循环50次.写出程序:一个两个线程
 * @author hadoop
 *
 */
// http://codewa.com/question/30345.html
public class AnswerOne {
	
	public static void main(String[] args) {
		AnswerOne ao = new AnswerOne();
		Business business = ao.new Business();
		for (int i =1;i<=10;i++) {
			final int k = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					business.child(k);
				}
			}).start();// 通过start创建一个子线程， 这个子线程要执行10
			// 主线程跑100次
			business.parent(i);
		}
		
	}
	
	/**
	 * 内部类，可声明为static，避免在main方法中new对象：AnswerOne ao = new AnswerOne();
	 * @author hadoop
	 *
	 */
	class Business {
		private boolean isSubTurn = true;
		public synchronized void child(int i) { // synchronized通过给方法加上锁实现互斥
			while (!isSubTurn) { // 主线程跑的话，子线程等待
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int j =1; j<=10;j++) {
				System.out.println("sub run " + j + " in round " + i); 
			}
			// 子线程跑完，挂起等待：
			isSubTurn = false;
			this.notify();
		}
		
		
		public synchronized void parent(int i) {
			while (isSubTurn) {
				try {
					this.wait(); // wait()：使一个线程处于等待（阻塞）状态，并且释放所持有的对象的锁；
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
            for (int j = 1; j <= 100; j++) {  
                System.out.println("main run " + j + " in round " + i);  
            }  
            isSubTurn = true;  
            this.notify(); // 唤醒这个内部类一个处于等待状态的线程：因为只有一个等待线程，所以该等待线程会被唤醒 
		}
		
		
	}

}
