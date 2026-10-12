/*
 * Mimir
 * Copyright (C) 2026  Faalser
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package commands;

import java.awt.Color;
import mimir.Mimir;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import registry.CommandRegistry;

public class Help extends BaseCommands {

  private CommandRegistry registry = new Mimir().getRegistry();

  public Help(SlashCommandInteractionEvent event) {
    super(event, "Displays the help menu.");
  }

  public Help() {
    super("Displays the help menu.");
  }

  public void main() {
    EmbedBuilder text = new EmbedBuilder();
    text.setTitle("Help menu");
    text.setColor(Color.CYAN);
    for (SlashCommandData command : registry.toDiscordData()) {
      text.addField(command.getName(), command.getDescription(), false);
    }
    text.setAuthor("Faalser", "https://forgejo.itwontfix.dev/Faalser");
    event.replyEmbeds(text.build()).queue();
  }
}
