class Solution_91 {
    public int numDecodings(String s) {
        int len  = s.length();
        int[] f = new int[10000];
        if (s.charAt(0) != '0') f[0] = 1;
        if (len == 1) return f[len-1];
        if (s.charAt(1) == '0') f[1] = 0; else f[1] = f[0];
        if (s.charAt(0) != '0' && (s.charAt(0)-'0')*10 + s.charAt(1) - '0' <= 26 && (s.charAt(0)-'0')*10 + s.charAt(1) - '0'>0) f[1]++;
        for (int i = 2;i < len;i ++){
            if (s.charAt(i)!= '0')f[i] = f[i-1];
            if (s.charAt(i-1) != '0' && (s.charAt(i-1)-'0')*10 + s.charAt(i) - '0' <= 26 && (s.charAt(i-1)-'0')*10 + s.charAt(i) - '0'>0) f[i] += f[i-2];
        }
        return (f[len-1]);
    }
}