package MazeGen.command;

import MazeGen.maze.logic.MazeDrawer;
import MazeGen.maze.logic.MazeGenerator;
import maze.visual.MazeForm;
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
        return "mazegen <width> <height>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if( args.length != 2)
        {
            throw new SyntaxErrorException();
        }
        int width = parseInt(sender, args[0]);
        int height = parseInt(sender, args[1]);
        MazeGenerator mazeGenerator = new MazeGenerator(width, height);
        mazeGenerator.gen();
        MazeDrawer mazeDrawer = new MazeDrawer(mazeGenerator.maze());
        mazeDrawer.drawMaze(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ, sender.getEntityWorld());
    }
}
