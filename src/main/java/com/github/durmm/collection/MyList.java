package com.github.durmm.collection;

import java.util.*;

public class MyList<E> implements List<E> {

    private Object[] array;
    private static final int defaultListSize = 10;
    private int size;

    /**
     * Creates list with {@code defaultListSize} initial size
     */
    MyList() {
        this(defaultListSize);
    }

    /**
     * Creates list with {@code initialListSize} initial size
     *
     * @param initialListSize initial size of list
     * @throws IllegalArgumentException if {@code initialListSize} argument is non negative integer
     */
    MyList(int initialListSize) {
        if (initialListSize <= 0)
            throw new IllegalArgumentException(
                    "The initialListSize should be non negative integer"
            );
        size = 0;
        array = new Object[initialListSize];
    }

    /**
     * Returns the number of elements in this list.  If this list contains
     * more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     * More formally, returns {@code true} if and only if this list contains
     * at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *                              list does not permit null elements
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    private class MyListIterator<E> implements Iterator<E> {
        private int index = 0;
        private boolean allowRemoveCall = false;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (size() == 0)
                return false;
            return index < size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (index >= size())
                throw new NoSuchElementException();
            allowRemoveCall = true;
            return (E) array[index++];
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws IllegalStateException if the {@code next} method has not
         *                               yet been called, or the {@code remove} method has already
         *                               been called after the last call to the {@code next}
         *                               method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            if (!allowRemoveCall)
                throw new IllegalStateException(" 'next' method has not\n" +
                        "yet been called, or the {@code remove} method has already\n" +
                        "been called after the last call to the 'next' method");
            allowRemoveCall = false;
            MyList.this.remove(index - 1);
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     * <p>
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must
     * allocate a new array even if this list is backed by an array).
     * The caller is thus free to modify the returned array.
     * <p>
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in proper
     * sequence
     * @see Arrays#asList(Object[])
     */
    @Override
    public Object[] toArray() {
        if (size() == 0)
            return new Object[0];
        return Arrays.copyOf(array, size());
    }

    /**
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     * <p>
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to {@code null}.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any null elements.)
     * <p>
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p>
     * <p>Suppose {@code x} is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of {@code String}:
     * <p>
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of this list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
        //TODO -- copied from ArrayList
    }

    // Modification Operations

    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws ClassCastException       if the class of the specified element
     *                                  prevents it from being added to this list
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this list
     */
    @Override
    public boolean add(E e) {
        if (size() == array.length)
            maximizeArray();
        addElementToArray(e);
        return true;
    }

    private void addElementToArray(E e) {
        array[size()] = e;
        size++;
    }

    /**
     * Maximized array two times, and replaces old values in new resized array
     */
    private void maximizeArray() {
        Object[] newArray = new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, size());
        array = newArray;
    }

    /**
     * If the real size of array three or more times large then element count in list,
     * minimize array one and a half times, and replaces old values in new resized
     * array. The minnimal size of the massive shoulb be {@code defaultListSize}
     */
    private void minimizeArray() {
        if (array.length <= defaultListSize)
            return;
        if (array.length <= 3 * size())
            return;
        int n = (int) (array.length * 0.75);
        if (n < defaultListSize)
            n = defaultListSize;
        Object[] newArray = new Object[n];
        System.arraycopy(array, 0, newArray, 0, size());
        array = newArray;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes the element with
     * the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list changed
     * as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     * @throws ClassCastException if the type of the specified element
     *                            is incompatible with this list
     *                            (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1)
            return false;
        remove(index);
        return true;
    }

    /**
     * Removes all occurrence of the specified element from this list,
     * if it is present (optional operation).  If this list does not contain
     * the element, it is unchanged.  More formally, removes all element with
     * the index {@code i} such that
     * {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list changed
     * as a result of the call).
     *
     * @param e element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     * @throws ClassCastException if the type of the specified element
     *                            is incompatible with this list
     *                            (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    public boolean removeAll(E e) {
        if (size() == 0)
            return false;
        boolean result = false;
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(e, array[i])) {
                remove(i);
                i--;
                result = true;
            }
        }
        minimizeArray();
        return result;
    }

    // Bulk Modification Operations

    /**
     * Returns {@code true} if this list contains all of the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} if this list contains all of the elements of the
     * specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              list
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException (<a href="Collection.html#optional-restrictions">optional</a>),
     *                              if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator it = c.iterator();
        while (it.hasNext()) {
            if (!contains(it.next()))
                return false;
        }
        return true;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).  The behavior of this
     * operation is undefined if the specified collection is modified while
     * the operation is in progress.  (Note that this will occur if the
     * specified collection is this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @see #add(Object)
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator it = c.iterator();
        if (!it.hasNext())
            return false;
        while (it.hasNext()) {
            add((E) it.next());
        }
        return true;
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list at the specified position (optional operation).  Shifts the
     * element currently at that position (if any) and any subsequent
     * elements to the right (increases their indices).  The new elements
     * will appear in this list in the order that they are returned by the
     * specified collection's iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified
     * collection is this list, and it's nonempty.)
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index > size()})
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if(index < 0 || index > size())
            throw new IllegalArgumentException();
        MyList list = getMyList(c);
        if (list.size() != 0) {
            addList(index, list);
            return true;
        }
        return false;
    }

    /**
     * Get MyList item from collection {@code c}
     *
     * @return {@code MyList} from given collection {@code c}
     * @throws NullPointerException if the specified collection is null
     */
    private MyList<E> getMyList(Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();
        MyList list = new MyList();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            list.add((E) it.next());
        }
        return list;
    }

    /**
     * @param list MyList object to added current list
     * @return current list if the siso of the {@code list} param is 0, else
     * return the added result list
     * @throws NullPointerException if the specified collection is null
     */
    private MyList<E> addList(int index, MyList list) {
        if (list == null)
            throw new NullPointerException();
        if(index < 0 || index > size())
            throw new IllegalArgumentException();
        if (list.size() == 0)
            return this;
        Object[] newArray = new Object[size() + list.size()];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy((E[])list.toArray(), 0, newArray, index, list.size());
        System.arraycopy(array, index, newArray, list.size() + index, size() - index);
        array = newArray;
        size = size() + list.size();
        return this;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection (optional operation).
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator it = c.iterator();
        boolean modified = false;
        while (it.hasNext()) {
            if (removeAll((E) it.next()))
                modified = true;
        }
        return modified;
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this list all of its elements that are not contained in the
     * specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection
     *                              (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        if (size() == 0)
            return false;
        boolean isListChaged = false;
        Iterator it = c.iterator();
        for(int i = 0; i < size(); i++ ) {
            if(!c.contains(get(i))) {
                removeAll(get(i));
                isListChaged = true;
            }
        }
        return isListChaged;
    }

    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        size = 0;
        minimizeArray();
    }

    // Positional Access Operations

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public E get(int index) {
        if (index < 0 | index >= size())
            throw new IndexOutOfBoundsException();
        return (E) array[index];
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element (optional operation).
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws ClassCastException        if the class of the specified element
     *                                   prevents it from being added to this list
     * @throws IllegalArgumentException  if some property of the specified
     *                                   element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public E set(int index, E element) {
        E e = get(index);
        array[index] = element;
        return e;
    }

    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws ClassCastException        if the class of the specified element
     *                                   prevents it from being added to this list
     * @throws IllegalArgumentException  if some property of the specified
     *                                   element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (size() == array.length)
            maximizeArray();
        for (int i = size() - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
    }

    /**
     * Removes the element at the specified position in this list (optional
     * operation).  Shifts any subsequent elements to the left (subtracts one
     * from their indices).  Returns the element that was removed from the
     * list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index >= size()})
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        E e = get(index);
        for (int i = index; i < size(); i++) {
            array[i] = array[i + 1];
        }
        size--;
        minimizeArray();
        return e;
    }

    // Search Operations

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *                            is incompatible with this list
     *                            (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        if (size() == 0)
            return -1;
        E el = (E) o;
        for (int i = 0; i < size(); i++)
            if (Objects.equals(el, array[i]))
                return i;
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index {@code i} such that
     * {@code Objects.equals(o, get(i))},
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     * @throws ClassCastException if the type of the specified element
     *                            is incompatible with this list
     *                            (<a href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        for (int i = 0; i < size(); i++)
            if (Objects.equals(o, array[i]))
                lastIndex = i;
        return lastIndex;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * @return a list iterator over the elements in this list (in proper
     * sequence)
     */
    @Override
    public ListIterator<E> listIterator() {
        return new MyListItr<>();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * @param index index of the first element to be returned from the
     *              list iterator (by a call to {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        if(index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        ListIterator it = new MyListItr<>();

        for(int i = 0; i < index; i++)
            it.next();
        return it;
        //TODO -- anonymous class
    }

    class MyListItr<F> implements ListIterator<F> {
        private int index = 0;
        private boolean allowRemoveOrSet = false;
        private int lastReturnedIndex = -1;

        public MyListItr() {
        }
        // Query Operations

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the forward direction. (In other words,
         * returns {@code true} if {@link #next} would return an element rather
         * than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the forward direction
         */
        @Override
        public boolean hasNext() {
            if (size() == 0)
                return false;
            return index < size();
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * This method may be called repeatedly to iterate through the list,
         * or intermixed with calls to {@link #previous} to go back and forth.
         * (Note that alternating calls to {@code next} and {@code previous}
         * will return the same element repeatedly.)
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        @Override
        public F next() {
            if (index >= size())
                throw new NoSuchElementException();
            allowRemoveOrSet = true;
            lastReturnedIndex = index;
            return (F) array[index++];
        }

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the reverse direction.  (In other words,
         * returns {@code true} if {@link #previous} would return an element
         * rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the reverse direction
         */
        public boolean hasPrevious() {
            if (size == 0)
                return false;
            if (index > 0)
                return true;
            return false;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards.  This method may be called repeatedly to
         * iterate through the list backwards, or intermixed with calls to
         * {@link #next} to go back and forth.  (Note that alternating calls
         * to {@code next} and {@code previous} will return the same
         * element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous
         *                                element
         */
        public F previous() {
            if (size == 0 || index <= 0)
                throw new NoSuchElementException();
            allowRemoveOrSet = true;
            lastReturnedIndex = index;
            return (F) array[index--];
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #next}. (Returns list size if the list
         * iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code next}, or list size if the list
         * iterator is at the end of the list
         */
        public int nextIndex() {
            return index;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #previous}. (Returns -1 if the list
         * iterator is at the beginning of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code previous}, or -1 if the list
         * iterator is at the beginning of the list
         */
        public int previousIndex() {
            return index - 1;
        }

        // Modification Operations

        /**
         * Removes from the list the last element that was returned by {@link
         * #next} or {@link #previous} (optional operation).  This call can
         * only be made once per call to {@code next} or {@code previous}.
         * It can be made only if {@link #add} has not been
         * called after the last call to {@code next} or {@code previous}.
         *
         * @throws IllegalStateException if neither {@code next} nor
         *                               {@code previous} have been called, or {@code remove} or
         *                               {@code add} have been called after the last call to
         *                               {@code next} or {@code previous}
         */
        public void remove() {
            if (!allowRemoveOrSet)
                throw new IllegalStateException();
            allowRemoveOrSet = false;
            MyList.this.remove(index);
            if (size < index)
                index = size;
        }

        /**
         * Replaces the last element returned by {@link #next} or
         * {@link #previous} with the specified element (optional operation).
         * This call can be made only if neither {@link #remove} nor {@link
         * #add} have been called after the last call to {@code next} or
         * {@code previous}.
         *
         * @param e the element with which to replace the last element returned by
         *          {@code next} or {@code previous}
         * @throws ClassCastException       if the class of the specified element
         *                                  prevents it from being added to this list
         * @throws IllegalArgumentException if some aspect of the specified
         *                                  element prevents it from being added to this list
         * @throws IllegalStateException    if neither {@code next} nor
         *                                  {@code previous} have been called, or {@code remove} or
         *                                  {@code add} have been called after the last call to
         *                                  {@code next} or {@code previous}
         */
        public void set(F e) {
            if (!allowRemoveOrSet)
                throw new IllegalStateException();
            MyList.this.set(lastReturnedIndex, (E) e);
        }

        /**
         * Inserts the specified element into the list (optional operation).
         * The element is inserted immediately before the element that
         * would be returned by {@link #next}, if any, and after the element
         * that would be returned by {@link #previous}, if any.  (If the
         * list contains no elements, the new element becomes the sole element
         * on the list.)  The new element is inserted before the implicit
         * cursor: a subsequent call to {@code next} would be unaffected, and a
         * subsequent call to {@code previous} would return the new element.
         * (This call increases by one the value that would be returned by a
         * call to {@code nextIndex} or {@code previousIndex}.)
         *
         * @param e the element to insert
         * @throws ClassCastException       if the class of the specified element
         *                                  prevents it from being added to this list
         * @throws IllegalArgumentException if some aspect of this element
         *                                  prevents it from being added to this list
         */
        public void add(F e) {
            allowRemoveOrSet = false;
            if (size() == 0) {
                MyList.this.add((E) e);
                index = 0;
            } else {
                MyList.this.add(lastReturnedIndex, (E) e);
                lastReturnedIndex++;
                index++;
            }
        }
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations supported
     * by this list.<p>
     * <p>
     * This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for {@code indexOf} and
     * {@code lastIndexOf}, and all of the algorithms in the
     * {@code Collections} class can be applied to a subList.<p>
     * <p>
     * The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   ({@code fromIndex < 0 || toIndex > size ||
     *                                   fromIndex > toIndex})
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
        //TODO
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
