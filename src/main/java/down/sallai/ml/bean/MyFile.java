package down.sallai.ml.bean;

import java.util.Date;

public class MyFile {
    private String name;
    private Date time;
    private Double size;

    public MyFile(String name, Date time, Double size) {
        this.name = name;
        this.time = time;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
