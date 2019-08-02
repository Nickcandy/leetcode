class Solution_714 {
    public int maxProfit(int[] prices, int fee) {
        int i,k = 0,sum = 0,len = prices.length;
        int max = 0,m = 0;
        int[] f = new int[len+2];
        f[len-1] = 0;max = prices[len-1];k = len-1;
        for (i = len-2;i >= 0;i --){
            f[i] = f[i+1];
            if(max!=0 && prices[i] < prices[m]) {f[i] = Math.max(f[i],f[m+1]+prices[m]-prices[i]-fee);max = 0;sum  = f[i];k = i;}
            if(sum!=0 && prices[i] < prices[k]) {f[i] = Math.max(f[i],f[k]+prices[k]-prices[i]);if (f[i] == f[k]+prices[k]-prices[i]) {k = i; sum = f[i];}}
            if (f[i] == f[i+1]){max = Math.max(max,prices[i]);m = i;} else max = 0;
        }
        return f[0];
    }
}