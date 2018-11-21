package Computable.memorize1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Computable.Computable;

public class Test {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Computable<String, Integer> computable = new Computable<String, Integer>() {

			@Override
			public Integer compute(String key) {
				System.out.println(Thread.currentThread().getName() + ":耗时的计算……");
				try {
					Thread.sleep(new Random().nextInt(8) * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new Random().nextInt(5);
			}
		};
		Memorize1<String, Integer> memorize1 = new Memorize1<String, Integer>(computable);
		executor.execute(new Runnable() {
			@Override
			public void run() {
				int num = memorize1.compute("test1");
				System.out.println(Thread.currentThread().getName() + "获得test1的计算结果：" + num);

			}
		});
		executor.execute(new Runnable() {

			@Override
			public void run() {
				int num = memorize1.compute("test1");
				System.out.println(Thread.currentThread().getName() + "获得test2的计算结果：" + num);

			}
		});

	}

}
