package down.sallai.ml.bean;

import java.util.Map;

public class SystemInfo {
    private Map<String,Object> Space;  //可用空间
    private Map<String,Object> memery;
    private int fileNumber;  //总文件数
    private int cpu;

    public Map<String, Object> getSpace() {
        return Space;
    }

    public void setSpace(Map<String, Object> space) {
        Space = space;
    }

    public Map<String, Object> getMemery() {
        return memery;
    }

    public void setMemery(Map<String, Object> memery) {
        this.memery = memery;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

}
