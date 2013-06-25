/*
Copyright (c) 2013 Eugene Scherba

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package trees;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

// TODO implement NavigableSet interface

/**
 * @author escherba
 *
 */
public class BinarySearchTree<E extends Comparable<E>> extends Tree<E> {

    private Node root;

    enum Arrow
    {
        VOID,
        LEFT,
        RIGHT
    }

    private class Node {
        private E value;
        public Node left;
        public Node right;

        public Node(E val) throws NullPointerException {
            if (val == null) {
                throw new NullPointerException();
            }
            value = val;
        }
    }

    private class Edge {
        public Node parent;
        public Node child;
        public Arrow direction;
        public Edge(Node nodeParent, Node nodeChild, Arrow dir) {
            parent = nodeParent;
            child = nodeChild;
            direction = dir;
        }
        public void moveLeft() {
            parent = child;
            child = child.left;
            direction = Arrow.LEFT;
        }
        public void moveRight() {
            parent = child;
            child = child.right;
            direction = Arrow.RIGHT;
        }
    }

    /**
     *
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /* (non-Javadoc)
     * @see trees.Tree#add(java.lang.Object)
     */
    @Override
    public boolean add(E value) {

        // return true if collection has been modified, false otherwise
        Node z = new Node(value); // node to insert

        Edge e = new Edge(null, root, Arrow.VOID);
        while (e.child != null) {
            int comparison = value.compareTo(e.child.value);
            if (comparison < 0) {          // arg0 <  x.value
                e.moveLeft();
            } else if (comparison > 0) {   // arg0 >= x.value
                e.moveRight();
            } else {
                return false;              // arg0 == x.value
            }
        }
        if (e.parent == null) {
            root = z; // tree was empty
        } else {
            int comparison = value.compareTo(e.parent.value);
            if (comparison < 0) {          // arg0 <  y.value
                e.parent.left  = z;
            } else if (comparison > 0) {   // arg0 >= y.value
                e.parent.right = z;
            } else {
                return false;              // arg0 == y.value
            }
        }
        size++;
        return true;
    }

    /* (non-Javadoc)
         * @see trees.Tree#addAll(java.util.Collection)
         */
    @Override
    public boolean addAll(Collection<? extends E> values) {
        boolean addedSome = false;
        for (E value: values) {
            if (add(value)) addedSome = true;
        }
        return addedSome;
    }

    /* (non-Javadoc)
     * @see trees.Tree#clear()
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* (non-Javadoc)
     * @see trees.Tree#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object value) {
        E e = (E) value;
        Edge edg = findNodeWithParent(e);
        return edg != null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection values) {
        for (Object value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see trees.Tree#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#iterator()
     */
    @Override
    @NotNull
    public Iterator<E> iterator() {

        final Object[] arr = toArray();
        final int arrLength = arr.length;
        final BinarySearchTree<E> self = this;

        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < arrLength;
            }

            @Override
            public E next() {
                if (! hasNext()) throw new NoSuchElementException();
                return (E)arr[currentIndex++];
            }

            @Override
            public void remove() {
                self.remove(arr[currentIndex]);
            }
        };
    }

    private void transplant(Edge e, Node v) {
        if (e.parent == null) {
            root = v;
        } else if (e.direction == Arrow.LEFT) {
            e.parent.left = v;
        } else if (e.direction == Arrow.RIGHT) {
            e.parent.right = v;
        } else {
            throw new IllegalStateException();
        }
    }

    /* (non-Javadoc)
     * @see trees.Tree#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object value) {
        E e = (E) value;
        Edge ze = findNodeWithParent(e);
        if (ze == null) {
            return false;   // value not found in tree
        }
        Node z = ze.child;
        if (z.left == null) {
            transplant(ze, z.right);
        } else if (z.right == null) {
            transplant(ze, z.left);
        } else if (size % 2 == 0) {
            // find successor of z -- vary between successor
            // and predecessor depending on tree size
            Edge ye = findMinWithParentFrom(z.right);
            Node y = ye.child;
            if (y != z.right) {  // ye.parent != z
                transplant(ye, y.right);
                y.right = z.right;
            }
            transplant(ze, y);
            y.left = z.left;
        } else {
            // find predecessor of z -- vary between successor
            // and predecessor depending on tree size
            Edge ye = findMaxWithParentFrom(z.left);
            Node y = ye.child;
            if (y != z.left) {  // ye.parent != z
                transplant(ye, y.left);
                y.left = z.left;
            }
            transplant(ze, y);
            y.right = z.right;
        }
        size--;
        return true;
    }

    /* (non-Javadoc)
     * @see trees.Tree#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection values) {
        boolean removedSome = false;
        for (Object value : values) {
            if (remove(value)) removedSome = true;
        }
        return removedSome;
    }

    /* (non-Javadoc)
     * @see trees.Tree#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection values) {
        if (values == null) {
            throw new NullPointerException();
        }
        E[] els = (E[])toArray();
        boolean modified = false;
        for (E el : els) {
            if (!values.contains(el)) {
                if (remove(el)) {
                    modified = true;
                }
            }
        }
        return modified;
    }

    /* (non-Javadoc)
     * @see trees.Tree#size()
     */
    @Override
    public int size() {
        return size;
    }

    /* (non-Javadoc)
     * @see trees.Tree#toArray()
     */
    @Override
    @NotNull
    public Object[] toArray() {
        return toArray(new Object[0]);
    }

    /* (non-Javadoc)
     * @see trees.Tree#toArray(T[])
     */
    @Override
    @NotNull
    public <T> T[] toArray(T[] values) {
        if (values == null) {
            throw new NullPointerException();
        }

        int bufferSize = values.length;
        if (size > bufferSize) {
            values = (T[])new Object[size];
        }

        // perform in-order tree traversal starting from root
        Deque<Node> stack = new ArrayDeque<Node>();
        Node curr = root;
        int i = 0;
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                values[i++] = (T)curr.value;
                curr = curr.right;
            }
        }

        // pad with a null value if source array is larger
        if (i < bufferSize) {
            values[i] = null;
        }
        return values;
    }

    /* (non-Javadoc)
     * @see trees.Tree#findMin()
     */
    @Override
    public E findMin() {
        Edge e = findMinWithParentFrom(root);
        return e.child.value;
    }

    private Edge findMinWithParentFrom(Node node) {
        Edge e = new Edge(null, node, Arrow.VOID);
        while (e.child.left != null) {
            e.moveLeft();
        }
        return e;
    }

    /* (non-Javadoc)
     * @see trees.Tree#findMax()
     */
    @Override
    public E findMax() {
        Edge e = findMaxWithParentFrom(root);
        return e.child.value;
    }

    private Edge findMaxWithParentFrom(Node node) {
        Edge e = new Edge(null, node, Arrow.VOID);
        while (e.child.right != null) {
            e.moveRight();
        }
        return e;
    }

    private Edge findNodeWithParent(E value) {
        // iterative tree search
        Edge e = new Edge(null, root, Arrow.VOID);
        while (e.child != null) {
            int comparison = value.compareTo(e.child.value);
            if (comparison < 0) {
                e.moveLeft();
            } else if (comparison > 0) {
                e.moveRight();
            } else {
                return e; // found the node
            }
        }
        return null;
    }
}
