package ru.sidey383.cube.paint;

import org.jetbrains.annotations.Nullable;
import ru.sidey383.cube.cube.Cube;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Painter {

    private double delta;

    private Color edgeColor;

    private Color vertexColor;

    private Color backgroundColor;

    public Painter(Color background ,Color edge, Color vertex, double delta) {
        this.backgroundColor = background;
        this.edgeColor = edge;
        this.vertexColor = vertex;
        this.delta = delta;
    }

    public BufferedImage getImage(Cube cube, double[] angels, double xSize, double ySize, int width, int height) {
        if(cube.getDimension() < 3)
            throw new IllegalArgumentException("wrong dimensions");
        List<Cube.Vertex> vertices = cube.getVertexes(angels);
        List<Cube.Edge> edges = cube.getEdges(angels);
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_FAST);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(edgeColor);
        for(Cube.Edge edge : edges) {
            //System.out.println(edge.toString());
            if(onPlane(edge)) {
                int[] dot1 = getDot(edge.a().coordinate(), xSize, ySize, width, height);
                int[] dot2 = getDot(edge.b().coordinate(), xSize, ySize, width, height);
                graphics2D.drawLine(dot1[0], dot1[1], dot2[0], dot2[1]);
                //System.out.println("draw line "+ dot1[0] +":"+dot1[1] +" "+dot2[0] +":"+dot2[1]);
            } else {
                double[] doubleDot = getDot(edge);
                if(doubleDot != null) {
                    //System.out.println("draw dot "+ doubleDot[0] +":"+doubleDot[1]);
                    int[] dot = getDot(doubleDot, xSize, ySize, width, height);
                    graphics2D.drawLine(dot[0], dot[1], dot[0], dot[1]);
                    //System.out.println("draw dot "+ dot[0] +":"+dot[1]);
                }
            }
        }
        return image;
    }

    public BufferedImage getProjectionImage(Cube cube, double[] angels, double xSize, double ySize, int width, int height) {
        if(cube.getDimension() < 3)
            throw new IllegalArgumentException("wrong dimensions");
        List<Cube.Vertex> vertices = cube.getVertexes(angels);
        List<Cube.Edge> edges = cube.getEdges(angels);
        BufferedImage image = new BufferedImage(width, height, Image.SCALE_FAST);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(edgeColor);
        //System.out.println(edges.size());
        for(Cube.Edge edge : edges) {
            int[] dot1 = getDot(edge.a().coordinate(), xSize, ySize, width, height);
            int[] dot2 = getDot(edge.b().coordinate(), xSize, ySize, width, height);
            graphics2D.drawLine(dot1[0], dot1[1], dot2[0], dot2[1]);
        }
        graphics2D.setColor(vertexColor);
        for(Cube.Vertex vertex : vertices) {
            int[] dot = getDot(vertex.coordinate(), xSize, ySize, width, height);
            graphics2D.fillRect(dot[0]-1, dot[1]-1, 3, 3);
        }
        return image;
    }

    @Nullable
    private double[] getDot(Cube.Edge edge) {
        double a = 0;
        if (((edge.a().coordinate()[2] - edge.b().coordinate()[2])) == 0) {
            return null;
        } else {
            a = edge.b().coordinate()[2] / (edge.a().coordinate()[2] - edge.b().coordinate()[2]);
        }
        for (int i = 3; i < edge.a().coordinate().length; i++) {
            if (a != edge.b().coordinate()[2] / (edge.a().coordinate()[2]- edge.b().coordinate()[2])) {
                return null;
            }
        }
        return new double[]{a*(edge.a().coordinate()[0]- edge.b().coordinate()[0]), a*(edge.a().coordinate()[1]- edge.b().coordinate()[1])};
    }

    private int[] getDot(double[] dot, double xSize, double ySize, int width, int height) {
        return new int[]{width/2 + (int)(dot[0]*width*2/xSize), height/2 + (int)(dot[1]*height*2/ySize)};
    }

    private boolean onPlane(Cube.Edge edge) {
            for(int i = 3; i < edge.a().coordinate().length; i++) {
                if(edge.a().coordinate()[i] >= delta) {
                    return false;
                }
                if(edge.b().coordinate()[i] >= delta) {
                    return false;
                }
            }
            return true;
    }

    private boolean onPlane(Cube.Vertex edge) {
        for(int i = 3; i < edge.coordinate().length; i++) {
            if(edge.coordinate()[i] >= delta) {
                return false;
            }
        }
        return true;
    }

}
