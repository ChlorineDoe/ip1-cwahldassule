package edu.brandeis.cosi103a.ip1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import edu.brandeis.cosi103a.ip1.Dicegame.Player;
import edu.brandeis.cosi103a.ip1.Dicegame.App;
import edu.brandeis.cosi103a.ip1.Dicegame.Dice;

public class AppTest {

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    // --- Player Tests ---

    @Test
    public void testPlayerInitialization() {
        Player p = new Player(5, "TestBot");
        assertEquals("TestBot", p.name, "Player name should be set correctly.");
        assertEquals(5, p.maxTurns, "Max turns should be set correctly.");
        assertEquals(0, p.score, "Initial score should be 0.");
        assertEquals(0, p.turns, "Initial turns should be 0.");
    }

    @Test
    public void testPlayerAddScore() {
        Player p = new Player(10, "TestBot");
        p.addScore(5);
        assertEquals(5, p.score, "Score should update to 5.");
        
        p.addScore(10);
        assertEquals(15, p.score, "Score should accumulate to 15.");
    }

    @Test
    public void testPlayerUpdateTurn() {
        Player p = new Player(3, "TestBot");
        p.updateTurn();
        assertEquals(1, p.turns, "Turns should increment to 1.");
        
        p.updateTurn();
        assertEquals(2, p.turns, "Turns should increment to 2.");
    }

    @Test
    public void testPlayerGameOver() {
        Player p = new Player(2, "TestBot");
        
        // Turn 0
        assertFalse(p.gameOver(), "Game should not be over at 0 turns.");
        
        // Turn 1
        p.updateTurn();
        assertFalse(p.gameOver(), "Game should not be over at 1 turn (max 2).");
        
        // Turn 2
        p.updateTurn();
        assertTrue(p.gameOver(), "Game should be over when turns >= maxTurns.");
    }

    // --- Dice Tests ---

    @Test
    public void testDiceRollRange() {
        // Based on logic: rand.nextInt(upper) + lower
        // If Dice(1, 6): nextInt(6) is 0-5. Result is 1-6.
        Dice d = new Dice(1, 6);
        
        for (int i = 0; i < 50; i++) {
            int result = d.roll();
            assertTrue(result >= 1 && result <= 6, "Roll should be between 1 and 6. Got: " + result);
        }
    }

    @Test
    public void testDiceInvalidValues() {
        // Lower (10) > Upper (5) should throw IllegalArgumentException
        Dice d = new Dice(10, 5);
        
        // Changed Error.class to IllegalArgumentException.class
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            d.roll();
        });
        
        assertEquals("Invalid Dice Values", exception.getMessage());
    }

    // --- App (Integration) Tests ---

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void testTurnLogicStopEarly() {
        // Simulate a turn where the user decides NOT to roll again ("N")
        // The turn method creates a new Scanner, so we must redirect System.in
        String simulatedInput = "N" + System.lineSeparator();
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);

        // Capture output to verify prompts (optional, but good for debugging)
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Player p = new Player(5, "Runner");
        Dice d = new Dice(1, 6);

        // Execute the turn logic
        App.turn(p, d);

        // Verify that the score was updated (at least one roll occurred)
        assertTrue(p.score > 0, "Player should have some score after a turn.");
        
        // Verify output contains the prompt
        String output = outContent.toString();
        assertTrue(output.contains("Do you want to roll again?"), "Output should prompt for reroll.");
        assertTrue(output.contains("Total score:"), "Output should show total score.");
    }
}