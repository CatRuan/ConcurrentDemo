package Computable.memorize1;

import java.util.HashMap;
import java.util.Map;

import Computable.Computable;
/**
 * Computable 用于入参一个值（K），经过计算（可能耗时）返回结果（V）
 * 为了避免传入相同的Key时，重复计算Value，引入Map缓存已经计算过的结果
 * 缺点，因为HashMap不是同步方法，为了解决并发导致的问题，对compute方法加锁,导致同一个时刻只有一个线程能够计算（即使线程使用不同的Key）
 * P87
 * @author ruand
 *
 * @param <K>
 * @param <V>
 */
public class Memorize1<K, V> implements Computable<K, V> {
	private Computable<K, V> computable;
	private Map<K, V> cache = new HashMap<>();
	
	public Memorize1(Computable<K, V> computable) {
		// TODO Auto-generated constructor stub
		this.computable = computable;
	}

	@Override
	public synchronized V compute(K key) {
		V value = cache.get(key);
		if (null == value) {
			System.out.println(Thread.currentThread().getName() + "正在计算"+key+"的结果");
			value = computable.compute(key);
			cache.put(key, value);
		}
		return value;
	}

}
