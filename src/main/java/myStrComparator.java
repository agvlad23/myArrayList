import java.util.Comparator;

/**
 * Comparator for String that compare only last char
 */
public class myStrComparator implements Comparator<String> {

    @Override
    public int compare(String s, String t1) {
        if(s.isEmpty()||t1.isEmpty())
            return 0;
        int i1=s.length()-1;
        int i2=t1.length()-1;

        boolean b=s.charAt(i1)>t1.charAt(i2);
        boolean b2=s.charAt(i1)<t1.charAt(i2);
        if (b)
            return 1;
        else if(b2)
            return -1;
        return 0;
    }
}
