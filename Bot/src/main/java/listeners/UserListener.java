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
package listeners;

import database.Request;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserListener extends ListenerAdapter {

  private HashMap<Long, Integer> userNbMessage = new HashMap<>();
  private HashMap<Long, Long> userVoiceTime = new HashMap<>();
  private HashMap<Long, Long> userVoiceTimeStart = new HashMap<>();

  public void updateAll() {
    Request request = new Request();
    Set<Long> userIDs = this.userNbMessage.keySet();
    for (Long userID : userIDs) // Update the number of messages sent by each user
    {
      request.addExp("nbMessage", userID, this.userNbMessage.get(userID));
      request.updateLevel(userID);
    }
    this.userNbMessage.clear();
    userIDs = this.userVoiceTime.keySet();
    for (Long userID : userIDs) // Update the number of seconds spent in voice chat by each user
    {
      request.addExp("timeVoice", userID, this.userVoiceTime.get(userID) / 1000);
      request.updateLevel(userID);
    }
    this.userVoiceTime.clear();
    System.out.println("Update...");
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    if (event.getAuthor().isBot()) return; // Ignore all bot messages
    if (!event.isFromGuild()) return; // Ignore all MPs

    long userID = event.getAuthor().getIdLong();
    if (this.userNbMessage.containsKey(userID)) {
      this.userNbMessage.put(userID, this.userNbMessage.get(userID) + 1);
    } else {
      this.userNbMessage.put(userID, 1);
    }
  }

  @Override
  public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
    if (event.getMember().getUser().isBot()) return; // Ignore all bot

    Long userID = event.getMember().getIdLong();
    if (event.getChannelJoined() != null) {
      this.userVoiceTimeStart.put(userID, new Timestamp(System.currentTimeMillis()).getTime());
    } else if (event.getChannelJoined() == null) {
      if (this.userVoiceTimeStart.containsKey(userID)) {
        Long time =
            new Timestamp(System.currentTimeMillis()).getTime()
                - this.userVoiceTimeStart.get(userID);
        if (this.userVoiceTime.containsKey(userID)) {
          this.userVoiceTime.put(userID, this.userVoiceTime.get(userID) + time);
        } else {
          this.userVoiceTime.put(userID, time);
        }
      }
      this.userVoiceTimeStart.remove(userID);
    }
  }
}
