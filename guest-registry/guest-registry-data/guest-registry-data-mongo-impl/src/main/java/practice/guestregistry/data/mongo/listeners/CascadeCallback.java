package practice.guestregistry.data.mongo.listeners;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.ReflectionUtils;
import practice.guestregistry.data.mongo.annotations.CascadeSave;

import java.lang.reflect.Field;

@Data
//TODO: test if it works on save
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private static final Logger log = LoggerFactory.getLogger("CIAA");
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

        log.debug("\n\n\n\n\nUSING ONE!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            log.debug("\n\nFOUND ONE!!!!!!!!!!!!!!!!!!!!!!!!!\n\n");
            final Object fieldValue = field.get(getSource());

            if (fieldValue != null) {
                final FieldCallback callback = new FieldCallback();

                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

                getMongoTemplate().save(fieldValue);
            }
        }

    }
}
