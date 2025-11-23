package lab2;

import java.io.Serializable;

public class MyLinkedHashSet implements Serializable {
    private MyLinkedList list;

    public MyLinkedHashSet() {
        this.list = new MyLinkedList();
    }

    public boolean add(Object e) {
        if (contains(e)) {
            return false;
        }
        list.add(e);
        return true;
    }

    public boolean contains(Object o) {
        return list.indexOf(o) != -1;
    }

    public boolean remove(Object o) {
        int index = list.indexOf(o);
        if (index != -1) {
            list.remove(index);
            return true;
        }
        return false;
    }

    public void clear() {
        list = new MyLinkedList();
    }

    public int size() {
        return list.size();
    }
    
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
