package practice.guestRegistry.dao;

import practice.guestRegistry.exceptions.SequenceException;

public interface SequenceDao {
    long getNextSequenceId (String key) throws SequenceException;
    void initCollection (String tableName);
}
