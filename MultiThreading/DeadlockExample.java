class A implements Runnable{
    Thread t;
    B b;

    A(){
        this.t = new Thread(this,"Thread A");
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public void run(){
        b.methodB(this);
    }

    synchronized void methodA(B b){
        System.out.println("methodA");
//        try{Thread.sleep(500);}
//        catch (InterruptedException e){System.out.println("INTERRUPTED A");}

        b.methodB(this);
    }
}
class B implements Runnable{
    Thread t;
    A a;

    B(){
        this.t = new Thread(this,"Thread B");
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public void run(){
        a.methodA(this);
    }

    synchronized void methodB(A a){
        System.out.println("methodB");
//        try{Thread.sleep(500);}
//        catch (InterruptedException e){System.out.println("INTERRUPTED B");}

        a.methodA(this);
    }
}
public class DeadlockExample{
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        a.setB(b);
        b.setA(a);

        a.t.start();
        b.t.start();
    }
}
