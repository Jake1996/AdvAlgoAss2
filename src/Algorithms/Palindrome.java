package Algorithms;

import java.io.*;


public class Palindrome
{
	 public static String readFile(String fileName) throws IOException 
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) 
            {
                sb.append(line.trim());
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
	public static void pdrome(String args,int size)
	{
		String text ="";
		try
		{
			text = readFile(args);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		String[] words = text.split(" ");
		int longest = size;
		for(int i=0;i<words.length;i++)
		{
			if(ifPalindrome(words[i])  && words[i].length()>size)
			{
				System.out.println(words[i]);
			}
		}
	}
	public static boolean ifPalindrome(String word)
	{
		for(int i=0,k=word.length()-1;i<k;i++,k--)
		{
			if(word.charAt(i)!=word.charAt(k))
				return false;
		}
		return true;
	}
	
}