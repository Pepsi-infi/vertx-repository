package utils;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import io.vertx.core.json.EncodeException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;

public class JsonUtil {

    public static ObjectMapper PRETTY_MAPPER = new ObjectMapper();

    public static ObjectMapper MAPPER = new ObjectMapper();

    public static JsonFormat JSON_FORMAT = new JsonFormat();

    static {
        PRETTY_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // PRETTY_MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        PRETTY_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PRETTY_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        PRETTY_MAPPER.setSerializationInclusion(Include.NON_NULL);
        PRETTY_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static String encodePrettily(Object obj) throws EncodeException {
        try {
            return PRETTY_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new EncodeException("Failed to encode as JSON: " + e.getMessage());
        }
    }

    public static String encodePrettilyForProto(Message message) throws EncodeException {
        try {
            return JSON_FORMAT.printToString(message);
        } catch (Exception e) {
            throw new EncodeException("Failed to encode as JSON: " + e.getMessage());
        }
    }

    public static void decodePrettilyForProto(String jsonString, Message.Builder builder){
        try{
            JSON_FORMAT.merge(new ByteArrayInputStream(jsonString.getBytes()), builder);
        } catch (Exception e){
            throw new EncodeException("Failed to decode from a String: " + e.getMessage());
        }
    }
}
