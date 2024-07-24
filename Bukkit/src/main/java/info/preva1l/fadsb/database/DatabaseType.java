package info.preva1l.fadsb.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DatabaseType {
    SQLITE("sqlite", "SQLite"),
    MYSQL("mysql", "MySQL"),
    MARIADB("mariadb", "MariaDB"),
    POSTGRESQL("postgres", "PostgreSQL"),
    MONGODB("mongodb", "MongoDB"),
    ;
    private final String id;
    private final String friendlyName;
}
