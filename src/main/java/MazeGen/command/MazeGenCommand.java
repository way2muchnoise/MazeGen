package MazeGen.command;

import MazeGen.maze.logic.draw.MazeDrawer2D;
import MazeGen.maze.logic.draw.MazeDrawer3D;
import MazeGen.maze.logic.gen.MazeGenerator2D;
import MazeGen.maze.logic.gen.MazeGenerator3D;
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
        return "mazegen [<X> <Y> <Z>] <width> [<height>] <depth>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        int x, y, z;
        int width, height, depth;
        if (args.length == 2 || args.length == 5)
        {
            if (args.length == 2)
            {
                x = sender.getPlayerCoordinates().posX;
                y = sender.getPlayerCoordinates().posY;
                z = sender.getPlayerCoordinates().posZ;
                width = parseInt(sender, args[0]);
                depth = parseInt(sender, args[1]);
            } else
            {
                x = parseInt(sender, args[0]);
                y = parseInt(sender, args[1]);
                z = parseInt(sender, args[2]);
                width = parseInt(sender, args[3]);
                depth = parseInt(sender, args[4]);
            }
            MazeGenerator2D mazeGenerator = new MazeGenerator2D(width, depth);
            mazeGenerator.gen();
            MazeDrawer2D mazeDrawer = new MazeDrawer2D(mazeGenerator.maze());
            mazeDrawer.drawMaze(x, y, z, sender.getEntityWorld());
        }
        else if (args.length == 3 || args.length == 6)
        {
            if (args.length == 3)
            {
                x = sender.getPlayerCoordinates().posX;
                y = sender.getPlayerCoordinates().posY;
                z = sender.getPlayerCoordinates().posZ;
                width = parseInt(sender, args[0]);
                height = parseInt(sender, args[1]);
                depth = parseInt(sender, args[2]);
            } else
            {
                x = parseInt(sender, args[0]);
                y = parseInt(sender, args[1]);
                z = parseInt(sender, args[2]);
                width = parseInt(sender, args[3]);
                height = parseInt(sender, args[4]);
                depth = parseInt(sender, args[5]);
            }
            MazeGenerator3D mazeGenerator = new MazeGenerator3D(width, height, depth);
            mazeGenerator.gen();
            MazeDrawer3D mazeDrawer = new MazeDrawer3D(mazeGenerator.maze());
            mazeDrawer.drawMaze(x, y, z, sender.getEntityWorld());
        }
        else
        {
            throw new SyntaxErrorException();
        }


    }
}
