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
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Clear extends BaseCommands {

  private int amount = 100;

  public Clear(SlashCommandInteractionEvent event) {
    super(event, "Clears a specified number of messages.");
  }

  public Clear() {
    super("Clears a specified number of messages.");
  }

  public void main() {
    this.setAmount(event.getOption("amount").getAsInt());
    List<Message> messages = event.getChannel().getHistory().retrievePast(amount).complete();
    try {
      event.deferReply().queue();
    } catch (Exception e) {
      e.printStackTrace();
    }
    messages.forEach(message -> message.delete().queue());
    event.getHook().sendMessage("Messages cleared.").queue();
  }

  public Collection<OptionData> getOptions() {
    return List.of(
        new OptionData(OptionType.INTEGER, "amount", "amount of messages to clear", true));
  }

  public int getNumberOptions() {
    return 1;
  }

  private void setAmount(int amount) {
    this.amount = amount;
  }
}
