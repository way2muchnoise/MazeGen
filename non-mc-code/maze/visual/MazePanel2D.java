package maze.visual;

import MazeGen.maze.data.Cell2D;
import MazeGen.maze.logic.gen.MazeGenerator2D;
import MazeGen.maze.logic.solve.MazeSolver2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel2D extends JPanel
{
    private MazeGenerator2D generator;
    private MazeSolver2D solver;
    private static final int multiplier = 30;

    public void setGenerator(MazeGenerator2D generator)
    {
        this.generator = generator;
        solver = null;
    }

    public void solve()
    {
        long time = System.currentTimeMillis();
        solver = new MazeSolver2D(generator.maze(), generator.endRow(), generator.endColumn());
        solver.solve();
        JOptionPane.showMessageDialog(this, "Maze took " + (System.currentTimeMillis() - time) + "ms to solve", "TIMER", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (generator != null)
        {
            //maze lines
            for (List<int[]> lines : Cell2D.getLines(generator.maze()))
            {
                for (int[] line : lines)
                {
                    g.drawLine(line[0] * multiplier, line[1] * multiplier, line[2] * multiplier, line[3] * multiplier);
                }
            }
            //start cell
            g.setColor(Color.GREEN);
            g.fillRect(multiplier/10, multiplier/10, multiplier - multiplier/5, multiplier - multiplier/5);
            //end cell
            g.setColor(Color.RED);
            g.fillRect((int) ((this.generator.endRow() + 0.1f) * multiplier), (int) ((this.generator.endColumn() + 0.1f) * multiplier), multiplier - multiplier/5, multiplier - multiplier/5);
            //when maze is solved draw it
            if (solver != null)
            {
                int i = 1;
                for (int[] square : solver.path()) {
                    g.setColor(Color.blue);
                    g.fillRect((int) ((square[0] + 0.2f) * multiplier), (int) ((square[1] + 0.2f) * multiplier), multiplier - 12, multiplier - 12);
                    g.setColor(Color.WHITE);
                    g.drawString(String.valueOf(i), (int) ((square[0] + 0.25f) * multiplier), (int) ((square[1] + 0.6f) * multiplier));
                    i++;
                }
            }
        }
    }
}
