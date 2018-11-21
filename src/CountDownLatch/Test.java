package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 使一个或者多个线程等待一组事件发生，闭锁用于等待事件
 * 这里以等待多个玩家进入游戏为例子
 * 
 * @author ruand
 *
 */
public class Test {

	public static void main(String[] args) {
		int playerNum = 5;
		CountDownLatch latch = new CountDownLatch(playerNum);
		Player player;
		for (int i = 0; i < playerNum; i++) {
			player = new Player(latch);
			player.setName("【" + i + "】");
			player.start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("游戏异常");
		}
		System.out.println("所有玩家就绪，开始游戏");
	}

}
