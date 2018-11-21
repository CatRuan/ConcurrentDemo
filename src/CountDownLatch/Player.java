package CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Player extends Thread{
	private CountDownLatch latch;
	
	public Player(CountDownLatch latch) {
		// TODO Auto-generated constructor stub
		this.latch = latch;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int millis= new Random().nextInt(5);
		try {
			System.out.println("等待" + Thread.currentThread().getName() + "号玩家接收游戏");
			Thread.sleep(millis*2000);
			latch.countDown();
			System.out.println("等待" + millis*2000 + "秒"+ Thread.currentThread().getName() + "号玩家，进入游戏");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
