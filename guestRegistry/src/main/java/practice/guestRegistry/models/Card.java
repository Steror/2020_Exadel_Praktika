package practice.guestRegistry.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "card")
public class Card {
    @Id
    Long id;
    String serialNumber;
    //Location location;
    LocalDateTime manufactured;
    LocalDateTime validUntil;
//    @JsonProperty("ctype")
    CardType ctype;


    public Card(Long id, String serialNumber, LocalDateTime manufactured, LocalDateTime validUntil, CardType ctype) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.manufactured = manufactured;
        this.validUntil = validUntil;
        this.ctype = ctype;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", manufactured=" + manufactured +
                ", validUntil=" + validUntil +
                ", ctype=" + ctype +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDateTime getManufatured() {
        return manufactured;
    }

    public void setManufatured(LocalDateTime manufactured) {
        this.manufactured = manufactured;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public CardType getCtype() {
        return ctype;
    }

    public void setCtype(CardType ctype) {
        this.ctype = ctype;
    }
}
