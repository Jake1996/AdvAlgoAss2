package Algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class KMP {
	
	public static int findPatternInText(char text[],char pattern[],int start,int end) {
		int prefixArray[] = generatePrefixArray(pattern);
		int j=0,i=start;
		while(i<=end) {
			if(i<=end&&j<pattern.length&&text[i]==pattern[j]) {
				i++;
				j++;
			}
			if(j==pattern.length) 
				return i-pattern.length;
			else if(i<=end&&j<pattern.length&&text[i]!=pattern[j]){
				if(j==0)
					i++;
				else
					j = prefixArray[j-1];
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
	public static int[] generatePrefixArray(char pattern[]) {
		int i,j;
		int arr[] = new int[pattern.length];
		arr[0] = 0;
		i=1;j=0;
		for(i=1;i<arr.length;i++) {
			while(j>0&&pattern[i]!=pattern[j]) j=arr[j-1];
			if(pattern[j]==pattern[i]) j++;
			arr[i] = j;
		}
		return arr;
	}
	public static int occurances(char text[],char pattern[],int start,int end) {
		int prefixArray[] = generatePrefixArray(pattern);
		int occ = 0;
		int i= start;
		int t;
		if(end>=text.length) end = text.length-1;
		while(i<=text.length-pattern.length) {
			t = occurances1(text, pattern, prefixArray, i, end);
			if(t!=-1) {
				occ++;
				i=t+1;
			}
			else {
				break;
			}
		}
		return occ;
	}
	public static int occurances1(char text[],char pattern[],int prefixArray[],int start,int end) {
		int j=0,i=start;
		while(i<=end) {
			if(i<=end&&j<pattern.length&&text[i]==pattern[j]) {
				i++;
				j++;
			}
			if(j==pattern.length)
				return i-pattern.length;
			else if(i<=end&&j<pattern.length&&text[i]!=pattern[j]){
				if(j==0)
					i++;
				else
					j = prefixArray[j-1];
			}
		}
		return -1;
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
