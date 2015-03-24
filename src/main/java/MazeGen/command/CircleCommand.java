package MazeGen.command;

import MazeGen.util.WorldUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

public class CircleCommand extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "drawCircle";
    }

    @Override
    public List getCommandAliases()
    {
        List<String> names = new ArrayList<String>();
        names.add("circle");
        return names;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "drawCircle <radius> [<height>]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length != 1 && args.length != 2)
        {
            throw new SyntaxErrorException();
        }
        try
        {
            int h = 1;
            int r = Integer.parseInt(args[0]);
            if (args.length == 2)
            {
                h = Integer.parseInt(args[1]);
            }
            WorldUtils.drawCircleWall(sender.getPlayerCoordinates().posX, sender.getPlayerCoordinates().posY, sender.getPlayerCoordinates().posZ, r, h, sender.getEntityWorld(), Blocks.diamond_block);

        } catch (NumberFormatException ex)
        {
            throw new SyntaxErrorException();
        }
    }
}
