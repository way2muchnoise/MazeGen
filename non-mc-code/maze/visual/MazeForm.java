package maze.visual;

import MazeGen.maze.logic.MazeGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeForm implements Runnable
{
    private JTextField widthText;
    private JTextField heightText;
    private JPanel drawBox;
    private JButton drawBtn;
    private JPanel mazeForm;
    private JButton solveBtn;

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new MazeForm());
    }

    public static void create(MazeGenerator generator)
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
                ((MazePanel) drawBox).solve();
                drawBox.repaint();
            }
        });
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

    public MazeForm(MazeGenerator generator)
    {
        initComponents();
        widthText.setText(String.valueOf(generator.width()));
        heightText.setText(String.valueOf(generator.height()));
        ((MazePanel) drawBox).setGenerator(generator);
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
        MazeGenerator generator = new MazeGenerator(width, height);
        generator.gen();
        ((MazePanel) drawBox).setGenerator(generator);
        drawBox.repaint();
        JOptionPane.showMessageDialog(mazeForm, "Maze took " + (System.currentTimeMillis() - time) + "ms to gen", "TIMER", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createUIComponents()
    {
        drawBox = new MazePanel();
    }
}
