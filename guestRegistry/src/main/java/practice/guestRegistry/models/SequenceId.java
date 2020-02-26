package practice.guestregistry.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence")
public class SequenceId {
    @Id
    private String id;
    private long seq;

    public SequenceId() {
    }

    public SequenceId(String tableName, int i) {
        this.id = tableName;
        this.seq = (long) i;
    }

    public SequenceId(String tableName, long i) {
        this.id = tableName;
        this.seq = i;
    }

    @Override
    public String toString() {
        return "SequenceId{" +
                "id='" + id + '\'' +
                ", seq=" + seq +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
