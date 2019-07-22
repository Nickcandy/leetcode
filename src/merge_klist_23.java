class mergeKlist {
    public ListNode mergeKLists(ListNode[] lists) {
        int len  = lists.length;
        int i = 0, j = len -1;
        ListNode til;
        ListNode t;
        ListNode res = new ListNode(0);
        while(i < j){
            while(i < j && lists[i] != null) i ++;
            while(i < j && lists[i] == null) j --;
            t = lists[i];
            lists[i] = lists[j];
            lists[j] = t;
        }
        len = j;
        build(lists, len);
        til = res;
        while(true){
            res.val = lists[0].val;
            lists[0] = lists[0].next;
            if(lists[0] == null){lists[0] = lists[len]; len --;}
            if(len != 0) fix(lists, len, 0); else {res.next = lists[0]; break;}
            res.next = new ListNode(0);
            res = res.next;
        }
        return til;
    }
    void build(ListNode[] lists,int len){
        if (len == 0) return;
        for(int i = len/ 2;i >= 0;i --){
            fix(lists, len, i);
        }
    }

    void fix(ListNode[] lists,int len, int k){
        int p = 0,q = 0,r = lists[k].val;
        ListNode t;
        if (k * 2 + 1 <= len) p = lists[k * 2 + 1].val;
        if (k * 2 + 2 <= len) q = lists[k * 2 + 2].val;
        if (p < q && r > p) {
            t = lists[p];
            lists[p] = lists[r];
            lists[r] = t;
            fix(lists, len, p);
        }
        if (p > q && r > q) {
            t = lists[q];
            lists[q] = lists[r];
            lists[r] = t;
            fix(lists, len, q);
        }

    }
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }
}