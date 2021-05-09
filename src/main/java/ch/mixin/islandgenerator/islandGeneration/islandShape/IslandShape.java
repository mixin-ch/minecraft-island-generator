package ch.mixin.islandgenerator.islandGeneration.islandShape;

import ch.mixin.islandgenerator.model.Coordinate3D;

import java.util.ArrayList;

public class IslandShape {
    private final ArrayList<Coordinate3D> layerTop;
    private final ArrayList<Coordinate3D> layerBot;
    private final ArrayList<Coordinate3D> layerMid;

    public IslandShape(ArrayList<Coordinate3D> layerTop, ArrayList<Coordinate3D> layerBot, ArrayList<Coordinate3D> layerMid) {
        this.layerTop = layerTop;
        this.layerBot = layerBot;
        this.layerMid = layerMid;
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
