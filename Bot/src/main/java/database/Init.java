/*
 * Mimir
 * Copyright (C) 2026  Faalser
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Code source : https://forgejo.itwontfix.dev/Faalser/Mimir
 * License : https://forgejo.itwontfix.dev/Faalser/Mimir/LICENSE
 * Contact : contact@itwontfix.dev
 */
package database;

import java.sql.*;

public class Init extends DataCommands {

  public Init() {
    super();
  }

  public void Initialize() throws SQLException {
    statement = connection.createStatement();
    query =
        "CREATE TABLE IF NOT EXISTS Users ("
            + "id BIGINT PRIMARY KEY,"
            + "name VARCHAR(255) NOT NULL,"
            + "warn INT DEFAULT 0"
            + ");"
            + "CREATE TABLE IF NOT EXISTS Servers ("
            + "id BIGINT PRIMARY KEY,"
            + "name VARCHAR(255) NOT NULL,"
            + "owners_id BIGINT NOT NULL,"
            + "owners_name VARCHAR(255) NOT NULL,"
            + "FOREIGN KEY (owners_id) REFERENCES Users(id) ON DELETE RESTRICT ON UPDATE CASCADE"
            + ");";
    statement.execute(query);
  }

  public void DropAll() throws SQLException {
    statement = connection.createStatement();
    query = "DROP TABLE IF EXISTS Servers; DROP TABLE IF EXISTS Users";
    statement.execute(query);
  }

  public void InsertServer(Long id, String name, Long owners_id, String owners_name)
      throws SQLException {
    statement = connection.createStatement();
    query =
        "INSERT INTO Servers (id, name, owners_id, owners_name) VALUES ("
            + id
            + ", '"
            + name
            + "', '"
            + owners_id
            + "', '"
            + owners_name
            + "')";
    statement.execute(query);
  }

  public void InsertUser(Long id, String name) throws SQLException {
    statement = connection.createStatement();
    query = "INSERT INTO Users (id, name) VALUES (" + id + ", '" + name + "')";
    statement.execute(query);
  }
}
