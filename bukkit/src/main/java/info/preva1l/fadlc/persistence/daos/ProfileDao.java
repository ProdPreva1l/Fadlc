package info.preva1l.fadlc.persistence.daos;

import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.persistence.Dao;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ProfileDao implements Dao<IClaimProfile> {
    private final HikariDataSource dataSource;

    /**
     * Get an object from the database by its id.
     *
     * @param id the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    @Override
    public Optional<IClaimProfile> get(UUID id) {
        return Optional.empty();
    }

    /**
     * Get all objects of type T from the database.
     *
     * @return a list of all objects of type T in the database.
     */
    @Override
    public List<IClaimProfile> getAll() {
        return List.of();
    }

    /**
     * Save an object of type T to the database.
     *
     * @param iClaimProfile the object to save.
     */
    @Override
    public void save(IClaimProfile iClaimProfile) {

    }

    /**
     * Update an object of type T in the database.
     *
     * @param iClaimProfile the object to update.
     * @param params        the parameters to update the object with.
     */
    @Override
    public void update(IClaimProfile iClaimProfile, String[] params) {

    }

    /**
     * Delete an object of type T from the database.
     *
     * @param iClaimProfile the object to delete.
     */
    @Override
    public void delete(IClaimProfile iClaimProfile) {

    }
}
