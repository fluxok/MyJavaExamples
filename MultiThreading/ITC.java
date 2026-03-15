class SharedResource {
    private short n;
    boolean hasData;
    synchronized short get(){
        while(!hasData){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e){
            System.out.println("InterruptedException");
        }
        System.out.println("GOT: "+n);
        notify();
        hasData = false;
        return n;
    }
    synchronized void set(short n){
        while(hasData){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e){
            System.out.println("InterruptedException");
        }
        System.out.println("SET: "+n);
        this.n = n;
        hasData = true;
        notify();
    }
}
class Producer implements Runnable{
    Thread t;
    SharedResource sR;

    Producer(SharedResource sR){
        this.sR = sR;
//        this.t = new Thread(this,"Producer");
        this.t = Thread.ofVirtual().name("Producer").unstarted(this);
    }

    @Override
    public void run() {
        short x=0;
        while(true) sR.set(x++);
    }
}
class Consumer implements Runnable{
    Thread t;
    SharedResource sR;

    Consumer(SharedResource sR){
        this.sR = sR;
//        t = new Thread(this, "Consumer");
        this.t = Thread.ofVirtual().name("Consumer").unstarted(this);
    }

    @Override
    public void run() {
        while (true) sR.get();
    }
}
class ITC{
    public static void main(String[] args) {
        SharedResource sR = new SharedResource();
        Producer p = new Producer(sR);
        Consumer c = new Consumer(sR);

        p.t.start();
        c.t.start();
    }
}