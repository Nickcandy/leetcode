class Solution_59 {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        fill(0,0,1,n,res);
        return res;
    }
    void fill(int a,int b, int x, int n,int[][] res){
        int i = 0;
        if (n == 0) return;
        if (n == 1) {res[a][b] = x; return;}
        for (i = b;i < b+n-1;i ++) {res[a][i] = x; x++;}
        for (i = a;i < a+n-1;i ++) {res[i][b+n-1] = x; x++;}
        for (i = b+n-1;i > b;i --) {res[a+n-1][i] = x; x++;}
        for (i = a+n-1;i > a;i --) {res[i][b] = x;x++;}
        fill(a+1,b+1,x,n-2, res);
    }
}