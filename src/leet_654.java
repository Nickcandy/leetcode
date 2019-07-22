import java.util.HashMap;
import java.util.Map;

public class leet_654 {
    class Solution {
        public TreeNode constructMaximumBinaryTree(int[] nums) {
            return make(0,nums.length-1, nums);
        }
        public TreeNode make(int a, int b, int[] nums){
            int maxNum = nums[b], flag = 0;
            if (a == b) return new TreeNode(nums[a]);
            for (int i = a;i < b;i ++) if (nums[i] > maxNum) {
                maxNum = nums[i]; flag = i;
            }
            TreeNode root = new TreeNode(maxNum);
            if (flag > a) root.left = make(a,flag-1, nums);
            if (flag < b) root.right = make(flag-1,b, nums);
            return  root;
        }
        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }
    }
}
