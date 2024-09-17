package info.preva1l.fadlc.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum DatabaseType {
    SQLITE("sqlite", "SQLite", "info.preva1l.fadfc.shaded.sqlite.jdbc.Driver"),
    MYSQL("mysql", "MySQL", "info.preva1l.fadfc.shaded.mysql.jdbc.Driver"),
    MARIADB("mariadb", "MariaDB", "info.preva1l.fadfc.shaded.mariadb.jdbc.Driver"),
    MONGO("mongodb", "MongoDB"),
    ;

    private final String id;
    private final String friendlyName;
    private String driverClass;
}