package MazeGen.maze.logic;

import MazeGen.maze.data.Cell;
import MazeGen.util.WorldUtils;
import net.minecraft.world.World;

import java.util.List;

public class MazeDrawer
{
    private final int wallWidth, width;
    private final Cell[][] maze;

    public MazeDrawer(Cell[][] maze)
    {
        this(maze, 2, 1);
    }

    public MazeDrawer(Cell[][] maze,int width, int wallWidth)
    {
        this.maze = maze;
        this.width = width;
        this.wallWidth = wallWidth;
    }

    public void drawMaze(int x, int y, int z, World world)
    {
        for (List<int[]> lines : Cell.getLines(maze))
        {
            for (int[] line : lines)
            {
                WorldUtils.drawLine(x + line[0]*width, y, z + line[1]*width , x + line[2]*width, y , z + line[3]*width, world);
            }
        }
    }
}
