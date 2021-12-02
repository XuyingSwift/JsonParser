import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

public class Node {
    private String name;
    private Float value;
    private Iterator<JsonNode> kids;
    private Float weight;

    public Node(){
    }


}
