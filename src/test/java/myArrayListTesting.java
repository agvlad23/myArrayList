import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class myArrayListTesting {

    MyArrayList<Integer> mA;
    ArrayList<Integer> nA;
    int size;

    private <T> void addTo(T elem, List<T>... lists){
        for (List<T> e:lists) {
            e.add(elem);
        }
    }

    @BeforeEach
    void setUp() {

        mA= new MyArrayList<Integer>(1,3,4,5,6,6,7,8,9,999,999);
        size=11;
        nA= new ArrayList<Integer>(Arrays.asList(1,3,4,5,6,6,7,8,9,999,999));
    }

    @Test
    void size() {
        assertAll(
                ()->assertEquals(size,mA.size()),
                ()->assertEquals(nA.size(),mA.size())

        );
    }

    @Test
    void isEmpty() {
        assertFalse(mA.isEmpty());
    }

    @Test
    void contains() {
        assertAll(
                ()-> assertTrue(nA.contains(1)),
                ()-> assertFalse(nA.contains(2)),
                ()-> assertTrue(nA.contains(3)),
                ()-> assertTrue(nA.contains(4)),
                ()-> assertTrue(nA.contains(5))
        );
    }

    @Test
    void iterator() {
        var it = mA.iterator();
        int index=0;
        assertTrue(it.hasNext());
        while (it.hasNext()){
            assertEquals(mA.get(index), it.next());
            index++;
        }
        assertThrows(NoSuchElementException.class,()->it.next());
    }

    @Test
    void toArray() {
        assertEquals(nA.toArray()[6], mA.toArray()[6]);
    }


    @Test
    void add() {
        addTo(12,mA,nA);

        assertEquals(nA.toString(),mA.toString());

        mA.subList(3,6).clear();
        nA.subList(3,6).clear();

        assertEquals(nA.toString(),mA.toString());

        addTo(124,mA,nA);
        addTo(124,mA,nA);
        addTo(124,mA,nA);
        addTo(125,mA,nA);

        assertEquals(nA.toString(),mA.toString());

        mA.subList(3,6).add(666);
        nA.subList(3,6).add(666);
        assertEquals(nA.toString(),mA.toString());
        addTo(55555,nA,mA);
        addTo(55555,nA,mA);
        addTo(55555,nA,mA);
        addTo(55555,nA,mA);

        mA.subList(3,6).clear();
        nA.subList(3,6).clear();
        assertEquals(nA.toString(),mA.toString());

        mA.subList(3,6).subList(2,3).add(666);
        nA.subList(3,6).subList(2,3).add(666);
        assertEquals(nA.toString(),mA.toString());

        var k1= mA.subList(1,5);
        var k2= nA.subList(1,5);
        var k11= mA.subList(4,7);
        var k22= nA.subList(4,7);
        //k11=[999, 999, 666]
        k1.add(5);
        k2.add(5);
        assertAll(
                ()->assertEquals(nA.toString(),mA.toString()),
                ()->assertEquals(k1.toString(),k2.toString())
        );

        //mA= [1, 3, 4, 666, 999, 5, 999, 666, 12, 124, 124, 124, 125, 55555, 55555, 55555, 55555]
        //k11 - [999, 5, 999]
        k11.add(732);
        assertAll(
                ()->assertEquals("[1, 3, 4, 666, 999, 5, 999, 732, 666, 12, 124, 124, 124, 125, 55555, 55555, 55555, 55555]",
                        mA.toString()),
                ()->assertEquals(k1.toString(),k2.toString())
        );

        //k22.add(732);
    }

    @Test
    void remove() {
        mA.remove(1);
        nA.remove(1);
        mA.remove(4);
        nA.remove(4);
        mA.remove(8);
        nA.remove(8);

        assertEquals(nA.toString(),mA.toString());

    }

    @Test
    void containsAll() {
        assertTrue(mA.containsAll(nA));
        assertTrue(mA.containsAll(mA));
    }

    @Test
    void addAll() {
        mA.addAll(Arrays.asList(1,66));
        nA.addAll(Arrays.asList(1,66));
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void testAddAll() {
        mA.addAll(1,Arrays.asList(55,312,5555));
        nA.addAll(1,Arrays.asList(55,312,5555));
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void removeAll() {
        mA.removeAll(Arrays.asList(1,66,6));
        nA.removeAll(Arrays.asList(1,66,6));
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void retainAll() {
        mA.retainAll(Arrays.asList(1,66,6));
        nA.retainAll(Arrays.asList(1,66,6));
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void clear() {
        mA.clear();
    }

    @Test
    void get() {
        for (int i=0;i<100;i++) {
            mA.add(i);
            assertEquals(i,mA.get(i+11));
        }
        assertEquals(3,mA.get(1));
    }

    @Test
    void set() {
        assertAll(
                ()->assertEquals(1,mA.set(1,1)),
                ()->assertEquals(3,mA.set(5,3)),
                ()->assertEquals(-1,mA.set(1,-1)),
                ()-> assertThrows(IndexOutOfBoundsException.class, ()->mA.set(-1,-1)),
                ()-> assertThrows(IndexOutOfBoundsException.class, ()->mA.set(mA.size()+1,-1))

        );

    }

    @Test
    void testAdd() {
        mA.subList(1,9).add(5,512345);
        nA.subList(1,9).add(5,512345);
        assertEquals(nA.toString(),mA.toString());
        mA.subList(1,9).subList(1,8).add(5,512345);
        nA.subList(1,9).subList(1,8).add(5,512345);
        assertEquals(nA.toString(),mA.toString());

    }

    @Test
    void testRemove() {
        Object o=mA.get(5);
        Object o2=mA.get(5);
        mA.remove(o);
        nA.remove(o2);
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void indexOf() {
        assertAll(
                ()->assertEquals(0, mA.indexOf(1)),
                ()->assertEquals(1, mA.indexOf(3)),
                ()->assertEquals(4, mA.indexOf(6)),
                ()->assertEquals(9, mA.indexOf(999))
        );
    }

    @Test
    void lastIndexOf() {
        assertAll(
                ()->assertEquals(0, mA.lastIndexOf(1)),
                ()->assertEquals(1, mA.lastIndexOf(3)),
                ()->assertEquals(5, mA.lastIndexOf(6)),
                ()->assertEquals(10, mA.lastIndexOf(999))
        );
    }

    @Test
    void listIterator() {
        var it=mA.listIterator();
        assertFalse(it.hasPrevious());
    }

    @Test
    void testListIterator() {
        var it=mA.listIterator(5);
        assertTrue(it.hasPrevious());
    }

    @Test
    void subList() {
        nA.subList(2,4).clear();
        mA.subList(2,4).clear();
        nA.add(22);
        mA.add(22);
        assertAll(
                ()->assertEquals(nA.subList(0,3).toString(),mA.subList(0,3).toString()),
                ()->assertEquals(nA.toString(),mA.toString()),
                ()->assertEquals(nA.subList(1,8).toString(),mA.subList(1,8).toString()),
                ()->assertEquals(nA.subList(1,8).subList(1,5).subList(2,3).toString(),mA.subList(1,8).subList(1,5).subList(2,3).toString()),
                ()->assertEquals(nA.subList(1,8).toString(),mA.subList(1,8).toString())

        );
    }
}