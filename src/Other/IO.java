package Other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IO {
	
	public static String[] InputAll(String filePath) {
		String Data = "";
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			while (br.ready()) {
				Data += br.readLine();
				if (br.ready()) Data += "@";
			}
			
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Data.split("@");
	}
	
	public static String InputById(String filePath, int id) {
		return Input(filePath, id + "", ",");
	}
	
	public static String InputValue(String filePath, String name, String token) {
		// use to Input config.txt (key: value)
		String strInput = Input(filePath, name, token);
		return strInput.substring(strInput.indexOf(token) + token.length());
	}
	
	public static String Input(String filePath, String name, String token) {
		try {
			String Data;
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			while (br.ready()) {
				Data = br.readLine();
				if (Data.split(token)[0].equals(name)) return Data;
			}
			
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void OutputAll(String filePath, String Data) {
		try {
			FileWriter fw = new FileWriter(filePath);
			fw.write(Data);
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void OutputAtLast(String filePath, String[] Data) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			
			fw.write("\n");
			for (int index = 0; index < (Data.length - 1); index++) {
				fw.write(Data[index] + "\n");
			}
			fw.write(Data[Data.length - 1]);
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void OutputById(String filePath, String newData, int id) {
		Output(filePath, newData, id + "", ",");
	}
	
	public static void Output(String filePath, String newData, String name, String token) {
		try {
			String Data = "", thisLine;
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			
			while (br.ready()) {
				thisLine = br.readLine();
				if (thisLine.split(token)[0].equals(name)) {
					if (newData != null) Data += newData;
					else if (Data.length() != 0) Data = Data.substring(0, Data.length() - 1);
				}
				else Data += thisLine;
				
				if (br.ready() && (Data.length() != 0)) Data += "\n";
			}
			fr.close();
			
			FileWriter fw = new FileWriter(filePath);
			fw.write(Data);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTxt(String filePath, int fileName) {
		try {
		    File file = new File(filePath, fileName + ".txt");
		    file.createNewFile();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static String[] traverseDirectory(String filePath) {
        try	{
        	return new File(filePath).list();
        } catch (Exception e){
        	e.printStackTrace();
        }
		return null;
	}
	
}
