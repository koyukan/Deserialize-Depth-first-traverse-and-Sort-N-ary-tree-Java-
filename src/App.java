import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class App {

    /*
     * Given string (e.g: "a2b1t4r7p1prtbac1cm2m") is a serialized n-ary tree. We
     * need to: * Deserialize the string into a n-ary tree * Sort the adjecent nodes
     * based on the order value * Perform a depth first traversal and print the key
     * values
     * 
     * 
     * Assumptions * Key values are unique * Node order values are single digit
     * (0-9)
     * 
     */

    /*
     * Create a n-ary tree data structure
     */
    public static class TreeNode {
        int order;
        Character key;
        int depth;
        List<TreeNode> children = new ArrayList<TreeNode>();

        TreeNode(int data, Character ch, int dpth) {
            order = data;
            key = ch;
            depth = dpth;
        }

        TreeNode(int data, Character ch, List<TreeNode> chld) {
            order = data;
            key = ch;
            children = chld;
        }

        public int getOrder() {
            return order;
        }
    }

    /*
     * Deserialize the string into a n-ary tree
     */

    public static TreeNode deserialize(String str) {
        Stack<TreeNode> stack = new Stack<>();

        stack.push(new TreeNode(0, '*', 0));

        char[] serialized = str.toCharArray();

        for (int i = 0; i < serialized.length; i++) {

            if (serialized[i] == stack.peek().key) {
                /*
                 * if the current character marks the end of the subtree, pop the child node and
                 * add it into parent
                 */
                TreeNode childNode = stack.pop();
                if (stack.size() > 0) {
                    TreeNode parentNode = stack.peek();
                    parentNode.children.add(childNode);
                    Collections.sort(parentNode.children, Comparator.comparing(TreeNode::getOrder).reversed());
                }

            } else {
                /*
                 * add the new node directly into the stack
                 */
                int depth = stack.peek().depth + 1;
                stack.push(new TreeNode(Character.getNumericValue(serialized[i + 1]), serialized[i], depth));
                i++;
            }
        }
        return stack.pop();
    }

    /*
     * Perform a DFT.
     */
    public static void depthFirstTraversal(TreeNode start) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        Map<Character, Boolean> isVisited = new HashMap<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            isVisited.put(current.key, true);
            if (current.depth > 0) {
                String builDash = new String(new char[current.depth - 1]).replace('\0', '_');
                System.out.println(builDash + current.key + " ");
            }
            for (TreeNode dest : current.children) {
                if (!isVisited.containsKey(dest.key)) {
                    isVisited.put(dest.key, false);
                }
                if (!isVisited.get(dest.key))
                    stack.push(dest);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        // "a2b1t4r7p1prtbac1x0xcm2m"
        // "a2ab3f1fg2gb"
        String s = args[0];
        depthFirstTraversal(deserialize(s));

    }
}
