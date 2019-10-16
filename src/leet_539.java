import java.util.List;

class Solution_539 {
    public int findMinDifference(List<String> timePoints) {
        int i, j, min = 1440,begin = -1, end = 1660, last = 0;
        boolean f[] = new boolean[1442];
        for (i = 0; i < timePoints.size(); i++)
            if(f[(timePoints.get(i).charAt(0) - '0') * 600 + (timePoints.get(i).charAt(1) - '0') * 60 + (timePoints.get(i).charAt(3) - '0') * 10 + (timePoints.get(i).charAt(4) - '0')]) return 0; else f[(timePoints.get(i).charAt(0) - '0') * 600 + (timePoints.get(i).charAt(1) - '0') * 60 + (timePoints.get(i).charAt(3) - '0') * 10 + (timePoints.get(i).charAt(4) - '0')] = true;
        for (i = 0;i < 1440;i ++)
            if (f[i]) if(begin == -1) {begin = i;last = i;} else {min = Math.min(min,i-last);last = i;}
        min = Math.min(1440-last+begin,min);
        return min;
    }
}