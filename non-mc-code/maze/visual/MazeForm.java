package maze.visual;

import MazeGen.maze.logic.gen.MazeGenerator2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MazeForm implements Runnable
{
    private JTextField widthText;
    private JTextField heightText;
    private JPanel drawBox;
    private JButton drawBtn;
    private JPanel mazeForm;
    private JButton solveBtn;
    private Random r;

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new MazeForm());
    }

    public static void create(MazeGenerator2D generator)
    {
        SwingUtilities.invokeLater(new MazeForm(generator));
    }

    public static void create(int width, int height)
    {
        SwingUtilities.invokeLater(new MazeForm(width, height));
    }


    @Override
    public void run()
    {
        JFrame frame = new JFrame("MazeGen");
        frame.setContentPane(this.mazeForm);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void initComponents()
    {
        drawBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    gen(Integer.parseInt(widthText.getText()), Integer.parseInt(heightText.getText()));
                } catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(mazeForm, "Please fill in numbers", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        solveBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((MazePanel2D) drawBox).solve();
                drawBox.repaint();
            }
        });
        r = new Random();
    }

    public MazeForm()
    {
        initComponents();
        widthText.setText("10");
        heightText.setText("10");
        gen(10, 10);
    }

    public MazeForm(int width, int height)
    {
        initComponents();
        widthText.setText(String.valueOf(width));
        heightText.setText(String.valueOf(height));
        gen(width, height);
    }

    public MazeForm(MazeGenerator2D generator)
    {
        initComponents();
        widthText.setText(String.valueOf(generator.width()));
        heightText.setText(String.valueOf(generator.height()));
        ((MazePanel2D) drawBox).setGenerator(generator);
        drawBox.repaint();
    }

    /*
    * Starts gen for a new maze
    *
    * @param width
    * @param height
    */
    private void gen(int width, int height)
    {
        long time = System.currentTimeMillis();
        MazeGenerator2D generator = new MazeGenerator2D(width, height, r.nextInt(width-1)+1, r.nextInt(height-1)+1);
        generator.gen();
        ((MazePanel2D) drawBox).setGenerator(generator);
        drawBox.repaint();
        JOptionPane.showMessageDialog(mazeForm, "Maze took " + (System.currentTimeMillis() - time) + "ms to gen", "TIMER", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createUIComponents()
    {
        drawBox = new MazePanel2D();
    }
}
