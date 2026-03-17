package JavaDownloadManager;

import java.util.*;

public class DownloadManager {
    private List<DownloadTask> toDowload;
    private int idx = 0;

    DownloadManager(List<String> links){
        for(String url:links) toDowload.add(new DownloadTask(url, idx++));
    }

    void pause(String ID){}
    void pauseAll(){}

    void cancel(String ID){}
    void cancelAll(){}

    void resume(String ID){}
    void resumeAll(){}

    List<DownloadTask> displayProgress(){return toDowload;}
}
