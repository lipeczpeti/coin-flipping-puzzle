package coins.state;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {

    private Coins state1 = new Coins(7, 3); // the original initial state

    private final Coins state2; // the goal state
    {
        BitSet bs = new BitSet(7);
        bs.set(0, 7);
        state2 = new Coins(7, 3, bs);
    }

    @Test
    void getFlips() {
        List<BitSet> flips = Coins.generateFlips(7, 3);
        assertEquals(flips.size(), state1.getFlips().size());
    }

    @Test
    void isGoalTest() {
        assertFalse(state1.isGoal());
        assertTrue(state2.isGoal());
    }

    @Test
    void generateFlipsThrowingExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(0, 4));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(4, 0));
        assertThrows(IllegalArgumentException.class, () -> Coins.generateFlips(2, 3));
    }

    @Test
    void canFlipShouldBeFalse() {
        BitSet bs = new BitSet(10);
        assertFalse(state1.canFlip(bs));
        bs = new BitSet(7);
        bs.set(2);
        assertFalse(state1.canFlip(bs));
    }

    @Test
    void generateFlipsTest() {
        long pieces = CombinatoricsUtils.binomialCoefficient(12, 5);
        List<BitSet> flips = Coins.generateFlips(12, 5);
        assertEquals((int) pieces, flips.size());
        pieces = CombinatoricsUtils.binomialCoefficient(6, 2);
        flips = Coins.generateFlips(6, 2);
        assertEquals((int) pieces, flips.size());
    }

    @Test
    void flip() {
        BitSet bs = new BitSet(5);
        bs.set(0, 3);
        state1 = new Coins(9, 5);
        state1.flip(bs);
        assertEquals(bs, state1.getCoins());
        BitSet bs2 = new BitSet(5);
        bs2.set(0, 4);
        state1.flip(bs2);
        BitSet bs3 = new BitSet(5);
        bs3.set(3);
        assertEquals(bs3, state1.getCoins());
    }

    @Test
    void canFlipTest() {
        BitSet bs = new BitSet(10);
        assertFalse(state1.canFlip(bs));
        bs.set(0, 3);
        assertTrue(state1.canFlip(bs));
        assertTrue(state2.canFlip(bs));
        bs = new BitSet(4);
        assertFalse(state1.canFlip(bs));
        assertFalse(state1.canFlip(bs));
        bs = new BitSet(6);
        bs.set(0, 3);
        assertTrue(state1.canFlip(bs));
        assertTrue(state2.canFlip(bs));
        bs = new BitSet(3);
        bs.set(1, 2);
        assertFalse(state1.canFlip(bs));
        assertFalse(state2.canFlip(bs));
    }
}