package ru.draimdev.draimmarriage.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import ru.draimdev.draimmarriage.DraimMarriage;

public class MainCommands implements CommandExecutor {
    private DraimMarriage pl;

    public MainCommands(DraimMarriage pl) {
        super();
        this.pl = pl;
    }

    @Override
    public boolean onCommand(final CommandExecutor sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("draimmarriage")) {
            final Player p = (Player)sender;
            if(args.length == 0) {
            }
        }
    }
}
