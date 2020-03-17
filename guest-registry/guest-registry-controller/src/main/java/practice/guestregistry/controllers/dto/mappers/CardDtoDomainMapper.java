package practice.guestregistry.controllers.dto.mappers;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practice.guestregistry.controllers.dto.models.CardDTO;
import practice.guestregistry.data.api.domain.Card;

@Component
public class CardDtoDomainMapper {
    private MapperFactory mapperFactory;

    @Autowired
    public CardDtoDomainMapper() {

        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(CardDTO.class, Card.class)
                .byDefault()
                .register();
    }

    public CardDTO map (Card card) {
        return this.mapperFactory.getMapperFacade(Card.class, CardDTO.class).map(card);
    }

    public Card map (CardDTO cardDTO) {
        return this.mapperFactory.getMapperFacade(CardDTO.class, Card.class).map(cardDTO);
    }
}
