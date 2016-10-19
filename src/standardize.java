import java.util.*;
import java.io.*;

public class standardize
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
	public static void main(String[] args)
	{
		String s = "";
		try 
        {
            s = readFile("./../assets/processedTales.txt");
           StringBuilder sb = new StringBuilder();
            sb.append(s);
            int i=0;
            while(i<sb.length())
            {
                if(i<=sb.length()-3 && sb.charAt(i)=='\n' && sb.charAt(i+1)=='\n' && sb.charAt(i+2)=='\n')
                {
                    sb.delete(i,i+3);
                    sb.insert(i,"$\n ");
                    i+=3;
                }
                else if(i<=sb.length()-2 && sb.charAt(i)=='\n' && sb.charAt(i+1)=='\n')
                {
                    sb.delete(i,i+1);
                    i++;
                }
                else if(sb.charAt(i)=='\n')
                {
                    sb.delete(i,i+1);
                    sb.insert(i," ");
                    i++;
                }
                else
                    {
                    	i++;
                    }
                
            }
            sb.insert(sb.length(),'$');

            i=0;
            int foundDollar = 0;
            while(i<sb.length())
            {
            	if(sb.charAt(i)=='\n')
            	{
            		sb.delete(i,i+1);
                    //sb.insert(i," ");
            		if(foundDollar>0)
            		{
            			sb.insert(i,'*');
            			foundDollar--;
            		}
            	}
            	else if(sb.charAt(i)=='$')
            	{
            		foundDollar = 2;
            	}
            	i++;
            }
            
           

            i=0;
            boolean starOpen = false;
            while(i<sb.length())
            {
            	if(sb.charAt(i)=='$')
            	{
            		sb.insert(i,' ');
            		i+=2;
            	}
            
            	else if(sb.charAt(i)=='*' && starOpen==false)
            	{
            		sb.insert(i+1,' ');
            		i+=2;
            		starOpen = true;
            	}
            	else if(sb.charAt(i)=='*' && starOpen==true)
            	{
            		sb.insert(i,' ');
            		sb.insert(i+2,' ');
            		i+=3;
            		starOpen = false;
            	}
            	else 
            	{
            		i+=1;
            	}
        	}

            i = 0;
            while(i<sb.length())
            {
                if(Character.isUpperCase(sb.charAt(i)))
                {
                    sb.setCharAt(i,Character.toLowerCase(sb.charAt(i)));
                }
                i++;
            }
            i=0;
            while(i<sb.length())
            {
                if(!sb.substring(i,i+1).matches("[a-zA-z0-9|\\s|$|*]"))// && !sb.substring(i,i+1).matches("[$|*|\\s]"));
                   { sb.delete(i,i+1); }
                else
                {
                    i++;
                }
            }
            i=0;
             while(i<sb.length()-1)
            {
                if(sb.charAt(i)==' ' && sb.charAt(i+1)==' ')
                {
                    sb.delete(i,i+1);
                }
                else{i++;}
            }
            //System.out.println(sb.toString());
            if(sb.toString().charAt(sb.length()-1)==' ')System.out.println("asdasdasdasd");
            writeFile("./../assets/standardized.txt",sb.toString());
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

	}
}