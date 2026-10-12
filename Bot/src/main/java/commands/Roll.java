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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Roll extends BaseCommands {
  private int face;

  public Roll(SlashCommandInteractionEvent event) {
    super(event, "Rolls a specified number of faces on a dice.");
  }

  public Roll() {
    super("Rolls a specified number of faces on a dice.");
  }

  public void main() {
    this.face = Objects.requireNonNull(event.getOption("dice")).getAsInt();
    int roll = new Random().nextInt(face) + 1;
    this.event.reply("You rolled a " + roll).queue();
  }

  public Collection<OptionData> getOptions() {
    return List.of(new OptionData(OptionType.INTEGER, "dice", "the number of faces on the dice"));
  }

  public int getNumberOptions() {
    return 1;
  }
}
