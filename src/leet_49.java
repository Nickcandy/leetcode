import java.util.*;

class Solution_49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        int len  = strs.length;
        Map<String, Integer> map = new HashMap<>();
        List<List<String>> res = new ArrayList<>();
        for(int i =0; i < len;i ++){
            char[] st = strs[i].toCharArray();
            Arrays.sort(st);
            if(!map.containsKey(Arrays.toString(st))) {map.put(Arrays.toString(st),map.size());res.add(new ArrayList<>());}
            res.get(map.get(Arrays.toString(st))).add(strs[i]);
        }
        return res;
    }
}