package JavaDownloadManager;

class DownloadTask implements Runnable{

    private final String link;
    private final String ID;

    private volatile long sizeInBytes;

    private volatile boolean suspendFlag;
    private volatile boolean terminateFlag;
    private DownloadStatus STATUS =  DownloadStatus.QUEUED;

    DownloadTask(String link, String ID) {
        this.link = link;
        this.ID = ID;
    }

    boolean isSuspendFlag() {
        return suspendFlag;
    }

    void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    boolean isTerminateFlag() {
        return terminateFlag;
    }

    void setTerminateFlag(boolean terminateFlag) {
        this.terminateFlag = terminateFlag;
    }

    long getSizeInBytes() {
        return sizeInBytes;
    }

    void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    synchronized void pause(){
        this.suspendFlag = true;
        this.STATUS = DownloadStatus.PAUSED;
    }

    synchronized void resume(){
        this.suspendFlag = false;
        this.STATUS = DownloadStatus.DOWNLOADING;
        notify();
    }

    void cancel(){
        this.suspendFlag = true;
        this.STATUS = DownloadStatus.CANCELLED;
        notify();
    }

    @Override
    public void run(){

    }
}
