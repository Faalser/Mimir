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

public class Request extends DataCommands {

  public Request() {
    super();
  }

  public int get(String column, Long id) {
    try {
      Statement statement = this.connection.createStatement();
      String query = "SELECT " + column + " FROM users " + "WHERE id = " + id + ";";
      ResultSet result = statement.executeQuery(query);
      return result.next() ? result.getInt(column) : -1;
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  public void updateLevel(Long id) {
    try {
      Statement statement = this.connection.createStatement();
      int exp = (this.get("nbMessage", id) * 10) + (this.get("timeVoice", id) * 5);
      int level = (int) Math.sqrt(exp / 1000.0);
      String query =
          "UPDATE users SET exp = " + exp + ", level = " + level + " WHERE id = " + id + ";";
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addExp(String column, Long id, Long value) {
    try {
      Statement statement = this.connection.createStatement();
      String query =
          "UPDATE users SET " + column + " = " + column + " + " + value + " WHERE id = " + id + ";";
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void addExp(String column, Long id, int value) {
    this.addExp(column, id, (long) value);
  }
}
