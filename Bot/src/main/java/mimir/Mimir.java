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
package mimir;

import commands.*;
import database.Init;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.SQLException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import registry.CommandRegistry;

public class Mimir {

  private static final String TOKEN =
      (Dotenv.load()).get("TOKEN_BOT_DISCORD"); // Load the bot token from the environment
  private static JDA jda;
  private static UserListener userListener = new UserListener();
  private static final Collection<GatewayIntent> intents =
      EnumSet.of(
          GatewayIntent.GUILD_MESSAGES,
          GatewayIntent.GUILD_MEMBERS,
          GatewayIntent.GUILD_PRESENCES,
          GatewayIntent.GUILD_EXPRESSIONS,
          GatewayIntent.GUILD_VOICE_STATES,
          GatewayIntent.DIRECT_MESSAGES,
          GatewayIntent.MESSAGE_CONTENT,
          GatewayIntent.GUILD_MESSAGE_REACTIONS,
          GatewayIntent.DIRECT_MESSAGE_REACTIONS,
          GatewayIntent.SCHEDULED_EVENTS);
  private static final CommandRegistry registry =
      new CommandRegistry() // Create the command registry
          .register(new Clear(), Clear::new)
          .register(new Help(), Help::new)
          .register(new Ping(), Ping::new)
          .register(new Roll(), Roll::new)
          .register(new Say(), Say::new)
          .register(new Stat(), Stat::new);

  public CommandRegistry getRegistry() {
    return registry;
  }

  void main(String[] args) throws InterruptedException {

    // JDA instance
    jda =
        JDABuilder.create(TOKEN, intents) // Create the JDA instance
            .setActivity(Activity.customStatus("https://discord.gg/tkHVfSbMWp"))
            .setStatus(OnlineStatus.IDLE)
            .enableCache(CacheFlag.VOICE_STATE)
            .addEventListeners(new CommandListener(registry))
            .addEventListeners(userListener)
            .build();

    jda.updateCommands().addCommands(registry.toDiscordData()).queue(); // Update the bot's commands

    jda.awaitReady(); // Wait for the bot to be ready

    // SQL requests
    try {
      Init init = new Init();
      init.DropAll(); // Drop all tables, I only use this for development
      init.Initialize(); // Initialize the database
      List<User> users = jda.getUsers();
      for (User user : users) // Insert each user into the database
      {
        if (!user.isBot()) {
          init.InsertUser(user.getIdLong(), user.getName());
        }
      }
      List<Guild> guilds = jda.getGuilds();
      for (Guild guild : guilds) // Insert each guild into the database
      {
        init.InsertServer(
            guild.getIdLong(),
            guild.getName(),
            guild.getOwnerIdLong(),
            guild.getOwner().getUser().getName());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Update the database every 10 minutes
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(userListener::updateAll, 1, 1, TimeUnit.MINUTES);

    // Update the bot's presence every minute
    ScheduledExecutorService scheduler2 = Executors.newSingleThreadScheduledExecutor();
    scheduler2.scheduleAtFixedRate(this::updatePresence, 1, 1, TimeUnit.MINUTES);
  }

  public void updatePresence() {
    if (jda.getPresence().getActivity() == Activity.customStatus("https://discord.gg/tkHVfSbMWp")) {
      jda.getPresence()
          .setActivity(
              Activity.customStatus(
                  jda.getGuilds().size()
                      + " servers, "
                      + jda.getGuilds().stream().mapToInt(g -> g.getMemberCount()).sum()
                      + " members"));
    } else {
      jda.getPresence().setActivity(Activity.customStatus("https://discord.gg/tkHVfSbMWp"));
    }
  }
}
