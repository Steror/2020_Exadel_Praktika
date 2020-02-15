package practice.guestRegistry.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "card")
public class Card {
    @Id
    Long id;
    String serialNumber;
    //Location location;
    Date manufatured;
    Date validUntil;
    CardType ctype;


    public Card(Long id, String serialNumber, Date manufatured, Date validUntil, CardType ctype) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.manufatured = manufatured;
        this.validUntil = validUntil;
        this.ctype = ctype;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", manufatured=" + manufatured +
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

    public Date getManufatured() {
        return manufatured;
    }

    public void setManufatured(Date manufatured) {
        this.manufatured = manufatured;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public CardType getCtype() {
        return ctype;
    }

    public void setCtype(CardType ctype) {
        this.ctype = ctype;
    }
}
