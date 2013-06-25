package trees;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.Iterator;

/**
 *
 */
public class BinarySearchTreeTest {

    @Test
    public void testAdd() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i = 0; i < 100; i++) {
            assertTrue(bst.add(i));
            assertTrue(bst.contains(i));
            assertTrue(bst.size == i + 1);

        }
        for (int i = 0; i < 100; i++) {
            assertTrue(bst.contains(i));
        }
    }

    @Test
    public void testRemove() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i = 0; i < 10; i++) {
            bst.add(i);
            //bst.printKeys();
        }
        System.out.println("Starting removal");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                // remove even numbers
                System.out.println("Removing " + i);
                assertTrue(bst.remove(i));
                int expectedSize = 10 - (i/2+1);
                System.out.println("Expected size: " + expectedSize);
                //bst.printKeys();
                assertTrue(bst.size() == expectedSize);
            }
        }
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                // we are at odd index. make sure previous even does not exist
                System.out.println("Expected to contain: " + i);
                System.out.println("Expected not to contain: " + (i - 1));
                assertFalse(bst.contains(i - 1));
                assertTrue(bst.contains(i));
            }
        }
    }

    @Test
    public void testFindMin() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i = 99; i > 0; i--) {
            bst.add(i);
            //int minCheck = bst.findMin();
            assertTrue(bst.findMin() == i);
        }
        for (int i = 0; i < 99; i++) {
            bst.remove(i);
            assertTrue(bst.findMin() == i+1);
        }
    }

    @Test
    public void testFindMax() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i = 0; i < 100; i++) {
            bst.add(i);
            assertTrue(bst.findMax() == i);
        }
        for (int i = 99; i > 0; i--) {
            bst.remove(i);
            assertTrue(bst.findMax() == i-1);
        }
    }

    @Test
    public void testToArray() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Random randomGenerator = new Random();
        TreeSet<Integer> s = new TreeSet<Integer>();
        for (int idx = 0; idx < 100; idx++){
            int randomInt = randomGenerator.nextInt(100);
            bst.add(randomInt);
            s.add(randomInt);
        }
        assertTrue(Arrays.equals(s.toArray(), bst.toArray()));
    }

    @Test
    public void testToArray2() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Random randomGenerator = new Random();
        TreeSet<Integer> s = new TreeSet<Integer>();
        for (int idx = 0; idx < 100; idx++){
            int randomInt = randomGenerator.nextInt(100);
            bst.add(randomInt);
            s.add(randomInt);
        }
        Object[] arr1 = s.toArray();
        Integer[] arr2 = bst.toArray(new Integer[1000]);
        boolean arraysDiffer = false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                arraysDiffer = true;
                break;
            }
        }
        assertTrue(!arraysDiffer);
    }

    @Test
    public void testIterator() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Random randomGenerator = new Random();
        TreeSet<Integer> s = new TreeSet<Integer>();
        // populate tree with some random values
        for (int idx = 0; idx < 100; idx++){
            int randomInt = randomGenerator.nextInt(100);
            bst.add(randomInt);
            s.add(randomInt);
        }
        Object[] arr1 = s.toArray();
        Iterator itr = bst.iterator();
        for (int i = 0; itr.hasNext(); i++) {
            Integer el = (Integer)itr.next();
            assertTrue(el == arr1[i]);
        }
    }

    @Test
    public void testIteratorRemove() throws Exception {
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        Random randomGenerator = new Random();
        TreeSet<Integer> s = new TreeSet<Integer>();
        // populate tree with some random values
        for (int idx = 0; idx < 100; idx++){
            int randomInt = randomGenerator.nextInt(100);
            bst.add(randomInt);
            s.add(randomInt);
        }

        // remove all values through the iterator
        for (Iterator itr = bst.iterator(); itr.hasNext(); itr.next()) {
            itr.remove();
        }
        assertTrue(bst.size() == 0);
    }
}
