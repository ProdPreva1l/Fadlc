package info.preva1l.fadlc.persistence.daos;

import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.models.IClaimChunk;
import info.preva1l.fadlc.models.claim.Claim;
import info.preva1l.fadlc.models.claim.IClaim;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.models.user.OfflineUser;
import info.preva1l.fadlc.persistence.Dao;
import info.preva1l.fadlc.utils.Logger;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class ClaimDao implements Dao<IClaim> {
    private final HikariDataSource dataSource;

    /**
     * Get an object from the database by its id.
     *
     * @param id the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    @Override
    public Optional<IClaim> get(UUID id) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        SELECT  `ownerUUID`, `ownerUsername`, `profiles`, `chunks`
                        FROM `listings`
                        WHERE `ownerUUID`=?;""")) {
                statement.setString(1, id.toString());
                final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    final UUID ownerUUID = id;
                    final String ownerName = resultSet.getString("ownerUsername");
                    final Map<IClaimChunk, Integer> chunks = new HashMap<>();
                    final Map<Integer, IClaimProfile> profiles = new HashMap<>();
                    return Optional.of(new Claim(new OfflineUser(ownerUUID, ownerName), chunks, profiles));
                }
            }
        } catch (SQLException e) {
            Logger.severe("Failed to get claim!", e);
        }
        return Optional.empty();
    }

    /**
     * Get all objects of type T from the database.
     *
     * @return a list of all objects of type T in the database.
     */
    @Override
    public List<IClaim> getAll() {
        List<IClaim> claims = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        SELECT  `ownerUUID`, `ownerUsername`, `profiles`, `chunks`
                        FROM `listings`""")) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final UUID ownerUUID = UUID.fromString(resultSet.getString("ownerUUID"));
                    final String ownerName = resultSet.getString("ownerUsername");
                    final Map<IClaimChunk, Integer> chunks = new HashMap<>();
                    final Map<Integer, IClaimProfile> profiles = new HashMap<>();
                    claims.add(new Claim(new OfflineUser(ownerUUID, ownerName), chunks, profiles));
                }
            }
        } catch (SQLException e) {
            Logger.severe("Failed to get all claims!", e);
        }
        return claims;
    }

    /**
     * Save an object of type T to the database.
     *
     * @param claim the object to save.
     */
    @Override
    public void save(IClaim claim) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        INSERT INTO `claims`
                        (`ownerUUID`, `ownerUsername`, `profiles`, `chunks`)
                        VALUES (?,?,?,?);""")) {
                statement.setString(1, claim.getOwner().getUniqueId().toString());
                statement.setString(2, claim.getOwner().getName());
                statement.setString(3, Fadlc.i().getGson().toJson(profileSerialize(claim.getProfiles())));
                statement.setString(4, Fadlc.i().getGson().toJson(chunkSerialize(claim.getClaimedChunks())));
                statement.execute();
            }
        } catch (SQLException e) {
            Logger.severe("Failed to add item to listings!", e);
        }
    }

    /**
     * Update an object of type T in the database.
     *
     * @param claim the object to update.
     * @param params   the parameters to update the object with.
     */
    @Override
    public void update(IClaim claim, String[] params) {

    }

    /**
     * Delete an object of type T from the database.
     *
     * @param claim the object to delete.
     */
    @Override
    public void delete(IClaim claim) {

    }

    private List<String> profileSerialize(Map<Integer, IClaimProfile> profiles) {
        List<String> list = new ArrayList<>();
        for (IClaimProfile profile : profiles.values()) {
            list.add(profile.getUniqueId().toString());
        }
        return list;
    }

    private List<String> chunkSerialize(Map<IClaimChunk, Integer> profiles) {
        List<String> list = new ArrayList<>();
        for (IClaimChunk chunk : profiles.keySet()) {
            list.add(chunk.getUniqueId().toString());
        }
        return list;
    }
}
