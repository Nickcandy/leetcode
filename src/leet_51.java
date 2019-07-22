import java.util.ArrayList;
import java.util.List;

class Solution_51 {
	    public List<List<String>> solveNQueens(int n) {
	        int[] a = new int[n+1];
	        List<List<String>> res = new ArrayList<>();
	        search(1,a,res);
	        return res;
	    }
	    void search(int m,int[] a, List<List<String>> res) {
	    	int n = a.length-1;
	    	if (m == n+1) {addRes(a,res); return;}
	    	for (int i = 1;i <= n;i ++) {
	    		for (int j = 1;j < m;j ++) 
	    			if (i == a[j] || j+a[j] == m+i || j-a[j] == m-i) continue;
	    		a[m] = i;
	    		search(m+1,a,res);
	    	}
	    	
	    }
	    void addRes(int[] a, List<List<String>> res) {
	    	List<String> k = new ArrayList<String>();
	    	for (int i = 1;i < a.length;i ++) {
	    		String st = new String();
	    		for (int j = 1;j < a.length;j ++)
	    			st += j == a[i]?"Q":".";
	    		k.add(st);
	    	}
	    	res.add(k);
	    }
}
