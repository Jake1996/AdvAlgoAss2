import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import DataStructure.Trie;

public class Index {

	public static void main(String[] args) {
		Trie obj = new Trie();
		try {
			FileReader file = new FileReader(new File("assets/AESOP TALES (with random URLs).txt"));
			BufferedReader fin = new BufferedReader(file);
			String line;
			String arr[];
			int i;
			while((line = fin.readLine())!=null) {
				arr = line.split(" ");
				for(i=0;i<arr.length;i++) {
					if(arr[i].equals("")) {
						continue;
					}
					arr[i] = arr[i].replaceAll("[^0-9a-zA-Z]", "");
					obj.add(arr[i]);
					//System.out.println(arr[i]);
				}
			}
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		obj.printIndex();
	}

}
