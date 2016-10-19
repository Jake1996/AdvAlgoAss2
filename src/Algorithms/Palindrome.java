import java.util.*;
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
	public static void main(String[] args)
	{
		String text ="";
		try
		{
			text = readFile("./../../assets/standardized.txt");
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		String[] words = text.split(" ");
		int longest = 0;
		for(int i =0;i<words.length;i++)
		{
			boolean isPalindrome = true;
			if(words[i].length()>longest)
			{
			for(int j=0,k=words[i].length()-1;j<k;j++,k--)
			{
				if(words[i].charAt(j)!=words[i].charAt(k))
					isPalindrome = false;

			}
			}
			if(isPalindrome && words[i].length()>longest)
				{
					longest = words[i].length();
					System.out.println(words[i]);
				}
		}
	}
}