package ru.sidey383.cube;

import ru.sidey383.cube.cube.Cube;
import ru.sidey383.cube.cube.NCube;
import ru.sidey383.cube.paint.Painter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Cube cube = new NCube(4);

        Painter painter = new Painter( new Color(255, 255 ,255), new Color(0, 0 ,0), new Color(255, 0 ,0), 0);
        int count = 240;
        File dir = new File("cubeDir");
        dir.mkdirs();
        for(int i = 0; i < count; i++) {
            BufferedImage image = painter.getProjectionImage(cube, angels(i*Math.PI/(2*count), cube.getDimension()), 12, 12, 500, 500);
            File outputfile = new File( dir, "image"+i+".png");
            try {
                outputfile.createNewFile();
                if (!ImageIO.write(image, "png", outputfile)) {
                    System.err.println("no appropriate writer is found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static double[] angels(double angel, int dimension) {
        double[] angels = new double[dimension*(dimension-1)/2];
        for(int i = 0; i < angels.length; i++) {
            angels[i] = angel;
        }
        return angels;
    }

}
