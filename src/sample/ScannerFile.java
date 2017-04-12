package sample;


import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

/**
 * Created by whiteelf on 27.11.15.
 */
public class ScannerFile {

	private static List<String> vocabulary = new ArrayList<>();
	private static File vocabularyFile = new File(Main.PATH+"vocabulary.txt");
	static File messageFile;
	public ScannerFile(){

		messageFile = new File(Main.PATH+"FileAndMD5.txt");
		if(!messageFile.exists()) try {
			messageFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		vocabulary = FileWorker.read(vocabularyFile);

	}


	public static void scanFile(File file, javafx.scene.control.Label Progress){
		if(FileWorker.read(messageFile).indexOf(getCheckSum(file)) == -1) {
			Progress.setText("Сканирование Начато");
			try {
				String stringText = FileWorker.readString(file);
				String[] st = stringText.split(" ");
				List<String> fileText = new LinkedList<>(Arrays.asList(st));
				List<String> cherText = new ArrayList<>();
				for (int i = 0; i < fileText.size(); i++) {
					cherText.add(fileText.get(i).toLowerCase());
				}
				List<String> infection = new ArrayList<>();
				for (int i = 0; i < cherText.size(); i++) {

					if (vocabulary.indexOf(cherText.get(i)) == -1) {
						infection.add(fileText.get(i));
					}
				}
				FileWorker.write(file, accept(infection, fileText));

				try {
					FileWorker.update(messageFile, getCheckSum(file) + "\n");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Progress.setText("Сканирование Закончено");
			}catch (Exception e){
				Progress.setText("Произошла ошибка во время выполнение программы");
			}
		} else{
				Progress.setText("Сканирование Закончено");
			}
		FileWorker.write(vocabularyFile,vocabulary);
	}





	public static List<String> accept(List<String> infection,List<String> text){
		if(infection.isEmpty()){
			return  text;
		}else {
			for (int i = 0; i < infection.size(); i++) {
				int re = JOptionPane.showConfirmDialog(null, "Вы хотите оставить это слово:  " + infection.get(i)+". Оно не было найденно в словаре русского языка", "Потвердите", JOptionPane.YES_NO_OPTION);
				System.out.println(infection.get(i));
				if (re != JOptionPane.YES_OPTION) {

						if (text.indexOf(infection.get(i)) != -1) {
							text.remove(infection.get(i));
						}
				}else{
					vocabulary.add(infection.get(i));
				}
			}
		}
		return  text;
	}
	public static String getCheckSum(File patient) {
		try {

			MessageDigest digest = MessageDigest.getInstance("MD5");

			int length = (int) patient.length();
			byte[] content = new byte[length];

			new FileInputStream(patient).read(content);

			byte[] md5 = digest.digest(content);

			StringBuilder md5String = new StringBuilder();
			for (byte b : md5) {
				md5String.append(b);
				}

			return md5String.toString();

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "";
		}
}
