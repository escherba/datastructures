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

import java.util.Collection;
import java.util.Iterator;

/**
 * @author escherba
 *
 */
public class BinarySearchTree<E extends Comparable<E>> extends Tree<E> {

    private Node root;

    private class Node {
        private E value;
        public Node left;
        public Node right;

        public Node(E arg0) throws IllegalArgumentException {
            if (arg0 != null) {
                value = arg0;
            } else {
                throw new IllegalArgumentException();
            }
        }

        public int numChildren() {
            return ((left  == null ? 0 : 1) +
                    (right == null ? 0 : 1));
        }

        public Node getOnlyChild() throws IllegalStateException {
            if (numChildren() == 1) {
                return left == null ? right : left;
            } else {
                throw new IllegalStateException();
            }
        }
    }
    enum Arrow
    {
        VOID,
        SELF,
        LEFT,
        RIGHT
    }

    /*
    enum Arrow
    {
        VOID,
        SELF,
        LEFT,
        RIGHT
    }

    private class Edge {
        public Node parent;
        public Arrow direction;

        public Edge(Node node, Arrow moveTo) {
            parent = node;
            direction = moveTo;
        }

        public Node getChild() {
            Node child = null;
            if (direction == Arrow.SELF) {
                child = parent;
            } else if (direction == Arrow.LEFT) {
                child = parent.left;
            } else if (direction == Arrow.RIGHT) {
                child = parent.right;
            }
            return child;
        }
    }
    */
    private class Edge {
        public Node parent;
        public Node child;
        public Arrow direction;
        public Edge(Node nodeParent, Node nodeChild, Arrow dir) {
            parent = nodeParent;
            child = nodeChild;
            direction = dir;
        }
    }

    /**
     *
     */
    public BinarySearchTree() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see trees.Tree#add(java.lang.Object)
     */
    @Override
    public boolean add(E arg0) {

        // return true if collection has been modified, false otherwise

        Node x = root;           // current pointer
        Node y = null;           // trailing pointer (parent of x)
        Node z = new Node(arg0); // node to insert

        while (x != null) {
            y = x;
            int comparison = arg0.compareTo(x.value);
            if (comparison < 0) {          // arg0 <  x.value
                x = x.left;
            } else if (comparison > 0) {   // arg0 >= x.value
                x = x.right;
            } else {
                return false;              // arg0 == x.value
            }
        }
        //z.parent = y;
        if (y == null) {
            root = z; // tree was empty
        } else {
            int comparison = arg0.compareTo(y.value);
            if (comparison < 0) {          // arg0 <  y.value
                y.left  = z;
            } else if (comparison > 0) {   // arg0 >= y.value
                y.right = z;
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
    public boolean addAll(Collection<? extends E> arg0) {

        // TODO
        // Paul has a problem with his code here in that
        // his implementation may return false despite the fact that
        // some elements from the collection had been added.
        boolean addedSome = false;
        for (E e: arg0) {
            if (add(e)) addedSome = true;
        }
        return addedSome;
    }

    /* (non-Javadoc)
     * @see trees.Tree#clear()
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        root = null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object arg0) {
        E e = (E) arg0;
        Node node = findNode(e);
        return node != null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection arg0) {
        for (Object o : arg0) {
            if (!contains(o)) {
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
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    private void transplant(Node up, Node v, Arrow direction) {
        if (up == null) {
            root = v;
        } else if (direction == Arrow.LEFT) {
            up.left = v;
        } else if (direction == Arrow.RIGHT) {
            up.right = v;
        } else {
            throw new IllegalArgumentException();
        }
        //if (v != null) v.parent = u.parent
    }

    /* (non-Javadoc)
     * @see trees.Tree#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object arg0) {
        E e = (E) arg0;
        Edge edge = findNodeWithParent(e);
        if (edge == null) {
            return false;   // value not found in tree
        }
        Node z = edge.child;
        if (z.left == null) {
            transplant(edge.parent, z.right, edge.direction);
        } else if (z.right == null) {
            transplant(edge.parent, z.left, edge.direction);
        } else {
            Node yp = findParentOfMinFrom(z.right);
            Node y = yp.left;
            if (yp != null) {
                // we do not keep parent information; z.right != y
                // should be equivalent to: y.parent != z
                transplant(yp, y.right, Arrow.LEFT);
                y.right = z.right;
                //y.right.parent = y;
            }
            transplant(edge.parent, y, edge.direction);
            y.left = z.left;
            //y.left.parent = y;
        }
        size--;
        return true;
    }



    /* (non-Javadoc)
     * @see trees.Tree#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection arg0) {
        boolean removedSome = false;
        for (Object o : arg0) {
            if (remove(o)) removedSome = true;
        }
        return removedSome;
    }

    /* (non-Javadoc)
     * @see trees.Tree#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see trees.Tree#size()
     */
    @Override
    public int size() {
        return size;
    }

    private void printKeysFrom(Node arg0) {
        if (arg0 != null) {
            printKeysFrom(arg0.left);
            System.out.print(arg0.value + ",");
            printKeysFrom(arg0.right);
        }
    }
    public void printKeys() {
        System.out.println("Current size: " + size());
        printKeysFrom(root);
        System.out.println();
    }

    /* (non-Javadoc)
     * @see trees.Tree#toArray()
     */
    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#toArray(T[])
     */
    @Override
    public Object[] toArray(Object[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see trees.Tree#findMin()
     */
    @Override
    public E findMin() {
        Node x = findMinNodeFrom(root);
        return x.value;
    }

    private Node findMinNodeFrom(Node arg0) {
        Node nodeParent = findParentOfMinFrom(arg0);
        Node node = (nodeParent == null) ? arg0 : nodeParent;
        return node.left == null ? node : node.left;
    }

    private Node findParentOfMinFrom(Node arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException();
        }
        // tree minimum
        Node x = arg0;
        Node y = null; //trailing pointer
        while (x.left != null) {
            y = x;
            x = x.left;
        }
        return y;
    }

    /* (non-Javadoc)
     * @see trees.Tree#findMax()
     */
    @Override
    public E findMax() {
        Node x = findMaxNodeFrom(root);
        return x.value;
    }

    private Node findMaxNodeFrom(Node arg0) {
        Node nodeParent = findParentOfMaxFrom(arg0);
        Node node = (nodeParent == null) ? arg0 : nodeParent;
        return node.right == null ? node : node.right;
    }

    private Node findParentOfMaxFrom(Node arg0) {
        if (arg0 == null) {
            throw new IllegalArgumentException();
        }
        // parent of tree maximum
        Node x = arg0;
        Node y = null;  // trailing pointer
        while (x.right != null) {
            y = x;
            x = x.right;
        }
        return y;
    }

    private Node findNode(E arg0) {
        // iterative tree search
        Node x = root;
        while (x != null) {
            int comparison = arg0.compareTo(x.value);
            if (comparison < 0) {
                x = x.left;
            } else if (comparison > 0) {
                x = x.right;
            } else {
                break;  // found the node
            }
        }
        return x;
    }

    private Edge findNodeWithParent(E arg0) {
        // iterative tree search
        Node x = root;
        Node y = null;
        Arrow direction = Arrow.VOID;
        while (x != null) {
            int comparison = arg0.compareTo(x.value);
            if (comparison < 0) {
                y = x;
                x = x.left;
                direction = Arrow.LEFT;
            } else if (comparison > 0) {
                y = x;
                x = x.right;
                direction = Arrow.RIGHT;
            } else {
                return new Edge(y, x, direction);  // found the node
            }
        }
        return null;
    }

}
