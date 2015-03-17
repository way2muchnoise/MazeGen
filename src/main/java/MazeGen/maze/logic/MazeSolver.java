package MazeGen.maze.logic;

import MazeGen.maze.data.Cell;

import java.util.ArrayList;
import java.util.List;

public class MazeSolver
{
    private List<Cell> open;
    private List<Cell> closed;
    private Cell[][] maze;
    private Cell end;

    public MazeSolver(Cell[][] maze)
    {
        open = new ArrayList<Cell>();
        closed = new ArrayList<Cell>();
        this.maze = maze;
        this.end = maze[maze.length - 1][maze[0].length - 1];
    }

    /*
    * Main solve loop
    */
    public void solve()
    {
        open.add(maze[0][0]);
        while (true)
        {
            Cell current = lowestFCostCell();
            open.remove(current);
            closed.add(current);
            int x = current.location()[0];
            int y = current.location()[1];
            if (current.holes()[0])
            {
                ListCheck(current, maze[x - 1][y]);
            }
            if (current.holes()[1])
            {
                ListCheck(current, maze[x][y - 1]);
            }
            if (current.holes()[2])
            {
                ListCheck(current, maze[x + 1][y]);
            }
            if (current.holes()[3])
            {
                ListCheck(current, maze[x][y + 1]);
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

    private boolean openContains(Cell find)
    {
        for (Cell cell : open)
            if (cell == find) return true;
        return false;
    }

    private boolean closedContains(Cell find)
    {
        for (Cell cell : closed)
            if (cell == find) return true;
        return false;
    }

    /**
     * Checks if next cell is in a list and adds if needed
     *
     * @param current the current Cell
     * @param next    the next Cell
     */
    private void ListCheck(Cell current, Cell next)
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
     * Calculates the distance of a cell to the end cell
     *
     * @param cell the origin Cell
     */
    private void CalculateDistanceToEnd(Cell cell)
    {
        double mX = maze.length;
        double mY = maze[0].length;
        double cX = cell.location()[0];
        double cY = cell.location()[1];
        double distanceH = Math.sqrt(Math.pow(mX - cX, 2) + Math.pow(mY - cY, 2));
        cell.distanceH((int) distanceH);
    }

    /**
     * Finds cell with lowest Fcost in the open list,
     * calcs the Hcost when not yet there
     *
     * @return the cell from the list
     */
    private Cell lowestFCostCell()
    {
        Cell current = open.get(0);
        for (Cell cell : open)
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
        List<Cell> parents = this.end.getParents();//gets parents from end to start
        parents.remove(null);//remove the last super parent null
        // reverse it so its from start to end
        List<int[]> path = new ArrayList<int[]>();
        for (int i = parents.size() - 1; i > -1; i--)
        {
            Cell parent = parents.get(i);
            int[] cell = new int[]{parent.location()[0], parent.location()[1]};
            path.add(cell);
        }
        return path;
    }
}
