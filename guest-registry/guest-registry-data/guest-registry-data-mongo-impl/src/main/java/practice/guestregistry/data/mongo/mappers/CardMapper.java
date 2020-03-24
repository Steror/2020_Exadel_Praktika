package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.CardEntity;
import practice.guestregistry.domain.Card;

@Component
public class CardMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public CardMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Card.class, CardEntity.class)
                .mapNullsInReverse(false)
                .mapNulls(false)
                .byDefault()
                .register();

    }

    public Card entityToDomain (CardEntity cardEntity) {
        return this.mapperFactory.getMapperFacade(CardEntity.class, Card.class).map(cardEntity);
    }

    public CardEntity domainToEntity (Card card) {
        return this.mapperFactory.getMapperFacade(Card.class, CardEntity.class).map(card);
    }
}
