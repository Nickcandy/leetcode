import java.util.HashMap;
import java.util.Map;


 class Solution_106 {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            int p = inorder.length-1;
            int q = postorder.length-1;
            if(p == -1) return null;
            Map<Integer,Integer> imap = new HashMap<>();
            for (int i = 0;i <= p;i ++) imap.put(inorder[i],i);
            TreeNode root = make(0,p,0,q, postorder, imap);
            return root;
        }
        TreeNode make(int a, int b, int c, int d, int[] porder, Map<Integer,Integer> imap){
            TreeNode root = new TreeNode(porder[d]);
            if (a == b) return root;
            int k = imap.get(porder[d]);
            root.left = (k-1 >= a) ? make(a,k-1, c, c+k-1-a, porder, imap) : null;
            root.right = (k+1 <= b) ? make(k+1, b, d-b+k, d-1,porder,imap) : null;
            return root;
        }
        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }
    }

