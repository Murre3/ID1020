import se.kth.id1020.util.Document;

import java.util.Comparator;
import java.util.HashMap;

public class DocumentComparator {
    private HashMap<Document, Double> docRelevance;

    public void setRelevanceMap(HashMap<Document, Double> docRelevance){
        this.docRelevance = docRelevance;
    }

    public static final Comparator<Document> POPULARITY = new Comparator<Document>(){
        @Override
        public int compare(Document d1, Document d2){
            return Integer.compare(d1.popularity, d2.popularity);
        }
    };

    public final Comparator<Document> RELEVANCE = new Comparator<Document>(){
      @Override
      public int compare(Document d1, Document d2){
          double rel1 = docRelevance.get(d1);
          double rel2 = docRelevance.get(d2);
          return Double.compare(rel1, rel2);
      }
    };

}
