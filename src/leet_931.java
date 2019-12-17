class Solution_931 {
    public int minFallingPathSum(int[][] A) {
        int n = A.length;
        int[][] f = new int[n][n];
        for (int i = n-2;i >= 0;i --){
            for (int j = 1;j < n-1;j ++)
                f[i][j] = Math.min(Math.min(f[i+1][j]+A[i+1][j],f[i+1][j-1]+A[i+1][j-1]),f[i+1][j+1]+A[i+1][j+1]);
            f[i][0] = Math.min(f[i+1][0]+A[i+1][0], f[i+1][1]+A[i+1][1]);
            f[i][n-1] = Math.min(f[i+1][n-1]+A[i+1][n-1],f[i+1][n-2]+A[i+1][n-2]);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0;i < n;i ++) if (f[0][i]+A[0][i] < res ) res = f[0][i];
        return res;
    }
}