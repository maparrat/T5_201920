package model.data_structures;

import java.util.Iterator;

public class SeparateChaining<K, V> implements ISeparateChaining<K, V>
{
	private int N;                                // number of key-value pairs
	private int M;                                // hash table size
	private SequentialSearchST<K, V>[] st;  // array of linked-list symbol tables

	/**
	 * Initializes an empty symbol table with {@code m} chains.
	 * @param m the initial number of chains
	 */
	public SeparateChaining(int m) {
		this.M = m;
		st = (SequentialSearchST<K, V>[]) new SequentialSearchST[m];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearchST<K, V>();
	} 

	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize(int chains) {
		SeparateChaining<K, V> temp = new SeparateChaining<K, V>(chains);
		for (int i = 0; i < M; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.M  = temp.M;
		this.N  = temp.N;
		this.st = temp.st;
	}

	// hash value between 0 and m-1
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	} 

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return N;
	} 

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(K key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	} 

	/**
	 * Returns the value associated with the specified key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with {@code key} in the symbol table;
	 *         {@code null} if no such value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public V get(K key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		int i = hash(key);
		return st[i].get(key);
	} 

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(K key, V val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}

		// double table size if average length of list >= 10
		if ((double) (N/M) > 5.0) resize(2*M);

		int i = hash(key);
		if (!st[i].contains(key)) N++;
		st[i].put(key, val);
	} 

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public V delete(K key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");

		int i = hash(key);
		if (st[i].contains(key)) N--;
		st[i].delete(key);

		// halve table size if average length of list <= 2
		if ((double) (N/M) > 5.0) resize(M/2);
	} 

	// return keys in symbol table as an Iterable
	public Iterator<K> keys()
	{
		Queue<K> queue = new Queue<K>();
		for (int i = 0; i < M; i++) {
			for (K key : st[i].keys())
				queue.enqueue(key);
		}
		return queue;
	} 



}