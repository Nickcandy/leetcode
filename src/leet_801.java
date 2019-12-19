class Solution_801 {
    public int minSwap(int[] A, int[] B) {
        int len = A.length;
        int[] f = new int[len];
        int[] g = new int[len];
        f[0] = 0;g[1] = 1;
        for(int i = 1;i < len;i ++){
            if (A[i]<=B[i-1] || B[i]<=A[i-1]) {f[i] = f[i-1];g[i] = g[i-1]+1;} else
            if (A[i]<=A[i-1] || B[i]<=B[i-1]) {f[i] = g[i-1];g[i] = f[i-1]+1;} else{
                f[i] = Math.min(g[i-1],f[i-1]);
                g[i] = f[i]+1;
            }
        }
        return Math.min(f[len-1],g[len-1]);
    }
}