package model.data_structures;

import java.util.Iterator;

public interface ISeparateChaining<K, V> 
{
	/**
	 * Agregar una dupla (K, V) a la tabla. Si la llave K
	 *existe, se reemplaza su valor V asociado. V no puede
	 *ser null.
	 * @param k
	 * @param v
	 */
	public void put(K k, V v);
	/**
	 * Obtener el valor V asociado a la llave K. Se obtiene
	 *null solo si la llave K no existe.
	 */
	public V get(K k);
	/**
	 * Borrar la dupla asociada a la llave K. Se obtiene el
	 *valor V asociado a la llave K. Se obtiene null solo si
	 *la llave K no existe.
	 * @param k
	 * @return
	 */
	public V delete(K k);
	/**
	 * Conjunto de llaves K presentes en la tabla
	 * @return
	 */
	public Iterator<K> keys();

}
