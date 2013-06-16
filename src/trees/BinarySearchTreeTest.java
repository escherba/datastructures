package trees;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: escherba
 * Date: 6/15/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
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
            bst.printKeys();
        }
        System.out.println("Starting removal");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                // remove even numbers
                System.out.println("Removing " + i);
                assertTrue(bst.remove(i));
                int expectedSize = 10 - (i/2+1);
                System.out.println("Expected size: " + expectedSize);
                bst.printKeys();
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
            int minCheck = bst.findMin();
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
}
