package Computable;

public interface Computable<K, V> {
	V compute(K key);
}
