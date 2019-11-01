package testsAdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Simple Class test sample
 */
public class AddTest {
	Math math;

	@Before
	public void setUp() throws Exception {
		math = new Math(5, 5);
	}

	@Test
	public void testAdd() {
		Assert.assertEquals(10, math.soma());
	}

}
