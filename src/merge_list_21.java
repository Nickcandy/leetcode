
class Solution21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode k = new ListNode(0);
        ListNode head = k;
        while (l1 != null || l2 != null) {
            if (l1 == null || l1.val > l2.val) {
                k.val = l2.val;
                l2 = l2.next;
            } else {
                k.val = l1.val;
                l1 = l1.next;
            }
            if (l1 != null || l2 != null){
            ListNode nxt = new ListNode(0);
            k.next = nxt;
            k = nxt;}
        }
        return head;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}