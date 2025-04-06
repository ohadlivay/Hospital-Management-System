package autopilot;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created By Tony on 09/07/2018
 */
public final class AutoUtils implements Serializable{

    public static void makeFile(String fileName,String data) {

        //get data bytes
        byte[] bytes = data.getBytes();

        //make file output stream
        try(FileOutputStream out
                    = new FileOutputStream(fileName)){
            //write to disk 
            out.write(bytes); 
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
