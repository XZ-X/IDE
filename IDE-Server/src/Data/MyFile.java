package Data;

import logic.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * change history type to File instead of MyFile
 * --invalidAutoSave
 */
public class MyFile implements Serializable {
    private ArrayList<File> history=new ArrayList<>();
    private String name;
    private Language type;
    private final User owner;
    private Thread saveThread;
    private int versionCnt=0;

    public MyFile(Language type,String name,User usr) throws IOException {
        this.type=type;
        this.name=name;
        this.owner=usr;
        switch (type) {
            case BF:
                history.add(new File(GlobalConstant.USER_FILES + name + "_" + owner.name + "_" + versionCnt + ".bf"));
                System.out.println(history.get(history.size()-1).createNewFile());
                break;
            case OOK:
                history.add(new File(GlobalConstant.USER_FILES + name + "_" + owner.name + "_" + versionCnt + ".ook"));
                history.get(history.size()-1).createNewFile();
                break;
        }
        versionCnt++;

    }
    public MyFile(Language type,String name,User usr,File file){
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

    public File open(){
        return history.get(history.size()-1);
    }

    public void rename(String name){
        this.name=name;
    }

    public Language getType() {
        return type;
    }

    public void save(String contents) throws IOException {
        switch (type) {
            case OOK:
                history.add(new File(GlobalConstant.USER_FILES + name + "_" + owner.name + "_" + versionCnt + ".ook"));
                break;
            case BF:
                history.add(new File(GlobalConstant.USER_FILES + name + "_" + owner.name + "_" + versionCnt + ".bf"));
                break;
        }
        File toSave=history.get(history.size()-1);
        toSave.createNewFile();
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(toSave));
            versionCnt++;
            writer.write(contents);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validAutoSave(){
        if(owner.getPreference().isAutoSave){
            //TODO:
        }
    }

}
