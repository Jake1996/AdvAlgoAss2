package Algorithms;

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
}
