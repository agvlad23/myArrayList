import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;


/**
 * Class to Sort List of any type
 */
public class MyQuickSort {

    private static <T> void mySwap(List<T> list,int i,int i1){
        T temp=list.get(i);
        list.set(i,list.get(i1));
        list.set(i1,temp);
    }

    private static <T extends Comparable<T>> int partition(List<T>list,int l,int h){
        
        int i=l-1;
        int j=h+1;
        T pivot=list.get(pivotChose(l,h));
        while (true){
            do
                i++;
            while (list.get(i).compareTo(pivot)<0);

            do
                j--;
            while (list.get(j).compareTo(pivot)>0);

            if(i>=j)
                return j;
            mySwap(list,i,j);
        }
    }

    private static <T> int partition(List<T>list,int l,int h,Comparator<T> c){

        int i=l-1;
        int j=h+1;
        T pivot=list.get(pivotChose(l,h));
        while (true){

            do
                i++;
            while (c.compare(list.get(i),pivot)<0);

            do
                j--;
            while (c.compare(list.get(j),pivot)>0);

            if(i>=j)
                return j;
            mySwap(list,i,j);
        }
    }


    private static int pivotChose(int l,int h){
        return (l + h) / 2;
    }

    private static  <T extends Comparable<T>> void myQuickSort(List<T> list,int l,int h){

        if (l<h){

            int p=partition(list,l,h);

            myQuickSort(list,l,p);
            myQuickSort(list,p+1,h);
        }

    }

    private static <T> void myQuickSort(List<T> list, int l,int h,Comparator<T> comparator){
        if (l<h){

            int p=partition(list,l,h,comparator);

            myQuickSort(list,l,p,comparator);
            myQuickSort(list,p+1,h,comparator);
        }
    }

    /** Sort list with recursive quickSort
     * @param list Not Null List with no nulls to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable> void quickSort(@NotNull List<T> list){
        myQuickSort(list,0,list.size()-1);
    }

    /** Sort list with recursive quickSort and comparator
     * @param list Not Null List to sort
     * @param c Not Null Comparator for List type
     * @param <T> Type
     */
    public static <T> void quickSort(@NotNull List<T> list, @NotNull Comparator<T> c){
        myQuickSort(list,0,list.size()-1,c);
    }
}
