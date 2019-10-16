import java.util.ArrayList;
import java.util.List;


class Solution_145 {
    public List<Integer> postorderTraversal(TreeNode root) {
        if (root != null) return (traversal(root));
        return new ArrayList<>();
    }

    List<Integer> traversal(TreeNode root){
        List<Integer> list = new ArrayList<>();
        if (root.left != null) list.addAll(traversal(root.left));
        if (root.right != null)list.addAll(traversal(root.right));
        if (root == null) list.add(root.val);
        return list;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}
