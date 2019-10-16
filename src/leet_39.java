import java.util.ArrayList;
import java.util.List;

class Solution_39 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> list = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        search(candidates,target,list, res, 0);
        return res;
    }
    void search(int[] c, int tar, List<Integer> list, List<List<Integer>> res, int til ){
        if (til == c.length) return ;
        if (c[til] == tar) {list.add(c[til]);List<Integer> k = new ArrayList<>();k.addAll(list); res.add(k);list.remove(list.size()-1);}
        if (c[til] < tar) {list.add(c[til]);search(c,tar-c[til],list,res,til);list.remove(list.size()-1);}
        search(c,tar,list,res,til+1);
    }

}