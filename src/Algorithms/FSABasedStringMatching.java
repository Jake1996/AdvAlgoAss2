package Algorithms;

import java.util.*;
import java.io.*;
class FSABasedStringMatching
{
	String pattern;
	int[][] stateTable;
	String text;
	public FSABasedStringMatching(String pattern,int[][] stateTable,String text)
	{
		
		this.pattern = pattern;
		this.text = text;
		this.stateTable = stateTable;
		createStateTable();
	}
	public FSABasedStringMatching(String pattern,String text)
	{
		this.pattern =pattern;
		this.text = text;
		stateTable = new int[pattern.length()+1][256];
		createStateTable();
	}
	public void createStateTable()  //creates state table of finite automata
	{
		int lps = 0;
		
		stateTable[0][(int)pattern.charAt(0)]=1;
		for(int i=1;i<=pattern.length();i++)
		{
			for(int j=0;j<256;j++)
			{
				stateTable[i][j]=stateTable[lps][j];
			}
			if(i<pattern.length())
			{
				stateTable[i][(int)pattern.charAt(i)]=i+1;
				lps = stateTable[lps][pattern.charAt(i)];
			}
		}

	}
	public ArrayList<Integer> findPatternInText()   //returns arraylist of indices where the patter is found.
	{
		ArrayList<Integer> patternFoundAtPosition = new ArrayList<Integer>();
		
		int state = 0;
		for(int j=0;j<text.length();j++)
		{
			state = stateTable[state][text.charAt(j)];
			//System.out.println(state);
			if(state==stateTable.length-1)
			{
				//System.out.println("adding to arraylist");
				patternFoundAtPosition.add(j-pattern.length()+1);
				state = 0;
			}
		}
		return patternFoundAtPosition;
	}
	public static String readFile(String fileName) throws IOException   
	{
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
   	 	try 
    	{
        	StringBuilder sb = new StringBuilder();
        	String line = br.readLine();
			while (line != null) 
        	{
            	sb.append(line);
            	sb.append("\n");
            	line = br.readLine();
        	}
        	return sb.toString();
    	} 
    	finally 
    	{
        	br.close();
    	}
	}
	public static void writeFile(String fileName,String text)
	{
		PrintWriter out;
		try
		{
		out = new PrintWriter(fileName);
		out.println(text);
		out.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}
	public static void writeFile(String fileName,ArrayList<String> text)
	{
		PrintWriter out;
		try
		{
		out = new PrintWriter(fileName);
			for(int i=0;i<text.size();i++)
			{ 
				out.println(text.get(i));
				//out.close();
			}
		out.close();	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}
	public static void processText(ArrayList<Integer> patternFoundAt,String text)  /*Runner program*/
	{
		ArrayList<String> removedStrings = new ArrayList<String>();
		StringBuilder processedText = new StringBuilder();
		processedText.append(text);
		int sum = 0;
		
		for(int i=0;i<patternFoundAt.size();i++)
		{
			int textIndex = patternFoundAt.get(i);
			int j=0;
			while(text.charAt(textIndex+j)!=' ' && text.charAt(textIndex+j)!='\n') 
			{
				j++;
			}
			j++;
			removedStrings.add(processedText.substring(textIndex-sum,textIndex+j-sum-1));
			processedText.delete(textIndex-sum,textIndex+j-sum-1);
			sum+=j-1;

		}
		writeFile("./../../assets/processedTales.txt",processedText.toString());
		writeFile("./../../assets/removedURL.txt",removedStrings);
	}
	public static void main(String[] args)   /*Runner program*/
	{
		String text="";
		try 
		{
			text = readFile("./../../assets/tales.txt");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		int[][] stateTable = new int[8][256];

		FSABasedStringMatching X = new FSABasedStringMatching("http",text);
		
		ArrayList<Integer> patternFoundAt = X.findPatternInText();
		processText(patternFoundAt,text);
		
		
		
		
		
	}

}
