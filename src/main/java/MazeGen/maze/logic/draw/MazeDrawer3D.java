package MazeGen.maze.logic.draw;

import MazeGen.maze.data.Cell3D;
import MazeGen.util.WorldUtils;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.List;

public class MazeDrawer3D implements IMazeDrawer
{
    private final int wallHeight, width;
    private final Cell3D[][][] maze;

    public MazeDrawer3D(Cell3D[][][] maze)
    {
        this(maze, 3, 3);
    }

    public MazeDrawer3D(Cell3D[][][] maze, int width, int wallHeight)
    {
        this.maze = maze;
        this.width = width + 1;
        this.wallHeight = wallHeight;
    }

    @Override
    public void drawMaze(int x, int y, int z, World world)
    {
        for (List<int[]> lines : Cell3D.getLines(maze))
        {
            for (int[] line : lines)
            {
                if (line[0] == line[3] && line[2] == line[5])
                {
                    for (int offsetX = 0; offsetX < width+1; offsetX++)
                        for (int offsetZ = 0; offsetZ < width+1; offsetZ++)
                            WorldUtils.drawLine(x + line[0] * width + offsetX, y + line[1] * wallHeight, z + line[2] * width + offsetZ, x + line[3] * width + offsetX, y + line[4] * wallHeight, z + line[5] * width + offsetZ, world, Blocks.diamond_block);
                }
                else
                {
                    WorldUtils.drawWall(x + line[0] * width, y + line[1] * wallHeight, z + line[2] * width, x + line[3] * width, y + line[4] * wallHeight, z + line[5] * width, wallHeight+1, world, Blocks.diamond_block);
                }
            }
        }
    }
}
