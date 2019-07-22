import java.util.HashSet;
import java.util.Set;

class Solution217 {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        int len = nums.length;
        for (int i = 0;i < len;i ++){
            if (hashSet.contains(nums[i])) return true; else
                hashSet.add(nums[i]);
        }
        return false;
    }
}