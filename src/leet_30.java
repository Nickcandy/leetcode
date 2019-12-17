import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution_30 {
    public List<Integer> findSubstring(String s, String[] words) {
        int len = s.length();
        int m = words.length;
        int i = 0, j = 0, n = 0;
        List<Integer> res = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        if (m > 0) n = words[0].length();

        for (i = 0; i < m; i++)
            if (map.containsKey(words[i])) map.put(words[i], map.get(words[i]) + 1);
            else map.put(words[i], 1);
        for (i = 0; i < n; i++) {
            int head = i;
            int k = 0;
            Map<String, Integer> mm = new HashMap<>();
            for (j = i; j <= len - n; j += n) {
                String st = s.substring(j, j + n);
                if (mm.containsKey(st)) mm.put(st, mm.get(st) + 1);
                else mm.put(st, 1);
                k ++;
                if (k == m) {
                    if (mm.equals(map)) res.add(head);
                    st = s.substring(head, head + n);
                    if (mm.get(st) == 1) mm.remove(st);
                    else
                        mm.put(st, mm.get(st) - 1);
                    head += n;
                    k --;
                }
            }
        }
        return res;
    }
}