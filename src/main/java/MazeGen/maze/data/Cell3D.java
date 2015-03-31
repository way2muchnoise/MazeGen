package MazeGen.maze.data;

import java.util.ArrayList;
import java.util.List;

public class Cell3D extends Cell2D
{
    private int y;
    private boolean up, down;

    public int y()
    {
        return this.y;
    }

    public boolean up()
    {
        return this.up;
    }

    public boolean down()
    {
        return this.down;
    }

    public Cell3D(int x, int y, int z)
    {
        super(x, y);
        this.y = z;
    }

    /**
     * Calculates H to a given 3d cell
     *
     * @param end the destination
     */
    public void calcH(Cell3D end)
    {
        double eX = end.location()[0];
        double eY = end.location()[1];
        double eZ = end.y();
        double cX = this.location()[0];
        double cY = this.location()[1];
        double cZ = this.y;
        this.distanceH((int) Math.sqrt(Math.pow(eX - cX, 2) + Math.pow(eY - cY, 2) + Math.pow(eZ - cZ, 2)));
    }

    /**
     * makes a hole in the walls between this cell and the next
     *
     * @param next the next cell
     */
    public void open(Cell3D next)
    {
        super.open(next);
        if (this.y > next.y)
        {
            this.down = true;
            next.up = true;
        }
        else if (next.y > this.y)
        {
            this.up = true;
            next.down = true;
        }
    }

    /**
     * renders the walls for a given cell
     *
     * @param top the value of y at the top cell
     * @return a list with arrays for each wall
     */
    public List<int[]> renderLines(int top)
    {
        List<int[]> lines = new ArrayList<int[]>();
        if (!west())
        {
            int[] line = {this.location()[0], y, this.location()[1], this.location()[0], y, this.location()[1] + 1};
            lines.add(line);
        }
        if (!north())
        {
            int[] line = {this.location()[0], y, this.location()[1], this.location()[0] + 1, y, this.location()[1]};
            lines.add(line);
        }
        if (!east())
        {
            int[] line = {this.location()[0] + 1, y, this.location()[1], this.location()[0] + 1, y, this.location()[1] + 1};
            lines.add(line);
        }
        if (!south())
        {
            int[] line = {this.location()[0], y, this.location()[1] + 1, this.location()[0] + 1, y, this.location()[1] + 1};
            lines.add(line);
        }
        if (!up())
        {
            int[] line = {this.location()[0], y + 1, this.location()[1], this.location()[0], y + 1, this.location()[1]};
            lines.add(line);
        }
        if (!down())
        {
            int[] line = {this.location()[0], y-1 , this.location()[1], this.location()[0], y - 1, this.location()[1]};
            lines.add(line);
        }
        return lines;
    }

    /**
     * Gets the lines for drawing the maze
     *
     * @param maze the maze to render
     * @return a list of a list with the walls for each cells
     */
    public static List<List<int[]>> getLines(Cell3D[][][] maze)
    {
        List<List<int[]>> lines = new ArrayList<List<int[]>>();
        for (Cell3D[][] plane : maze)
            for (Cell3D[] column : plane)
                for(Cell3D cell : column)
                    lines.add(cell.renderLines(maze[0].length));
        return lines;
    }
}
