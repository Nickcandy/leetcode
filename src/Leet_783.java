import java.util.ArrayList;
import java.util.List;

class Solution_783 {
    public int minDiffInBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        traversing(root,list);
        int len = list.size();
        int min = Integer.MAX_VALUE;
        for (int i = 0;i < len-1;i ++)
            if(Math.abs(list.get(i)-list.get(i+1)) < min) min = Math.abs(list.get(i)-list.get(i+1));
        return min;
    }
    void traversing(TreeNode root, List<Integer> list){
        if(root.left != null) traversing(root.left, list);
        list.add(root.val);
        if(root.right != null) traversing(root.right, list);
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
