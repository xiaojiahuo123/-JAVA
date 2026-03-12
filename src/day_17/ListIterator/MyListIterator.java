package day_17.ListIterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class MyListIterator {
    @Test
    public void test1(){
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "hello","java","world");

        //演示从头到尾遍历
        ListIterator<String> listIterator = list.listIterator();
        while(listIterator.hasNext()){
            String s = listIterator.next();
            System.out.println(s);
        }
    }

    @Test
    public void test2(){
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "hello","java","world");

        //演示从尾到头遍历
        ListIterator<String> listIterator = list.listIterator(list.size());
/*  此处是因为ArrayList集合中的size方法返回一个int数据，比如此处的数据长度是3，所以对于listIterator(int index) 方法来说，传入的索引是最后一位，从后开始向前迭代
public int size() {
    return size;
}*/
        //迭代器一开始 [size]位置，第一次previous()取[size-1]位置的元素
        while(listIterator.hasPrevious()){//判断前面还有没有元素可迭代器
            String s = listIterator.previous();//previous取迭代器当前位置的前一个位置的元素
            System.out.println(s);
        }
    }
}
