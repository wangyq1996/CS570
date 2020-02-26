package cs570.edu.assignment;

import java.util.Random;
import java.util.Stack;

/**
 * Editor : Yuqi Wang
 * Date : 11/06/2019
 * @param <E>
 */
public class Treap<E extends Comparable> {

    private Random priorityGenerator;
    private Node<E> root;

    private static class Node<E extends Comparable> extends Treap<E> implements Comparable<E>{
        public E data;
        public int priority;
        public Node<E> left;
        public Node<E> right;

        public Node(E data, int priority){
            if(data == null) throw new NullPointerException("Invalid Data");
            this.data = data;
            this.priority = priority;
            this.left = null;
            this.right =null;
        }


        public Node<E> rotateRight(){
            Node<E> temp = new Node<E>(this.data, this.priority);
            if (this.left != null) {
                if (this.right != null) temp.right = this.right;
                if (this.left.right != null) temp.left = this.left.right;
                this.data = this.left.data;
                this.priority = this.left.priority;
                this.right = temp;
                if (this.left.left != null) this.left = this.left.left;
                else this.left = null;
            }
            return this;
        }


        public Node<E> rotateLeft(){
            Node<E> temp = new Node<E>(this.data, this.priority);
            if (this.right != null) {
                if (this.left != null) temp.left = this.left;
                if (this.right.left != null) temp.right = this.right.left;
                this.data = this.right.data;
                this.priority = this.right.priority;
                this.left = temp;
                if (this.right.right != null) this.right = this.right.right;
                else this.right = null;
            }
            return this;
        }


        // Test Method
        public String toString(){
            StringBuilder str = new StringBuilder();
            str.append(data);
            str.append(',');
            str.append(priority);
            return str.toString();
        }

        @Override
        public int compareTo(E o) {
            if(this.data.compareTo(o) >0) return 1;
            else if(this.data.compareTo(o) < 0)return -1;
            return 0;
        }
    }

    /**
     * @Constructor
     */
    public Treap(){
        this.priorityGenerator = new Random();
        this.root = null;
    }

    public Treap(long seed){
        this.priorityGenerator = new Random(seed);
        this.root = null;
    }

    /**
     * Add Function
     * @param key
     * @return
     */
    public boolean add (E key){
        return add(key,priorityGenerator.nextInt());
    }

    public boolean add (E key, int priority){
        //BST add
        if(root == null) {
            root = new Node (key,priority);
            return true;
        }
        Node temp = root;
        Node addNode = new Node(key,priority);
        Stack<Node> stack = new Stack<>();

        while(temp !=null){
            stack.push(temp);
            if(temp.data.compareTo(key) == 0) return false;
            else if(temp.data.compareTo(key) > 0) temp = temp.left;
            else if(temp.data.compareTo(key) < 0) temp = temp.right;
        }
        if(addNode.data.compareTo(stack.peek().data) <0) stack.peek().left = addNode;
        else if(addNode.data.compareTo(stack.peek().data) >0)stack.peek().right = addNode;

        //Heap Rotate
        while(!stack.isEmpty() && stack.peek().priority < addNode.priority){
            if(stack.peek().left == null) stack.pop().rotateLeft();
            else{
                if(stack.peek().left.priority == addNode.priority) stack.pop().rotateRight();
                else stack.pop().rotateLeft();
            }
        }
        //if(stack.isEmpty()) root = addNode;

        return true;
    }

    /**
     * Delete Function
     * @param key
     * @return
     */
    public boolean delete (E key){
        Node<E> temp = this.root;
        while(temp != null) {
            if(temp.data.compareTo(key) == 0) break;
            else if(temp.data.compareTo(key) > 0) temp = temp.left;
            else if(temp.data.compareTo(key) < 0) temp = temp.right;
        }
        if(temp == null) return false;
        Stack<Node<E>> stack = new Stack<>();
        rotateDown(temp,stack);
        if(stack.peek().left !=null && stack.peek().left.compareTo(key) == 0) stack.peek().left = null;
        else stack.peek().right = null;
        return true;
    }

    /**
     * Find Function
     * @param root
     * @param key
     * @return
     */
    private boolean find (Node<E> root, E key){
        if(root == null) return false;
        while(root != null) {
            if(root.data.compareTo(key) == 0) return true;
            else if(root.data.compareTo(key) > 0) root = root.left;
            else if(root.data.compareTo(key) < 0) root = root.right;
        }
        return false;
    }

    public boolean find (E key){
        return find(this.root,key);
    }

    /**
     * To String
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(root, 1, sb);
        return sb.toString();
    }

    private void print(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        }
        else {
            sb.append("(key=");
            sb.append(node.data);
            sb.append(", ");
            sb.append("priority=");
            sb.append(node.priority);
            sb.append(")");
            sb.append("\n");
            print(node.left, depth + 1, sb);
            print(node.right, depth + 1, sb);
        }
    }

    /**
     * personal function design for Delete only
     * @param node
     * @param stack
     */
    private void rotateDown(Node<E> node,Stack<Node<E>> stack){
        if(node.right == null && node.left == null) return;
        else if(node.right == null){
            Node<E> temp = node.rotateRight();
            stack.push(temp);
            node =temp.right;
            rotateDown(node,stack);
        }
        else if(node.left == null){
            Node<E> temp = node.rotateLeft();
            stack.push(temp);
            node =temp.left;
            rotateDown(node,stack);
        }
        else if(node.left != null && node.right != null){
            if(node.left.priority < node.right.priority){
                Node<E> temp = node.rotateLeft();
                stack.push(temp);
                node =temp.left;
                rotateDown(node,stack);
            }
            else{
                Node<E> temp = node.rotateRight();
                stack.push(temp);
                node =temp.right;
                rotateDown(node,stack);
            }
        }
        else return;
    }


    public static void main(String []args){
        Treap test = new Treap<Integer>();
        test.add('A',10);
        test.add('B',10);
        test.add('E',10);
        test.add('G',10);
        test.add('H',10);
        test.add('K',10);
        //test.add(null);
        System.out.println(test.find('C'));
        test.add('C',80);
        System.out.println(test.toString());
        System.out.println(test.find('C'));
        test.delete('C');
        System.out.println(test.toString());
        test.delete('I');
        System.out.println(test.toString());
    }
}
