import java.util.ArrayList;
import java.util.List;

class Solution_778 {
    public int swimInWater(int[][] grid) {
        List<Coor> list = new ArrayList<>();
        int n = grid.length;
        int[][] f= new int[n][n];
        Coor a = new Coor(0,0);
        list.add(a);
        for (int i = 0;i < n;i ++)
            for (int j = 0;j < n;j ++)
                f[i][j] = n*n;
        f[0][0] = grid[0][0];
        while(list.size() > 0){
            a = list.get(0);
            if (a.x > 0){
                Coor b = new Coor(a.x-1,a.y);
                if (f[a.x][a.y] < f[b.x][b.y]){
                    int k = f[b.x][b.y];
                    f[b.x][b.y] = Math.max(grid[b.x][b.y],f[a.x][a.y]);
                    if (k != f[b.x][b.y]) list.add(b);
                }
            }
            if (a.y > 0){
                Coor b = new Coor(a.x,a.y-1);
                if (f[a.x][a.y] < f[b.x][b.y]){
                    int k = f[b.x][b.y];
                    f[b.x][b.y] = Math.max(grid[b.x][b.y],f[a.x][a.y]);
                    if (k != f[b.x][b.y]) list.add(b);
                }
            }
            if (a.x < n-1){
                Coor b = new Coor(a.x+1,a.y);
                if (f[a.x][a.y] < f[b.x][b.y]){
                    int k = f[b.x][b.y];
                    f[b.x][b.y] = Math.max(grid[b.x][b.y],f[a.x][a.y]);
                    if (k != f[b.x][b.y]) list.add(b);
                }
            }
            if (a.y < n-1){
                Coor b = new Coor(a.x,a.y+1);
                if (f[a.x][a.y] < f[b.x][b.y]){
                    int k = f[b.x][b.y];
                    f[b.x][b.y] = Math.max(grid[b.x][b.y],f[a.x][a.y]);
                    if (k != f[b.x][b.y]) list.add(b);
                }
            }
            list.remove(0);
        }
        return (f[n-1][n-1]);

    }
    public class Coor {
        int x;
        int y;
        Coor(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}