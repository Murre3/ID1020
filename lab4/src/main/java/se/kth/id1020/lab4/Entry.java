package se.kth.id1020.lab4;

import java.util.Map;


public class Entry implements Map.Entry<String, Integer>, Comparable<Entry> {
    private String key;
    private Integer value;
    
    public Entry(String k, Integer v){
        key = k;
        value = v;
    }
    
    public boolean hasValue(){
        return value > 0;
    }
    
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Integer setValue(Integer v) {
        Integer oldV = value;
        value = v;
        return oldV; // returns the previous value
    }

    @Override
    public int compareTo(Entry o) {
        if(this.value < o.value) return -1;
        if(this.value > o.value) return 1;
        return 0; // equal
    }
    
    @Override
    public String toString(){
        return key + " : " + value;
    }

}
