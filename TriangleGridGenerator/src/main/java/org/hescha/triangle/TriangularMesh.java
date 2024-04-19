package org.hescha.triangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class TriangularMesh extends JFrame {
    private final int width = 800;
    private final int height = 600;

    private final List<Point> pointList;
    private final int step;
    private JPanel canvasPanel;

    void init() {
        setTitle("Triangular Mesh");
        setSize(width, height);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        canvasPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTriangularMesh(g);
            }
        };
        canvasPanel.setBounds(0, 0, width * 3, height * 3);
        panel.add(canvasPanel);

        add(panel);
        setVisible(true);
    }

    public JPanel getCanvasPanel() {
        return canvasPanel;
    }

    public TriangularMesh(int step, List<Point> pointList) {
        this.pointList = pointList;
        this.step = step;
        init();
    }

    private void drawTriangularMesh(Graphics g) {
        drawMesh(g);
        drawBorders(g);
    }

    private void drawBorders(Graphics g) {
        // Set line color and stroke
        g.setColor(new Color(255, 128, 128)); // Light red

        // Draw line through all points
        pointList.add(pointList.get(0));
        for (int i = 0; i < pointList.size() - 1; i++) {
            Point p1 = pointList.get(i);
            Point p2 = pointList.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        pointList.remove(pointList.size() - 1);
    }

    private void drawMesh(Graphics g) {
        int minX = (int) pointList.stream().mapToDouble(Point::getX).min().orElse(Integer.MAX_VALUE);
        int maxX = (int) pointList.stream().mapToDouble(Point::getX).max().orElse(Integer.MIN_VALUE);
        int minY = (int) pointList.stream().mapToDouble(Point::getY).min().orElse(Integer.MAX_VALUE);
        int maxY = (int) pointList.stream().mapToDouble(Point::getY).max().orElse(Integer.MIN_VALUE);

        // Определение размеров сетки
        int width = maxX - minX;
        int height = maxY - minY;
        int rows = height / step;
        int cols = width / step;

        // смещение для отображения области в окне
        int xOffset = minX;
        int yOffset = minY;

        // Отрисовка сетки
        g.setColor(Color.BLACK);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * step + xOffset;
                int y = i * step + yOffset;
                if (isInsideArea(x, y) && isInsideArea(x + step, y) && isInsideArea(x, y + step)) {
                    g.drawLine(x, y, x + step, y);
                    g.drawLine(x, y, x, y + step);
                    g.drawLine(x, y + step, x + step, y);
                }
            }
        }
    }

    private boolean isInsideArea(int x, int y) {
        // Вычислить уравнения линий между точками области
        double[] eqs = new double[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            Point p1 = pointList.get(i);
            Point p2 = pointList.get((i + 1) % pointList.size());
            eqs[i] = (p2.getY() - p1.getY()) * (x - p1.getX()) - (y - p1.getY()) * (p2.getX() - p1.getX());
        }
        // Проверить, лежит ли точка слева или справа от каждой из линий
        for (int i = 1; i < eqs.length; i++) {
            if (eqs[i] > 0) {
                return false;
            }
        }
        // Вернуть true, если точка лежит слева от всех линий
        return true;
    }
}