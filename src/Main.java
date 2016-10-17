import java.util.Scanner;

import Algorithms.KMP;
import Algorithms.RabinKarp;

public class Main {

	public static void main(String[] args) {
		Scanner in  = new Scanner(System.in);
		int choice;
		String txtfile="assets/standardized.txt";
		String pattern;
		int start=0,end=8;
		do {
			System.out.println("1..Find_Length_ofText(txtfile)\n2..Find_Pattern ( pattern , InTextRange,  algo)\n3..Build_Cross_Index(txtfile, algo)");
			System.out.print("4..Find_Maximal,Palindromes(PalindromeSize,  InTextRange )\n5..Print_Stats ( ) \n6..Exit\nEnter your choice: ");
			choice = in.nextInt();
			switch(choice) {
				case 1 :
					System.out.print("Enter the file name: ");
					txtfile = in.next();
					//run preprocessing here
					break;
				case 2 :
					System.out.println("Enter the pattern : ");
					in.nextLine();
					pattern = in.nextLine();
					System.out.print("Algos : \n1..Rabin-karp\n2..KMP\n3..Suffix Tree\nEnter Algo choice : ");
					int algo = in.nextInt();
					System.out.print("InTextRange Choice : \n1..Indices\n2..pattern\nEnter Choice : ");
					int ic = in.nextInt();
					if(ic == 1) {
						System.out.print("Enter start and end index: ");
						start = in.nextInt();
						end = in.nextInt();
					}
					else {
						System.out.println("Enter Start pattern : ");
						in.nextLine();
						if(algo==1) {
							start = RabinKarp.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
							
						}
						else if(algo==2) {
							start = KMP.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
						}
						else {
							//start = suffix.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
						}
						if(start==-1) break;
						System.out.println("Enter End pattern : ");
						if(algo==1) {
							end = RabinKarp.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
						}
						else if(algo==2) {
							end = KMP.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
						}
						else {
							//end = suffix.findPatternInText("dfgfdg".toCharArray(), in.nextLine().toCharArray());
						}
						if(end==-1) break;
					}
					System.out.print("Occurances of pattern : ");
					if(algo==1) {
						System.out.println(RabinKarp.occurances("dfgfgfdg".toCharArray(), pattern.toCharArray(),start,end));
					}
					else if(algo==2) {
						System.out.println(KMP.occurances("dfgfdg".toCharArray(), pattern.toCharArray(),start,end));
					}
					else {
						//end = suffix.occurances("dfgfdg".toCharArray(), pattern.toCharArray(),start,end);
					}
					System.out.println();
					break;
				case 3 :
					Storywise.Index(txtfile);
					break;
				case 4 :
					break;
				case 5 :
					break;

			}
		}while(choice!=6);
		in.close();
	}

}
