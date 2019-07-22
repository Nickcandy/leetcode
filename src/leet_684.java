import java.util.HashSet;
import java.util.Set;

    class Solution_684 {
        public int[] findRedundantConnection(int[][] edges) {
            int len  = edges.length;
            int t;
            int colorNum = 0;
            HashSet<Integer>[] color = new HashSet[500];
            int[] f = new int[1000];
            for (int i  = 0;i < len;i ++){
                int a = edges[i][0];
                int b = edges[i][1];
                if (f[a] == f[b] && f[a] != 0) return edges[i];
                if (f[a] == 0) {t = a;a = b;b = t;}
                if (f[b] == 0 && f[a] == 0) { f[b] = ++colorNum;f[a] = f[b];color[colorNum] = new HashSet<>(); color[colorNum].add(b);color[colorNum].add(a); } else
                    if (f[b] == 0 && f[a] != 0) {f[b] = f[a]; color[f[a]].add(b);}
                    else {
                        color[f[a]].addAll(color[f[b]]);
                        for (int node : color[f[b]]) f[node] = f[a];
                    }
            }
            return null;
        }
    }

