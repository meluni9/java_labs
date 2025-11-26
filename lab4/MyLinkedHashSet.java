package lab4;

import java.io.Serializable;

public class MyLinkedHashSet<E> implements Serializable {
    private MyLinkedList<E> list;

    public MyLinkedHashSet() {
        this.list = new MyLinkedList<>();
    }

    public boolean add(E e) {
        if (contains(e)) {
            return false;
        }
        list.add(e);
        return true;
    }
    
    public void addAll(MyList<? extends E> c) {
         for (int i = 0; i < c.size(); i++) {
            add(c.get(i));
        }
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

    public int size() {
        return list.size();
    }
    
    public void clear() {
        list = new MyLinkedList<>();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
