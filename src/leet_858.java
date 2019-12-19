class Solution_858 {
    public int mirrorReflection(int p, int q) {
        int i;
        for (i = q/2;i >= 1;i --) if (p % i == 0 && q % i == 0) break;
        int sum = p*q/i;
        if ((sum / q)%2 == 0) return 2;
        if ((sum / p)%2 == 1) return 1; else return 0;
    }
}