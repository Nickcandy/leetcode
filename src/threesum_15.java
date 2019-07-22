import java.util.*;

class Solution15 {
    public List<List<Integer>> threeSum(int nums[]) {
        HashSet<Integer> dup = new HashSet<Integer>();
        HashSet<Integer> set = new HashSet<Integer>();
        List rst = new ArrayList();
        int max = Integer.MIN_VALUE;int min = Integer.MAX_VALUE;
        if (nums.length < 3) return rst;
        for(int i = 0;i < nums.length;i ++) {
            if (set.contains(nums[i])) dup.add(nums[i]); else set.add(nums[i]);
            if(nums[i] > max) max = nums[i];
            if(nums[i] < min) min = nums[i];
        }
        Arrays.sort(nums);
        for(int i = 0;i < nums.length;i ++) {
            if(i != 0 && nums[i] == nums[i-1]) continue;
            List black = new ArrayList();
            for (int j = i + 1; j < nums.length; j++) {
                int sum = -nums[i] - nums[j];
                if (sum < min) break;
                if (sum > max) continue;
                if (j>2 && nums[j] == nums[j-2]) continue;
                if (set.contains(sum) && !black.contains(sum)) {
                    if(nums[i] == nums[j]*-2) if (!dup.contains(nums[j])) continue;
                    if(nums[j] == nums[i]*-2) if (!dup.contains(nums[i])) continue;
                    if(nums[i] == 0 && nums[j] ==0 & (j == nums.length-1 || nums[j+1]!=0)) continue;
                    List p = new ArrayList();
                    p.add(nums[i]);
                    p.add(nums[j]);
                    p.add(sum);
                    rst.add(p);
                    black.add(nums[j]);
                    black.add(sum);
                }
            }
            set.remove(nums[i]);
        }
        return rst;
    }
}