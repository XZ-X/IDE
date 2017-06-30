package Data;

import serverUtilities.FileTools;

import java.io.*;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Created by xuxiangzhe on 2017/6/15.
 * This class is the "file" model in this project. Every file contains a Hash Map of files and Strings indicting its edit time.
 * This "file" class provides more support to version control and rename by encapsulating the "rename","getHistory" method by myself.
 * Notice:
 * - All the files will be stored in the same path specified in the "GlobalConstant" class(the USER_FILES entry).
 * - All the files will be stored in the format "something+ separator+ something +....", in which the "separator" is specified in the class GlobalConstant
 *  (the FILE_NAME_SEPARATOR entry)
 * -
 *
 * change history type to File instead of MyFile
 * --invalidAutoSave
 */
public class MyFile implements Serializable {
    //Attributes
    private LinkedHashMap<File,String> history;
    private String name;
    private Language type;
    private final User owner;
    //helper
    private int versionCnt=0;
    private Clock clock=Clock.systemDefaultZone();





    //constructor
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
                        + versionCnt + ".bf"),clock.instant().atOffset(ZoneOffset.ofHours(8)).toString());
                break;
            case OOK:
                history.put(new File(GlobalConstant.USER_FILES + name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + owner.name
                        + GlobalConstant.FILE_NAME_SEPARATOR
                        + versionCnt + ".ook"),clock.instant().atOffset(ZoneOffset.ofHours(8)).toString());
                break;
        }
        versionCnt++;
        while(!getLast().createNewFile());

    }



    //getters and setters
    public Map<File,String> getHistory(){
        return history;
    }

    public String getName(){
        return name;
    }

    public Language getType() {
        return type;
    }


    //function-supports
    public void rename(String name) {
        //first, lookup the file with certain old-name, then copy its contents, then make a new file with new-name, then delete the old file.
        this.name = name;
        ArrayList<File> files=new ArrayList<>(history.keySet());
        for (File file : files) {
            String oriName=file.getName();
            //new file's name: the new name + origin name with the first part and first "separator" deleted.
            String filename=GlobalConstant.USER_FILES + name + GlobalConstant.FILE_NAME_SEPARATOR ;
            int substringIndex=oriName.indexOf(GlobalConstant.FILE_NAME_SEPARATOR)+GlobalConstant.FILE_NAME_SEPARATOR.length();
            filename+=oriName.substring(substringIndex);
            File temp = new File(filename);
            String ori = FileTools.convertF2S(file);

            try {
                System.out.println(temp.createNewFile());
                System.out.println(temp.getName());
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
                System.out.println(ori);
                writer.write(ori);
                writer.flush();
                writer.close();
                while (!file.delete());
            } catch (IOException e) {
                e.printStackTrace();
            }
            history.put(temp, history.remove(file));
        }
    }

    public File open(){
        return getLast();
    }

    public void save(String contents) throws IOException {
        //only different files will be stored
        if(!FileTools.isDifferent(contents,open())) {
            switch (type) {
                case OOK:
                    history.put(new File(GlobalConstant.USER_FILES + name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + owner.name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + versionCnt + ".ook"),clock.instant().atOffset(ZoneOffset.ofHours(8)).toString());
                    break;
                case BF:
                    history.put(new File(GlobalConstant.USER_FILES + name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + owner.name
                            + GlobalConstant.FILE_NAME_SEPARATOR
                            + versionCnt + ".bf"),clock.instant().atOffset(ZoneOffset.ofHours(8)).toString());
                    break;
            }
            File toSave = getLast();
            if (toSave.createNewFile()) {
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
    }

    public boolean delete(){
        boolean ret=true;
        for(File file:history.keySet()){
            ret=ret&&file.delete();
        }
        return ret;
    }

    public void setVersionTo(String versionName){
        LinkedHashMap<File,String> newHistory=new LinkedHashMap<>();
        ArrayList<Map.Entry<File,String>> oldHistory=new ArrayList<>(history.entrySet());

        //why I sort the list first?
        //Even though there won't be any difference here, taking the CPU-architecture( jump-predict) into consideration, the following branch can be
        //optimized effectively provided a large amount of versions.
        oldHistory.sort(Comparator.comparing(Map.Entry::getKey));

        for(Map.Entry<File,String> entry:oldHistory){
            //version name here will actually be "versionName+.bf/.ook"
            String[] name=entry.getKey().getName().split("\\.")[0].split(GlobalConstant.FILE_NAME_SEPARATOR);
            if(Integer.parseInt(name[name.length-1])<=Integer.parseInt(versionName)+1){
                newHistory.put(entry.getKey(),entry.getValue());
            }else {
                entry.getKey().delete();
            }
        }
        history=newHistory;

    }


    //utilities
    private File getLast(){
        ArrayList<Map.Entry<File,String>> entries=new ArrayList<>(history.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        return entries.get(entries.size()-1).getKey();
    }

}
