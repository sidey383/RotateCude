package ru.sidey383.cube.cube;

import ru.sidey383.cube.matrix.Matrix;
import ru.sidey383.cube.matrix.RotateMatrix;

import java.util.ArrayList;
import java.util.List;

public class NCube implements Cube {

    private final List<Vertex> vertices;

    private final List<Cube.Edge> edges;

    private final int dimension;

    public NCube(int dimension) {
        this.dimension = dimension;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        for(long i = 0; i < (1L<<getDimension()); i++) {
            Vertex ver = new Vertex(new double[getDimension()]);
            for(int j = 0; j < dimension; j++) {
                ver.coordinate()[j] = ((1&(i>>j)) == 1) ? 1 : -1;
            }
            vertices.add(ver);
            //System.out.println(vertices.get(vertices.size()-1));
        }
        for(int i = 0; i < vertices.size(); i++) {
            for(int j = 0; j < vertices.size(); j++) {
                int distance = 0;
                for(int k = 0; k < dimension; k++) {
                    distance += vertices.get(i).coordinate()[k] != vertices.get(j).coordinate()[k] ? 1 : 0;
                }
                if(distance == 1) {
                    edges.add(new Cube.Edge(vertices.get(i), vertices.get(j)));
                }
            }
        }

    }

    @Override
    public List<Vertex> getVertexes(double[] rotate) {
        Matrix matrix = new RotateMatrix(rotate);
        return vertices.stream().map((vertex) -> vertex.rotate(matrix)).toList();
    }

    @Override
    public List<Edge> getEdges(double[] rotate) {
        Matrix matrix = new RotateMatrix(rotate);
        return edges.stream().map((edge) -> edge.rotate(matrix)).toList();
    }

    @Override
    public int getDimension() {
        return dimension;
    }

}
