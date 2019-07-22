import java.util.ArrayList;
import java.util.List;

class Solution_22 {
    public List<String> generateParenthesis(int n) {
        List<String> rst = new ArrayList<String>();
        generate("",0,n,rst);
        return  rst;
    }
    void generate(String st,int k,int n, List<String> rst){
        int i;
        if (n == 0) {for(i = 1;i <= k;i ++) st+=")"; rst.add(st); return;}
        else{
            String origin = st;
            st += "(";
            generate(st,k+1,n-1,rst);
            st = origin;
            st += ")";
            if (k > 0) generate(st,k-1,n,rst);
            st = origin;
        }
    }
}