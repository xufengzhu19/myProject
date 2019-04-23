package basic.datatype.list;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MyList {

    //头插入
    public static void headInsert(ListNode head, ListNode newhead) {
        ListNode old = head;
        head = newhead;
        head.next = old;
    }
    //尾插入
    public static void tailInsert(ListNode tail,ListNode newtail){

    }

//    遍历
//查找

//    /    插入

//    删除

    public static void main(String[] args) {
        LinkedList a=new LinkedList();
//        ListNode b=new ListNode(2);
//        ListNode c=new ListNode(3);
//        ListNode d=new ListNode(4);
//
//        a.next=b;
//        c.next=

//        Stack st=new Stack();
    }
}

