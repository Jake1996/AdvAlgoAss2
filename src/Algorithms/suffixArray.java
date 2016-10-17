//import java.util.Arrays;
import java.util.*;
//import java.util.*;
import java.io.*;



public class suffixArray {
    private Suffix[] suffixes;
    String text;
    private int lcp[];
    /**
     * Initializes a suffix array for the given {@code text} string.
     * @param text the input string
     */
    public suffixArray(String text) {
        
        this.text = text;
        int n = text.length()-1;
        System.out.println("text kength is : " +text.length());

        this.suffixes = new Suffix[n];

        int i=0;
        StringBuilder subtext ;
        while(i<text.length()-1)
        {
           //System.out.println(i);
            subtext = new StringBuilder();
            int j=0;
            while(text.charAt(i+j)!='$')
            {
                subtext.append(text.charAt(i+j));
                j++;
            }

            subtext.append('$');
            j++;

            for(j = 0; j < subtext.length(); j++)
            {
                suffixes[i+j] = new Suffix(i+j,i+subtext.length()-1);
                
            }
               // (i+j,i+subtext.length())
            i=i+subtext.length();
           // System.out.println(i);

        }
        Arrays.sort(suffixes);
       /*for(int k=0;k<suffixes.length;k++)
       {
           
            System.out.println(suffixes[k]);
       }*/
       initializeLCP();
    }
    private void initializeLCP()
    {
        lcp = new int[suffixes.length];
        for(int i=0;i<suffixes.length;i++)
            lcp[i] = -1;
    }

    private class Suffix implements Comparable<Suffix> {
        private final int start;
        private final int end;

        private Suffix(int start, int end) {
            this.start = start;
            this.end = end;
        }
        private int length() {
            return end-start+1;
        }
        private char charAt(int i) {
            return suffixArray.this.text.charAt(this.start+i);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }

        public String toString() {
            return suffixArray.this.text.substring(start,end+1);
        }
    }

    /**
     * Returns the length of the input string.
     * @return the length of the input string
     */
    public int length() {
        return suffixes.length;
    }

    public Suffix index(int i) {
        if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        return suffixes[i];
    }
    public int getLCP(String s1,Suffix s2)
    {
        int n = Math.min(s1.length(), s2.length());
        for (int i = 0; i < n; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return n;
    }
    public int getLCP(int i) {
        if (i < 1 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        if(lcp[i]==-1) 
            {
                lcp[i] = calculateLCP(suffixes[i], suffixes[i-1]);
            }
        return lcp[i];
    }

    // longest common prefix of s and t
    private static int calculateLCP(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return n;
    }

    /**
     * Returns the <em>i</em>th smallest suffix as a string.
     * @param i the index
     * @return the <em>i</em> smallest suffix as a string
     * @throws java.lang.IndexOutOfBoundsException unless {@code 0 <= i < n}
     */
    public String select(int i) {
        if (i < 0 || i >= suffixes.length) throw new IndexOutOfBoundsException();
        return suffixes[i].toString();
    }

    /**
     * Returns the number of suffixes strictly less than the {@code query} string.
     * We note that {@code rank(select(i))} equals {@code i} for each {@code i}
     * between 0 and <em>n</em>-1.
     * @param query the query string
     * @return the number of suffixes strictly less than {@code query}
     */
    public int rank(String query) {
        int lo = 0, hi = suffixes.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(query, suffixes[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    // compare query string to suffix
    private static int compare(String query, Suffix suffix) {
        int n = Math.min(query.length(), suffix.length());
        for (int i = 0; i < n; i++) {
            if (query.charAt(i) < suffix.charAt(i)) return -1;
            if (query.charAt(i) > suffix.charAt(i)) return +1;
        }
        return query.length() - suffix.length();
    }
    public int numOfPatternOcurrences(String query)
    {
        int low = 0;
        int high = suffixes.length-1;
        int index = patternSearch(query,low,high);

        int li,hi;
        li = index-1;
        hi = index+1;
        if(index!=-1)
        {
            int count = 1;
            while(li>=0)
            {
                if(getLCP(query,suffixes[li])==query.length())
                {
                    count++;
                    li--;
                }
                else
                {
                    break;
                }

            }
            while(hi<suffixes.length)
            {
                if(getLCP(query,suffixes[hi])==query.length())
                {
                    count++;
                    hi++;
                }
                else
                {
                    break;
                }
            }
            return count;
        }
        else return 0;

    }
    public int patternSearch(String query,int low,int high)
    {
        if(low<high)
        {
            int mid = (low+high)/2;
            if(getLCP(query,suffixes[mid])==query.length())
                return mid;
            int lcp = getLCP(query,suffixes[mid]);
            if(lcp==query.length())
            {
                return mid;
            }
            else if(suffixes[mid].length()>=query.length() || lcp<suffixes[mid].length())
            {
                if(query.charAt(lcp)>suffixes[mid].charAt(lcp))
                    return patternSearch(query,mid+1,high);
                else 
                    return patternSearch(query,low,mid);
            }
            else
            {
                return patternSearch(query,mid+1,high);
            }
            
        }
        return -1;
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s="";
        try 
        {
            s = readFile("./../../assets/standardized.txt");
           
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        suffixArray suffix = new suffixArray(s);
        System.out.println(suffix.numOfPatternOcurrences(in.next()));
        // StdOut.println("rank(" + args[0] + ") = " + suffix.rank(args[0]));

      /*  System.out.println("  i ind lcp rnk select");
        System.out.println("---------------------------");*/



        /*for (int i = 0; i < s.length()-1; i++) {
            int start = suffix.index(i).start;
            int end = suffix.index(i).end;
            String ith = "\"" + s.substring(start, end+1) + "\"";
            assert s.substring(start,end+1).equals(suffix.select(i));
            int rank = suffix.rank(s.substring(start,end+1));
            if (i == 0) {
          //System.out.printf("%3d %3d %3d %3s %3d %s\n", i, start,end, "-", rank, ith);
            }
            else {
                int lcp = suffix.getLCP(i);
            //  System.out.printf("%3d %3d %3d %3d %3d %s\n", i, start,end, lcp, rank, ith);
            }*/

       
    }

}
