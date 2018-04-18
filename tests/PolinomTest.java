import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolinomTest {
    @Test
    void valuePolinom() {
        assertEquals(9, new Polinom("x^3+1").valuePolinom(2));
        assertEquals(-1, new Polinom("-1").valuePolinom(1));
        assertEquals(0, new Polinom("0").valuePolinom(10));
        assertEquals(6, new Polinom("x-10").valuePolinom(16));
        assertEquals(4, new Polinom("3x-2").valuePolinom(2));
        assertEquals(-6, new Polinom("-3x").valuePolinom(2));
        assertEquals(4, new Polinom("x").valuePolinom(4));
        assertEquals(-4, new Polinom("-x").valuePolinom(4));
        assertEquals(6, new Polinom("2x").valuePolinom(3));
        assertEquals(9, new Polinom("x^2").valuePolinom(3));
        assertEquals(48, new Polinom("3x^4").valuePolinom(2));
        assertEquals(54, new Polinom("2x^3").valuePolinom(3));
        assertEquals(57, new Polinom("2x^3+x").valuePolinom(3));
        assertEquals(5, new Polinom("x^2+x-1").valuePolinom(2));
        assertEquals(5, new Polinom("2x^3+x^2+3x-1").valuePolinom(1));
    }

    @Test
    void sum() {
        assertEquals(new Polinom(new int[] {1}), new Polinom(new int[] {0}).sum(new Polinom(new int[] {1})));
        assertEquals(new Polinom(new int[] {0, 1}), new Polinom(new int[] {0, 1}).sum(new Polinom(new int[] {0})));
        assertEquals(new Polinom(new int[] {0, 2}), new Polinom(new int[] {0, 1}).sum(new Polinom(new int[] {0, 1})));
        assertEquals(new Polinom(new int[] {0, 0, 0, 5}), new Polinom(new int[] {10, -1, 0, 5}).sum(new Polinom(new int[] {-10, 1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {0, 1}).sum(new Polinom(new int[] {0, -1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {-1, 0, 0, 1}).sum(new Polinom(new int[] {1, 0, 0, -1})));
    }

    @Test
    void minus() {
        assertEquals(new Polinom(new int[] {1}), new Polinom(new int[] {1}).minus(new Polinom(new int[] {0})));
        assertEquals(new Polinom(new int[] {0, 1}), new Polinom(new int[] {0, 1}).minus(new Polinom(new int[] {0})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {0, 1}).minus(new Polinom(new int[] {0, 1})));
        assertEquals(new Polinom(new int[] {0, 0, 0, 5}), new Polinom(new int[] {10, -1, 0, 5}).minus(new Polinom(new int[] {10, -1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {-1, 0, 0, 1}).minus(new Polinom(new int[] {-1, 0, 0, 1})));
    }

    @Test
    void multiply() {
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {1, 0, 1}).multiply(new Polinom(new int[] {0})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {1}).multiply(new Polinom(new int[] {0})));
        assertEquals(new Polinom(new int[] {0, 1}), new Polinom(new int[] {0, 1}).multiply(new Polinom(new int[] {1})));
        assertEquals(new Polinom(new int[] {-1, 0, 1}), new Polinom(new int[] {-1, 1}).multiply(new Polinom(new int[] {1, 1})));
        assertEquals(new Polinom(new int[] {-10, 11, 6}), new Polinom(new int[] {-2, 3}).multiply(new Polinom(new int[] {5, 2})));
        assertEquals(new Polinom(new int[] {-35, 10, 21, -4, -2, 3, 1}), new Polinom(new int[] {7, -2, 0, 1}).multiply(new Polinom(new int[] {-5, 0, 3, 1})));
    }
    @Test
    void divide() {
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {-3, 1}).divide(new Polinom(new int[] {1, 0, 1})));
        assertEquals(new Polinom(new int[] {0, 1}), new Polinom(new int[] {0, 1}).divide(new Polinom(new int[] {1})));
        assertEquals(new Polinom(new int[] {-2, 3}), new Polinom(new int[] {-10, 11, 6}).divide(new Polinom(new int[] {5, 2})));
        assertEquals(new Polinom(new int[] {5, 2}), new Polinom(new int[] {-10, 11, 6}).divide(new Polinom(new int[] {-2, 3})));
        assertEquals(new Polinom(new int[] {1, -4, 2}), new Polinom(new int[] {-3, -22, 23, -10, 2}).divide(new Polinom(new int[] {5, -3, 1})));
        assertEquals(new Polinom(new int[] {7, -2, 0, 1}), new Polinom(new int[] {-35, 10, 21, -4, -2, 3, 1}).divide(new Polinom(new int[] {-5, 0, 3, 1})));
    }

    @Test
    void remainder() {
        assertEquals(new Polinom(new int[] {-3, 1}), new Polinom(new int[] {-3, 1}).remainder(new Polinom(new int[] {1, 0, 1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {0, 1}).remainder(new Polinom(new int[] {1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {-10, 11, 6}).remainder(new Polinom(new int[] {5, 2})));
        assertEquals(new Polinom(new int[] {-8, 1}), new Polinom(new int[] {-3, -22, 23, -10, 2}).remainder(new Polinom(new int[] {5, -3, 1})));
        assertEquals(new Polinom(new int[] {0}), new Polinom(new int[] {-35, 10, 21, -4, -2, 3, 1}).remainder(new Polinom(new int[] {-5, 0, 3, 1})));
    }

    @Test
    void equals() {
        assertTrue(new Polinom(new int[] {0, 1}).equals(new Polinom(new int[] {0, 1})));
        assertTrue(new Polinom(new int[] {10, 1}).equals(new Polinom(new int[] {10, 1})));
        assertTrue(new Polinom(new int[] {10, 3, 1}).equals(new Polinom(new int[] {10, 3, 1})));
        assertFalse(new Polinom(new int[] {0, 1}).equals(new Polinom(new int[] {10, 1})));
        assertFalse(new Polinom(new int[] {10}).equals(new Polinom(new int[] {11})));
    }

}