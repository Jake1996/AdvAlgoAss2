import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import Algorithms.KMP;


public class Build_cross_Index {
	public static void main(String args[]) {;
		String line="";
		try {
			FileReader file = new FileReader(new File("assets/standardized.txt"));
			BufferedReader fin = new BufferedReader(file);
			line = fin.readLine();
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		line = line.replaceAll("[^0-9A-Za-z*$ ]", "");
		String arr[] = line.split(" ");
		ArrayList<String> index = new ArrayList<>();
		HashSet<String> keys = new HashSet<>();
		for(int i=0;i<arr.length;i++) {
			if(!keys.contains(arr[i])) {
				keys.add(arr[i]);
				index.add(arr[i]);
			}
		}
		keys = null;
		Collections.sort(index);
		String title="";
		int storyStart=0,storyEnd=0;
		char carr[] = line.toCharArray();
		int titleEnd;
		int temp;
		for(String s :index) {
			int total = 0;
			storyEnd = KMP.findPatternInText(carr, " $* ".toCharArray());
			System.out.println(s);
			s =  " "+s+" ";
			while(storyEnd!=carr.length-1) {
				storyStart = storyEnd;
				titleEnd = KMP.findPatternInText(carr, " * ".toCharArray(),storyStart+4);
				title = line.substring(storyStart+4, titleEnd);
				storyEnd = KMP.findPatternInText(carr, " $* ".toCharArray(),titleEnd);
				if(storyEnd==-1) break;
				temp = KMP.occurances(carr, s.toCharArray(), storyStart, storyEnd);
				if(temp>0) {
					System.out.println("\t"+title+" : "+temp);
					total+=temp;
				}
			}
			System.out.println("Total : "+total);
			System.out.println();
		}
	}
}
