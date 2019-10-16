class Solution_563 {
    public int findTilt(TreeNode root) {
        if (root == null) return 0;
        return tilt(root);
    }
    public int tilt(TreeNode root){
        int res = 0;
        int left = 0, right = 0;
        if (root.left != null) {res += tilt(root.left);left = sum(root.left);}
        if (root.right != null) {res += tilt(root.right);right = sum(root.right);}
        res += Math.abs(left - right);
        return res;
    }
    public int sum(TreeNode root){
        int res = 0;
        if (root.left != null) res+= sum(root.left);
        if (root.right != null) res+= sum(root.right);
        return res+=root.val;
        }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }
}