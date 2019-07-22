import java.util.*;

class Solution17 {
    public List<String> letterCombinations(String digits) {
        List<String> rst = new ArrayList<String>();
        int len = digits.length();
        make(digits,0, rst, "");
        return rst;
    }
    void make(String digits,int i, List<String> rst,String st){
        char[][] ltr = {{},{},{'a','b','c'},{'d','e','f'},{'g','h','i'},{'j','k','l'},{'m','n','o'},{'p','q','r','s'},{'v','t','u'},{'w','x','y','z'}};
        int n = 3;
        if (i == digits.length()) {if (i != 0) rst.add(st);return;}
        if (digits.charAt(i) < '2' || digits.charAt(i) > '9') make(digits,i+1, rst, st);
        if (digits.charAt(i) == '9' || digits.charAt(i) == '7') n ++;
        for (int ii = 0;ii < n;ii ++){
            st = st + ltr[digits.charAt(i)-'0'][ii];
            make(digits,i+1,rst,st);
            st = st.substring(0,i);
        }
    }
}