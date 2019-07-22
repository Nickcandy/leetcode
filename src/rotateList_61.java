class Solution61 {
    public ListNode rotateRight(ListNode head, int k) {
        ListNode tail = head;
        int len = 1;
        while(tail.next != null){
            tail = tail.next;
            len ++;
        }
        tail.next = head;
        k = k % len;
        int p = len-k;
        while(p > 0){
            p --;
            head = head.next;
        }
        ListNode res = head.next;
        head.next = null;
        return res;

    }
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
