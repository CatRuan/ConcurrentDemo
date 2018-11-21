package Computable.momerize2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Computable.Computable;
/**
 * Computable 用于入参一个值（K），经过计算（可能耗时）返回结果（V）
 * 在memorize1的基础上，不使用HashMap,而是使用线程安全的ConcurrentHashMap
 * 缺点，当线程2开始计算值时，已经在计算的相同KEY值的线程1无法告诉线程2知道这个情况，而导致重复的耗时计算
 * @author ruand
 *
 * @param <K>
 * @param <V>
 */
public class Memorize2<K, V> implements Computable<K, V> {
	private Computable<K, V> computable;
	private Map<K, V> cache = new ConcurrentHashMap<>();
	
	public Memorize2(Computable<K, V> computable) {
		// TODO Auto-generated constructor stub
		this.computable = computable;
	}

	@Override
	public V compute(K key) {
		V value = cache.get(key);
		if (null == value) {
			System.out.println(Thread.currentThread().getName() + "正在计算"+key+"的结果");
			value = computable.compute(key);
			cache.put(key, value);
		}
		return value;
	}

}
