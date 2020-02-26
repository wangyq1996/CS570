import java.util.Map;
import java.util.*;
import java.io.*;
public class Anagrams {
    final Integer[] primes = {2 , 3 , 5 , 7 , 11 , 13 , 17 , 19 , 23 , 29 , 31 , 37 , 41 , 43 , 47 , 53 , 59 , 61 ,
            67 , 71 , 73 , 79 , 83 , 89 , 97 , 101};
    Map<Character,Integer> letterTable;
    Map<Long,ArrayList<String>> anagramTable;

    public Anagrams(){
        letterTable = new HashMap<>();
        anagramTable = new HashMap<>();
        buildLetterTable();
    }

    private void buildLetterTable(){for(int i = 0;i<26;i++) letterTable.put((char) ((char)i+97),primes[i]);}

    private Long myHashCode(String s){
        long output = 1;
        for(int i=0;i<s.length();i++) output *= letterTable.get(s.charAt(i));
        return output;
    }

    private void addWord(String s){
        if(anagramTable.containsKey(myHashCode(s))) anagramTable.get(myHashCode(s)).add(s);
        else {
            ArrayList<String> ary = new ArrayList<>();
            ary.add(s);
            anagramTable.put(myHashCode(s),ary);
        }
    }

    public void processFile(String s) throws IOException{
        FileInputStream fstream = new FileInputStream ( s );
        BufferedReader br = new BufferedReader ( new InputStreamReader ( fstream ));
        String strLine ;
        while (( strLine = br .readLine ()) != null ){
            this . addWord ( strLine );
        }
        br . close ();
    }

    private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries(){
        int max = Integer.MIN_VALUE;
        ArrayList<Map.Entry<Long,ArrayList<String>>> ary = new ArrayList<>();
        for(Map.Entry<Long,ArrayList<String>> s: anagramTable.entrySet()){
            if(s.getValue().size() > max){
                ary.clear();
                max = s.getValue().size();
                ary.add(s);
            }
            else if(s.getValue().size() == max) ary.add(s);
        }
        return ary;
    }

    public static void main(String[] args){
        Anagrams a = new Anagrams ();
        final long startTime = System . nanoTime ();
        try {
            a.processFile ( "words_alpha.txt" );
        } catch ( IOException e1 ) {
            e1 .printStackTrace ();
        }
        ArrayList < Map . Entry < Long , ArrayList < String > > > maxEntries = a . getMaxEntries ();
        final long estimatedTime = System.nanoTime () - startTime ;
        final double seconds = (( double ) estimatedTime /1000000000) ;
        System.out .println ( " Time : " + seconds );
        System .out .println ( " Length of list of max anagrams : " + maxEntries );
    }
}
