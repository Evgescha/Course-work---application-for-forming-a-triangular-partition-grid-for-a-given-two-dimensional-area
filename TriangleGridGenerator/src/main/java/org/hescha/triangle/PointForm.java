package org.hescha.triangle;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PointForm extends JFrame {
    private JTable pointTable;
    private DefaultTableModel tableModel;
    private JTextField stepField;
    private JTextField pointField;
    private TriangularMesh triangularMesh;

    public PointForm() {
        super("Point Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new FlowLayout());
        JLabel pointLabel = new JLabel("Enter number of points:");
        northPanel.add(pointLabel);
        pointField = new JTextField(10);
        northPanel.add(pointField);
        JButton updateTableButton = new JButton("Update table");
        northPanel.add(updateTableButton);
        JLabel stepLabel = new JLabel("Enter step number");
        northPanel.add(stepLabel);
        stepField = new JTextField("10");
        northPanel.add(stepField);
        add(northPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"X", "Y"}, 0);
        pointTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pointTable);
        add(scrollPane, BorderLayout.CENTER);


        JPanel southPanel = new JPanel(new FlowLayout());
        JButton loadButton = new JButton("Load data");
        southPanel.add(loadButton);
        JButton saveButton = new JButton("Save data");
        southPanel.add(saveButton);
        JButton generateButton = new JButton("Generate triangles");
        southPanel.add(generateButton);
        JButton saveImageButton = new JButton("Save image");
        southPanel.add(saveImageButton);
        add(southPanel, BorderLayout.SOUTH);

        updateTableButton.addActionListener(e -> updateTableButtonAction(pointField));
        loadButton.addActionListener(e -> loadButtonAction());
        saveButton.addActionListener(e -> saveButtonAction());
        generateButton.addActionListener(e -> generateButtonAction(stepField));
        saveImageButton.addActionListener(e -> saveImageButtonAction());

        setSize(550, 300);
        setVisible(true);
    }

    private void saveImageButtonAction() {
        try {
            // create a file chooser to select the file to save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // get the selected file to save
                File fileToSave = fileChooser.getSelectedFile();
                JPanel panel = triangularMesh.getCanvasPanel();
                // Get the preferred size of the panel
                Dimension size = panel.getBounds().getSize();
                // Create a BufferedImage and get its Graphics object
                BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                // Draw the panel onto the BufferedImage
                panel.printAll(g2d);
                g2d.dispose();
                // Save the BufferedImage to a file
                ImageIO.write(image, "png", new File(fileToSave.getAbsolutePath() + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something goes wrong while saving file.");
        }
    }

    private void generateButtonAction(JTextField stepField) {
        List<Point> points = readPointsFromTable();
        if (points == null) return;
        try {
            int step = Integer.parseInt(stepField.getText());
            if (step < 1) throw new RuntimeException();
            triangularMesh = new TriangularMesh(step, points);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(PointForm.this, "Please enter valid positive number of step");
            throw ex;
        }
    }

    private void saveButtonAction() {
        saveFile();
    }

    private void saveFile() {
        try {
            // create a file chooser to select the file to save
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // get the selected file to save
                File fileToSave = fileChooser.getSelectedFile();

                List<Point> points = readPointsFromTable();
                int step = Integer.parseInt(stepField.getText());
                int pointNumber = points.size();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                // create a file writer to write to the file
                String separator = "\r\n";
                FileWriter fw = new FileWriter(fileToSave);
                fw.append(step + "").append(separator);
                fw.append(pointNumber + "").append(separator);
                // write the matrix data
                for (Point point : points) {
                    fw.append(point.x + " " + point.y).append(separator);
                }
                fw.append(separator);
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something goes wrong while saving file.");
        }
    }


    //return step number
    // reading
    // step
    // point number
    // pointX pointY
    public static int openFile(List<Point> points) {
        Scanner scanner;
        int step = -1;
        // Creates an instance of JFileChooser to select a file.
        JFileChooser fileChooser = new JFileChooser();
        // The showDialog method returns the option selected by the user.
        int ret = fileChooser.showDialog(null, "Open file");
        // If the user approves the option, the selected file is processed.
        if (ret == JFileChooser.APPROVE_OPTION) {
            // The selected file is stored in the file variable.
            try {
                File file = fileChooser.getSelectedFile();
                scanner = new Scanner(file);
                // A scanner is created to read the data from the file.
                step = scanner.nextInt();
                int pointsNumber = scanner.nextInt();

                for (int i = 0; i < pointsNumber; i++) {
                    Point point = new Point(scanner.nextInt(), scanner.nextInt());
                    points.add(point);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please check that file exists and contains correct values.");
                return -1;
            }
        }
        return step;
    }


    private void loadButtonAction() {
        List<Point> points = new ArrayList<>();
        int step = openFile(points);
        stepField.setText(step + "");
        if (step > 0) {
            tableModel.setRowCount(points.size());
            pointField.setText(points.size() + "");
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                tableModel.setValueAt(point.x, i, 0);
                tableModel.setValueAt(point.y, i, 1);
            }
        }
    }

    private void updateTableButtonAction(JTextField pointField) {
        try {
            int numPoints = Integer.parseInt(pointField.getText());
            if (numPoints > 2) {
                tableModel.setRowCount(numPoints);
            } else {
                JOptionPane.showMessageDialog(PointForm.this, "Please enter a number more or equals 3");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(PointForm.this, "Please enter a valid integer for the number of points");
        }
    }

    public List<Point> readPointsFromTable() {
        List<Point> points = new ArrayList<>();
        int x, y;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String xCoord = tableModel.getValueAt(i, 0).toString();
            String yCoord = tableModel.getValueAt(i, 1).toString();

            try {
                x = Integer.parseInt(xCoord);
                y = Integer.parseInt(yCoord);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(PointForm.this, "Please enter valid integers for the coordinates on point " + i);
                throw ex;
            }
            points.add(new Point(x, y));
        }
        return points;
    }

    public static void main(String[] args) {
        new PointForm();
    }
}
