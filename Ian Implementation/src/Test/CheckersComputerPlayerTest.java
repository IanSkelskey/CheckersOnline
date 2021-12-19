package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import core.CheckersComputerPlayer;

class CheckersComputerPlayerTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}
	
	public void setUp() throws Exception {
		
	}
	
	public void tearDown() throws Exception {
		
	}
	@Test
	void testCheckersComputerPlayer() {
		CheckersComputerPlayer testAI = new CheckersComputerPlayer();
		assertFalse(testAI.equals(null));
	}

	@Test
	void testUpdateCP() {
		fail("Not yet implemented");
	}

	@Test
	void testNextMove() {
		fail("Not yet implemented");
	}

}
