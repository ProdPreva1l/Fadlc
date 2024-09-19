package info.preva1l.fadlc.persistence.daos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadlc.Fadlc;
import info.preva1l.fadlc.models.claim.ClaimProfile;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.models.claim.IProfileGroup;
import info.preva1l.fadlc.models.claim.settings.IProfileFlag;
import info.preva1l.fadlc.models.claim.settings.ProfileFlag;
import info.preva1l.fadlc.persistence.Dao;
import info.preva1l.fadlc.utils.Logger;
import lombok.AllArgsConstructor;
import org.bukkit.Particle;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
public class ProfileDao implements Dao<IClaimProfile> {
    private final HikariDataSource dataSource;
    private static final Type stringListType = new TypeToken<List<String>>(){}.getType();
    private static final Type flagsType = new TypeToken<Map<ProfileFlag, Boolean>>(){}.getType();

    /**
     * Get an object from the database by its id.
     *
     * @param uniqueId the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    @Override
    public Optional<IClaimProfile> get(UUID uniqueId) {
        Gson gson = Fadlc.i().getGson();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        SELECT  `id`, `name`, `groups`, `flags`, `border`
                        FROM `profiles`
                        WHERE `uuid`=?;""")) {
                statement.setString(1, uniqueId.toString());
                final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    final UUID uuid = uniqueId;
                    final String name = resultSet.getString("name");
                    final int id = resultSet.getInt("id");
                    final List<IProfileGroup> groups = groupDeserialize(gson.fromJson(resultSet.getString("groups"), stringListType));
                    final Map<IProfileFlag, Boolean> flags = gson.fromJson(resultSet.getString("flags"), flagsType);
                    final Particle particle = Particle.valueOf(resultSet.getString("border"));
                    return Optional.of(new ClaimProfile(uuid, name, id, groups, flags, particle));
                }
            }
        } catch (SQLException e) {
            Logger.severe("Failed to get profile!", e);
        }
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
     * @param profile the object to save.
     */
    @Override
    public void save(IClaimProfile profile) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                        INSERT INTO `profiles`
                        (`uuid`, `id`, `name`, `groups`, `flags`, `particle`)
                        VALUES (?,?,?,?,?,?)
                        ON CONFLICT(`uuid`) DO UPDATE SET
                            `id` = excluded.`id`,
                            `name` = excluded.`name`,
                            `groups` = excluded.`groups`,
                            `flags` = excluded.`flags`,
                            `particle` = excluded.`particle`;""")) {

                String groups = Fadlc.i().getGson().toJson(groupSerialize(profile.getGroups()));
                String flags = Fadlc.i().getGson().toJson(profile.getFlags());
                statement.setString(1, profile.getUniqueId().toString());
                statement.setInt(2, profile.getId());
                statement.setString(3, profile.getName());
                statement.setString(4, groups);
                statement.setString(5, flags);
                statement.setString(6, profile.getBorder().name());
                statement.execute();
            }
        } catch (SQLException e) {
            Logger.severe("Failed to add item to profiles!", e);
        }
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

    private List<String> groupSerialize(List<IProfileGroup> groups) {
        List<String> list = new ArrayList<>();
        for (IProfileGroup group : groups) {
            list.add(group.getUniqueId().toString());
        }
        return list;
    }

    private List<IProfileGroup> groupDeserialize(List<String> groups) {
        List<IProfileGroup> list = new ArrayList<>();
        for (String uuidStr : groups) {
            UUID uuid = UUID.fromString(uuidStr);
            // impl this shit here from persistence manager and what not
        }
        return list;
    }
}
