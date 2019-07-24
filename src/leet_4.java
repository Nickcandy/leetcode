public class leet_4 {
    class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int a = nums1.length;
            int b = nums2.length;
            int m = 0,n = 0;
            int tar = (a+b)/2+1;
            int h = tar;
            if (a > b) {n = b;m = tar-b;} else {m = a;n = tar-a;}
            m --;n --;
            while(true){
                if(h % 2 == 0) h = h / 2; else h = h/2+1;
                if((m == a-1 || n == -1 || nums1[m+1] >= nums2[n]) && (n == b-1 || m == -1 || nums1[m] <= nums2[n+1])) break;
                if(m != a-1 && n!= -1 && nums1[m+1] < nums2[n]) {m = m+h;n = n-h;}
                else
                if(n != b-1 && m != -1 &&nums1[m] > nums2[n+1]) {m = m-h;n = n+h;}
                if(m < 0){m = -1;n = tar -1;}
                if(n < 0){n = -1;m = tar -1;}
                if(m >= a) {m = a-1;n = tar-a-1;}
                if(n >= b) {m = tar-b-1;n = b-1;}
            }

            int p = 0;
            int q = 0;
            if(m!=-1 && n!=-1) p = Math.max(nums1[m],nums2[n]);
            if(m!=-1 && n!=-1) q = Math.min(nums1[m],nums2[n]);
            if(m == -1) {p = nums2[n];if(n>0) q = nums2[n-1];}
            if(n == -1) {p = nums1[m];if(m>0) q = nums1[m-1];}
            if (m > 0) q = Math.max(q,nums1[m-1]);
            if (n > 0) q = Math.max(q,nums2[n-1]);
            if((a+b)%2 == 1) return p; else return((double)(p+q)/2);
        }
    }
}
