package Semaphore;

import java.util.Random;

public class Customer extends Thread{
	private Restaurant restaurant;
	
	public Customer(Restaurant restaurant) {
		// TODO Auto-generated constructor stub
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		restaurant.haveASet();
		System.out.println(Thread.currentThread().getName() + "正在吃饭……");
		int a= new Random().nextInt(3);
		try {
			Thread.sleep(a*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		restaurant.leave();
	}
}
