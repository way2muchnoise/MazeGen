package MazeGen.maze.logic;

import MazeGen.maze.data.Cell2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator2D
{
    private Cell2D[][] board;
    private int endRow, endColumn;
    private Random r;

    public Cell2D[][] maze()
    {
        return this.board;
    }

    public int width()
    {
        return this.board.length;
    }

    public int height()
    {
        return this.board[0].length;
    }

    public int endRow()
    {
        return this.endRow;
    }

    public int endColumn()
    {
        return this.endColumn;
    }

    public MazeGenerator2D(int width, int height, int endRow, int endColumn)
    {
        this.board = new Cell2D[width][height];
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                this.board[i][j] = new Cell2D(i, j);
            }
        }
        this.endRow = endRow;
        this.endColumn = endColumn;
        r = new Random();
    }

    public MazeGenerator2D(int width, int height)
    {
        this(width, height, width-1, height-1);
    }

    /**
     * Main gen loop
     */
    public void gen()
    {
        Stack<Cell2D> stack = new Stack<Cell2D>();
        Cell2D current = this.board[0][0];//start cell is top left
        while (true)
        {
            //System.out.println(stack.size()); Gen time drastically increased when uncommented
            Cell2D next = getRandomUnvisitedNeighbour(current, r);
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
    private Cell2D getRandomUnvisitedNeighbour(Cell2D current, Random r)
    {
        int[] location = current.location();
        List<Cell2D> neighbours = new ArrayList<Cell2D>();
        //add cells that are neighbours
        if (location[0] - 1 > -1)
        {
            neighbours.add(this.board[location[0] - 1][location[1]]);
        }
        if (location[0] + 1 < this.board.length)
        {
            neighbours.add(this.board[location[0] + 1][location[1]]);
        }
        if (location[1] - 1 > -1)
        {
            neighbours.add(this.board[location[0]][location[1] - 1]);
        }
        if (location[1] + 1 < this.board[0].length)
        {
            neighbours.add(this.board[location[0]][location[1] + 1]);
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
    private static List<Cell2D> removeAllVisited(List<Cell2D> cells)
    {
        List<Cell2D> unvisited = new ArrayList<Cell2D>();
        for (Cell2D cell : cells)
            if (!cell.visited()) unvisited.add(cell);
        return unvisited;
    }
}
