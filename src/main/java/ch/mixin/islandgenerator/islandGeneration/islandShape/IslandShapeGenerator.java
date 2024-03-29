package ch.mixin.islandgenerator.islandGeneration.islandShape;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.main.IslandGeneratorPlugin;
import ch.mixin.islandgenerator.model.Coordinate2D;
import ch.mixin.islandgenerator.model.Coordinate3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

public class IslandShapeGenerator {
    private final IslandGeneratorPlugin plugin;
    private IslandShapePremise islandShapePremise;

    public IslandShapeGenerator(IslandGeneratorPlugin plugin) {
        this.plugin = plugin;
    }

    public IslandShape generateIslandShape(int spawnRegionHeight) {
        islandShapePremise = new IslandShapePremise(plugin.getConfig().getInt("islandDistance"), plugin.getConfig().getInt("islandRadius"));

        ArrayList<Coordinate2D> basePlane = generateBasePlane();
        ArrayList<Coordinate3D> shape = convert(basePlane, 0);

        ArrayList<Coordinate2D> subPlane;

        subPlane = new ArrayList<>(basePlane);

        for (int i = 1; i <= spawnRegionHeight && i <= plugin.getConfig().getInt("islandDistance") * 0.5; i++) {
            subPlane = generateSubPlane(subPlane, islandShapePremise.getPlaneTopReducers(), islandShapePremise.getTopFlatness());
            if (subPlane.size() == 0)
                break;
            shape = Functions.merge(shape, convert(subPlane, i));
        }

        subPlane = new ArrayList<>(basePlane);

        for (int i = 1; i <= spawnRegionHeight && i <= plugin.getConfig().getInt("islandDistance") * 0.5; i++) {
            subPlane = generateSubPlane(subPlane, islandShapePremise.getPlaneBotReducers(), islandShapePremise.getBotFlatness());
            if (subPlane.size() == 0)
                break;
            shape = Functions.merge(shape, convert(subPlane, -i));
        }

        return makeIslandShape(shape);
    }

    private IslandShape makeIslandShape(ArrayList<Coordinate3D> layerMid) {
        HashMap<Coordinate2D, Integer> layerTopMap = new HashMap<>();
        HashMap<Coordinate2D, Integer> layerBotMap = new HashMap<>();

        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        int minZ = 0;
        int maxZ = 0;

        double furthestBlockDistance = 0;

        for (int i = 0; i < layerMid.size(); i++) {
            Coordinate3D coordinate3D = layerMid.get(i);
            furthestBlockDistance = Math.max(coordinate3D.length(), furthestBlockDistance);

            if (coordinate3D.length() > plugin.getConfig().getInt("islandDistance") * 0.5) {
                layerMid.remove(coordinate3D);
                i--;
                continue;
            }

            minX = Math.min(minX, coordinate3D.getX());
            maxX = Math.max(maxX, coordinate3D.getX());
            minY = Math.min(minY, coordinate3D.getY());
            maxY = Math.max(maxY, coordinate3D.getY());
            minZ = Math.min(minZ, coordinate3D.getZ());
            maxZ = Math.max(maxZ, coordinate3D.getZ());

            Coordinate2D coordinate2D = coordinate3D.to2D();
            int y = coordinate3D.getY();

            if (!layerTopMap.containsKey(coordinate2D) || layerTopMap.get(coordinate2D) < y)
                layerTopMap.put(coordinate2D, y);
            if (!layerBotMap.containsKey(coordinate2D) || layerBotMap.get(coordinate2D) > y)
                layerBotMap.put(coordinate2D, y);
        }

        ArrayList<Coordinate3D> layerTop = new ArrayList<>();
        ArrayList<Coordinate3D> layerBot = new ArrayList<>();

        for (Coordinate2D c2d : layerBotMap.keySet()) {
            Coordinate3D c3d = c2d.to3D(layerBotMap.get(c2d) - 1);
            furthestBlockDistance = Math.max(c3d.length(), furthestBlockDistance);

            if (c3d.length() <= plugin.getConfig().getInt("islandDistance") * 0.5) {
                layerBot.add(c3d);
                minY = Math.min(minY, c3d.getY());
            }
        }

        for (Coordinate2D c2d : layerTopMap.keySet()) {
            Coordinate3D c3d = c2d.to3D(layerTopMap.get(c2d) + 1);
            furthestBlockDistance = Math.max(c3d.length(), furthestBlockDistance);

            if (c3d.length() <= plugin.getConfig().getInt("islandDistance") * 0.5) {
                layerTop.add(c3d);
                maxY = Math.max(maxY, c3d.getY());
            }
        }

        Coordinate3D weightCenter = new Coordinate3D((maxX + minX) / 2, (maxY + minY) / 2, (maxZ + minZ) / 2);
        double weightRadius = 0;

        for (Coordinate3D cd3 : layerMid)
            weightRadius = Math.max(weightRadius, cd3.distance(weightCenter));
        for (Coordinate3D cd3 : layerBot)
            weightRadius = Math.max(weightRadius, cd3.distance(weightCenter));
        for (Coordinate3D cd3 : layerTop)
            weightRadius = Math.max(weightRadius, cd3.distance(weightCenter));

        int extraRadiusMax = plugin.getConfig().getInt("glassSphereExtraRadiusMax");
        int extraRadiusMin = plugin.getConfig().getInt("glassSphereExtraRadiusMin");

        if (extraRadiusMin > extraRadiusMax)
            extraRadiusMin = extraRadiusMax;

        int extraRadius = plugin.getRandom().nextInt(extraRadiusMax + 1 - extraRadiusMin) + extraRadiusMin;
        weightRadius = Math.max(0, weightRadius + extraRadius);

        return new IslandShape(weightCenter, weightRadius, layerTop, layerBot, layerMid);
    }

    private ArrayList<Coordinate2D> generateBasePlane() {
        ArrayList<Coordinate2D> startPoints = new ArrayList<>();
        for (int i = 0; i < islandShapePremise.getIslandNumber(); i++) {
            ArrayList<Coordinate2D> spawnArea = islandShapePremise.getSpawnArea();
            Coordinate2D coordinate2D = spawnArea.get(new Random().nextInt(spawnArea.size()));

            if (!startPoints.contains(coordinate2D))
                startPoints.add(coordinate2D);
        }

        return generatePlane(startPoints, islandShapePremise.getPlaneBaseSize());
    }

    private ArrayList<Coordinate2D> generateSubPlane(ArrayList<Coordinate2D> plane, HashMap<Coordinate2D, Double> planeReducers, int flatness) {
        ArrayList<Coordinate2D> topPlane = new ArrayList<>();

        for (int i = 0; i < flatness; i++) {
            for (Coordinate2D point : plane) {
                double placement = 1;

                for (Coordinate2D neighbour : point.neighbours()) {
                    if (!plane.contains(neighbour)) {
                        placement *= planeReducers.get(neighbour.difference(point));
                    }
                }

                if (new Random().nextDouble() < placement) {
                    topPlane.add(point);
                }
            }

            plane = new ArrayList<>(topPlane);
            topPlane.clear();
        }

        return plane;
    }

    private ArrayList<Coordinate2D> generatePlane(ArrayList<Coordinate2D> fertilePoints, int size) {
        ArrayList<Coordinate2D> plane = new ArrayList<>();

        for (int i = 0; i < size && fertilePoints.size() > 0; i++) {
            Coordinate2D spreader = fertilePoints.get(new Random().nextInt(fertilePoints.size()));
            plane.add(spreader);
            fertilePoints.remove(spreader);
            for (Coordinate2D neighbour : spreader.neighbours()) {
                if (plane.contains(neighbour))
                    continue;
                if (fertilePoints.contains(neighbour))
                    continue;
                if (neighbour.length() > plugin.getConfig().getInt("islandDistance") * 0.5)
                    continue;

                fertilePoints.add(neighbour);
            }
        }

        plane = Functions.merge(plane, fertilePoints);
        return plane;
    }

    public ArrayList<Coordinate3D> convert(ArrayList<Coordinate2D> plane, int y) {
        return plane.stream().map(v -> v.to3D(y)).collect(Collectors.toCollection(ArrayList::new));
    }
}
