package ch.mixin.namegenerator.name;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NameGenerator {
    private static HashMap<Character, Double> vocalMap = new HashMap<Character, Double>() {
        private static final long serialVersionUID = 1L;

        {
            put('E', 0.1740);
            put('I', 0.0755);
            put('A', 0.0651);
            put('U', 0.0435);
            put('O', 0.0251);
        }
    };
    private static HashMap<Character, Double> consonantMap = new HashMap<Character, Double>() {
        private static final long serialVersionUID = 1L;

        {
            put('N', 0.0978);
            put('S', 0.0727);
            put('R', 0.0700);
            put('T', 0.0615);
            put('D', 0.0508);
            put('H', 0.0476);
            put('L', 0.0344);
            put('C', 0.0306);
            put('G', 0.0301);
            put('M', 0.0253);
            put('B', 0.0189);
            put('W', 0.0189);
            put('F', 0.0166);
            put('K', 0.0121);
            put('Z', 0.0113);
            put('P', 0.0079);
            put('V', 0.0067);
            put('J', 0.0027);
            put('Y', 0.0004);
            put('X', 0.0003);
            put('Q', 0.0002);
        }
    };

    private Random random;

    public NameGenerator(Random random) {
        this.random = random;
    }

    public String generateName(Integer lengthMin, Integer lengthMax) {
        if (lengthMax == 0)
            return "";

        String name = "";

        Integer length = random.nextInt(lengthMax - lengthMin + 1) + lengthMin;
        Integer charType = random.nextInt(9 + 1);
        for (int i = 0; i < length; i++) {
            if (charType <= 4) {
                name += randomCharacter(vocalMap);
            } else {
                name += randomCharacter(consonantMap);
            }

            charType += 4 + random.nextInt(1 + 1);
            charType %= 10;
        }

        name = name.substring(0, 1) + name.substring(1).toUpperCase();
        return name;
    }

    private Character randomCharacter(HashMap<Character, Double> characterMap) {
        Double sum = 0.0;
        for (Double value : characterMap.values()) {
            sum += value;
        }
        Double pointer = random.nextDouble() * sum;
        for (Character character : characterMap.keySet()) {
            pointer -= characterMap.get(character);
            if (pointer < 0) {
                return character;
            }
        }

        return null;
    }

    public ArrayList<String> generateNames(Integer amount, Integer lengthMin, Integer lengthMax) {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < amount; i++) {
            names.add(generateName(lengthMin, lengthMax));
        }
        return names;
    }
}
