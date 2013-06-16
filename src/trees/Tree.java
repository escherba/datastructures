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
public abstract class Tree<E> implements Collection<E> {
    protected int size;

	/* (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public abstract boolean add(E arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public abstract boolean addAll(Collection<? extends E> arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	@Override
	public abstract void clear();

	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public abstract boolean contains(Object arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public abstract boolean containsAll(Collection<?> arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public abstract boolean isEmpty();

	/* (non-Javadoc)
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public abstract Iterator<E> iterator();

	/* (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public abstract boolean remove(Object arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public abstract boolean removeAll(Collection<?> arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public abstract boolean retainAll(Collection<?> arg0);

	/* (non-Javadoc)
	 * @see java.util.Collection#size()
	 */
	@Override
	public abstract int size();

	/* (non-Javadoc)
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public abstract Object[] toArray();


	/* (non-Javadoc)
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public abstract <T> T[] toArray(T[] arg0);

	public abstract E findMin();

	public abstract E findMax();

}
