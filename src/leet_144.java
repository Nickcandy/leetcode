import java.util.ArrayList;
import java.util.List;

class Solution_144 {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList();
        res = preorder(root);
        return res;
    }
    List<Integer> preorder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        res.add(root.val);
        if (root.left!= null) res.addAll(preorder(root.left));
        if (root.right!= null) res.addAll(preorder(root.right));
        return  res;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}