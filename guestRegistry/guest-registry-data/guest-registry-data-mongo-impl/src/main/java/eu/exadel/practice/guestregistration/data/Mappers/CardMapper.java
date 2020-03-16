package eu.exadel.practice.guestregistration.data.Mappers;

import eu.exadel.practice.guestregistration.data.domain.Card;
import eu.exadel.practice.guestregistration.data.entities.CardEntity;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CardMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public CardMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(Card.class, CardEntity.class);
    }

    public Card entityToDomain (CardEntity cardEntity) {
        Card card = new Card();
        this.mapperFactory.getMapperFacade(CardEntity.class, Card.class).map(cardEntity, card);
        return card;
    }

    public CardEntity domainToEntity (Card card) {
        CardEntity cardEntity = new CardEntity();
        this.mapperFactory.getMapperFacade(Card.class, CardEntity.class).map(card, cardEntity);
        return cardEntity;
    }
}
