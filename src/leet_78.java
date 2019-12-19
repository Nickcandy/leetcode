import java.util.ArrayList;
import java.util.List;

class Solution_78 {
    public List<List<Integer>> subsets(int[] nums) {
        return search(0,nums);
    }
    List<List<Integer>> search(int n, int [] nums){
        List<List<Integer>> res  = new ArrayList<>();
        if (n != nums.length-1) res = search(n+1, nums);
        else {
            List<Integer> a = new ArrayList<>();
            res.add(a);
            List<Integer> b = new ArrayList<>();
            b.add(nums[n]);
            res.add(b);
            return res;
        }
        int len = res.size();
        for (int i = 0;i < len;i ++){
            List<Integer> k = new ArrayList<>();
            k.addAll(res.get(i));
            k.add(nums[n]);
            res.add(k);
        }
        return res;
    }
}