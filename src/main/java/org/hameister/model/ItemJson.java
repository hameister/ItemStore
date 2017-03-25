package org.hameister.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

/**
 * Created by hameister on 22.01.17.
 */
@JsonComponent
public class ItemJson {

    public static class Serializer extends JsonObjectSerializer<Item> {

        @Override
        protected void serializeObject(Item value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            System.out.println("serializeObject");

            jgen.writeNumberField("id", value.getId());
            jgen.writeStringField("description", value.getDescription());
            jgen.writeStringField("location", value.getLocation());
        }
    }


    public static class Deserilizer extends JsonObjectDeserializer<Item> {

        @Override
        protected Item deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
            System.out.println("deserializeObject");
            String description = tree.get("description").asText();
            String location = tree.get("location").asText();
            return new Item(1l,description, location, null);
        }
    }
}
