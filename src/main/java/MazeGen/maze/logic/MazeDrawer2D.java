package MazeGen.maze.logic;

import MazeGen.maze.data.Cell2D;
import MazeGen.util.WorldUtils;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.List;

public class MazeDrawer2D
{
    private final int wallHeight, width;
    private final Cell2D[][] maze;

    public MazeDrawer2D(Cell2D[][] maze)
    {
        this(maze, 3, 3);
    }

    public MazeDrawer2D(Cell2D[][] maze, int width, int wallHeight)
    {
        this.maze = maze;
        this.width = width + 1;
        this.wallHeight = wallHeight;
    }

    public void drawMaze(int x, int y, int z, World world)
    {
        for (List<int[]> lines : Cell2D.getLines(maze))
        {
            for (int[] line : lines)
            {
                WorldUtils.drawWall(x + line[0] * width, y, z + line[1] * width, x + line[2] * width, y, z + line[3] * width, wallHeight, world, Blocks.diamond_block);
            }
        }
    }
}
