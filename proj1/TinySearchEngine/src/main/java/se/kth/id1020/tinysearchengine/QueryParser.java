package se.kth.id1020.tinysearchengine;

import java.util.Scanner;
import java.util.ArrayList;

public class QueryParser {
    
    public ArrayList<String> tokens;
    public Property property;
    public Direction direction;
    private final String ORDERBY = "orderby";
    public boolean orderEnabled = false;
    
    public QueryParser(String query){
        tokens = new ArrayList();
        Scanner qSc = new Scanner(query);
        String token;
        
        while(qSc.hasNext()){
            token = qSc.next();
            if(!token.toLowerCase().equals(ORDERBY))
                tokens.add(token);
            else{
                orderEnabled = true;
                break;
            }
        }
        
        // an "orderby" token was found, we need to parse which property
        // and direction to order the results by
        if(orderEnabled){
            // parsing property
            if(qSc.hasNext()){
                token  = qSc.next();
                if(setProperty(token) != Property.nonproperty){
                    property = setProperty(token);
                }else{
                    System.out.println("Bad property token: " + token + ", invalid input");
                }
            }else{
                System.out.println("Missing property token, invalid input");
                return;
            }
            
            // parsing order direction
            if(qSc.hasNext()){
                token = qSc.next();
                if(setDirection(token) != Direction.nonorder)
                    direction = setDirection(token);
                else{
                    System.out.println("Bad direction token: " + token + ", invalid input");
                }
            }else{
                System.out.println("Missing direction token, invalid input");
            }    
        }
        
        
    }
    
    
    private Property setProperty(String s){
        s = s.toLowerCase();
        switch(s){
            case "count":
                return Property.count;
            case "popularity":
                return Property.popularity;
            case "occurrence":
                return Property.occurrence;
            default:
                return Property.nonproperty;
        }
    }
    
    private Direction setDirection(String s){
        s = s.toLowerCase();
        switch(s){
            case "asc":
                return Direction.asc;
            case "desc":
                return Direction.desc;
            default:
                return Direction.nonorder;
        }
        
        
        
    }

}