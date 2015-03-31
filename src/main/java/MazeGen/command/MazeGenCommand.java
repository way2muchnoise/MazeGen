package MazeGen.command;

import MazeGen.maze.logic.MazeDrawer2D;
import MazeGen.maze.logic.MazeGenerator2D;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;

import java.util.ArrayList;
import java.util.List;

public class MazeGenCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "mazegen";
    }

    @Override
    public List getCommandAliases()
    {
        List<String> aliases = new ArrayList<String>();
        aliases.add("maze");
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "mazegen [<X> <Y> <Z>] <width> <height>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        int x, y, z;
        int width, height;
        if(args.length == 2)
        {
            x = sender.getPlayerCoordinates().posX;
            y = sender.getPlayerCoordinates().posY;
            z = sender.getPlayerCoordinates().posZ;
            width = parseInt(sender, args[0]);
            height = parseInt(sender, args[1]);
        }
        else if (args.length == 5)
        {
            x = parseInt(sender, args[0]);
            y = parseInt(sender, args[1]);
            z = parseInt(sender, args[2]);
            width = parseInt(sender, args[3]);
            height = parseInt(sender, args[4]);
        }
        else
        {
            throw new SyntaxErrorException();
        }

        MazeGenerator2D mazeGenerator = new MazeGenerator2D(width, height);
        mazeGenerator.gen();
        MazeDrawer2D mazeDrawer = new MazeDrawer2D(mazeGenerator.maze());
        mazeDrawer.drawMaze(x, y, z, sender.getEntityWorld());
    }
}
