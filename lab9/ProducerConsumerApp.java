package lab9;

import java.util.Random;

class Node {
    String data;
    Node next;
    
    public Node() { this.data = null; }
}

class RingBuffer {
    private final int capacity;
    private int size = 0;
    
    private Node head; 
    private Node tail; 

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        
        Node first = new Node();
        Node current = first;
        for (int i = 1; i < capacity; i++) {
            Node newNode = new Node();
            current.next = newNode;
            current = newNode;
        }
        current.next = first; 

        this.head = first;
        this.tail = first;
    }

    public synchronized void put(String item) throws InterruptedException {
        while (size == capacity) {
            wait();
        }

        tail.data = item;
        tail = tail.next;
        size++;

        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (size == 0) {
            wait();
        }

        String item = head.data;
        head = head.next;
        size--;

        notifyAll();
        return item;
    }
}

public class ProducerConsumerApp {
    public static void main(String[] args) {
        System.out.println("=== Task 2: Producer-Consumer (Ring Buffer) ===");

        RingBuffer buffer1 = new RingBuffer(10);
        RingBuffer buffer2 = new RingBuffer(10);

        for (int i = 1; i <= 5; i++) {
            final int threadNum = i;
            Thread producer = new Thread(() -> {
                Random random = new Random();
                while (true) {
                    try {
                        String msg = "Потік №" + threadNum + " згенерував повідомлення " + random.nextInt(100);
                        buffer1.put(msg);
                        Thread.sleep(random.nextInt(100)); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            producer.setDaemon(true); 
            producer.start();
        }

        for (int i = 1; i <= 2; i++) {
            final int threadNum = i;
            Thread translator = new Thread(() -> {
                while (true) {
                    try {
                        String msg = buffer1.take();
                        String newMsg = "Потік №" + threadNum + " переклав повідомлення: [" + msg + "]";
                        buffer2.put(newMsg);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            translator.setDaemon(true); 
            translator.start();
        }

        try {
            for (int i = 1; i <= 100; i++) {
                String finalMsg = buffer2.take();
                System.out.println("Main отримав (" + i + "/100): " + finalMsg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Робота завершена. Демон-потоки будуть автоматично зупинені.");
    }
}
