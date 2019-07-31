class Solution_29 {
    public int divide(int dividend, int divisor) {
        int flag = 1;
        int res = 0;
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) flag  =1; else flag  = -1;
        long a = Math.abs((long)dividend);
        long b =  Math.abs((long)divisor);
        res = (int)(a/b);
        return res*flag;
    }
}