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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Say extends BaseCommands {

  public Say(SlashCommandInteractionEvent event) {
    super(event, "Says a specified message.");
  }

  public Say() {
    super("Says a specified message.");
  }

  public void main() {
    this.event.reply(Objects.requireNonNull(event.getOption("content")).getAsString()).queue();
  }

  public Collection<OptionData> getOptions() {
    return List.of(new OptionData(OptionType.STRING, "content", "something the bot will say"));
  }

  public int getNumberOptions() {
    return 1;
  }
}
