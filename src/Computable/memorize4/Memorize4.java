package Computable.memorize4;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import Computable.Computable;

/**
 * Computable 用于入参一个值（K），经过计算（可能耗时）返回结果（V）
 * 在memorize2的基础上，使用线程安全的ConcurrentHashMap + FutureTask 
 * 同时使用putIfAbsent方法
 * 
 * @author ruand
 *
 * @param <K>
 * @param <V>
 */
public class Memorize4<K, V> implements Computable<K, V> {
	private Computable<K, V> computable;
	private Map<K, FutureTask<V>> cache = new ConcurrentHashMap<>();
	
	public Memorize4(Computable<K, V> computable) {
		// TODO Auto-generated constructor stub
		this.computable = computable;
	}

	@Override
	public V compute(K key) {
		while (true) {
			FutureTask<V> futureTask = cache.get(key);
			if (null == futureTask) {
				System.out.println(Thread.currentThread().getName() + "正在计算"+key+"的结果");
				FutureTask<V> futureTask2 = new FutureTask<>(new Callable<V>() {

					@Override
					public V call() throws Exception {
						// TODO Auto-generated method stub
						return computable.compute(key);
					}
					
				});
				futureTask = cache.putIfAbsent(key, futureTask2);
				if (null == futureTask) {
					System.out.println(Thread.currentThread().getName() + "首次计算");
					futureTask = futureTask2;
					futureTask.run();
				}
				
			}
			try {
				return futureTask.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
	}
}
