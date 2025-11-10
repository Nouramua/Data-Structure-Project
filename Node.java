public class Node<T> {

    public T data;
    public Node<T> next;

    Node() {
        data = null;
        next = null;
    }
    
    Node(T data) {
        this.data = data;
        this.next = null;
    }
}