package practice.guestregistry.data.mongo.listeners;


import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import practice.guestregistry.data.mongo.annotations.CascadeSave;

import java.lang.reflect.Field;

@Data
//TODO: test if it works on save
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private Object source;
    private MongoTemplate mongoTemplate;
    private int counter;

    CascadeCallback(final Object source, final MongoTemplate mongoTemplate, int counter) {
        this.source = source;
        this.setMongoTemplate(mongoTemplate);
        this.counter = counter;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
//        System.out.println(this.getClass().toString() + "[C]" + counter);
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());

            if (fieldValue != null) {
                final FieldCallback callback = new FieldCallback();

                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                getMongoTemplate().save(fieldValue);
            }
        }

    }
}
