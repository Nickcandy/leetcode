import java.util.ArrayList;
import java.util.List;

class Solution_32 {
    public int longestValidParentheses(String s) {
        int len = s.length();
        int max = 0;
        int til = 0;
        int sum = -1;
        List<Area> list = new ArrayList<>();
        while(til < len-1){
            if (s.charAt(til) == '(' && s.charAt(til+1) == ')'){
                int head = til, tail = til+1;
                int lhead = -1;
                int ltail = -1;
                while(head != lhead && tail != ltail) {
                    lhead = head;
                    ltail = tail;
                    while (tail < len - 2 && s.charAt(tail + 1) == '(' && s.charAt(tail + 2) == ')') {
                        tail += 2;
                        til = tail;
                    }
                    while (head > 0 && tail < len - 1 && s.charAt(head - 1) == '(' && s.charAt(tail + 1) == ')') {
                        head--;
                        tail++;
                    }

                }

                if (sum == -1) {sum ++;Area area = new Area(head,tail);list.add(area);max=tail-head+1;} else {
                    if (head == list.get(sum).tail + 1){
                        list.get(sum).tail = tail;
                        while(list.get(sum).head > 0 && list.get(sum).tail < len-1 && s.charAt(list.get(sum).head-1) == '(' && s.charAt(list.get(sum).tail+1) == ')'){
                            list.get(sum).head --;
                            list.get(sum).tail ++;
                            while (sum > 0 && list.get(sum).head == list.get(sum-1).tail + 1){
                                sum --;
                                list.get(sum).tail = list.get(sum+1).tail;
                                list.remove(sum+1);
                            }
                        }
                    } else {
                        sum ++;
                        list.add(new Area(head,tail));
                    }
                    if ( list.get(sum).tail -  list.get(sum).head + 1 > max) max =  list.get(sum).tail -  list.get(sum).head + 1;
                    til =  list.get(sum).tail;
                }
            }
            til ++;
        }
        return max;
    }
    public class Area {
        Area(int a, int b){
            head = a;
            tail = b;
        }
        int head;
        int tail;
     }
}
