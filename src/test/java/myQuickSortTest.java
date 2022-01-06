import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class myQuickSortTest {

    MyArrayList<Integer> mA;
    MyArrayList<Integer> mA2;
    ArrayList<Integer> nA;
    ArrayList<Integer> nA2;
    MyArrayList<String> mStrA;
    ArrayList<String> nStrA;
    @BeforeEach
    void setUp() {
        nA=new ArrayList<Integer>(Arrays.asList(555,3,4,5,6,6,7,8,9,999));
        nA2=new ArrayList<Integer>(Arrays.asList(555,3,4,5,6,6,7,8,9,999));
        mA=new MyArrayList<Integer>(Arrays.asList(555,3,4,5,6,6,7,8,9,999));
        mA2=new MyArrayList<Integer>(Arrays.asList(555,3,4,5,6,6,7,8,9,999));
        mStrA=new MyArrayList<String>(Arrays.asList("asd","bcsss","hsasdx","yyyy","ppp","cc"));
        nStrA=new ArrayList<String>(Arrays.asList("cc","asd","ppp","bcsss","hsasdx","yyyy"));

    }

    @Test
    @DisplayName("Sorting Comparable elements")
    void Qs() {
        MyQuickSort.quickSort(nA);
        MyQuickSort.quickSort(mA);
        MyQuickSort.quickSort(mStrA);

        Collections.sort(nA2);
        Collections.sort(mA2);
        Collections.sort(nStrA);

        assertAll(
                ()->assertEquals(nA2,nA),
                ()->assertEquals(nA2,mA),
                ()->assertEquals(mA2.toString(),mA.toString()),
                ()->assertEquals(nA2.toString(),mA.toString()),
                ()->assertEquals(mStrA.toString(),nStrA.toString())
        );
    }

    @Test
    @DisplayName("Sorting elements with Comparator")
    void QsComparator() {
        MyStrComparator comparator= new MyStrComparator();

        MyQuickSort.quickSort(nA);
        MyQuickSort.quickSort(mA);
        MyQuickSort.quickSort(mStrA,comparator);

        Collections.sort(nA2);
        Collections.sort(mA2);
        nStrA.sort(comparator);

        assertAll(
                ()->assertEquals(nA2,nA),
                ()->assertEquals(nA2,mA),
                ()->assertEquals(mA2.toString(),mA.toString()),
                ()->assertEquals(nA2.toString(),mA.toString()),
                ()->assertEquals(nStrA.toString(),mStrA.toString())
        );
    }
}