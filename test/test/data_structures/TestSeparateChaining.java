package test.data_structures;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.SeparateChaining;

public class TestSeparateChaining
{
	private SeparateChaining<Integer, String> sc;

	@Before
	public void setUp1() 
	{
		sc = new SeparateChaining<>(5);
	}
	public void setUp2() 
	{
		sc.put(1, "A");
		sc.put(2, "B");
		sc.put(3, "C");
		sc.put(4, "D");
		sc.put(5, "E");
		sc.put(6, "F");
		sc.put(7, "G");
		sc.put(8, "H");
		sc.put(9, "I");
		sc.put(10, "J");
	}

	@Test
	public void testPut()
	{
		sc.put(5, "A");
		sc.put(10, "B");
		sc.put(11, "C");
		sc.put(11, "D");

		assertTrue(sc.contains(11));
		assertTrue(sc.size() == 3);
		assertNotNull(sc.get(10));
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