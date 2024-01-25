package arch.sm213.machine.student;

import machine.AbstractMainMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainMemoryTest {
    MainMemory testMainMemory;
    byte[] bytes1;
    byte[] bytes2;
    int int1;
    int int2;

    @BeforeEach
    void setup() {
        testMainMemory = new MainMemory(17);
        bytes1 = new byte[]{(byte) 0x80, (byte) 0x80, (byte) 0x08, (byte) 0x08};
        int1 = -2139092984;
        bytes2 = new byte[]{(byte) 0x10, (byte) 0x10, (byte) 0xff, (byte) 0xff};
        int2 = 269549567;
    }

    @Test
    //Tests that the correct exception is thrown for the Set method
    void testSetExceptions() {
        //test set() at an address that is not aligned.
        try {
            testMainMemory.set(14, bytes1);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }

        //test set() at an address that is aligned but is not in memory.
        try {
            testMainMemory.set(20, bytes1);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }

        //test set() at an address that is aligned and in memory at address but address+3 exceeds size of memory.
        try {
            testMainMemory.set(16, bytes1);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }
    }

    @Test
    //Tests that the correct exception is thrown for the Get method
    void testGetExceptions() {
        //Set a test value somewhere in memory.
        try {
            testMainMemory.set(12, bytes1);
        } catch (Exception E) {
            fail("Exception thrown");
        }

        //test get() at an address that is not aligned.
        try {
            testMainMemory.get(12,5);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }

        //test set() at an address that is aligned but is not in memory.
        try {
            testMainMemory.get(20,4);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }

        //test set() at an address that is aligned and in memory at address but address+3 exceeds size of memory.
        try {
            testMainMemory.get(16,4);
            fail("No exception thrown");
        } catch (AbstractMainMemory.InvalidAddressException E) {
            //pass
        } catch (Exception E) {
            fail("Incorrect exception thrown");
        }
    }

    @Test
    //Tests set and get methods properly put set values into memory and retrieves the correct values.
    void testSetAndGet() {

        try {
            testMainMemory.set(0, bytes1);
            byte[] testBytes1 = testMainMemory.get(0, 4);
            for (int i = 0; i < bytes1.length; i++) {
                assertEquals(testBytes1[i], bytes1[i]);
            }
        } catch (Exception E) {
            fail("Exception thrown");
        }
    }

    @Test
    //Tests isAccessAligned by passing in passing and failing address/lengths
    void isAccessAlignedTest() {
        assertTrue(testMainMemory.isAccessAligned(0, 4));
        assertFalse(testMainMemory.isAccessAligned(1, 4));
        assertFalse(testMainMemory.isAccessAligned(2, 4));
        assertTrue(testMainMemory.isAccessAligned(2, 2));
        assertFalse(testMainMemory.isAccessAligned(3, 4));
        assertTrue(testMainMemory.isAccessAligned(4, 4));
    }

    @Test
    //tests that IntegerToBytes performs the correct conversion
    void testIntegerToBytes() {
        byte[] testBytes1 = new byte[1];
        byte[] testBytes2 = new byte[1];;
        try {
            testBytes1 = testMainMemory.integerToBytes(int1);
            testBytes2 = testMainMemory.integerToBytes(int2);
        } catch (Exception E) {
            fail("Exception thrown");
        }
        assertEquals(4, testBytes1.length);
        for (int i = 0; i < 4; i++) {
            assertEquals(testBytes1[i], bytes1[i]);
        }
        assertEquals(4, testBytes2.length);
        for (int i = 0; i < 4; i++) {
            assertEquals(testBytes2[i], bytes2[i]);
        }
    }

    @Test
    //tests that BytesToInteger performs the correct conversion
    void testBytesToInteger() {
        int testInt1 = testMainMemory.bytesToInteger((byte) 0x80, (byte) 0x80, (byte) 0x08, (byte) 0x08);
        int testInt2 = testMainMemory.bytesToInteger((byte) 0x10, (byte) 0x10, (byte) 0xff, (byte) 0xff);
        assertEquals(int1, testInt1);
        assertEquals(int2, testInt2);

    }
}
