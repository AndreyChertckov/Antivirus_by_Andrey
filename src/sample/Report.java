package sample;

import java.io.*;
import java.util.Date;

/**
 * Created by whiteelf on 21.11.15.
 */
public class Report {
    static Date date = new Date();
    static long milli = date.getTime();
    File messageFile = new File(Main.PATH+"MassageFile.txt");

    Report(){
        try {
            if(!messageFile.exists()){
                messageFile.createNewFile();
            }
        } catch (IOException e) {
        e.printStackTrace();

        }
    }
    public void reportMessageAutoStart(String nameFile,String yesNo){

        try {
            FileWorker.update(messageFile,"\n"+nameFile+" " + yesNo+" " +String.valueOf(milli) + " milliseconds from January 1, 1970 ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

}
