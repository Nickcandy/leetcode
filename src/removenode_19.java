
class Solution19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int length = 0;
        ListNode p = head;
        while(p.next != null){
            length ++;
            p = p.next;
        }
        if(length == 0) return null;
        if(n == length) return head.next;
        p = head;
        int k = 0;
        while(k < n){
            head = head.next;
            k ++;
        }
        if (n != 1) head.next = head.next.next; else head.next = null;
        return p;
    }
    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}