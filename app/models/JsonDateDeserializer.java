package models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 28.08.2014
 * Time: 2:51
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            return JsonDateSerializer.dateFormat.parse(jsonParser.getValueAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
