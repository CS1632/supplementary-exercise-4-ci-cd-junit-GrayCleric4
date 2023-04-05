package edu.pitt.cs;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	@Before
	public void setUp() throws Exception 
	{
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat.bugInjectionOn = true;

		// INITIALIZE THE TEST FIXTURE
		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create an unrented Cat with ID 1 and name "Jennyanydots", assign to c1
		// TODO: Fill in
		// c1 = new Cat(1, "Jennyanydots"); 
		c1 = Mockito.mock(Cat.class); 
		Mockito.when(c1.getId()).thenReturn(1);
		Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");
		Mockito.when(c1.getRented()).thenReturn(false);

		// 3. Create an unrented Cat with ID 2 and name "Old Deuteronomy", assign to c2
		// TODO: Fill in
		// c2 = new Cat(2, "Old Deuteronomy");
		c2 = Mockito.mock(Cat.class); 
		Mockito.when(c2.getId()).thenReturn(2); 
		Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");
		Mockito.when(c2.getRented()).thenReturn(false);

		// 4. Create an unrented Cat with ID 3 and name "Mistoffelees", assign to c3
		// TODO: Fill in
		// c3 = new Cat(3, "Mistoffelees");
		c3 = Mockito.mock(Cat.class); 
		Mockito.when(c3.getId()).thenReturn(3);
		Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");
		Mockito.when(c3.getRented()).thenReturn(false);
	}

	@After
	public void tearDown() throws Exception 
	{
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 * </pre>
	 */

	@Test
	public void testGetCatNullNumCats0() 
	{
		assertNull("Actual is not null", r.getCat(2));
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 */

	@Test
	public void testGetCatNumCats3() 
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Cat resultCat = r.getCat(2);

		assertNotNull("Return value was null", resultCat);
		assertEquals("The ID was not 2", 2, resultCat.getId());
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats0() 
	{
		assertFalse("Return value was true", r.catAvailable(2)); 
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1 is added to r using addCat(Cat c).
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */
	@Test
	public void testCatAvailableFalseNumCats1() 
	{
		r.addCat(c1);
		assertFalse("Return value was true", r.catAvailable(2)); 
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatAvailableTrueNumCats3() 
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		r.rentCat(3);
		Mockito.when(c3.getRented()).thenReturn(true);

		assertTrue("Cat was not available but it should be", r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats3() 
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		r.rentCat(2);
		Mockito.when(c2.getRented()).thenReturn(true);

		assertFalse("Cat 2 should not be available but was", r.catAvailable(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatExistsFalseNumCats0() 
	{
		assertFalse("Return was true when it should be false", r.catExists(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatExistsTrueNumCats3() 
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		assertTrue("Cat didn't exist but should have", r.catExists(2));
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats0()
	{
		assertEquals("Not equal to empty string", "", r.listCats());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats3()
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		
		assertEquals("Actual list is not the same as 'ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n'",
		"ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n", r.listCats());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testRentCatFailureNumCats0() 
	{
		assertFalse("Should be false but was true", r.rentCat(2));
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testRentCatFailureNumCats3() 
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c2.getRented()).thenReturn(true);

		assertFalse("Was true when false expected", r.rentCat(2));
		Mockito.verify(c1, Mockito.times(0)).rentCat();;
		Mockito.verify(c2, Mockito.times(0)).rentCat();;
		Mockito.verify(c3, Mockito.times(0)).rentCat();;
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testReturnCatFailureNumCats0() 
	{
		assertFalse("Was true, false expected", r.returnCat(2)); 
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testReturnCatNumCats3()
	{
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);

		Mockito.when(c2.getRented()).thenReturn(true);

		assertTrue("Expected true but was false", r.returnCat(2));
		Mockito.verify(c2, Mockito.times(1)).returnCat();
		Mockito.verify(c1, Mockito.times(0)).returnCat();
		Mockito.verify(c3, Mockito.times(0)).returnCat();
	}
}
