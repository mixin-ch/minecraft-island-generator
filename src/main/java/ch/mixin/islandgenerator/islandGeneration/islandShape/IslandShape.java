package ch.mixin.islandgenerator.islandGeneration.islandShape;

import ch.mixin.islandgenerator.model.Coordinate3D;

import java.util.ArrayList;

public class IslandShape {
    private final Coordinate3D weightCenter;
    private final double weightRadius;
    private final ArrayList<Coordinate3D> layerTop;
    private final ArrayList<Coordinate3D> layerBot;
    private final ArrayList<Coordinate3D> layerMid;

    public IslandShape(Coordinate3D weightCenter, double weightRadius, ArrayList<Coordinate3D> layerTop, ArrayList<Coordinate3D> layerBot, ArrayList<Coordinate3D> layerMid) {
        this.weightCenter = weightCenter;
        this.weightRadius = weightRadius;
        this.layerTop = layerTop;
        this.layerBot = layerBot;
        this.layerMid = layerMid;
    }

    public Coordinate3D getWeightCenter() {
        return weightCenter;
    }

    public double getWeightRadius() {
        return weightRadius;
    }

    public ArrayList<Coordinate3D> getLayerTop() {
        return layerTop;
    }

    public ArrayList<Coordinate3D> getLayerBot() {
        return layerBot;
    }

    public ArrayList<Coordinate3D> getLayerMid() {
        return layerMid;
    }
}
