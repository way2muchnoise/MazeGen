package MazeGen.maze.data;

import java.util.ArrayList;
import java.util.List;

public class Cell
{
    private boolean visited;
    private int[] location;
    private boolean[] holes; //W,N,E,S
    private Cell parent;
    private int distanceG;
    private int distanceH;

    public boolean visited()
    {
        return this.visited;
    }

    public int[] location()
    {
        return this.location;
    }

    public boolean west()
    {
        return this.holes[0];
    }

    public boolean north()
    {
        return this.holes[1];
    }

    public boolean east()
    {
        return this.holes[2];
    }

    public boolean south()
    {
        return this.holes[3];
    }

    public Cell parent()
    {
        return this.parent;
    }

    public int distanceG()
    {
        return this.distanceG;
    }

    public int distanceH()
    {
        return this.distanceH;
    }

    public int distanceF()
    {
        return (this.distanceH + this.distanceG);
    }

    public void visited(boolean visited)
    {
        this.visited = visited;
    }

    public void location(int[] location)
    {
        this.location = location;
    }

    public void parent(Cell parent)
    {
        this.parent = parent;
    }

    public void distanceG(int distanceG)
    {
        this.distanceG = distanceG;
    }

    public void distanceH(int distanceH)
    {
        this.distanceH = distanceH;
    }

    public Cell(int x, int y)
    {
        this.visited = false;
        this.location = new int[]{x, y};
        this.holes = new boolean[4];
        //default values for solving
        this.parent = null;
        this.distanceH = -1;
        this.distanceG = 0;
    }


    /**
     * Calculates H to a given cell
     *
     * @param end the destination
     */
    public void calcH(Cell end)
    {
        double eX = end.location[0];
        double eY = end.location[1];
        double cX = this.location[0];
        double cY = this.location[1];
        this.distanceH = (int) Math.sqrt(Math.pow(eX - cX, 2) + Math.pow(eY - cY, 2));
    }

    /**
     * makes a hole in the walls between this cell and the next
     *
     * @param next the next cell
     */
    public void open(Cell next)
    {
        if (this.location[0] < next.location[0])
        {
            this.holes[2] = true;
            next.holes[0] = true;
        } else if (this.location[0] > next.location[0])
        {
            this.holes[0] = true;
            next.holes[2] = true;
        } else if (this.location[1] < next.location[1])
        {
            this.holes[3] = true;
            next.holes[1] = true;
        } else if (this.location[1] > next.location[1])
        {
            this.holes[1] = true;
            next.holes[3] = true;
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
            int[] line = {this.location[0], this.location[1], this.location[0], this.location[1] + 1};
            lines.add(line);
        }
        if (!north())
        {
            int[] line = {this.location[0], this.location[1], this.location[0] + 1, this.location[1]};
            lines.add(line);
        }
        if (!east())
        {
            int[] line = {this.location[0] + 1, this.location[1], this.location[0] + 1, this.location[1] + 1};
            lines.add(line);
        }
        if (!south())
        {
            int[] line = {this.location[0], this.location[1] + 1, this.location[0] + 1, this.location[1] + 1};
            lines.add(line);
        }
        return lines;
    }

    /**
     * gets a list of all parents of a cell
     *
     * @return list of the parents
     */
    public List<Cell> getParents()
    {
        List<Cell> parents = new ArrayList<Cell>();
        Cell cell = this;
        do
        {
            parents.add(cell.parent);
            cell = cell.parent;
        } while (cell != null);
        return parents;
    }

    /**
     * Gets the lines for drawing the maze
     *
     * @param maze the maze to render
     * @return a list of a list with the walls for each cells
     */
    public static List<List<int[]>> getLines(Cell[][] maze)
    {
        List<List<int[]>> lines = new ArrayList<List<int[]>>();
        for (Cell[] column : maze)
            for (Cell cell : column)
                lines.add(cell.renderLines());
        return lines;
    }
}
