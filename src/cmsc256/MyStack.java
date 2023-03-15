/*
 * MyStack.java
 * Project 4
 * CMSC 256
 * Spring Semester
 *
 * Implementing a Stack Interface and using it to validate HTML tags
 */

package cmsc256;

import bridges.base.SLelement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

public class MyStack<E> implements StackInterface<E> {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(isBalanced(new File("webpage")));
    }

    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        MyStack<String> tags = new MyStack<>();
        addTags(tags, webpage);
        MyStack<String> stack = new MyStack<>();
        return validTags(stack, tags);
    }
    public static boolean validTags(MyStack<String> stack, MyStack<String> tags){
        while (!tags.isEmpty()) {
            String s = tags.pop();
            if (s.equals("</html>")) {
                stack.push("<html>");
            } else if (s.equals("</body>")) {
                stack.push("<body>");
            } else if (s.equals("</p>")) {
                stack.push("<p>");
            } else if (s.equals("</h1>")) {
                stack.push("<h1>");
            } else if (stack.isEmpty() || !stack.pop().equals(s)) {
                return false;
            }
        }
        return stack.isEmpty();
    }
    public static void addTags(MyStack<String> tags, File webpage) throws FileNotFoundException {
        try (Scanner reader = new Scanner(webpage)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                while (line.contains("<") && line.contains(">")) {
                    int leftIndex = line.indexOf("<");
                    int rightIndex = line.indexOf(">");
                    tags.push(line.substring(leftIndex, rightIndex + 1));
                    line = line.substring(rightIndex + 1);
                }
            }
        }
    }

    private SLelement<E> head;

    public MyStack() {
        clear();
    }

    @Override
    public void push(E newEntry) {
        if (newEntry == null) throw new IllegalArgumentException();
        SLelement<E> newNode = new SLelement<>(newEntry);
        if (head == null) {
            head = newNode;
        } else {
            SLelement<E> last = head;
            while (last.getNext() != null) last = last.getNext();
            last.setNext(newNode);
        }
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();
        SLelement<E> currNode = head;
        SLelement<E> prev;
        if (currNode.getNext() == null) {
            E temp = currNode.getValue();
            head = null;
            return temp;
        }
        do {
            prev = currNode;
            currNode = currNode.getNext();
        } while (currNode.getNext() != null);
        E temp = currNode.getValue();
        prev.setNext(null);
        return temp;
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();
        SLelement<E> currNode = head;
        while (currNode.getNext() != null) currNode = currNode.getNext();
        return currNode.getValue();
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        head = null;
    }
}
