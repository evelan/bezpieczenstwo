package pl.pwr.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Examples are from https://pl.wikipedia.org/wiki/Protokół_Diffiego-Hellmana
 * Created by Evelan on 24/10/2016.
 */
public class DHProtocolTest {

    private BigInteger p, g;
    private BigInteger aliceSecret;
    private BigInteger bobSecret;
    private BigInteger expectedResultForBoth;

    @Before
    public void setup() {
        p = BigInteger.valueOf(23);
        g = BigInteger.valueOf(5);
        aliceSecret = BigInteger.valueOf(6);
        bobSecret = BigInteger.valueOf(15);
        expectedResultForBoth = BigInteger.valueOf(2);
    }

    @Test
    public void whenAliceCalculateSecret_correct() throws Exception {
        BigInteger aliceResult = DHProtocol.calculateSecret(g, aliceSecret, p);
        BigInteger expectedResult = BigInteger.valueOf(8);
        Assert.assertEquals(expectedResult, aliceResult);
    }

    @Test
    public void whenBobCalculateSecret_correct() throws Exception {
        BigInteger bobResult = DHProtocol.calculateSecret(g, bobSecret, p);
        BigInteger expectedResult = BigInteger.valueOf(19);
        Assert.assertEquals(expectedResult, bobResult);
    }


    @Test
    public void whenBobCalculateS_correct() throws Exception {
        BigInteger bobResult = DHProtocol.calculateSecret(BigInteger.valueOf(8), bobSecret, p);
        Assert.assertEquals(expectedResultForBoth, bobResult);
    }

    @Test
    public void whenAliceCalculateS_correct() throws Exception {
        BigInteger aliceResult = DHProtocol.calculateSecret(BigInteger.valueOf(19), aliceSecret, p);
        Assert.assertEquals(expectedResultForBoth, aliceResult);
    }

}