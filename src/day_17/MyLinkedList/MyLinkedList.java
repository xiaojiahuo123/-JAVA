package day_17.MyLinkedList;

public class MyLinkedList<E> {
    private Node<E> first;//用于记录双向链表的头结点的地址，默认值是null
    private Node<E> last;//用于记录双向链表的尾结点的地址，默认值是null
    private int size;//记录双向链表中结点的数量，同时就是元素个数

    //添加内部类 ,静态成员内部类，因为在Node内部类中，没有用到外部类MyLinkedList的任何非静态成员
    private static class Node<E>{
        Node<E> previous;//记录前一个结点的地址
        E data;
        Node<E> next;//记录下一个结点的地址

        Node(Node<E> previous, E data, Node<E> next) {
            this.previous = previous;
            this.data = data;
            this.next = next;
        }
    }
}
