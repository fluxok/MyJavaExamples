package JavaDownloadManager;

import java.util.concurrent.atomic.AtomicInteger;

class DownloadTask implements Runnable{

    private final String link;
    private final String ID;

    private AtomicInteger receivedChunks = new AtomicInteger(0);
    private final int totalChunks = 100;
    private long sizeInBytes;

    private volatile boolean suspendFlag;
    private volatile boolean terminateFlag;
    private volatile DownloadStatus STATUS =  DownloadStatus.QUEUED;

    DownloadTask(String link, String ID) {
        this.link = link;
        this.ID = ID;
    }

    private boolean isSuspendFlag() {
        return suspendFlag;
    }

    private void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    private boolean isTerminateFlag() {
        return terminateFlag;
    }

    private void setTerminateFlag(boolean terminateFlag) {
        this.terminateFlag = terminateFlag;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    private void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public String getID() {
        return ID;
    }

    synchronized public DownloadStatus getSTATUS() {
        return STATUS;
    }

    synchronized void pause(){
        setSuspendFlag(true);
        this.STATUS = DownloadStatus.PAUSED;
    }

    synchronized void resume(){
        setSuspendFlag(false);
        this.STATUS = DownloadStatus.DOWNLOADING;
        notify();
    }

    synchronized void cancel(){
        setTerminateFlag(true);
        this.STATUS = DownloadStatus.CANCELLED;
        notify();
    }

    @Override
    public void run() {
        STATUS =  DownloadStatus.DOWNLOADING;

        for(int i=0; i<totalChunks; i++){
            if(isTerminateFlag()) break;

            try {
                synchronized (this) {
                    while (isSuspendFlag()) wait();
                }
                Thread.sleep(100);
                receivedChunks.getAndIncrement();
            }catch (InterruptedException e){
                setTerminateFlag(true);
                STATUS =  DownloadStatus.CANCELLED;
                Thread.currentThread().interrupt();
                break;
            }
        }
        if(!isTerminateFlag()) STATUS =  DownloadStatus.COMPLETED;
    }
}