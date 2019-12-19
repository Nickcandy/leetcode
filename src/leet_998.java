class Solution_998 {
    public TreeNode insertIntoMaxTree(TreeNode root, int val) {
        TreeNode a = root;
        TreeNode res = new TreeNode(val);
        if (root == null) return res;
        if (root.val < val){
            res.left = root;
            return res;
        }
        if (root.right == null && root.val > val){
            root.right = res;
            return root;
        }
        while(a.right != null && a.right.val > val) a = a.right;
        if (a.right == null) a.right = res; else {
            res.left = a.right;
            a.right = res;
        }
        return root;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}