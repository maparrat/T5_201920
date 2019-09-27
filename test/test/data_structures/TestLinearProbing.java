package test.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.LinearProbing;

public class TestLinearProbing
{
	private LinearProbing<Integer, String> lp;

	@Before
	public void setUp1() 
	{
		lp = new LinearProbing<>(5);
	}
	
	public void setUp2() 
	{
		lp.put(1, "A");
		lp.put(2, "B");
		lp.put(3, "C");
		lp.put(4, "D");
		lp.put(5, "E");
		lp.put(6, "F");
		lp.put(7, "G");
		lp.put(8, "H");
		lp.put(9, "I");
		lp.put(10, "J");
	}

	@Test
	public void testPut()
	{
		lp.put(5, "A");
		lp.put(10, "B");
		lp.put(11, "C");
		lp.put(11, "D");

		assertTrue(lp.contains(11));
		assertTrue(lp.size() == 3);
		assertNotNull(lp.get(10));
	}

	@Test
	public void testGet()
	{
		setUp2();
		assertEquals("A", lp.get(1));
		assertEquals("B", lp.get(2));
		assertEquals("C", lp.get(3));
		assertEquals("D", lp.get(4));
		assertEquals("E", lp.get(5));
		assertNotEquals("A", lp.get(6));
		assertNotEquals("A", lp.get(7));
		assertNotEquals("A", lp.get(8));
		assertNotEquals("A", lp.get(9));
		assertNotEquals("A", lp.get(10));
		
	}

	@Test
	public void testDelete()
	{
		setUp2();
		assertEquals("A", lp.delete(1));
		assertNull(lp.get(1));
		assertEquals("B", lp.delete(2));
		assertNull(lp.get(2));
		assertEquals("C", lp.delete(3));
		assertNull(lp.get(3));
		assertEquals("D", lp.delete(4));
		assertNull(lp.get(4));
		assertEquals("E", lp.delete(5));
		assertNull(lp.get(5));
	}

	@Test
	public void testKeys()
	{
		setUp2();
		Iterator<Integer> respuesta = lp.keys();
		
		assertTrue(respuesta.hasNext());
		
		for (int i = 0; i < 10; i++)
		{
			respuesta.next();
		}
		assertFalse(respuesta.hasNext());
	}
	
	@Test
	public void testRehash()
	{
		lp.rehash(4);
		assertEquals(4, lp.tamanoArreglo());
	}
}