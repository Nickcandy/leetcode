class Solution_396 {
    public int maxRotateFunction(int[] A) {
        int len = A.length;
        int res = 0,sum = 0,ans = 0;
        for (int i = 0;i < len;i ++) {res += i*A[i]; sum += A[i];ans = res;}
        for (int i = len-1;i >= 0;i --){
            res = res-A[i]*(len-1)+sum-A[i];
            if (res > ans) ans = res;
        }
        return ans;

    }
}