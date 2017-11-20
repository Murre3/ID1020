import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;
import se.kth.id1020.util.Sentence;

import java.util.*;

public class TinySearchEngine implements TinySearchEngineBase {

    // indexing hashmaps
    private HashMap<String, ArrayList<Attributes>> map = new HashMap<>();
    private HashMap<Document, ArrayList<String>> wordsInDocs = new HashMap<>();

    // ordering variables
    private static final int DESC = -1;
    private static final String ORDERBY = "orderby";
    private boolean orderEnabled = false;
    private Comparator<Document> property;
    private int direction;
    private DocumentComparator dComp;
    private HashMap<Document, Double> docRelevance;

    private Stack<String> infixStack;

    @Override
    public void preInserts() {

    }

    @Override
    public void insert(Sentence sentence, Attributes attributes) {
        List<Word> words = sentence.getWords();

        for(Word w : words){

            // ========= Actual indexing =========
            ArrayList<Attributes> mapList = map.get(w.word);
            // attributes does not exist yet, is added
            if(mapList == null) {
                ArrayList<Attributes> temp = new ArrayList<>();
                temp.add(attributes);
                map.put(w.word, temp);
            }
            else // attributes does exist, attributes is added to node
                mapList.add(attributes);
            // ===================================


            // ========= Index the contents (words) of each document
            ArrayList<String> wordList = wordsInDocs.get(attributes.document);

            if(wordList == null){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(w.word);
                wordsInDocs.put(attributes.document, temp);
            }
            else
                wordList.add(w.word);
            // =====================================================

        }

    }

    @Override
    public void postInserts() {

    }

    @Override
    public List<Document> search(String s) {
        // needed for resolveParse
        dComp = new DocumentComparator();

        List<Document> docs = resolveParse(s);
        if(docs == null) return null;

        // sort accordingly if ordering is enabled
        if(orderEnabled) {
            dComp.setRelevanceMap(docRelevance);
            Collections.sort(docs, property);
            if (direction == DESC)
                Collections.reverse(docs);
        }

        return docs;
    }


    @Override
    public String infix(String s) {
        String res = infixStack.pop(); // pop the nestled queries

        // if ordering is enabled, the three order-tokens will be last on the stack
        if(orderEnabled){
            res += " " +
                    infixStack.pop() + " " + infixStack.pop() + " " + infixStack.pop();
                    // orderby                  property                direction
        }
        return res;
    }

    // ============== PRIMARY SEARCH METHOD =================
    private List<Document> resolveParse(String query){
        // helper stack to show the infix correspondence of the query
        infixStack = new Stack<>();

        // hashmap containing the relevance for the current query and one for current sub-query
        docRelevance = new HashMap<>();
        HashMap<Document, Double> tempRel;
        Stack<HashMap> relStack = new Stack<>();


        // we iterate over the query from right to left
        // with the help of a stack
        Stack<List<Document>> parseStack = new Stack<>();
        query = query.trim();
        String[] tokens = query.split("\\s+"); // removes all whitespace
        int i = tokens.length-1;
        orderEnabled = false; // ensures the variable is reset for every query

        // ordering check makes sure ordering tokens are skipped if present
        if(tokens.length > 3 && tokens[i-2].equals(ORDERBY)){
            orderEnabled = true;

            infixStack.push(tokens[i]);
            setDirection(tokens[i--]); // direction token

            infixStack.push(tokens[i]);
            setProperty(tokens[i--]); // property token

            infixStack.push(tokens[i]);
            i--; // orderby token is skipped in loop
        }


        // iterate the query
        for(; i >= 0; i--){ // initial value of i is already set up

            // prevents search for e.g. "word1 orderby" without property and/or direction
            if(tokens[i].equals(ORDERBY))
                throw new UnsupportedOperationException("Improper use of orderby");

            // operands
            if(!isOperator(tokens[i])) {
                List<Document> searchResults = simpleSearch(tokens[i]);
                tempRel = new HashMap<>();

                parseStack.push(searchResults);
                infixStack.push(tokens[i]);

                if(searchResults != null)
                    for(Document d : searchResults)
                        tempRel.put( d, relevance(d, tokens[i]) );
                relStack.push(tempRel);
            }
            // operators
            else{
                char op = tokens[i].charAt(0);
                // pops the two operands from the stack(s) and operate on them
                // using the operator in the respective external method
                String w1 = infixStack.pop(), w2 = infixStack.pop();
                List<Document> resultDocs = opDocs(parseStack.pop(), parseStack.pop(), op);
                parseStack.push(resultDocs);
                infixStack.push(infixOp(w1, w2, op));

                // does the same with the relevance-hashmaps
                relStack.push( opRelevance(resultDocs, relStack.pop(), relStack.pop(), op) );
            }
        }
        docRelevance = relStack.pop(); // sets the global relevance-map to the result of all ops
        return parseStack.pop(); // ought to be last item on stack, search result of all op's
    }
    //=======================================================


    // ================ PARSING HELPER METHODS ==============
    private List<Document> simpleSearch(String word){
        List<Document> docs = new ArrayList<>();
        if(map.get(word) == null) return null;
        for(Attributes a : map.get(word)){
            if(!docs.contains(a.document))
                docs.add(a.document);
        }
        return docs;
    }

    private List<Document> opDocs(List<Document> l1, List<Document> l2, char op){
        List<Document> opDocs = new ArrayList<>();
        switch(op) {
            case '+':
                // intersection
                if(l1 == null || l2 == null) return null;
                for(Document d : l1){
                    if(l2.contains(d))
                        opDocs.add(d);
                }
                break;
            case '-':
                // difference
                if(l2 != null) {
                    for (Document d : l1)
                        if (!l2.contains(d))
                            opDocs.add(d);
                }else return l1; // if l2 is empty all of l1 is the difference
                break;
            case '|':
                // union
                if(l1 == null) return l2;
                if(l2 == null) return l1;
                opDocs.addAll(l1);
                for(Document d : l2){
                    if(!opDocs.contains(d))
                        opDocs.add(d);
                }
                break;
            default:
                throw new UnsupportedOperationException
                        ("Somehow you got true from isOperator() when you shouldn't have");
        }

        return opDocs;
    }

    private boolean isOperator(String s){
        if(s.length() != 1) return false;
        char c = s.charAt(0);
        return c == '+' ||  c == '-' || c == '|';
    }
    // =====================================================


    // ================ INFIX HELPER METHOD ================
    private String infixOp(String s1, String s2, char op){
        return "(" + s1 + " " + op + " " + s2 + ")";
    }
    // =====================================================



    // =========== ORDERING METHODS ========================
    private void setDirection(String dir){
        switch (dir) {
            case "asc":
                direction = DESC*-1;
                break;
            case "desc":
                direction = DESC;
                break;
            default:
                throw new UnsupportedOperationException("Improper direction token: " + dir);
        }
    }
    private void setProperty(String prop){
        switch (prop) {
            case "popularity":
                property = DocumentComparator.POPULARITY;
                break;
            case "relevance":
                property = dComp.RELEVANCE;
                break;
            default:
                throw new UnsupportedOperationException("Unsupported ordering property: " +
                        prop);
        }

    }
    // ====================================================

    // =========== RELEVANCE METHODS ======================
    private HashMap<Document, Double> opRelevance(List<Document> operatedDocs,
                                                   HashMap<Document, Double> q1Rel,
                                                   HashMap<Document, Double> q2Rel,
                                                   char op){
        HashMap<Document, Double> result = new HashMap<>();

        // in case of one empty hashmap, return the other
        if(q1Rel.keySet().isEmpty()) {
            // - (difference) only looks at the first term's relevance
            if(op == '-') return q1Rel;
            return q2Rel;
        }
        if(q2Rel.keySet().isEmpty())
            return q1Rel;

        switch(op){
            case '+': // intersection and union have the same relevance calculation
            case '|':
                for(Document d : operatedDocs) {
                    // ensures that all the 0-values for relevance will not cause NPE
                    double r1 = q1Rel.get(d) == null ? 0 : q1Rel.get(d);
                    double r2 = q2Rel.get(d) == null ? 0 : q2Rel.get(d);
                    result.put(d, r1 + r2);
                }
                break;

            case '-': // we only look at the relevance relating to the first term
                for(Document d : operatedDocs){
                    double r = q1Rel.get(d) == null ? 0 : q1Rel.get(d);
                    result.put(d, r);
                }
                break;
            default:
                throw new UnsupportedOperationException
                        ("Somehow you got true from isOperator() when you shouldn't have");
        }

        return result;
    }

    private double relevance(Document d, String q){

        // ==== Term Frequency ====
        // number of times the term occurs in the doc : n(q,d)
        int termCount = 0;
        for(String s : wordsInDocs.get(d)){
            if(s.equals(q)) termCount++;
        }

        // total number of terms in the document : T(d)
        int dTerms = wordsInDocs.get(d).size();


        // ==== Inverse Document Frequency ====
        // total number of docs : N.D
        int totDocs = wordsInDocs.keySet().size();

        // number of docs that contain the search term : n.D(q)
        ArrayList<Attributes> termAttr = map.get(q);
        ArrayList<Document> termDocs = new ArrayList<>();
        for(Attributes a : termAttr){
            if(!termDocs.contains(a.document)) termDocs.add(a.document);
        }
        int termDocsN = termDocs.size();

        // term frequency : n(q,d)/T(d)
        double tf = (double)termCount/dTerms;

        // inverse document frequency : log10( N.D/n.D(q) )
        double idf = Math.log10(    (double)totDocs/termDocsN);
        System.out.println("" + tf*idf);
        // final step of multiplying term frequency with inverse document frequency = relevance
        return tf*idf;
    }
    // ====================================================
}
