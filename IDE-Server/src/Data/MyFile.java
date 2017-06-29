package Data;

import serverUtilities.FileTools;

import java.io.*;
import java.time.Clock;
import java.util.*;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * change history type to File instead of MyFile
 * --invalidAutoSave
 */
public class MyFile implements Serializable {
    private LinkedHashMap<File,String> history;
    private String name;
    private Language type;
    private final User owner;
    private Thread saveThread;
    private int versionCnt=0;
    private Clock clock=Clock.systemDefaultZone();

    public MyFile(Language type,String name,User usr) throws IOException {
        history=new LinkedHashMap<>();
        this.type=type;
        this.name=name;
        this.owner=usr;
        switch (type) {
            case BF:
                history.put(new File(GlobalConstant.USER_FILES + name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + owner.name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + versionCnt + ".bf"),clock.instant().toString());
                break;
            case OOK:
                history.put(new File(GlobalConstant.USER_FILES + name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + owner.name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + versionCnt + ".ook"),clock.instant().toString());
                break;
        }
        versionCnt++;
        getLast().createNewFile();

    }
    public Map<File,String> getHistory(){
        return history;
    }

    public String getName(){return name;}

    public File open(){
        return getLast();
    }

    public void rename(String name){
        this.name=name;
    }

    public Language getType() {
        return type;
    }

    public void save(String contents) throws IOException {
        if(!FileTools.isDifferent(contents,open())) {
            switch (type) {
                case OOK:
                    history.put(new File(GlobalConstant.USER_FILES + name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + owner.name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + versionCnt + ".ook"),clock.instant().toString());
                    break;
                case BF:
                    history.put(new File(GlobalConstant.USER_FILES + name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + owner.name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + versionCnt + ".bf"),clock.instant().toString());
                    break;
            }
            File toSave = getLast();
            toSave.createNewFile();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));
                versionCnt++;
                writer.write(contents);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void delete(){
        for(File file:history.keySet()){
            file.delete();
        }
    }

    public void setVersionTo(String versionName){
        LinkedHashMap<File,String> newHistory=new LinkedHashMap<>();
        ArrayList<Map.Entry<File,String>> oldHistory=new ArrayList<>(history.entrySet());
        oldHistory.sort(Comparator.comparing(Map.Entry::getKey));
        for(Map.Entry<File,String> entry:oldHistory){

            String[] name=entry.getKey().getName().split("\\.")[0].split(GlobalConstant.FILE_NAME_SEPARATOR);
            if(Integer.parseInt(name[name.length-1])<=Integer.parseInt(versionName)+1){
                newHistory.put(entry.getKey(),entry.getValue());
            }else {
                entry.getKey().delete();
            }
        }
        history=newHistory;

    }
    private void validAutoSave(){
        if(owner.getPreference().isAutoSave){
            //TODO:
        }
    }

    private File getLast(){
        ArrayList<Map.Entry<File,String>> entries=new ArrayList<>(history.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        return entries.get(entries.size()-1).getKey();
    }

}
