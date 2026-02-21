class SharedResource {
    int item;
    boolean available = false;

    // synchronize put method
    synchronized void put(int item) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        this.item = item;
        available = true;
        System.out.println("Produced: " + item);
        notify();
    }

    // synchronize get method
    synchronized void get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println("Consumed: " + item);
        available = false;
        notify();
    }
}

class Producer extends Thread {
    SharedResource resource;

    // Constructor
    Producer(SharedResource resource) {
        this.resource = resource;
    }

    // run method
    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.put(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    // Constructor
    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    // run method
    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.get();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        // Create shared resource
        SharedResource resource = new SharedResource();

        // Create producer and consumer
        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        // Start threads
        p.start();
        c.start();
    }
}

