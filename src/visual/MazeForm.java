package visual;

import logic.MazeGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeForm
{
    private JTextField widthText;
    private JTextField heightText;
    private JPanel drawBox;
    private JButton drawBtn;
    private JPanel mazeForm;
    private JButton solveBtn;

    public static void main(String... args)
    {
        JFrame frame = new JFrame("MazeGen");
        frame.setContentPane(new MazeForm().mazeForm);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MazeForm()
    {
        widthText.setText("10");
        heightText.setText("10");
        gen(10, 10);
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
