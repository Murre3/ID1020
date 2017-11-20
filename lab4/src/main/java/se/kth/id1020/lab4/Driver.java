package se.kth.id1020.lab4;
import edu.princeton.cs.introcs.In;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;

public class Driver {

   public static void main(String[] args) {
       
       Trie trie = new Trie();
       
       /*
       trie.put("ab");
       trie.put("ac");
       trie.put("ac");
       trie.put("b");
       trie.put("bc");
       trie.put("bc");
       trie.put("bc");
       trie.put("bc");
       trie.put("bad");
       trie.put("baf");
       trie.put("baf");
       trie.put("baf");
       System.out.println("distinct: " + trie.distinct(""));
       System.out.println("count: " + trie.count(""));
       /*
       trie.createIterator("b");
       for(Map.Entry<String, Integer> e : trie){
           System.out.println(e);
       }
       /**/
       
      URL url = ClassLoader.getSystemResource("kap1.txt");

      if (url != null) {
         System.out.println("Reading from: " + url);
      } else {
         System.out.println("Couldn't find file: kap1.txt");
      }

      In input = new In(url);

      while (!input.isEmpty()) {
         String line = input.readLine().trim();
         String[] words = line.split("(\\. )|:|,|;|!|\\?|( - )|--|(\' )| ");
         String lastOfLine = words[words.length - 1];

         if (lastOfLine.endsWith(".")) {
            words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
         }

         for (String word : words) {
            String word2 = word.replaceAll("\"|\\(|\\)|’|“|”|-|'", "");

            if (word2.isEmpty()) {
               continue;
            }
            
            // Add the word to the trie
            word2 = word2.toLowerCase();
            trie.put(word2);
            System.out.println(word2);
         }
      }
   //Perform analysis
   trie.createIterator("");
   
   // name indicates what it contains, not what it prioritizes
   PriorityQueue<Map.Entry<String, Integer>> pq_Max = new PriorityQueue(11);
   PriorityQueue<Map.Entry<String, Integer>> pq_Min = new PriorityQueue(11,
                         Collections.<Map.Entry<String,Integer>>reverseOrder());
   
   for(Map.Entry<String,Integer> e : trie){
       // adds all values
       if(e.getValue() > 0){
           pq_Max.add(e);
           pq_Min.add(e);
       }
       if(pq_Max.size() == 11)
           pq_Max.poll();
       if(pq_Min.size() == 11)
           pq_Min.poll();
       
   }
   // print 10 max (most used)
   System.out.println("Most used: ");
   while(pq_Max.peek() != null)
       System.out.println(pq_Max.poll());
   
   
   // print 10 min (least used)
   System.out.println("Least used: ");
   while(pq_Min.peek() != null)
       System.out.println(pq_Min.poll());
   
   
   // find most common 2-letter prefix
   System.out.println("Most common 2-letter prefix: ");
   System.out.println(trie.maxTwoPrefix());
   
   
   // find most common letter
   System.out.println("Most common letter: ");
   System.out.println(trie.maxStartingLetter());
   
   
   
   
   }
}