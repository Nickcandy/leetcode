import java.util.Stack;

class Solution20 {
    public boolean isValid(String s) {
        Stack<Integer> stack = new Stack<Integer>();
        int len = s.length();
        int i = 0;
        for (i = 0;i < len;i ++){
            if (s.charAt(i) == '(') stack.push(1);
            else if (s.charAt(i) == '[') stack.push(2);
            else if (s.charAt(i) == '{') stack.push(3);
            else if (s.charAt(i) == ')') {if (stack.peek() == 1) stack.pop(); else return false;}
            else if (s.charAt(i) == ']') {if (stack.peek() == 2) stack.pop(); else return false;}
            else if (s.charAt(i) == '}') {if (stack.peek() == 3) stack.pop(); else return false;}
        }
        return stack.empty();
    }
}