package MazeGen.maze.logic.gen;

import MazeGen.maze.data.Cell3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator3D implements IMazeGenerator
{
    private Cell3D[][][] maze;
    private int endX, endY, endZ;

    public Cell3D[][][] maze()
    {
        return this.maze;
    }

    public int width()
    {
        return this.maze.length;
    }

    public int height()
    {
        return this.maze[0].length;
    }

    public int depth()
    {
        return this.maze[0][0].length;
    }

    public int endX()
    {
        return this.endX;
    }

    public int endY()
    {
        return this.endY;
    }

    public int endZ()
    {
        return this.endZ;
    }

    public MazeGenerator3D(int width, int height, int depth, int endX, int endY, int endZ)
    {
        this.maze = new Cell3D[width][height][depth];
        for(int i = 0; i < width; i++)
        {
            for (int ii = 0; ii < height; ii++)
            {
                for (int iii = 0; iii < depth; iii++)
                {
                    this.maze[i][ii][iii] = new Cell3D(i, ii, iii);
                }
            }
        }
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
    }

    public MazeGenerator3D(int width, int height, int depth)
    {
        this(width, height, depth, width-1, height-1, depth-1);
    }

    /**
     * Main gen loop
     */
    @Override
    public void gen()
    {
        Stack<Cell3D> stack = new Stack<Cell3D>();
        Cell3D current = this.maze[0][0][0];
        while (true)
        {
            Cell3D next = getRandomUnvisitedNeighbour(current, r);
            if (next != null)
            {
                current.open(next);
                stack.push(current);
                current = next;
                current.visited(true);
            } else
            {
                if (stack.size() == 0)
                {
                    break;
                }
                current = stack.pop();
            }
        }
    }

    /**
     * Finds a random unvisited neighbours
     *
     * @param current the current Cell
     * @param r       a random
     *
     * @return a random unvisited neighbour cell, returns null when there are non left
     */
    private Cell3D getRandomUnvisitedNeighbour(Cell3D current, Random r)
    {
        int[] location = current.location();
        int y = current.y();
        List<Cell3D> neighbours = new ArrayList<Cell3D>();
        //add cells that are neighbours
        if (location[0] - 1 > -1)
        {
            neighbours.add(this.maze[location[0] - 1][y][location[1]]);
        }
        if (location[0] + 1 < this.maze.length)
        {
            neighbours.add(this.maze[location[0] + 1][y][location[1]]);
        }
        if (location[1] - 1 > -1)
        {
            neighbours.add(this.maze[location[0]][y][location[1] - 1]);
        }
        if (location[1] + 1 < this.maze[0].length)
        {
            neighbours.add(this.maze[location[0]][y][location[1] + 1]);
        }

        if (y + 1 < this.maze[0][0].length)
        {
            neighbours.add(this.maze[location[0]][y + 1][location[1]]);
        }
        if (y - 1 > -1)
        {
            neighbours.add(this.maze[location[0]][y - 1][location[1]]);
        }
        //remove all visited cells
        neighbours = removeAllVisited(neighbours);
        //select a random cell
        if (neighbours.size() > 0)
        {
            int index = r.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        //returns null when no more unvisited neighbours
        return null;
    }

    /**
     * Remove all visited cells from given list
     *
     * @param cells list to check
     * @return list with only unvisited cells
     */
    private static List<Cell3D> removeAllVisited(List<Cell3D> cells)
    {
        List<Cell3D> unvisited = new ArrayList<Cell3D>();
        for (Cell3D cell : cells)
            if (!cell.visited()) unvisited.add(cell);
        return unvisited;
    }
}
