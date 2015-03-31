package MazeGen.maze.logic;

import MazeGen.maze.data.Cell2D;

import java.util.ArrayList;
import java.util.List;

public class MazeSolver2D
{
    private List<Cell2D> open;
    private List<Cell2D> closed;
    private Cell2D[][] maze;
    private Cell2D end;

    public MazeSolver2D(Cell2D[][] maze)
    {
        this(maze, maze.length - 1, maze[0].length - 1);
    }

    public MazeSolver2D(Cell2D[][] maze, int endRow, int endColumn)
    {
        open = new ArrayList<Cell2D>();
        closed = new ArrayList<Cell2D>();
        this.maze = maze;
        this.end = maze[endRow][endColumn];
    }

    /*
    * Main solve loop
    */
    public void solve()
    {
        open.add(maze[0][0]);
        while (true)
        {
            Cell2D current = lowestFCostCell();
            open.remove(current);
            closed.add(current);
            int x = current.location()[0];
            int y = current.location()[1];
            if (current.west())
            {
                listCheck(current, maze[x - 1][y]);
            }
            if (current.north())
            {
                listCheck(current, maze[x][y - 1]);
            }
            if (current.east())
            {
                listCheck(current, maze[x + 1][y]);
            }
            if (current.south())
            {
                listCheck(current, maze[x][y + 1]);
            }
            if (closedContains(end))
            {
                break;
            }
            if (open.size() == 0)
            {
                break;
            }
        }
    }

    /**
     * Is cell in the open stack
     *
     * @param find cell to find
     * @return true if in the open stack
     */
    private boolean openContains(Cell2D find)
    {
        for (Cell2D cell : open)
            if (cell == find) return true;
        return false;
    }

    /**
     * Is cell in the closed stack
     *
     * @param find cell to find
     * @return true if in the closed stack
     */
    private boolean closedContains(Cell2D find)
    {
        for (Cell2D cell : closed)
            if (cell == find) return true;
        return false;
    }

    /**
     * Checks if next cell is in a list and adds if needed
     *
     * @param current the current Cell
     * @param next    the next Cell
     */
    private void listCheck(Cell2D current, Cell2D next)
    {
        if (closedContains(next))
        {
            return; //ignore
        } else if (!openContains(next))
        {
            open.add(next);
            next.parent(current);
            next.distanceG(current.distanceG() + 1);
        } else
        {
            if (next.distanceG() > current.distanceG() + 1)
            {
                next.parent(current);
                next.distanceG(current.distanceG() + 1);
            }
        }
    }

    /**
     * Finds cell with lowest Fcost in the open list,
     * calcs the Hcost when not yet there
     *
     * @return the cell from the list
     */
    private Cell2D lowestFCostCell()
    {
        Cell2D current = open.get(0);
        for (Cell2D cell : open)
        {
            if (current.distanceH() == -1)
            {
                current.calcH(end);
            }
            if (current.distanceF() > cell.distanceG())
            {
                current = cell;
            }
        }
        return current;
    }

    /**
     * gives the cell coords for the path
     *
     * @return list of arrays with values
     */
    public List<int[]> path()
    {
        List<Cell2D> parents = this.end.getParents();//gets parents from end to start
        parents.remove(null);//remove the last super parent null
        // reverse it so its from start to end
        List<int[]> path = new ArrayList<int[]>();
        for (int i = parents.size() - 1; i > -1; i--)
        {
            Cell2D parent = parents.get(i);
            int[] cell = new int[]{parent.location()[0], parent.location()[1]};
            path.add(cell);
        }
        return path;
    }
}
