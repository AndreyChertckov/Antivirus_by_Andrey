package sample;

import javafx.util.converter.BigIntegerStringConverter;
import jdk.internal.util.xml.impl.ReaderUTF8;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whiteelf on 27.11.15.
 */
public class FileWorker {

    public static List<String> read(File file) {
        //Этот спец. объект для построения строки
        List<String> sb = new ArrayList<>();

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader(file));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.add(s);
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb;
    }
    public static void write(File file,String text) {

        try {

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void update(File file,String newText) throws FileNotFoundException {

        StringBuilder sb = new StringBuilder();
        String oldFile = readString(file);
        sb.append(oldFile);
        sb.append(newText);
        write(file,sb.toString());
    }

    public static String readString(File file){

            //Этот спец. объект для построения строки
            StringBuilder sb = new StringBuilder();

            try {
                //Объект для чтения файла в буфер
                BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
                try {
                    //В цикле построчно считываем файл
                    String s;
                    while ((s = in.readLine()) != null) {
                        sb.append(s);
                    }
                } finally {
                    //Также не забываем закрыть файл
                    in.close();
                }
            } catch(IOException e) {
                throw new RuntimeException(e);
            }

            //Возвращаем полученный текст с файла
            return sb.toString();

    }

    public static void write(File file,List<String> text) {

        try {

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.write(text.get(0));
                //Записываем текст у файл
                for(int i = 1;i<text.size();i++){
                    out.write(" " +text.get(i));
                }
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
