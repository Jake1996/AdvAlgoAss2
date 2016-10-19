package Algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class RabinKarp {
	static final long mod = 1000000007;
	static final long prime = 97;
	public static int findPatternInText(char text[],char pattern[],int start ,int end) {
		int length = pattern.length;
		long hash = 0;
		long primepow = 1;
		long currentHash=0;
		for(int i=0;i<length;i++) {
			hash = (hash*prime+pattern[i])%mod;
		}
		for(int i=1;i<length;i++) {
			primepow = (prime*primepow)%mod;
		}
		int i;
		for(i=start;i<=end&&i<(start+length);i++) {
			currentHash = (currentHash*prime+text[i])%mod;

		}
		if(currentHash == hash) {
			int j;
			for(j=0;j<length;j++)
				if(pattern[j]!=text[i-length+j])
					break;
			if(j==length)
				return i-length;
		}
		for(i = start+length;i<=end;i++) {
			currentHash = (currentHash+mod - (primepow*text[i-length])%mod)%mod;
			currentHash = (currentHash*prime+text[i])%mod;
			if(currentHash==hash) {
				int j;
				for(j=0;j<length;j++)
					if(pattern[j]!=text[i-length+1+j])
						break;
				if(j==length)
					return i-length+1;
			}
		}
		return -1;
	}
	public static int findPatternInText(char text[],char pattern[]) {
		return findPatternInText(text, pattern, 0, text.length-1);
	}
	public static int findPatternInText(char text[],char pattern[],int start) {
		return findPatternInText(text, pattern, start, text.length-1);
	}
	public static int occurances(char text[],char pattern[],int start ,int end) {
		int occ = 0;
		int length = pattern.length;
		long hash = 0;
		long primepow = 1;
		long currentHash=0;
		for(int i=0;i<length;i++) {
			hash = (hash*prime+pattern[i])%mod;
		}
		for(int i=1;i<length;i++) {
			primepow = (prime*primepow)%mod;
		}
		int i;
		for(i=start;i<=end&&i<(start+length);i++) {
			currentHash = (currentHash*prime+text[i])%mod;

		}
		if(currentHash == hash) {
			int j;
			for(j=0;j<length;j++)
				if(pattern[j]!=text[i-length+j])
					break;
			if(j==length)
				occ++;
		}
		for(i = start+length;i<=end;i++) {
			currentHash = (currentHash+mod - (primepow*text[i-length])%mod)%mod;
			currentHash = (currentHash*prime+text[i])%mod;
			if(currentHash==hash) {
				int j;
				for(j=0;j<length;j++)
					if(pattern[j]!=text[i-length+1+j])
						break;
				if(j==length)
					occ++;
			}
		}
		return occ;
	}
	public static void Build_cross_Index(String filename) {
	String line="";
	try {
		FileReader file = new FileReader(new File(filename));
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
		storyEnd = findPatternInText(carr, " $* ".toCharArray());
		System.out.println(s);
		s =  " "+s+" ";
		while(storyEnd!=carr.length-1) {
			storyStart = storyEnd;
			titleEnd = findPatternInText(carr, " * ".toCharArray(),storyStart+4);
			title = line.substring(storyStart+4, titleEnd);
			storyEnd = findPatternInText(carr, " $* ".toCharArray(),titleEnd);
			if(storyEnd==-1) break;
			temp = occurances(carr, s.toCharArray(), storyStart, storyEnd);
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
