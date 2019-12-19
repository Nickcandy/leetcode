class Solution_60 {
    public String getPermutation(int n, int k) {
        String st = "";
        for (int i = 1;i <= n;i ++) st = st + i;
        return find(st,k-1);
    }
    String find(String st, int k){
        int i,sum = 1;
        String res = "";
        if (st.length() == 1) return st;
        for (i = 1;i < st.length();i ++) sum *= i;
        res += (char)(st.charAt(k/sum));
        st = st.substring(0,k/sum)+ st.substring(k/sum+1);
        res += find(st, k%sum);
        return res;
    }
}