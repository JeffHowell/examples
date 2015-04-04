package org.summa.experiments;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link KeyMapKey}.
 */
public class KeyMapKeyTest {
    private KeyMapKey<String, String> uut;

    @Before
    public void setUp() throws Exception {
        uut = new KeyMapKey<String, String>();
    }

    @Test()
    public void testIsEmpty() {
        Assert.assertTrue("expected empty", uut.left.isEmpty());
        Assert.assertFalse("left.isEmpty and right.isEmpty should be the same",
                uut.right.isEmpty() ^ uut.left.isEmpty());
        Assert.assertEquals("size should be zero", 0, uut.left.size());
        Assert.assertTrue("left.size and right.size should be the same",
                uut.left.size() == uut.right.size());
    }

    @Test()
    public void testNotEmpty() {
        uut.left.put("aKey", "aValue");

        Assert.assertFalse("expected not empty", uut.left.isEmpty());
        Assert.assertFalse("left.isEmpty and right.isEmpty should be the same",
                uut.right.isEmpty() ^ uut.left.isEmpty());
        Assert.assertFalse("size should not be zero", 0 == uut.left.size());
        Assert.assertTrue("left.size and right.size should be the same",
                uut.left.size() == uut.right.size());
    }

    @Test()
    public void testClear() {
        uut.left.put("aKey", "aValue");
        uut.left.clear();

        Assert.assertTrue("expected empty", uut.left.isEmpty());
        Assert.assertFalse("left.isEmpty and right.isEmpty should be the same",
                uut.right.isEmpty() ^ uut.left.isEmpty());
        Assert.assertEquals("size should be zero", 0, uut.left.size());
        Assert.assertTrue("left.size and right.size should be the same",
                uut.left.size() == uut.right.size());
    }

    @Test()
    public void testPutLeftGetRight() {
        uut.left.put("LeftKey", "RightKey");

        Assert.assertEquals("Value of RightKey should be LeftKey", "LeftKey",
                uut.right.get("RightKey"));
    }

    @Test()
    public void testPutRightGetLeft() {
        uut.right.put("East", "West");

        Assert.assertEquals("Value of left.\"West\"should be \"East\"", "East",
                uut.left.get("West"));
    }

    @Test()
    public void testKeyAndValueDifferentTypes_StringInteger() {
        Integer integerValue = new Integer(5);
        KeyMapKey<String, Integer> uut2 = new KeyMapKey<String, Integer>();
        uut2.left.put("StringKey", integerValue);

        Assert.assertEquals("expected StringKey", "StringKey",
                uut2.right.get(new Integer(5)));
    }

    @Test()
    public void testAddAll() {
        Map<String, String> toBeAdded = new HashMap<String, String>();
        toBeAdded.put("RedKey", "BlueKey");
        toBeAdded.put("RedFish", "BlueFish");
        toBeAdded.put("BlackQueen", "WhiteQueen");

        uut.left.putAll(toBeAdded);

        Assert.assertEquals("putAll right not correct", "RedFish",
                uut.right.get("BlueFish"));
    }

    @Test()
    public void testRemove() {
        uut.left.put("RedKey", "BlueKey");
        uut.left.put("RedFish", "BlueFish");
        uut.left.put("BlackQueen", "WhiteQueen");
        uut.left.remove("RedFish");

        Assert.assertNull("should have been removed.",
                uut.right.get("BlueFish"));
        Assert.assertNull("should have been removed.", uut.left.get("RedFish"));
    }

    @Test()
    public void testLeftKeySet() {
        uut.left.put("RedKey", "BlueKey");
        uut.left.put("RedFish", "BlueFish");
        uut.left.put("BlackQueen", "WhiteQueen");
        Set<String> leftKeySet = uut.left.keySet();

        Assert.assertEquals("should be 3 keys", 3, leftKeySet.size());
        Assert.assertTrue("should contain RedKey",
                leftKeySet.contains("RedKey"));
        Assert.assertTrue("should contain RedFish",
                leftKeySet.contains("RedFish"));
        Assert.assertTrue("should contain BlackQueen",
                leftKeySet.contains("BlackQueen"));
    }

    @Test()
    public void testRightKeySet() {
        uut.left.put("RedKey", "BlueKey");
        uut.left.put("RedFish", "BlueFish");
        uut.left.put("BlackQueen", "WhiteQueen");
        Set<String> rightKeySet = uut.right.keySet();

        Assert.assertEquals("should be 3 keys", 3, rightKeySet.size());
        Assert.assertTrue("should contain BlueKey",
                rightKeySet.contains("BlueKey"));
        Assert.assertTrue("should contain BlueFish",
                rightKeySet.contains("BlueFish"));
        Assert.assertTrue("should contain WhiteQueen",
                rightKeySet.contains("WhiteQueen"));
    }

    @Test()
    public void testLeftValues() {
        uut.left.put("RedKey", "BlueKey");
        uut.left.put("RedFish", "BlueFish");
        uut.left.put("BlackQueen", "WhiteQueen");

        Collection<String> leftValues = uut.left.values();
        Assert.assertEquals("should be 3 keys", 3, leftValues.size());
        Assert.assertTrue("should contain BlueKey",
                leftValues.contains("BlueKey"));
        Assert.assertTrue("should contain BlueFish",
                leftValues.contains("BlueFish"));
        Assert.assertTrue("should contain WhiteQueen",
                leftValues.contains("WhiteQueen"));
    }

    @Test()
    public void testRightValues() {
        uut.left.put("RedKey", "BlueKey");
        uut.left.put("RedFish", "BlueFish");
        uut.left.put("BlackQueen", "WhiteQueen");
        Collection<String> rightValues = uut.right.values();

        Assert.assertEquals("should be 3 keys", 3, rightValues.size());
        Assert.assertTrue("should contain RedKey",
                rightValues.contains("RedKey"));
        Assert.assertTrue("should contain RedFish",
                rightValues.contains("RedFish"));
        Assert.assertTrue("should contain BlackQueen",
                rightValues.contains("BlackQueen"));
    }

    @Test()
    public void testContains() {
        uut.left.put("East", "West");
        uut.left.put("RedFish", "BlueFish");

        Assert.assertTrue("should contain East", uut.left.containsKey("East"));
        Assert.assertTrue("should contain West", uut.left.containsValue("West"));
        Assert.assertTrue("should contain East", uut.right.containsKey("West"));
        Assert.assertTrue("should contain East",
                uut.right.containsValue("East"));
    }

    @Test()
    public void testPutSameKeyTwice() {
        Assert.assertNull("first put of key Red should return null",
                uut.left.put("Red", "Green"));
        Assert.assertNotNull("second put of key Red should not return null",
                uut.left.put("Red", "Blue"));
        Assert.assertEquals("subsequent put of key Red should previous value",
                "Blue", uut.left.put("Red", "Yellow"));
        Assert.assertEquals(
                "multiple put of same key should only have 1 entry in map", 1,
                uut.left.size());
    }

    @Test
    public void usage() throws Exception {
        // create the mapper that will relate a primary key from System A to the
        // corresponding primary key in System B.
        KeyMapKey<String, Long> mapSystemAAndSystemB = new KeyMapKey<String, Long>();

        // systemBKeyForSystemAKey is a map keyed on primary keys from system A.
        // If we invoke
        // systemBKeyForSystemAKey.get(<some-primary-key-from-system-a>), the
        // returned value is
        // the related primary key from System B
        Map<String, Long> systemBKeyForSystemAKey = mapSystemAAndSystemB.left;

        // And, the converse point of view...
        // systemAKeyForSystemBKey is a map keyed on primary keys from system B.
        // If we invoke
        // systemAKeyForSystemBKey.get(<some-primary-key-from-system-b>), the
        // returned value is
        // the related primary key from System A
        Map<Long, String> systemAKeyForSystemBKey = mapSystemAAndSystemB.right;

        // so let's say that System A and System B each have a concept of a
        // User. In System A, the User is identified with a PersonNumber. In
        // System B,
        // the user is identified with a UserID.
        //
        // We can initialize the KeyMapKey with primary keys that relate each of
        // the System A users with System B users.
        //
        // Create some keys for the two systems:
        // User Joe:
        // System A calls Joe "JosephSmith"
        // System B calls Joe "99445"
        String userId1 = "JosephSmith";
        Long personNumber1 = 99445L;

        // User Sally:
        // System A calls Sally "X135"
        // System B calls Sally "99448"
        String userId2 = "X135";
        Long personNumber2 = 994438L;

        // put Joe and Sally's primary keys into the mapper
        systemBKeyForSystemAKey.put(userId1, personNumber1);
        // note that the line above could be replaced with
        //
        // systemAKeyForSystemBKey.put(personNumber1, userId1)
        //
        // which would have exactly the same effect on the state of the mapper

        systemBKeyForSystemAKey.put(userId2, personNumber2);

        // Later, we are processing some work for Joe. System B calls out to
        // System A, passing Joe's PersonNumber. For system A to proceed, it
        // must translate Joe's person number into a UserID.
        String joeInSysA = systemAKeyForSystemBKey.get(personNumber1);
        Assert.assertEquals(userId1, joeInSysA);
    }
}