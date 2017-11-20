package se.kth.id1020.tinysearchengine;

import se.kth.id1020.util.Attributes;



public class SubAttributes {
    public Attributes attributes;
    public int count;
    
    public SubAttributes(Attributes attr){
        attributes = attr;
        count = 1;
    }
    
    public int compareByProperty(SubAttributes attr2, Property p){
        switch(p){
            case count: // count
                if(count == attr2.count) return 0; //equal count
                // returns 1 if this > other, -1 if this < other
                return (count > attr2.count) ? 1 : -1;
            case popularity: // popularity    
                if(attributes.document.popularity ==
                        attr2.attributes.document.popularity)
                    return 0; // equal popularity
                
                // returns 1 if this > other, -1 if this < other
                return (attributes.document.popularity >
                        attr2.attributes.document.popularity)
                        ? 1 : -1;
                
            case occurrence: // occurrence
                if(attributes.occurrence == attr2.attributes.occurrence)
                    return 0; // equal occurence
                
                // returns 1 if this > other, -1 if this < other
                return (attributes.occurrence > attr2.attributes.occurrence)
                        ? 1 : -1;
                
                
            default:
                throw new IllegalArgumentException("Invalid property to compare");
        }
    }

}
