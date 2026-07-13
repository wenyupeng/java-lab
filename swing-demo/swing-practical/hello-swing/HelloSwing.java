import javax.swing.*;
import java.awt.*;

public class HelloSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello Swing");
            frame.add(new JLabel("Hello, Java Swing!"), BorderLayout.NORTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set size of the window
            // frame.setSize(300, 200);
            // set the absolute location to the window
            // frame.setLocation(300, 300);
            // set the window to the center of the screen
            // frame.setLocationRelativeTo(null);
            frame.setBounds(100, 100, 450, 300);

            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("Floor Area:"));
            panel.add(new JTextField(10));

            frame.add(panel, BorderLayout.SOUTH);

            JTextArea notesArea =new JTextArea(6, 30);
            notesArea.setLineWrap(true);
            notesArea.setWrapStyleWord(true);

            JScrollPane scrollPane =new JScrollPane(notesArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            JButton calculateButton =new JButton("Calculate");
            calculateButton.addActionListener(event -> {
                System.out.println("Calculate button clicked");
            });
            frame.add(calculateButton, BorderLayout.EAST);
            JCheckBox insulationCheckBox =new JCheckBox("Property has insulation");
            insulationCheckBox.addActionListener(event -> {System.out.println("Insulation checkbox clicked");});
            frame.add(insulationCheckBox, BorderLayout.WEST);

            JMenuBar menuBar = new JMenuBar();

            JMenu fileMenu = new JMenu("File");

            JMenuItem newItem = new JMenuItem("New");
            JMenuItem saveItem = new JMenuItem("Save");
            JMenuItem exitItem = new JMenuItem("Exit");

            fileMenu.add(newItem);
            fileMenu.add(saveItem);
            fileMenu.addSeparator();
            fileMenu.add(exitItem);

            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);

            JPanel formPanel =new JPanel(new GridBagLayout());

            GridBagConstraints constraints =new GridBagConstraints();

            constraints.insets =new Insets(5, 5, 5, 5);

            constraints.gridx = 0;
            constraints.gridy = 0;

            formPanel.add(new JLabel("Floor Area:"),constraints);
            frame.add(formPanel,BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}