CREATE TABLE IF NOT EXISTS claims
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    ownerUUID     TEXT NOT NULL,
    ownerUsername TEXT NOT NULL,
    profiles      TEXT NOT NULL, -- list of uuids
    chunks        TEXT NOT NULL  -- list of uuids
);

CREATE TABLE IF NOT EXISTS chunks
(
    uniqueId    TEXT    NOT NULL PRIMARY KEY,
    world       TEXT    NOT NULL,
    x           INTEGER NOT NULL,
    z           INTEGER NOT NULL,
    server      TEXT    NOT NULL,
    timeClaimed INTEGER NOT NULL,
    profile     INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS profiles
(
    uuid   TEXT    NOT NULL PRIMARY KEY,
    id     INTEGER NOT NULL,
    name   TEXT    NOT NULL,
    groups TEXT    NOT NULL, -- list of uuids
    flags  TEXT    NOT NULL  -- {"EXPLOSION_DAMAGE": "false", "MOB_GRIEFING": "false"}
);

CREATE TABLE IF NOT EXISTS groups
(
    uuid     TEXT NOT NULL PRIMARY KEY,
    name     TEXT NOT NULL,
    users    TEXT NOT NULL, -- list of users (uuid + username)
    settings TEXT NOT NULL  -- {"PLACE_BLOCKS": "false", "BREAK_BLOCKS": "false"}
);