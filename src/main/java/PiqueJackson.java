import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PiqueJackson {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(new File("C:\\Users\\xuyin\\IdeaProjects\\GSON\\src\\main\\resources\\f.json"));
        ObjectNode node = getModel(root, mapper);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

        try{
            FileWriter writer = new FileWriter("myR.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static ObjectNode getObjectNode(JsonNode next, ObjectMapper mapper) {
        ObjectNode objectNode = mapper.createObjectNode();
        String name = next.get("name").asText();
        Float value = next.get("value").floatValue();
        objectNode.put("name", name);
        objectNode.put("value", value);
        return objectNode;
    }

    private static ObjectNode getModel(JsonNode root, ObjectMapper mapper) {
        ObjectNode objectNode = mapper.createObjectNode();

        Float value = root.get("factors").get("tqi").get("Binary Security Quality").get("value").floatValue();
        //Float value = root.get("tqi").get("Binary Security Quality").get("value").floatValue();

        objectNode.put("name", "Binary Security Quality");
        objectNode.put("value", value);
        //Iterator<JsonNode> kids = root.get("factors").get("tqi").get("Binary Security Quality").get("children").elements();
        Iterator<JsonNode> kids = root.get("factors").get("tqi").get("Binary Security Quality").get("children").elements();
        //Iterator<JsonNode> weights = root.get("factors").get("tqi").get("Binary Security Quality").get("weights").elements();
        Iterator<JsonNode> weights = root.get("factors").get("tqi").get("Binary Security Quality").get("weights").elements();
        ArrayNode qa = getQuality_aspects(kids, weights, mapper);
        objectNode.put("children", qa);
        return objectNode;

    }
    // quality_aspects
    private static ArrayNode getQuality_aspects(Iterator<JsonNode> qualityAspects, Iterator<JsonNode> weights, ObjectMapper mapper) {
        ArrayNode list = mapper.createArrayNode();

        while (qualityAspects.hasNext() && weights.hasNext()) {
            JsonNode next = qualityAspects.next();
            Iterator<JsonNode> category = next.get("children").elements();
            ObjectNode node = getObjectNode(next, mapper);
            node.put("children", getProductFactors(category, mapper));
            list.add(node);
        }
        return list;
    }

    // product-factors
    private static ArrayNode getProductFactors( Iterator<JsonNode> category, ObjectMapper mapper) {
        ArrayNode list = mapper.createArrayNode();
        while (category.hasNext()) {
            JsonNode next = category.next();
            Iterator<JsonNode> measures = next.get("children").elements();
            ObjectNode objectNode = getObjectNode(next, mapper);
            objectNode.put("children", getMeasures(measures, mapper));
            list.add(objectNode);
        }
        return list;

    }

    // measures
    private static ArrayNode getMeasures(Iterator<JsonNode> measures, ObjectMapper mapper) {
        ArrayNode list = mapper.createArrayNode();
        while (measures.hasNext()) {
            JsonNode next = measures.next();
            Iterator<JsonNode> instance = next.get("children").elements();
            ObjectNode objectNode = getObjectNode(next, mapper);
            objectNode.put("children", getDiagnostics(instance, mapper));
            list.add(objectNode);
        }
        return list;
    }

    // diagnostics

    private static ArrayNode getDiagnostics(Iterator<JsonNode> measures, ObjectMapper mapper) {
        ArrayNode list = mapper.createArrayNode();
        while (measures.hasNext()) {
            JsonNode next = measures.next();
            Iterator<JsonNode> diagnostics = next.get("children").elements();
            ObjectNode objectNode = getObjectNode(next, mapper);
            list.add(objectNode);
        }
        return list;
    }

}
