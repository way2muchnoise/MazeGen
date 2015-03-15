package visual;

import logic.MazeGenerator;
import logic.MazeSolver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MazePanel extends JPanel
{
    private MazeGenerator generator;
    private MazeSolver solver;
    private static final int multiplier = 30;

    public void setGenerator(MazeGenerator generator)
    {
        this.generator = generator;
        solver = null;
    }

    public void solve()
    {
        solver = new MazeSolver(generator.maze());
        solver.solve();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (generator != null)
        {
            //maze lines
            for (List<int[]> lines : generator.getLines())
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
            g.fillRect((int) ((this.generator.width() - 0.9f) * multiplier), (int) ((this.generator.height() - 0.9f) * multiplier), multiplier - multiplier/5, multiplier - multiplier/5);
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
