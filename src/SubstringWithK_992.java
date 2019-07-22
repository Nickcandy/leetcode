import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraysWithKDistinct(int[] A, int K) {
            Map<Integer, Integer> map;
            Map<Integer, Integer> lastmap = new HashMap<>();
            int len = A.length - 1;
            int i = 0, j = 0, count = 0, sum = 0, last = 0;
            for (i = 0; i <= len; i++) {
                j = last;
                map = new HashMap<>(lastmap);
                if (i != 0) {
                    if (map.containsKey(A[i-1])) {map.put(A[i - 1], map.get(A[i - 1]) - 1);
                    if (map.get(A[i - 1]) == 0) {map.remove(A[i - 1]);count--;}}
                }
                if(j == i && i != 0)
                    j ++;
                if(K == 1) {j = i+1;count = 1;map.put(A[i],1);}
                while (count < K) {
                    if (j == len+1) return sum;
                    if (map.containsKey(A[j])) {
                        map.put(A[j], map.get(A[j]) + 1);
                    } else {
                        map.put(A[j], 1);
                        count++;
                    }
                    j ++;
                }
                last = j;
                lastmap = new HashMap<>(map);
                sum++;
                while (j <= len && map.containsKey(A[j])) {
                    map.put(A[j], map.get(A[j]) + 1);
                    sum++;
                    j ++;
                }
            }
            return sum;
    }
}