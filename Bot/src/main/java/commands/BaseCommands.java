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
package commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class BaseCommands {

  protected final SlashCommandInteractionEvent event;
  protected Long userID;
  protected String description;
  protected long gatewayPing;
  protected Date date;
  protected final int id;
  protected static int nextId = 1;

  protected BaseCommands(SlashCommandInteractionEvent event, String description) {
    this.event = event;
    this.userID = this.event != null ? this.event.getUser().getIdLong() : null;
    this.description = description;
    this.gatewayPing = this.event != null ? this.event.getJDA().getGatewayPing() : 0;
    this.date = new Date();
    this.id = nextId++;
  }

  protected BaseCommands(String description) {
    this(null, description);
  }

  protected abstract void main();

  public String getName() {
    return this.getClass().getSimpleName().toLowerCase();
  }

  public String getAuthorName() {
    return this.event.getMember().getEffectiveName();
  }

  public String getAuthorId() {
    return this.event.getMember().getId();
  }

  public String getDescription() {
    return this.description;
  }

  public Collection<OptionData> getOptions() {
    return new ArrayList<>();
  }

  public int getNumberOptions() {
    return 0;
  }

  public void Execute() {
    System.out.println(
        "Executing : "
            + this.getName()
            + " \n  Author : "
            + this.getAuthorName()
            + " ("
            + this.getAuthorId()
            + ")"
            + " \n  Date : "
            + this.date
            + " \n  ID : "
            + this.id
            + " \n  Gateway Ping : "
            + this.gatewayPing
            + "ms");
    Thread t =
        new Thread(this.getClass().getName()) {
          @Override
          public void run() {
            main();
          }
        };
    t.start();
  }
}
