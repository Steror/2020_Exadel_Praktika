package practice.guestregistry.data.mongo.mappers;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.data.mongo.entities.CardEntity;
import practice.guestregistry.data.mongo.entities.PersonEntity;
import practice.guestregistry.domain.Card;
import practice.guestregistry.domain.Person;

import java.time.LocalDateTime;

@Component
public class CardMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public CardMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Card.class, CardEntity.class)
//                .mapNullsInReverse(false)
//                .mapNulls(false)
                .exclude("id")
                .exclude("manufactured")
                .exclude("validUntil")
                .byDefault()
                .field("locationId", "locationEntity.id")
                .field("locationName", "locationEntity.name")
                .customize(new CustomMapper<Card, CardEntity>() {
                    @Override
                    public void mapAtoB(Card card, CardEntity cardEntity, MappingContext context) {
                        LocalDateTime manufacturedParse = LocalDateTime.parse(card.getManufactured());
                        cardEntity.setManufactured(manufacturedParse);

                        LocalDateTime validUntilParse = LocalDateTime.parse(card.getValidUntil());
                        cardEntity.setValidUntil(validUntilParse);

                        if (card.getId() != null && ObjectId.isValid(card.getId())) {
                            cardEntity.setId(new ObjectId(card.getId()));
                            super.mapAtoB(card, cardEntity, context);
                        } else {
                            cardEntity.setId(null);
                        }
                    }

                    @Override
                    public void mapBtoA(CardEntity cardEntity, Card card, MappingContext context) {
                        card.setManufactured(cardEntity.getManufactured().toString());
                        card.setValidUntil(cardEntity.getValidUntil().toString());
//                        super.mapBtoA(cardEntity, card, context);
                        card.setId(cardEntity.getId().toHexString());
                    }
                })
                .register();

    }

    public Card entityToDomain (CardEntity cardEntity) {
        return this.mapperFactory.getMapperFacade(CardEntity.class, Card.class).map(cardEntity);
    }

    public CardEntity domainToEntity (Card card) {
        return this.mapperFactory.getMapperFacade(Card.class, CardEntity.class).map(card);
    }
}
