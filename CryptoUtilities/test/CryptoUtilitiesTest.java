import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Allen Zheng
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testIsWitnessToCompositeness1() {
        NaturalNumber w = new NaturalNumber2(2);
        final NaturalNumber wExpected = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(4);
        final NaturalNumber nExpected = new NaturalNumber2(4);
        boolean composite = CryptoUtilities.isWitnessToCompositeness(w, n);
        boolean compositeExpected = true;
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(compositeExpected, composite);
    }

    @Test
    public void testIsWitnessToCompositeness2() {
        NaturalNumber w = new NaturalNumber2(2);
        final NaturalNumber wExpected = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(17);
        final NaturalNumber nExpected = new NaturalNumber2(17);
        boolean composite = CryptoUtilities.isWitnessToCompositeness(w, n);
        boolean compositeExpected = false;
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(compositeExpected, composite);
    }

    @Test
    public void testisPrime1v1() {
        NaturalNumber n = new NaturalNumber2(15);
        final NaturalNumber nExpected = new NaturalNumber2(15);
        boolean prime = CryptoUtilities.isPrime1(n);
        boolean primeExpected = false;
        assertEquals(nExpected, n);
        assertEquals(prime, primeExpected);
    }

    @Test
    public void isPrime1v2() {
        NaturalNumber n = new NaturalNumber2(19);
        final NaturalNumber nExpected = new NaturalNumber2(19);
        boolean prime = CryptoUtilities.isPrime1(n);
        boolean primeExpected = true;
        assertEquals(nExpected, n);
        assertEquals(prime, primeExpected);
    }

    @Test
    public void testisPrime2v1() {
        // 561 sometimes can be tested to be "prime" theoretically but should
        // considered as a composite. (Challenging case)
        NaturalNumber n = new NaturalNumber2(561);
        final NaturalNumber nExpected = new NaturalNumber2(561);
        boolean prime = CryptoUtilities.isPrime2(n);
        boolean primeExpected = false;
        assertEquals(nExpected, n);
        assertEquals(prime, primeExpected);
    }

    @Test
    public void testisPrime2v2() {
        // 561 sometimes can be tested to be "prime" theoretically but should
        // considered as a composite. (Challenging case)
        NaturalNumber n = new NaturalNumber2(19);
        final NaturalNumber nExpected = new NaturalNumber2(19);
        boolean prime = CryptoUtilities.isPrime2(n);
        boolean primeExpected = true;
        assertEquals(nExpected, n);
        assertEquals(prime, primeExpected);
    }

    @Test
    public void testGenerateNextLikelyPrime1() {
        // 561 sometimes can be tested to be "prime" theoretically but should
        // considered as a composite. (Challenging case)
        NaturalNumber n = new NaturalNumber2(11);
        final NaturalNumber nExpected = new NaturalNumber2(11);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    @Test
    public void testGenerateNextLikelyPrime2() {
        // 561 sometimes can be tested to be "prime" theoretically but should
        // considered as a composite. (Challenging case)
        NaturalNumber n = new NaturalNumber2(90);
        final NaturalNumber nExpected = new NaturalNumber2(97);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

}
