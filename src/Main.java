import java.io.IOException;
import java.util.Scanner;

import Algorithms.KMP;
import Algorithms.Palindrome;
import Algorithms.RabinKarp;
import Algorithms.suffixArray;

public class Main {

	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		int choice;
		String txtfile="assets/standardized.txt";
		String pattern;
		int start=0,end=0;
		System.out.println("Enter the file name : ");
		txtfile = "assets/"+in.next();
		String text="";
		try {
			text = Palindrome.readFile(txtfile);
			text = text.trim();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		do {
			System.out.println("1..Find_Length_ofText(txtfile)\n2..Find_Pattern ( pattern , InTextRange,  algo)\n3..Build_Cross_Index(txtfile, algo)");
			System.out.print("4..Find_Maximal,Palindromes(PalindromeSize,  InTextRange )\n5..Print_Stats ( ) \n6..Exit\nEnter your choice: ");
			choice = in.nextInt();
			switch(choice) {
				case 1 :
					System.out.println("No of characters : "+text.length());
					break;
				case 2 :
					System.out.println("Enter the pattern : ");
					in.nextLine();
					pattern = in.nextLine();
					System.out.print("Algos : \n1..Rabin-karp\n2..KMP\n3..Suffix Tree\nEnter Algo choice : ");
					int algo = in.nextInt();
					System.out.print("InTextRange Choice : \n1..Indices\n2..pattern\nEnter Choice : ");
					int ic = in.nextInt();
					suffixArray suffix = new suffixArray(text);
					if(ic == 1) {
						System.out.print("Enter start and end index: ");
						start = in.nextInt();
						end = in.nextInt();
					}
					else {
						System.out.println("Enter Start pattern : ");
						in.nextLine();
						if(algo==1) {
							start = RabinKarp.findPatternInText(text.toCharArray(), in.nextLine().toCharArray());
							
						}
						else if(algo==2) {
							start = KMP.findPatternInText(text.toCharArray(), in.nextLine().toCharArray());
						}
						else {
							start = suffix.patternSearchIndex(in.nextLine());
						}
						if(start==-1) break;
						System.out.println("Enter End pattern : ");
						if(algo==1) {
							end = RabinKarp.findPatternInText(text.toCharArray(), in.nextLine().toCharArray());
						}
						else if(algo==2) {
							end = KMP.findPatternInText(text.toCharArray(), in.nextLine().toCharArray());
						}
						else {
							end = suffix.patternSearchIndex(in.nextLine());
						}
						if(end==-1) break;
					}
					System.out.print("Occurances of pattern : ");
					if(algo==1) {
						System.out.println(RabinKarp.occurances(text.toCharArray(), pattern.toCharArray(),start,end));
					}
					else if(algo==2) {
						System.out.println(KMP.occurances(text.toCharArray(), pattern.toCharArray(),start,end));
					}
					else {
						suffixArray tsuf = new suffixArray(text.substring(start,end)+"$");
						System.out.println(tsuf.numOfPatternOcurrences(pattern));
					}
					System.out.println();
					break;
				case 3 :
					System.out.print("Algos : \n1..Rabin-karp\n2..KMP\n3..Suffix Tree\nEnter Algo choice : ");
					int type = in.nextInt();
					if(type==1) {
						RabinKarp.Build_cross_Index(txtfile);
					}
					else if(type==2) {
						KMP.Build_cross_Index(txtfile);
					}
					else {
						Storywise.Index(txtfile);
					}
					System.out.println();
					break;
				case 4 :
					System.out.print("Enter the minimum pallindrome size : ");
					int size = in.nextInt();
					Palindrome.pdrome(txtfile,size);
					break;
				case 5 :
					break;

			}
		}while(choice!=6);
		in.close();
	}

}
