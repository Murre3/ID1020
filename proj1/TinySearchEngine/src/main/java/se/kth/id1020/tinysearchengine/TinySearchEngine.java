package se.kth.id1020.tinysearchengine;

import java.util.List;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;
import java.util.ArrayList;

public class TinySearchEngine implements TinySearchEngineBase {

    private ArrayList<Node> index;
    private QueryParser qp;
    private ArrayList<SubAttributes> unionAttributes;
    
    public TinySearchEngine(){
        index = new ArrayList();
    }
    
    @Override
    public void insert(Word word, Attributes attr) {
        int idx = BinarySearch.search(word.word, index);
        
        
        // if the word is not found, revise index (result will be negative 
        // for the search results to know there are no matches for the word)
        if(idx < 0) idx *= -1;
        
        // first case - no words added
        if(index.isEmpty()){
            index.add(new Node(word, attr));
            return;
        }
        
        Node temp = index.get(idx);
        // second case - word is not indexed 
        
        if(temp.compareTo(word.word) != 0) {
            index.add(idx, new Node(word, attr));
            return;
        }
        
        // third case - word is indexed but not the given attributes
        temp.addAttributes(attr);
        
    }

    @Override
    public List<Document> search(String query) {
        
        // simple search with just one word
        if(query.split("\\s+").length == 1)// delim is whitespace
            return attrToDocs(singleSearch(query), false);
        
        
        // get the order (+property/direction)
        // and tokenize the query
        qp = new QueryParser(query);
        
        
        // union find, merges all the lists that results from each word search
        unionAttributes = new ArrayList();
        for(String token : qp.tokens){
            mergeAttributes(singleSearch(token));
        }
        
        if(qp.orderEnabled)
            bubbleSort();
        
        return attrToDocs(unionAttributes, qp.orderEnabled);
    }
    
    public List<SubAttributes> singleSearch(String query) {
        int idx = BinarySearch.search(query, index);
        
        // not found in the search, no results
        if(idx < 0)
            return new ArrayList();
        
        // initializes list
        ArrayList<SubAttributes> matchingAttributes = new ArrayList();
        
        // converts the attributes in the given query-token's node
        // to the list of SubAttributes
        for(SubAttributes sA : index.get(idx).attributes){
            SubAttributes sA2 = new SubAttributes(sA.attributes);
            sA2.count = sA.count;
            matchingAttributes.add(sA2);
        }
        return matchingAttributes;
    }
    
    /*
        MERGE LIST WITH UNIONATTRIBUTES
    */
    private void mergeAttributes(List<SubAttributes> listToMerge){
        // adds ALL the attributes since they will be unique with distinct words
        for(SubAttributes temp : listToMerge){
            for(SubAttributes s : unionAttributes){
                if(temp.attributes.document.equals(s.attributes.document)){
                    temp.count += s.count;
                    s.count = temp.count;
                }
            }
            unionAttributes.add(temp);
        }
    }
    /**/
    
    // Converts the SubAttributes list to a Documents list,
    // but avoiding adding duplicates of documents
    private List<Document> attrToDocs(List<SubAttributes> attributes, boolean sorted){
        ArrayList<Document> docResults = new ArrayList();
        
        // descending
        if(sorted && qp.direction == Direction.desc){
            SubAttributes a;
            for(int i = attributes.size()-1; i >= 0; i--){
                a = attributes.get(i);
                if(!docResults.contains(a.attributes.document)){
                    docResults.add(a.attributes.document);
                    //System.out.println(a.attributes + ", Count = " + a.count);
                }
                
            }
            
        }else{ // ascending
            for(SubAttributes a : attributes){
            
                if(!docResults.contains(a.attributes.document)){
                    docResults.add(a.attributes.document);
                    //System.out.println(a.attributes + ", Count = " + a.count);
                }
            }
        }
        
        return docResults;
    }
    
    
    
    /*
            BUBBLE SORT METHOD
    */
    public void bubbleSort(){
        
        int r  = unionAttributes.size()-2;
        boolean swapped = true;
        SubAttributes curr;
        SubAttributes next;
        
        // ascending standard
        while(r >= 0 && swapped){
            swapped = false;
            
            for(int i = 0; i <= r; i++){
                curr = unionAttributes.get(i);
                next = unionAttributes.get(i+1);
                
                if(curr.compareByProperty(next, qp.property) > 0){
                    unionAttributes.set(i+1, curr);
                    unionAttributes.set(i, next);
                    swapped = true;
                }
            }
            r--;
        }
        //arraylist should be sorted
    }
        
        
    
}
