package MazeGen.maze.data;

import java.util.ArrayList;
import java.util.List;

public class Cell3D extends Cell2D
{
    private int z;
    private boolean up, down;

    public int z()
    {
        return this.z;
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
        this.z = z;
        this.up = false;
        this.down = false;
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
        double eZ = end.z();
        double cX = this.location()[0];
        double cY = this.location()[1];
        double cZ = this.z;
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
        if (this.z > next.z)
        {
            this.down = true;
            next.up = true;
        }
        else if (next.z > this.z)
        {
            this.up = true;
            next.down = true;
        }
    }

    /**
     * renders the walls for a given cell
     *
     * @return a list with arrays for each wall
     */
    public List<int[]> renderLines()
    {
        List<int[]> lines = new ArrayList<int[]>();
        if (!west())
        {
            int[] line = {this.location()[0], this.location()[1], z, this.location()[0], this.location()[1] + 1, z};
            lines.add(line);
        }
        if (!north())
        {
            int[] line = {this.location()[0], this.location()[1], z, this.location()[0] + 1, this.location()[1], z};
            lines.add(line);
        }
        if (!east())
        {
            int[] line = {this.location()[0] + 1, this.location()[1], z, this.location()[0] + 1, this.location()[1] + 1, z};
            lines.add(line);
        }
        if (!south())
        {
            int[] line = {this.location()[0], this.location()[1] + 1, z, this.location()[0] + 1, this.location()[1] + 1, z};
            lines.add(line);
        }
        if (!up())
        {
            int[] line = {this.location()[0], this.location()[1] + 1, z+1, this.location()[0] + 1, this.location()[1] + 1, z+1};
            lines.add(line);
        }

        if (!down())
        {
            int[] line = {this.location()[0], this.location()[1] + 1, z-1, this.location()[0] + 1, this.location()[1] + 1, z-1};
            lines.add(line);
        }
        return lines;
    }
}
