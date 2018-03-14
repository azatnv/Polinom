import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolinomTest {
    @Test
    void valuePolinom() {
        assertEquals(9, new Polinom("x^3+1").valuePolinom(2));
        assertEquals(-1, new Polinom("-1").valuePolinom(1));
        assertEquals(0, new Polinom("0").valuePolinom(10));
        assertEquals(6, new Polinom("x-10").valuePolinom(16));
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
        assertEquals(new Polinom("1"), new Polinom("0").sum(new Polinom("1")));
        assertEquals(new Polinom("x"), new Polinom("x").sum(new Polinom("0")));
        assertEquals(new Polinom("2x"), new Polinom("x").sum(new Polinom("x")));
        assertEquals(new Polinom("5x^3"), new Polinom("5x^3-x+10").sum(new Polinom("x-10")));
        assertEquals(new Polinom("0"), new Polinom("x").sum(new Polinom("-x")));
        assertEquals(new Polinom("0"), new Polinom("x^3-1").sum(new Polinom("-x^3+1")));
    }

    @Test
    void minus() {
        assertEquals(new Polinom("1"), new Polinom("1").minus(new Polinom("0")));
        assertEquals(new Polinom("x"), new Polinom("x").minus(new Polinom("0")));
        assertEquals(new Polinom("0"), new Polinom("x").minus(new Polinom("x")));
        assertEquals(new Polinom("5x^3"), new Polinom("5x^3-x+10").minus(new Polinom("-x+10")));
        assertEquals(new Polinom("0"), new Polinom("x^3-1").minus(new Polinom("x^3-1")));
    }

    @Test
    void multiply() {
    }

    @Test
    void divide() {
    }

    @Test
    void remainder() {
    }

    @Test
    void equals() {
        assertTrue(new Polinom("x").equals(new Polinom("x")));
        assertTrue(new Polinom("x+10").equals(new Polinom("x+10")));
        assertTrue(new Polinom("x^2+3x+10").equals(new Polinom("x^2+3x+10")));
        assertFalse(new Polinom("x").equals(new Polinom("x^2")));
        assertFalse(new Polinom("11").equals(new Polinom("10")));
    }

}