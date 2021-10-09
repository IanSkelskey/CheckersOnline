package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.CheckersLogic;

/**
 * CheckersLogicTest Class
 * Tests all major methods of CheckersLogic Class
 * @author Ian Skelskey
 * @version 1.0
 * @since 10-7-2021
 */
class CheckersLogicTest {
	
	CheckersLogic testGame;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Before eaching...");
		CheckersLogic testGame = new CheckersLogic();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/** Test method for {@link core.CheckersLogic#CheckersLogic()}. */
	@Test
	void testCheckersLogic() {
		System.out.println("testCheckersLogic()...");
	}

	/** Test method for {@link core.CheckersLogic#getBoard()}. */
	@Test
	void testGetBoard() {
		System.out.println("testGetBoard()...");

	}

	/** Test method for {@link core.CheckersLogic#move(int, int, int, int)}. */
	@Test
	void testMove() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#getMoveType(int)}. */
	@Test
	void testGetMoveType() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#turn()}. */
	@Test
	void testTurn() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#isOver()}. */
	@Test
	void testIsOver() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CheckersLogic#setActivePlayer(core.CheckersPlayer)}.
	 */
	@Test
	void testSetActivePlayer() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#getActivePlayer()}. */
	@Test
	void testGetActivePlayer() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#switchPlayer()}. */
	@Test
	void testSwitchPlayer() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#passInput(java.lang.String)}. */
	@Test
	void testPassInput() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#clearInput()}. */
	@Test
	void testClearInput() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#splitInstructions()}. */
	@Test
	void testSplitInstructions() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#asRow(int)}. */
	@Test
	void testAsRow() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#asCol(char)}. */
	@Test
	void testAsCol() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#validateMove()}. */
	@Test
	void testValidateMove() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#validateInput()}. */
	@Test
	void testValidateInput() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CheckersLogic#validatePairs(java.lang.String[])}.
	 */
	@Test
	void testValidatePairs() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#isValidLetter(char)}. */
	@Test
	void testIsValidLetter() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#isValidNumber(char)}. */
	@Test
	void testIsValidNumber() {
		fail("Not yet implemented");
	}

	/** Test method for {@link core.CheckersLogic#getPlayer(char)}. */
	@Test
	void testGetPlayer() {
		fail("Not yet implemented");
	}
}
