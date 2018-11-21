package Exchanger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * 两方栅栏
 * 当一个线程到达exchange调用点时，如果它的伙伴线程此前已经调用了此方法，那么它的伙伴会被调度唤醒并与之进行对象交换，然后各自返回。
 * 如果它的伙伴还没到达交换点，那么当前线程将会被挂起，直至伙伴线程到达——完成交换正常返回；或者当前线程被中断——抛出中断异常；又或者是等候超时——抛出超时异常
 * @author ruand
 *
 */
public class Test {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<>();
		CountDownLatch latch = new CountDownLatch(2);
	
		executor.execute(new Runnable() {
		
			@Override
			public void run() {
				Thread.currentThread().setName("小张");
				try {
					Thread.sleep(2000);
					System.out.println(Thread.currentThread().getName() + "，正在等待付款");
					System.out.println(Thread.currentThread().getName() + "获得："
							+  exchanger.exchange("别墅",1,TimeUnit.SECONDS));
					latch.countDown();
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + "继续卖房");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					System.out.println("交易失败，对方付款超时");
				}

			}
		});
		executor.execute(new Runnable() {

			@Override
			public void run() {
				Thread.currentThread().setName("小李");
				try {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName() + "，正在等待商品");
					System.out.println(Thread.currentThread().getName() + "获得："
							+  exchanger.exchange("1000元"));
					latch.countDown();
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + "回家");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		try {
			latch.await();
			System.out.println("交易成功");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("交易失败");
		}
		
	}

}
