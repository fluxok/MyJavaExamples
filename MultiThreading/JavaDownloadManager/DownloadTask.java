package JavaDownloadManager;

public class DownloadTask implements Runnable{
    private boolean suspendFlag;
    private boolean terminateFlag;
    private String link;
    private long SizeInBits;

    DownloadTask(String link) {
        this.link = link;
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

    long getSizeInBits() {
        return SizeInBits;
    }

    void setSizeInBits(long sizeInBits) {
        SizeInBits = sizeInBits;
    }

    void pause(){}

    void resume(){}

    void cancel(){}

    @Override
    public void run(){

    }

}
