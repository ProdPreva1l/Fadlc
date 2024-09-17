package info.preva1l.fadlc.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * An interface for interacting with data, by passing daos.
 */
public interface DataHandler {
    /**
     * Get all the objects of type <T>
     * @param clazz type to get
     * @return list of <T>
     */
    <T extends DatabaseObject> List<T> getAll(Class<T> clazz);

    /**
     * Get an object of a certain class and id.
     * @param clazz type to get.
     * @param id the uuid of the object.
     * @return an optional of the object, an empty optional if the object was not found.
     */
    <T extends DatabaseObject> Optional<T> get(Class<T> clazz, UUID id);

    /**
     * Search for an object of a certain class and matching the name.
     * @param clazz type to get.
     * @param name the name to search for, either a username or server name.
     * @return an optional of the object, an empty optional if the object was not found.
     */
    <T extends DatabaseObject> Optional<T> search(Class<T> clazz, String name);

    /**
     * Save an object of a certain type.
     * @param clazz class of the object
     * @param t object
     */
    <T extends DatabaseObject> void save(Class<T> clazz, T t);

    /**
     * Update an object in the database.
     * @param clazz class of the object
     * @param t the new object
     * @param params update params
     */
    <T extends DatabaseObject> void update(Class<T> clazz, T t, String[] params);

    /**
     * Delete an object of t
     * @param clazz class of object
     * @param t object
     */
    <T extends DatabaseObject> void delete(Class<T> clazz, T t);

    /**
     * Delete a specific object in a collection
     * @param clazz class of collection
     * @param t collection
     * @param o object to delete
     */
    <T extends DatabaseObject> void deleteSpecific(Class<T> clazz, T t, Object o);

    /**
     * Registers a dao with the database handler.
     * @param clazz the daos linkage
     * @param dao the instance of the dao
     */
    void registerDao(Class<?> clazz, Dao<? extends DatabaseObject> dao);
}