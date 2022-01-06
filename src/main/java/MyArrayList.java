import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MyArrayList<T> implements List<T>,Comparable<MyArrayList<T>>{

    private static final int DEFAULT_SIZE=15;
    private static final int COEF=2;
    private MyArrayListElements<T> elements=new MyArrayListElements<>();
    private int size;
    private int iFrom=0;
    private MyInteger iToParent=new MyInteger(0);
    private MyInteger iTo= new MyInteger(0);
    private MyArrayList<T> prev;
    private MyArrayList<T> next;

    private MyArrayList(MyArrayList<T> ts, int i, int i1) {
        elements=ts.elements;
        size=ts.size;
        //size=i1-i;
        iFrom=i+ts.iFrom;
        iTo.value=i1+ts.iFrom;
        iToParent=ts.iTo;

        prev=ts;
        ts.next=this;

    }

    /**Initializes list with other list of elements
     * @param list list of elements (must no be null)
     */
    public MyArrayList(@NotNull List<? extends T> list){
        size=0;
        elements.e=(T[]) new Object[list.size()+DEFAULT_SIZE];
        for (T e:list)
            if (e != null)
                add(e);

    }

    /**Initializes empty list with given capacity
     * @param capacity initial capacity of list
     */
    public MyArrayList(int capacity){
        if (capacity<0)
            throw new IllegalArgumentException("Illefal Capacity "+ capacity);
        size=0;
        elements.e=(T[]) new Object[capacity];
    }

    private void addLength(){

        T[] tempElements= (T[]) new Object[COEF*
                                            Math.max(elements.e.length,1)];
        copyArray(elements.e,0,tempElements,0, elements.e.length);

        elements.e=tempElements;
    }

    private void copyArray(@NotNull T[] arFrom, int index1, T[] arTo, int index2, int count){
        if(index1+count>arFrom.length||index2+count>arTo.length)
            throw new ArrayIndexOutOfBoundsException();

        System.arraycopy(arFrom,index1,arTo,index2,count);
    }


    /** Returns current size of list
     * @return Number of elements
     */
    @Override
    public int size() {
        return iTo.value-iFrom;
    }

    /** Checks if List is empty
     * @return true if empty, else if not
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /** Checks whether the list contains an element
     * @param o Element to find in the list
     * @return true if list contains element, false if not
     */
    @Override
    public boolean contains(Object o) {
        if (o==null)
            return false;
        for (T i:elements.e)
            if ((o).equals(i))
                return true;
        return false;
    }

    /** Returns iterator for List
     * @return Iterator
     */
    @Override
    public @NotNull Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    /** Returns array of current list
     * @return Array of objects
     */
    @Override
    public Object[] toArray() {
        T[] tempElements= (T[]) new Object[elements.size];
        copyArray(elements.e,0,tempElements,0, elements.size);
        return tempElements;

    }

    /** Copies an array to given array
     * @param t1s array to copy
     * @param <T1> new type
     * @return array with copied elements
     */
    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        if (t1s.length<size()){
             t1s= (T1[]) new Object[size()];
        }
        for (int i=0;i<size();i++)
            t1s[i]=(T1)elements.e[i];
        if (t1s.length>size())
            t1s[size()]=null;
        return t1s;

    }



    /**Adds an element to the list
     * @param t Element to add
     * @return returns true if element added
     */
    @Override
    public boolean add(T t) {

        if (elements.e.length<=size())
            addLength();
        var tempListPrev=prev;

        while(tempListPrev!=null){
            tempListPrev.iTo.value++;
            tempListPrev=tempListPrev.prev;
        }

        if (iTo.value>= elements.size) {
            iTo.value = elements.size++;
            size++;
            elements.e[iTo.value++] = t;
        }
        else{
            adds(iTo.value++,t);
            elements.size++;
        }

        return true;

    }

    /** Remove Object from the list
     * @param o Object to remove from list
     * @return returns true if element removed
     */
    @Override
    public boolean remove(Object o) {
        int index=indexOf(o);
        if (index<0)
            return false;
        remove(index,index+1);
        return true;
    }

    /** Checks if list contains all items from the collection
     * @param collection Collection
     * @return true if list contains all items from the collection
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (var i:collection )
            if (!contains(i))
                return false;
        return true;
    }

    /**Adds to list all elements from collection
     * @param collection Collection
     * @return true if added all elements from collection
     */
    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (var e:collection)
            add(e);
        return true;
    }

    /**Adds all elements from collection to list beginning from index
     * @param index index from list from where start to add
     * @param collection Collection
     * @return true if added all elements from collection
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        for (var e:collection)
            add(index++,e);
        return true;
    }

    /**Remove from the list all elements from collection
     * @param collection Collection
     * @return true if removed all elements from collection
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        for (var e:collection)
            while (contains(e))
                remove(e);
        return true;
    }

    /**Retains elements from Collection in the list
     * @param collection Collection
     * @return true if all elements from collection retained
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> collection) {
        boolean changed=false;
        var temp=elements.e.clone();
        for (var e:temp)
            if (!collection.contains(e)) {
                remove(e);
                changed = true;
            }

        return changed;

    }

    /**Removes all elements from list
     *
     */
    @Override
    public void clear() {
        remove(iFrom,iTo.value);
        iFrom=0;
        iTo.value=0;
    }

    /**Returns element with given index from list
     * @param i index
     * @return element
     */
    @Override
    public T get(int i) {
        if (i<size() && i>=0)
            return elements.e[i];
        else throw new IndexOutOfBoundsException();
    }

    /**Sets element with given index with given value
     * @param i index
     * @param t value
     * @return element
     */
    @Override
    public T set(int i, T t) {
        if (i<size() && i>=0)
            return elements.e[i]=t;
        else throw new IndexOutOfBoundsException();
    }

    /**Adds element to array with given index
     * @param i index
     * @param t element
     */
    private void adds(int i, T t) {
        if (elements.e.length==size)
            addLength();
        copyArray(elements.e, i, elements.e, i+1, elements.size-i);
        elements.e[i]=t;
    }

    /**Adds element to array with given index
     * @param i index
     * @param t element
     */
    @Override
    public void add(int i, T t) {
        if (elements.e.length<=size())
            addLength();
        var tempListPrev=prev;

        while(tempListPrev!=null){
            tempListPrev.iTo.value++;
            tempListPrev=tempListPrev.prev;
        }

        if (iTo.value>= elements.size) {
            iTo.value = elements.size++;
            size++;

            adds(i+iFrom,t);
            iTo.value++;
        }
        else{
            adds(i +iFrom,t);
            iTo.value++;
            elements.size++;
        }

    }

    /**Removes element with given index
     * @param i index
     * @return removed element
     */
    @Override
    public T remove(int i) {
        if(i<0 || i+iFrom>=iTo.value)
            throw new IndexOutOfBoundsException("index " + i+ " is out of bound for length " + iTo.value);
        T elem=elements.e[i];
        remove(i+iFrom,i+1+iFrom);
        return elem;
    }

    private boolean remove( int i,int i1){
        size= elements.size;
        copyArray(elements.e,i1,elements.e,i,size-i1);
        for (int j=size;j>size-i1+i;j--){
            elements.e[j-1]=null;

        }
        size -=i1-i;
        elements.size-=i1-i;
        iTo.value--;

        var tempListPrev=prev;
        while(tempListPrev!=null){
            tempListPrev.iTo.value--;
            tempListPrev=tempListPrev.prev;
        }

        return true;
    }

    /**Returns index of first occurrence
     * @param o Object
     * @return first index of object, -1 if not found
     */
    @Override
    public int indexOf(Object o) {
        for (int i=0;i<size;i++){
            if (elements.e[i]==o || elements.e[i].equals(o))
                return i;
        }
        return -1;
    }

    /**Returns index of last occurrence
     * @param o Object
     * @return last index of object, -1 if not found
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i=iTo.value-1;i>=iFrom;i--){
            if (elements.e[i]==o || elements.e[i].equals(o))
                return i;
        }
        return -1;
    }

    /**Return ListIterator
     * @return ListIterator
     */
    @Override
    public @NotNull ListIterator<T> listIterator() {
        return new MyArrayListListIterator();
    }

    /**Return ListIterator witch begins with index
     * @param i index
     * @return ListIterator
     */
    @Override
    public @NotNull ListIterator<T> listIterator(int i) {
        return new MyArrayListListIterator(i);
    }

    /**Return sublist of list start from i to t1
     * @param i index from
     * @param i1 index to
     * @return subList of the list
     */
    @Override
    public @NotNull List<T> subList(int i, int i1) {
        //copyArray(elements,i,ar,0,i1-i);
        return new MyArrayList<>(this,i,i1);
    }

    /**Return String representation of List
     * @return String representation of List
     */
    @Override
    public String toString(){
        StringBuilder s= new StringBuilder();
        if (iTo.value> elements.size+1)
            iTo.value = elements.size;
        for (int i=iFrom;i<iTo.value;i++) {
            if (i!=iFrom)
                s.append(", ");
            s.append(String.valueOf(elements.e[i]));
        }
        return '['+ s.toString() +']';
    }


    /** Compares elements from lists
     * @param ts myArrayList to compare
     * @return 0 if equals, -1 else
     */
    @Override
    public int compareTo(@NotNull MyArrayList<T> ts) {
        if (size()==ts.size()){
            for (int i=0;i<size();i++)
                if(elements.e[i]!=ts.elements.e[i])
                    return -1;
        }
        return 0;
    }


    private class MyArrayListElements<T>{
        T[]e;
        int size;
        int l=0;
        public MyArrayListElements(){
            T[] e= (T[])new Object[DEFAULT_SIZE];
            l=e.length;
        }
    }

    private class MyInteger {
        private int value;
        public MyInteger(int i){value=i;}
    }

    private class MyArrayListIterator implements Iterator<T>{
        private int currentIndex=0;

        @Override
        public boolean hasNext() {
            return currentIndex<size;
        }

        @Override
        public T next() {
            if (! hasNext())
                throw new NoSuchElementException();
            return  elements.e[currentIndex++];
        }
    }

    private class MyArrayListListIterator implements ListIterator<T>{
        private int currentIndex;

        public MyArrayListListIterator(int i){
            if (i<iFrom || i>iTo.value)
                throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + iTo.value);
            currentIndex=i;
        }

        public MyArrayListListIterator(){
            currentIndex=0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex<size();
        }

        @Override
        public T next() {
            if (! hasNext())
                throw new NoSuchElementException();
            return  elements.e[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex>0;
        }

        @Override
        public T previous() {
            if (! hasPrevious())
                throw new NoSuchElementException();
            return  elements.e[--currentIndex];
        }

        @Override
        public int nextIndex() {
            if (! hasNext())
                throw new NoSuchElementException();
            return currentIndex++;
        }

        @Override
        public int previousIndex() {
            if (! hasPrevious())
                throw new NoSuchElementException();
            return currentIndex--;
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(currentIndex);

        }

        @Override
        public void set(T t) {
            MyArrayList.this.set(currentIndex-1,t);

        }

        @Override
        public void add(T t) {
            MyArrayList.this.adds(currentIndex,t);

        }
    }
}

