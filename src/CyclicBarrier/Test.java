package CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏类似于闭锁，它能阻塞一组线程直到某个事件发生， 栅栏与闭锁的关键区别在于，栅栏用于等待其他线程。(大家都在等，直到最后一个线程到达了)
 * 例子： 三个人合作建桥，有三个桩，每人打一个，同时打完之后才能一起搭桥（搭桥需要三人一起合作）。也就是说三个人都打完桩之后才能继续工作。
 * @author ruand
 *
 */
public class Test {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier= new CyclicBarrier(4,new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("大家都到齐了");
			}
		});
		Tourist t1 = new Tourist(cyclicBarrier, "出租车");
		t1.setName("张三");
		t1.start();
		Tourist t2 = new Tourist(cyclicBarrier, "私家车");
		t2.setName("李四");
		t2.start();
		Tourist t3 = new Tourist(cyclicBarrier, "自行车");
		t3.setName("王麻子");
		t3.start();
		Tourist t4 = new Tourist(cyclicBarrier, "顺风车");
		t4.setName("周五");
		t4.start();
	}

}
