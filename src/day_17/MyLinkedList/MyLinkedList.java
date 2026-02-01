package day_17.MyLinkedList;

import java.util.StringJoiner;

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

    public void add(E e){//新增节点
        //直接从last后面开始新增，不管链表中原本有没有节点，都是可以使用的(原本没有节点的last是null)
        Node<E> newNode = new Node<>(last,e,null);
/*
前向连接 ： newNode.prev = last （在构造函数中完成）
后向连接 ： last.next = newNode （在else分支中完成）
指针更新 ： last = newNode （确保后续添加操作基于新的尾节点）
### 3. 为什么需要双向连接？
如果只设置 newNode.prev = last ，而不设置 last.next = newNode ：

- 新节点知道自己的前一个节点是last ，但
- last不知道自己的后一个节点是newNode
这会导致：

- 从 first 开始遍历链表时，无法到达 newNode （因为 last.next 仍然是 null ）
- 链表结构不完整，新节点实际上 未被整合到链表中
* */
       if(first == null){
           first = newNode; //如果原来不存在首节点，那么新增的节点就是链表的首节点，这样直接操作赋值的是地址
       }else{
           last.next = newNode;//添加的方式，尾插的方式就是直接在其last的下一位的地址改为该节点的地址实现添加到链表中
       }
        last = newNode;//新结点成了链表的最后一个结点
        //元素或结点个数+1
        size++;
    }

    public Node<E> findNode(Object o){//查找
        if (o == null) {
            //System.out.println("查询的对象为null");
            return null;
        }else{
            Node<E> myNode =first; //这个时候直接从第一个的地址给创建的新对象，然后按照顺序往后走
            while (myNode.next != null){//链表不是空的或则不是最后一个节点
                if ( !myNode.equals(o)){
                    myNode = myNode.next;//一个链表中，节点的next就是下一个节点的地址，这里作为一个新对象，也就等于是变为了下一个节点
                    break;
                }
            }
            return myNode;//如果没找到,node=null，如果找到了，node是目标结点的地址
        }
    }

    @Override
    public String toString(){
        //拼接所有结点的data，返回
        StringJoiner joiner = new StringJoiner(",","[","]");
        Node<E> node = first;
        while(node!= null){
            joiner.add(node.data+"");
            node = node.next;//让node往右移动
        }

        return joiner.toString();
    }
}
