package language.thread.concurrency.restaurant;

import language.thread.concurrency.restaurant.Restaurant;

public class WaitPerson implements Runnable{

    private Restaurant restaurant;

    public WaitPerson(Restaurant r) {
        restaurant = r;
    }
    @Override
    public void run() {

        try {
            while(!Thread.interrupted()) {
                synchronized(this) {
                    while(restaurant.meal == null)
                        wait(); // ... for the chef to produce a meal
                }
                System.out.println("Waitperson got " + restaurant.meal);
                synchronized(restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); // Ready for another
                }
            }
        } catch(InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }

    }


}
