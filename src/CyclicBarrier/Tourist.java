package CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Tourist extends Thread {
	
	private CyclicBarrier cyclicBarrier;
	private String transport;
	
	public Tourist(CyclicBarrier cyclicBarrier,String transport) {
		this.cyclicBarrier = cyclicBarrier;
		this.transport = transport;
	}

	@Override
	public void run() {
		tripDetail();
		int millis= new Random().nextInt(9);
		try {
			Thread.sleep(millis*1000);
			System.out.println(Thread.currentThread().getName() + "已经到达码头");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + ":登游轮咯");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void tripDetail () {
		System.out.println(Thread.currentThread().getName() + "正在乘" + transport + "来码头");
	}

}
