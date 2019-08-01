class Solution_552{
        public int checkRecord(int n) {
            int[] f = new int[n+1];
            int k = 1000000007;
            int sum = 0;
            f[0] = 1;f[1] = 2;if (n > 1) f[2] = 4;
            if (n>2) f[3] = 7;
            for (int i = 4;i <= n;i ++){
                f[i] = f[i-1]*2 - f[i-4];
                if (f[i] > k) f[i] %= k;
                if (f[i] < 0) f[i] += k;
            }

            for(int i = 1;i <= n;i ++){
                long kk = (long)f[i-1]*(long)f[n-i];
                kk = kk % k;
                sum += (int)kk;
                sum %= k;
            }
            sum += f[n];
            sum %= k;
            return sum;
        }
}
