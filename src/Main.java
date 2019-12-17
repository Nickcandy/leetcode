import java.util.HashSet;
import java.util.Set;

import static jdk.internal.org.objectweb.asm.commons.GeneratorAdapter.OR;

public class Main {
    public static void main(String[] args) {
        int[] a = {5,6,8,7,2,4,6,3,9,10,1};
        make(a,0,a.length-1);
        for (int i = 0;i < a.length;i ++)
        System.out.println(a[i]);
    }
    public static void make(int[] a,int head, int tail){
        int x = a[head];
        int i = head;
        int j = tail;

        while(i < j){
            while (a[j] >= x && i < j) j --;
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
            while (a[i] <= x && i < j) i ++;
            t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        i ++;j --;
        for (int ii = head;ii <= tail;ii ++)
            System.out.print(a[ii]);
        System.out.println();
        if (j > head) make(a,head,j);
        if (i < tail) make(a,i,tail);
    }

}