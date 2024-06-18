package oopproject1.data;

import java.util.NoSuchElementException;

public class LinkedStack<E> {
    private class Node {
        E key;
        Node next;
        Node(E key, Node next){
            this.key = key;
            this.next = next;
        }
    }

    private Node top;
    public boolean isEmpty() { return top == null; }
    public void push(E obj) {
        top = new Node(obj, top);
    }

    public E pop() {
        if(isEmpty()) throw new NoSuchElementException();
        E retval = top.key;
        top = top.next;
        return retval;
    }
    public E peek(){
        return isEmpty() ? null : top.key;
    }
}
