class Solution_1144 {
    public int movesToMakeZigzag(int[] nums) {
        int len = nums.length;
        int[] f = new int[len];
        int[] g = new int[len];
        for (int i = 0;i < len-1;i ++) {f[i] = nums[i+1]-nums[i];g[i] = f[i];}
        int sum = 0, res;
        int flag= 1;
        for (int i = 0;i < len-1;i++){
            flag = -flag;
            if(flag*f[i] >= 0) {
                sum += Math.abs(f[i])+1;
                if(flag > 0 && i <= len-3) f[i+1] = nums[i+2]-nums[i]+1;
            }
        }
        res = sum;
        flag = -1;
        sum  = 0;
        for (int i = 0;i < len-1;i++){
            flag = -flag;
            if(flag*g[i] >= 0) {
                sum += Math.abs(g[i])+1;
                if(flag > 0 && i <= len-3) g[i+1] = nums[i+2]-nums[i]+1;
            }
        }
        res = Math.min(res,sum);
        return res;
    }
}