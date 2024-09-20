package info.preva1l.fadlc.persistence.daos;

import com.zaxxer.hikari.HikariDataSource;
import info.preva1l.fadlc.models.MessageLocation;
import info.preva1l.fadlc.models.user.BukkitUser;
import info.preva1l.fadlc.models.user.OnlineUser;
import info.preva1l.fadlc.persistence.Dao;
import info.preva1l.fadlc.utils.Logger;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserDao implements Dao<OnlineUser> {
    private final HikariDataSource dataSource;

    /**
     * Get an object from the database by its id.
     *
     * @param id the id of the object to get.
     * @return an optional containing the object if it exists, or an empty optional if it does not.
     */
    @Override
    public Optional<OnlineUser> get(UUID id) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT  `uniqueId`, `username`, `availableChunks`, `showBorders`, `showEnterMessages`, `showLeaveMessages`, `messageLocation`
                    FROM `users`
                    WHERE `uniqueId`=?;""")) {
                statement.setString(1, id.toString());
                final ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    final UUID ownerUUID = id;
                    final String ownerName = resultSet.getString("username");
                    final int availableChunks = resultSet.getInt("availableChunks");
                    final boolean showBorders = resultSet.getBoolean("showBorders");
                    final boolean enterMessages = resultSet.getBoolean("showEnterMessages");
                    final boolean leaveMessages = resultSet.getBoolean("showLeaveMessages");
                    final MessageLocation messageLocation = MessageLocation.valueOf(resultSet.getString("messageLocation"));
                    return Optional.of(new BukkitUser(ownerName, ownerUUID, Bukkit.getPlayer(ownerUUID),
                            availableChunks, showBorders, enterMessages, leaveMessages, messageLocation));
                }
            }
        } catch (SQLException e) {
            Logger.severe("Failed to get user!", e);
        }
        return Optional.empty();
    }

    /**
     * Get all objects of type T from the database.
     *
     * @return a list of all objects of type T in the database.
     */
    @Override
    public List<OnlineUser> getAll() {
        return List.of();
    }

    /**
     * Save an object of type T to the database.
     *
     * @param onlineUser the object to save.
     */
    @Override
    public void save(OnlineUser onlineUser) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO `users`
                    (`uniqueId`, `username`, `availableChunks`, `showBorders`, `showEnterMessages`, `showLeaveMessages`, `messageLocation`)
                    VALUES (?,?,?,?,?,?,?)
                    ON CONFLICT(`uniqueId`) DO UPDATE SET
                            `username` = excluded.`username`,
                            `availableChunks` = excluded.`availableChunks`;""")) {
                statement.setString(1, onlineUser.getUniqueId().toString());
                statement.setString(2, onlineUser.getName());
                statement.setInt(3, onlineUser.getAvailableChunks());
                statement.setBoolean(4, onlineUser.isViewBorders());
                statement.setBoolean(5, onlineUser.isShowEnterMessages());
                statement.setBoolean(6, onlineUser.isShowLeaveMessages());
                statement.setString(7, onlineUser.getMessageLocation().name());
                statement.execute();
            }
        } catch (SQLException e) {
            Logger.severe("Failed to add item to users!", e);
        }
    }

    /**
     * Update an object of type T in the database.
     *
     * @param onlineUser the object to update.
     * @param params     the parameters to update the object with.
     */
    @Override
    public void update(OnlineUser onlineUser, String[] params) {

    }

    /**
     * Delete an object of type T from the database.
     *
     * @param onlineUser the object to delete.
     */
    @Override
    public void delete(OnlineUser onlineUser) {

    }
}
