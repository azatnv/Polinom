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
        assertEquals(new Polinom("0"), new Polinom("x^2+1").multiply(new Polinom("0")));
        assertEquals(new Polinom("0"), new Polinom("1").multiply(new Polinom("0")));
        assertEquals(new Polinom("x"), new Polinom("x").multiply(new Polinom("1")));
        assertEquals(new Polinom("x^2-1"), new Polinom("x-1").multiply(new Polinom("x+1")));
        assertEquals(new Polinom("6x^2+11x-10"), new Polinom("3x-2").multiply(new Polinom("2x+5")));
        assertEquals(new Polinom("x^6+3x^5-2x^4-4x^3+21x^2+10x-35"), new Polinom("x^3-2x+7").multiply(new Polinom("x^3+3x^2-5")));
    }

    @Test
    void divide() {
        assertEquals(new Polinom("0"), new Polinom("x-3").divide(new Polinom("x^2+1")));
        assertEquals(new Polinom("x"), new Polinom("x").divide(new Polinom("1")));
        assertEquals(new Polinom("3x-2"), new Polinom("6x^2+11x-10").divide(new Polinom("2x+5")));
        assertEquals(new Polinom("2x+5"), new Polinom("6x^2+11x-10").divide(new Polinom("3x-2")));
        assertEquals(new Polinom("2x^2-4x+1"), new Polinom("2x^4-10x^3+23x^2-22x-3").divide(new Polinom("x^2-3x+5")));
        assertEquals(new Polinom("x^3-2x+7"), new Polinom("x^6+3x^5-2x^4-4x^3+21x^2+10x-35").divide(new Polinom("x^3+3x^2-5")));
    }

    @Test
    void remainder() {
        assertEquals(new Polinom("x-3"), new Polinom("x-3").remainder(new Polinom("x^2+1")));
        assertEquals(new Polinom("0"), new Polinom("x").remainder(new Polinom("1")));
        assertEquals(new Polinom("0"), new Polinom("6x^2+11x-10").remainder(new Polinom("2x+5")));
        assertEquals(new Polinom("x-8"), new Polinom("2x^4-10x^3+23x^2-22x-3").remainder(new Polinom("x^2-3x+5")));
        assertEquals(new Polinom("0"), new Polinom("x^6+3x^5-2x^4-4x^3+21x^2+10x-35").remainder(new Polinom("x^3+3x^2-5")));
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