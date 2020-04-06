package practice.guestregistry.data.sql.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import practice.guestregistry.data.sql.entities.LocationEntity;
import practice.guestregistry.data.sql.mappers.LocationMapper;
import practice.guestregistry.domain.Location;
import practice.guestregistry.exceptions.EntityCreationException;
import practice.guestregistry.exceptions.EntityDeletionException;
import practice.guestregistry.exceptions.EntityUpdateException;
import practice.guestregistry.exceptions.ResourceNotFoundException;
import practice.guestregistry.data.api.dao.LocationDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("db-h2")
@Repository
//@Repository("db-h2")
public class LocationDaoImpl implements LocationDao {
    private final LocationMapper mapper;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    @Autowired
    public LocationDaoImpl(
            EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        this.mapper = new LocationMapper();
    }

    @Override
    public Location findById(String id) {
        int identifier = Integer.decode(id);
       LocationEntity entity =  entityManager.find(LocationEntity.class, identifier);
       if (entity == null) {
           throw new ResourceNotFoundException("Can't find entity by id: " + identifier);
       } else {
           return mapper.entityToDomain(entity);
       }
    }

    @Override
    public List<Location> findAll() {
//        query.addEntity(SwitcherServiceSource.class)
        List response = (List) entityManager
                .createNativeQuery("SELECT * FROM LOCATION", LocationEntity.class)
                .getResultList()
                .stream()
                .map(elem -> mapper.entityToDomain((LocationEntity) elem))
                .collect(Collectors.toList());
        return response;
    }

    @Override
//    @Transactional
    public void add(Location location) {
//        LocationEntity savedEntity = entityManager.persist(mapper.domainToEntity(location));
//        entityManager.getTransaction().begin();
        LocationEntity mapped = mapper.domainToEntity(location);
        if (location.getId() != null) {
            throw new EntityCreationException("Location ID must be null");
        }

        entityManager.getTransaction().begin();
        entityManager.persist(mapped);
//        entityManager.flush();
        entityManager.getTransaction().commit();
        location.setId(mapped.getId().toString());
    }

    @Override
    public void update(Location location) {
        if (location == null) {
            throw new EntityUpdateException("id must be not null");
        }
        entityManager.getTransaction().begin();
        entityManager.merge(mapper.domainToEntity(location));
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(String id) {
        entityManager.getTransaction().begin();
        int rowsEffected = entityManager.createNativeQuery("DELETE FROM LOCATION WHERE ID = " + id).executeUpdate();
        entityManager.getTransaction().commit();
        if (rowsEffected != 1) {
            throw new EntityDeletionException("No id, nothing to delete");
        }
    }

    @Override
    public void deleteAll() {
        entityManager.getTransaction().begin();
//        entityManager.createNativeQuery("DELETE FROM LOCATION WHERE id = * ").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM LOCATION").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public boolean existById(String id) {
        if (id == null) {
            return false;
        }
        return entityManager.find(LocationEntity.class, Integer.decode(id)) != null;
    }

    @Override
    public boolean exist(Location location) {
        return entityManager.contains(mapper.domainToEntity(location));
    }
}
