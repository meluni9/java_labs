package lab4;

import java.io.Serializable;

public interface MyList<E> extends Serializable {
    void add(E e);
    void add(int index, E element);
    
    void addAll(MyList<? extends E> c);
    
    E get(int index);
    E remove(int index);
    void set(int index, E element);
    int indexOf(Object o);
    int size();
    Object[] toArray();
}