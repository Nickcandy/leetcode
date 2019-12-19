class Solution_649 {
    public String predictPartyVictory(String senate) {
        boolean  flag = false;
        int r = 0,d = 0;
        while(!flag){
            char ch = senate.charAt(0);
            int len = senate.length();
            int i;
            flag = true;
            for (i = 0;i < len;i ++){
                if (senate.charAt(i)!= ch) flag  = false;
                if (r == 0) {if (senate.charAt(i) == 'R') r ++; else r--;} else
                if (r < 0) {if (senate.charAt(i) == 'R') {senate=senate.substring(0,i)+senate.substring(i+1);len --;i--;d--;} else d ++;} else
                if (r > 0 && d == 0) {if (senate.charAt(i) == 'D') {senate=senate.substring(0,i)+senate.substring(i+1);len --;i--;r--;} else r ++;}
            }
        }
        if (senate.charAt(0) == 'R') return "Radiant"; else return "Dire";
    }
}