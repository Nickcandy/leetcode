import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution_969 {
    public List<Integer> pancakeSort(int[] A) {
        int i,j, len = A.length,t;
        int p = 0;
        int[] a = new int[len];
        List<Integer> res = new ArrayList<>();
        for (i = 0;i < len;i ++) a[i] = A[i];
        for (i = 0;i < len;i ++)
            for (j = i+1;j < len;j ++)
                if(a[i] > a[j]) {
                    t = a[i];
                    a[i] = a[j];
                    a[j] = t;
                }
        for (i = len-1;i > 0;i --){
            for (j = 0;j <= i;j ++)
                if (A[j] == a[i]) {p = j;}
            if (p == i) continue;
            if (p != 0){
                reverse(A,p);
                res.add(p+1);
            }
            res.add(i+1);
            reverse(A,i);
        }
        return res;
    }
    void reverse(int[] a, int p){
        for (int i = 0;i <= p/2;i ++){
            int t = a[i];
            a[i] = a[p-i];
            a[p-i] = t;
        }
    }
}