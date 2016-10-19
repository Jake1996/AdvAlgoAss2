import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import DataStructure.Trie;

public class Storywise {

	public static void Index(String filename) {
		Trie obj= new Trie();
		try {
			FileReader file = new FileReader(new File(filename));
			BufferedReader fin = new BufferedReader(file);
			String line;
			String arr[];
			int i;
			String title="";
			while((line = fin.readLine())!=null) {
				arr = line.split(" ");
				for(i=0;i<arr.length;i++) {
					if(arr[i].equals("$*")) {
						i++;
						title=arr[i++];
						while(i<arr.length&&!arr[i].equals("*")) {
							title = title+" "+arr[i++];
						}
					}
					arr[i] = arr[i].toUpperCase();
					arr[i] = arr[i].replaceAll("[^0-9A-Z]", "");
					if(!arr[i].equals(""))
						obj.add(arr[i],title);
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
