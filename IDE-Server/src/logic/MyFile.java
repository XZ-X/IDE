package logic;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * change history type to File instead of MyFile
 * --invalidAutoSave
 */
public class MyFile implements Serializable {
    private ArrayList<File> history=new ArrayList<>();
    private String name;
    private String type;
    private final User owner;
    private Thread saveThread;
    private int versionCnt=0;

    public MyFile(String type,String name,User usr){
        this.type=type;
        this.name=name;
        this.owner=usr;
        history.add(new File("/logic/"+name+"_"+owner+"_"+versionCnt));
        versionCnt++;

    }
    public MyFile(String type,String name,User usr,File file){
        this.type=type;
        this.name=name;
        this.owner=usr;
        history.add(file);
        versionCnt++;
    }
    public ArrayList<MyFile> getHistory(){
        //TODO:new arraylist convert history to myFile
        return null;
    }
    public String getName(){return name;}
    public void rename(String name){
        this.name=name;
    }

    public String getType() {
        return type;
    }

    public void save(){

    }
    private void validAutoSave(){
        if(owner.getPreferrence().isAutoSave){
            //TODO:
        }
    }

}
