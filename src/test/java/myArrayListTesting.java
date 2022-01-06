import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class myArrayListTesting {

    List<Integer> mA;
    List<Integer> nA;
    int size;

    private <T> void addTo(T elem, List<T>... lists){
        for (List<T> e:lists) {
            e.add(elem);
        }
    }

    @BeforeEach
    void setUp() {

        mA= new MyArrayList<Integer>(Arrays.asList(1,3,4,5,6,6,7,8,9,999,999));
        size=11;
        nA= new ArrayList<Integer>(Arrays.asList(1,3,4,5,6,6,7,8,9,999,999));
    }

    @Test
    @DisplayName("Initialization")
    void initializationTest(){
        assertThrows(IllegalArgumentException.class,()->new MyArrayList<Float>(null));
        var k=new MyArrayList<String>(5);
        k.add("sdsd");
        k= new MyArrayList<String>(0);
        k.add("adsad");
        assertThrows(IllegalArgumentException.class,()->new MyArrayList<String>(-5));
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
                ()-> assertTrue(mA.contains(1)),
                ()-> assertFalse(mA.contains(2)),
                ()-> assertTrue(mA.contains(3)),
                ()-> assertTrue(mA.contains(4)),
                ()-> assertFalse(mA.contains(null))
        );
    }

    @Test
    void iterator() {
        var it = mA.iterator();
        var it2 = nA.iterator();
        assertTrue(it.hasNext());
        while (it.hasNext()){
            assertEquals(it2.next(), it.next());
        }
        assertThrows(NoSuchElementException.class,()->it.next());
    }

    @Test
    void toArray() {
        assertEquals(nA.toArray()[6], mA.toArray()[6]);
        assertTrue(Arrays.equals(mA.toArray(),nA.toArray()));
    }


    @Test
    void add() {
        addTo(12,mA,nA);
        addTo(null,mA,nA);
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

        //mA -  [1, 3, 4, 666, 999, 5, 999, 666, 12, null, 124, 124, 124, 125, 55555, 55555, 55555, 55555]
        //k11 - [999, 5, 999]
        k11.add(732);
        assertAll(
                ()->assertEquals("[1, 3, 4, 666, 999, 5, 999, 732, 666, 12, null, 124, 124, 124, 125, 55555, 55555, 55555, 55555]",
                        mA.toString()),
                ()->assertEquals(k1.toString(),k2.toString())
        );

        //k22.add(732); // ошибка
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

        assertThrows(IndexOutOfBoundsException.class ,()->mA.remove(8));
        assertThrows(IndexOutOfBoundsException.class ,()->mA.remove(-1));

        mA.subList(1,5).remove(1);
        nA.subList(1,5).remove(1);
        assertEquals(nA.toString(),mA.toString());

        mA.subList(1,5).subList(1,3).remove(1);
        nA.subList(1,5).subList(1,3).remove(1);
        assertEquals(nA.toString(),mA.toString());

        assertThrows(IndexOutOfBoundsException.class ,()->{
                while(true)
                    mA.remove(0);
        });

    }

    @Test
    void containsAll() {
        assertTrue(mA.containsAll(nA));
        assertTrue(mA.containsAll(mA));
        assertTrue(nA.containsAll(mA));
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

        assertThrows(IndexOutOfBoundsException.class, ()->mA.addAll(-1,Arrays.asList(55,312,5555)));
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
        nA.clear();
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void get() {
        var mA2=new MyArrayList<Integer>();
        for (int i=0;i<100;i++) {
            mA.add(i);
            mA2.add(i);
            assertEquals(i,mA.get(i+11));
            assertEquals(i,mA2.get(i));
        }
        assertEquals(3,mA.get(1));
        assertThrows(IndexOutOfBoundsException.class,()-> mA.get(-1));
    }

    @Test
    void set() {
        assertAll(
                ()->assertEquals(1,mA.set(1,1)),
                ()->assertEquals(3,mA.set(5,3)),
                ()->assertEquals(-1,mA.set(1,-1)),
                ()->assertEquals(null,mA.set(1,null)),
                ()-> assertThrows(IndexOutOfBoundsException.class, ()->mA.set(-1,1)),
                ()-> assertThrows(IndexOutOfBoundsException.class, ()->mA.set(mA.size()+1,1))

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

        assertThrows(IndexOutOfBoundsException.class,()-> mA.add(-1,5));

        mA.subList(1,9).subList(1,8).add(5,null);
        nA.subList(1,9).subList(1,8).add(5,null);
        assertEquals(nA.toString(),mA.toString());

    }

    @Test
    void testRemove() {
        Object o=mA.get(5);
        Object o2=nA.get(5);
        mA.subList(1,8).subList(1,6).remove(o);
        nA.subList(1,8).subList(1,6).remove(o2);
        assertEquals(nA.toString(),mA.toString());

        mA.add(null);
        nA.add(null);

        mA.remove(null);
        nA.remove(null);
        assertEquals(nA.toString(),mA.toString());
    }

    @Test
    void indexOf() {
        assertAll(
                ()->assertEquals(0, mA.indexOf(1)),
                ()->assertEquals(1, mA.indexOf(3)),
                ()->assertEquals(4, mA.indexOf(6)),
                ()->assertEquals(9, mA.indexOf(999)),
                ()->assertEquals(-1, mA.indexOf(225125136)),
                ()->assertEquals(-1, mA.indexOf(null)),
                ()-> { mA.add(null);
                    assertEquals(11,mA.indexOf(null));}
        );
    }

    @Test
    void lastIndexOf() {
        assertAll(
                ()->assertEquals(0, mA.lastIndexOf(1)),
                ()->assertEquals(1, mA.lastIndexOf(3)),
                ()->assertEquals(5, mA.lastIndexOf(6)),
                ()->assertEquals(10, mA.lastIndexOf(999)),
                ()->assertEquals(-1, mA.indexOf(225125136)),
                ()->assertEquals(-1, mA.indexOf(null)),
                ()-> { mA.add(null);
                    assertEquals(11,mA.indexOf(null));}
        );
    }

    @Test
    void listIterator() {
        var it=mA.listIterator();
        var it2=nA.listIterator();
        assertFalse(it.hasPrevious());
        assertTrue(it.hasNext());
        assertAll(
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.previous(),it2.previous())
        );
    }

    @Test
    void testListIterator() {
        assertThrows(IndexOutOfBoundsException.class,()-> mA.listIterator(-1));
        var it=mA.listIterator(5);
        var it2=nA.listIterator(5);
        assertTrue(it.hasPrevious());
        assertTrue(it.hasNext());
        assertAll(
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.next(),it2.next()),
                ()->assertEquals(it.previous(),it2.previous()),
                ()->assertEquals(it.previous(),it2.previous())
        );

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