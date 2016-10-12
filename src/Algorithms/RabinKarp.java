package Algorithms;

public class RabinKarp {
	public static long findPatternInText(char text[],char pattern[],int start ,int end) {
		int length = pattern.length;
		final int mod = 1000000007;
		final int prime = 97;
		long hash = 0;
		int primepow = 1;
		for(int i=0;i<length;i++) {
			hash *= prime;
			hash += pattern[i];
			hash %= mod;
			primepow *= prime;
		}
		primepow/=prime;
		long currentHash=0;
		int i;
		for(i=start;i<=end&&i<(start+length);i++) {
			currentHash *= prime;
			currentHash += text[i];
		}
		if(currentHash == hash) return i-length;
		for(i = start+length;i<=end;i++) {
			currentHash -= text[i-length]*primepow;
			currentHash *= prime;
			currentHash += text[i];
			if(currentHash==hash) return i-length+1;
		}
		return -1;
	}
	
}
