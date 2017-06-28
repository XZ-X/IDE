package serverUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by xuxiangzhe on 2017/6/27.
 */
public class FileTools {
    public static String convertF2S(File file){
        StringBuffer buffer=new StringBuffer();
        try {
            Scanner scanner=new Scanner(new FileInputStream(file));
            while (scanner.hasNext()){
                buffer.append(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new String(buffer);
    }

    public static boolean isDifferent(File a,File b){
        String aContent=convertF2S(a);
        String bContent=convertF2S(b);
        return aContent.equals(bContent);
    }

    public static boolean isDifferent(String a,File b){
        String bContent=convertF2S(b);
        return a.equals(bContent);
    }
}
