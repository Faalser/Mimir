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

import database.Request;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Stat extends BaseCommands {

  public Stat(SlashCommandInteractionEvent event) {
    super(event, "Shows the stats of the user.");
  }

  public Stat() {
    super("Shows the stats of the user.");
  }

  public void main() {
    Request request = new Request();
    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(event.getUser().getEffectiveName().hashCode());
    embed.setTitle(event.getUser().getEffectiveName() + " stats");
    embed.setThumbnail(event.getUser().getAvatarUrl());
    embed.addField("Level", String.valueOf(request.get("level", this.userID)), true);
    embed.addField("Exp", String.valueOf(request.get("exp", this.userID)), true);
    embed.addField("NbMessage", String.valueOf(request.get("nbMessage", this.userID)), false);
    embed.addField("TimeVoice", String.valueOf(request.get("timeVoice", this.userID)), false);
    event.replyEmbeds(embed.build()).queue();
  }
}
