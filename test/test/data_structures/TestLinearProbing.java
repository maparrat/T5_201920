package test.data_structures;

import static org.junit.Assert.*;

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
	}

	@Test
	public void testDelete()
	{
		setUp2();
	}

	@Test
	public void testKeys()
	{
		setUp2();
	}	
}