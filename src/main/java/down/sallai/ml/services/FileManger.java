package down.sallai.ml.services;

import java.util.List;

public interface FileManger {
    public Boolean deleteAllFile(String fileLocal);
    public List getAllFile(String fileLocal);
    public Boolean delByName(String name,String fileLocal);
}
