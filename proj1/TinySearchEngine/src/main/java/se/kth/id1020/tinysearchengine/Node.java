package se.kth.id1020.tinysearchengine;

import java.util.ArrayList;
import se.kth.id1020.util.Word;
import se.kth.id1020.util.Attributes;

public class Node {
    public Word word;
    public ArrayList<SubAttributes> attributes;
    
    public Node(Word w, Attributes a){
        word = w;
        SubAttributes sA = new SubAttributes(a);
        attributes = new ArrayList();
        attributes.add(sA);
    }
    
    public int compareTo(String s){
        return this.word.word.compareTo(s);
    }
    
    public void addAttributes(Attributes attr){
        boolean attrExists = false;
        for(SubAttributes a : attributes){
            
            // If the attributes already exists, we increment its count
            // and stop comparing it to the attributes that do exist
            if(a.attributes.document.equals(attr.document)){
                attrExists = true;
                a.count++;
                break;
            }
        }
        // If the attributes does not exist yet, we add it
        if(!attrExists)
                attributes.add(new SubAttributes(attr));
                
    }
    
}
