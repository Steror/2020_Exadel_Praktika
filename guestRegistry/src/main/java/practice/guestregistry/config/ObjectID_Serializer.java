package practice.guestregistry.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectID_Serializer extends JsonSerializer<ObjectId> {
    @Override
    public void serialize(ObjectId objectId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (objectId == null) {
            jsonGenerator.writeNull();
        } else {
            jsonGenerator.writeString(objectId.toString());
        }
    }
}
