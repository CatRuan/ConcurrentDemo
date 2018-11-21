package Semaphore;

import java.util.concurrent.Semaphore;

public class Restaurant {
	private Semaphore tables;

	public Restaurant(int tableNum) {
		tables = new Semaphore(tableNum, true);
	}

	public boolean haveASet() {
		try {

			tables.acquire();
			int leaveNum = tables.availablePermits();
			System.out.println(Thread.currentThread().getName() + "入座,剩余桌位：" + leaveNum);
			return true;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "没座位了……");
		return false;
	}

	public void leave() {
		tables.release();
		int leaveNum = tables.availablePermits();
		System.out.println(Thread.currentThread().getName() + "离座,剩余桌位：" + leaveNum);
	}
}
