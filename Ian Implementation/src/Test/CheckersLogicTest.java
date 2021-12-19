package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Assert;

import core.CheckersLogic;

class CheckersLogicTest {
	//Constants
	private final int BOARD_WIDTH = 8, BOARD_HEIGHT = 8;
	private final char BLANK = '_', RED = 'o', BLUE = 'x';
	
	private static char[][] testBoard;
	
	private static CheckersLogic testGame;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass() is running...");
		testGame = new CheckersLogic();

	}
	
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		
	}
	
//	@BeforeEach
//	public static void setup() throws Exception {
//		
//	}
//	
//	@AfterEach
//	public static void tearDown() throws Exception {
//		
//	}

	@Test
	void testInitBoard() {
		int xCount = 0;
		int oCount = 0;
		testGame.initBoard();
		testBoard = testGame.getBoard();
		for(int i = 0; i < BOARD_HEIGHT; i++) {
			for(int j = 0; j < BOARD_WIDTH; j++) {
				System.out.print(testBoard[i][j]);
				if((i+j) % 2 == 1) {
					if(testBoard[i][j] == RED) {
						oCount += 1;
					}else if(testBoard[i][j] == BLUE) {
						xCount += 1;
					}
				}
			}
			System.out.print("\n");
		}
		System.out.println("x count: " + xCount + "; o count: " + oCount);
		assertEquals(oCount, 12);
		assertEquals(xCount, 12);
		assertEquals(testGame.boardRows(), BOARD_HEIGHT);
		assertEquals(testGame.boardColumns(), BOARD_WIDTH);
	}

	@Test
	void testMovablePieces() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBoard() {
		fail("Not yet implemented");
	}

	@Test
	void testBoardRows() {
		assertEquals(testGame.boardRows(), BOARD_HEIGHT);
	}

	@Test
	void testBoardColumns() {
		fail("Not yet implemented");
	}

	@Test
	void testBoardPosition() {
		fail("Not yet implemented");
	}

	@Test
	void testBoardContains() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckMoves() {
		fail("Not yet implemented");
	}

	@Test
	void testPositionForMoveFrom() {
		fail("Not yet implemented");
	}

	@Test
	void testTurn() {
		fail("Not yet implemented");
	}

	@Test
	void testMove() {
		fail("Not yet implemented");
	}

	@Test
	void testMovePiece() {
		fail("Not yet implemented");
	}

	@Test
	void testDeletePiece() {

		testGame.initBoard();
		testBoard = testGame.getBoard();
		for(int i = 0; i < BOARD_HEIGHT; i++) {
			for(int j = 0; j < BOARD_WIDTH; j++) {
				if(testBoard[i][j] == RED) {
					System.out.println("Deleting piece at: (" + j + ", " + i +") owned by : x");
					testGame.deletePiece(RED, new Point(j,i));
				}else if(testBoard[i][j] == BLUE) {
					System.out.println("Deleting piece at: (" + j + ", " + i +") owned by : o");
					testGame.deletePiece(BLUE, new Point(j,i));
				}
				assertTrue(testBoard[i][j] == BLANK);
			}
		}
		//Why are these not empty now? 
		System.out.println(testGame.getPieces(BLUE));
		System.out.println(testGame.getPieces(RED));

		assertTrue(testGame.getPieces(BLUE).isEmpty());
		assertTrue(testGame.getPieces(RED).isEmpty());
	}

	@Test
	void testIsOver() {
		assertFalse(testGame.isOver());
	}

	@Test
	void testCanPieceMove() {
		fail("Not yet implemented");
	}

	@Test
	void testGetInstructions() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMoveType() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateMove() {
		fail("Not yet implemented");
	}

	@Test
	void testValidateInput() {
		fail("Not yet implemented");
	}

	@Test
	void testValidatePairs() {
		fail("Not yet implemented");
	}

	@Test
	void testIsValidLetter() {
		fail("Not yet implemented");
	}

	@Test
	void testIsValidNumber() {
		fail("Not yet implemented");
	}

}
