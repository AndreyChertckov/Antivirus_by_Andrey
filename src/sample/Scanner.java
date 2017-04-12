package sample;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by whiteelf on 12.11.15.
 */
public class Scanner implements Runnable{

    File dir;
    List<File> allFiles = new ArrayList<>();
    List<File> newFiles = new ArrayList<>();
    int indOfFile =-1;
    Report report;
    File startupFile;
    String path = "C:\\Users\\"+Main.USER+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\";

    Scanner(Report r){
        startupFile = new File(Main.PATH+"startup.txt");
        if (!startupFile.exists()) try {
            startupFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dir = new File(path);
        report = r;
        for(File pat:dir.listFiles()){
            allFiles.add(pat);
        }
    }

    @Override
    public void run() {
        allFiles.clear();
        List<String> startupList = FileWorker.read(startupFile);
        for(int i =0;i < startupList.size();i++){
            allFiles.add( new File(path+startupList.get(i)));
        }
            while (true) {
                try {

                    newFiles.clear();

                    for (File pat : dir.listFiles()) {

                        newFiles.add(pat);
                    }


                    if (allFiles.size() < newFiles.size()) {


                        for (int i = 0; i <= newFiles.size(); i++) {
                            boolean b = false;
                            for (int j = 0; j < allFiles.size(); j++) {
                                if (Objects.equals(newFiles.get(i).getName(), allFiles.get(j).getName())) {
                                    b = true;
                                    break;
                                }
                            }
                            if (!b) {

                                int re = JOptionPane.showConfirmDialog(null,"Хотите установить это: "+newFiles.get(i).getName() + " в автозагрузку","Потвердите", JOptionPane.YES_NO_OPTION);
                                if (re == JOptionPane.YES_OPTION) {
                                    allFiles.add(newFiles.get(i));
                                    report.reportMessageAutoStart(newFiles.get(i).getName(), "YES");
                                    break;
                                } else {
                                    report.reportMessageAutoStart(newFiles.get(i).getName(), "NO");
                                    indOfFile = i;
                                    break;
                                }


                            }
                        }

                    }
                    if (indOfFile != -1) {
                        newFiles.get(indOfFile).delete();
                        newFiles.remove(indOfFile);
                    }
                    indOfFile = -1;
                    allFiles.clear();
                    startupList.clear();
                    for (File pat : dir.listFiles()) {

                        allFiles.add(pat);
                        startupList.add(pat.getName());
                    }

                    FileWorker.write(startupFile,startupList);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}
