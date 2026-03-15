class NewThread implements Runnable{
    Thread t;
    String name;
    boolean suspendFlag;

    NewThread(String name){
        this.name = name;
        this.t = new Thread(this, name);
    }

    @Override
    public void run(){
        for(int i=15; i>0; i--){
            System.out.println(name+": "+i);
            try{Thread.sleep(500);}
            catch(InterruptedException e){System.out.println("InterruptedException on Sleep of: "+ name);}

            synchronized (this) {
                while (suspendFlag) {
                    try {wait();}
                    catch (InterruptedException e) {System.out.println("InterruptedException on wait() of: " + name);}
                }
            }
        }
    }

    synchronized void mySuspend(){
        this.suspendFlag = true;
    }

    synchronized void myResume(){
        this.suspendFlag = false;
        notify();
    }
}
public class ITC2 {
    public static void main(String[] args) {
        NewThread n1 = new NewThread("THREAD A");
        NewThread n2 = new NewThread("THREAD B");

        n1.t.start();
        n2.t.start();
    }
}