package me.hsgamer.bettergui.itemlistener;

import java.util.Arrays;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class Command extends BukkitCommand {

  public Command() {
    super("setitemmenu", "Bind an item to a menu", "/setitemmenu <menu>",
        Arrays.asList("itemmenu", "sim"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    return false;
  }
}
