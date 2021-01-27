package down.sallai.ml.services;

import down.sallai.ml.bean.MyFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileMangerImpl implements FileManger {
    @Override
    public Boolean deleteAllFile(String fileLocal) {
        File file = new File(fileLocal);
        if(!file.isDirectory()) return false;
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isDirectory()) {
                try {
                    deleteAllFile(f.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{

                f.delete();
            }

        }
        return true;
    }


    @Override
    public ArrayList<MyFile> getAllFile(String fileLocal) {
        File file = new File(fileLocal);
        if(!file.isDirectory()) return null;
        File[] files = file.listFiles();
        ArrayList<MyFile> f1 = new ArrayList<MyFile>();
        for(File f : files){

            f1.add(new MyFile(f.getName(),null,(double)f.length()/1024/1024/1024));

        }
        System.out.println(f1.size());
        return f1;
    }

    @Override
    public Boolean delByName(String name,String fileLocal) {
        File file = new File(fileLocal + name);
        boolean delete = file.delete();
        return delete;
    }


}
