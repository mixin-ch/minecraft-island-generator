package ch.mixin.islandgenerator.islandGeneration.islandShape;

import ch.mixin.islandgenerator.helperClasses.Functions;
import ch.mixin.islandgenerator.model.Coordinate2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class IslandShapePremise {
    private final ArrayList<Coordinate2D> spawnArea;
    private final int islandNumber;
    private final int planeBaseSize;
    private final HashMap<Coordinate2D, Double> planeTopReducers;
    private final HashMap<Coordinate2D, Double> planeBotReducers;
    private final int topFlatness;
    private final int botFlatness;

    public IslandShapePremise(int islandDistance,int islandRadius) {
        spawnArea = randomizeSpawnArea(islandDistance);
        islandNumber = randomizeIslandNumber(islandDistance);
        planeBaseSize = randomizePlaneSize(islandRadius);
        planeTopReducers = randomizePlaneReducers();
        planeBotReducers = randomizePlaneReducers();
        topFlatness = randomizeFlatness();
        botFlatness = randomizeFlatness();
    }

    private ArrayList<Coordinate2D> randomizeSpawnArea(int islandDistance) {
        return Functions.getCircle2D(new Coordinate2D(0, 0), new Random().nextDouble() * islandDistance * 0.5);
    }

    private int randomizeIslandNumber(int islandDistance) {
        int limit = 0;

        double weight = 0;
        double probability = 1;
        while (new Random().nextDouble() < probability) {
            limit++;
            weight = islandDistance / (double) Math.pow(2, limit);
            probability = weight / (weight + 5);
        }

        return new Random().nextInt((int) Math.pow(limit, 2)) + 1;
    }

    private int randomizePlaneSize(int islandRadius) {
        double size = Math.pow(islandRadius, 2);
        size *= new Random().nextDouble() + 0.25;
        return (int) Math.round(size);
    }

    private HashMap<Coordinate2D, Double> randomizePlaneReducers() {
        HashMap<Coordinate2D, Double> planeReducers = new HashMap<>();

        for (Coordinate2D coordinate2D : new Coordinate2D(0, 0).neighbours()) {
            double reducement = 0.9;
            while (new Random().nextDouble() < 0.5) {
                reducement *= new Random().nextDouble() * 0.5 + 0.5;
            }

            planeReducers.put(coordinate2D, reducement);
        }

        return planeReducers;
    }

    private int randomizeFlatness() {
        int flatness = 1;
        while (new Random().nextDouble() < 0.85) {
            flatness++;
        }
        return flatness;
    }

    public ArrayList<Coordinate2D> getSpawnArea() {
        return spawnArea;
    }

    public int getIslandNumber() {
        return islandNumber;
    }

    public int getPlaneBaseSize() {
        return planeBaseSize;
    }

    public HashMap<Coordinate2D, Double> getPlaneTopReducers() {
        return planeTopReducers;
    }

    public HashMap<Coordinate2D, Double> getPlaneBotReducers() {
        return planeBotReducers;
    }

    public int getTopFlatness() {
        return topFlatness;
    }

    public int getBotFlatness() {
        return botFlatness;
    }
}
