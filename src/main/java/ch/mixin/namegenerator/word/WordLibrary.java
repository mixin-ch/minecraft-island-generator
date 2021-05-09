package ch.mixin.namegenerator.word;

import ch.mixin.namegenerator.helperClasses.ReaderFunctions;
import ch.mixin.namegenerator.word.adjective.Adjective;
import ch.mixin.namegenerator.word.adjective.AdjectiveType;
import ch.mixin.namegenerator.word.noun.Noun;
import ch.mixin.namegenerator.word.noun.NounType;
import ch.mixin.namegenerator.word.preposition.Preposition;
import ch.mixin.namegenerator.word.preposition.PrepositionType;
import ch.mixin.namegenerator.word.verb.Verb;
import ch.mixin.namegenerator.word.verb.VerbType;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class WordLibrary {
    private static HashMap<AdjectiveType, ArrayList<Adjective>> adjectives;
    private static HashMap<NounType, ArrayList<Noun>> nouns;
    private static HashMap<PrepositionType, ArrayList<Preposition>> prepositions;
    private static HashMap<VerbType, ArrayList<Verb>> verbs;

    private static String directoryPath;

    private final Random random;

    public WordLibrary(Random random) {
        this.random = random;
    }

    static {
        initializeWords();
    }

    private static void initializeWords() {
        directoryPath = "namegenerator/words";
        adjectives = new HashMap<>();
        nouns = new HashMap<>();
        prepositions = new HashMap<>();
        verbs = new HashMap<>();
        for (WordType wordType : WordType.values()) {
            initializeWordType(wordType);
        }
    }

    public Adjective getAdjective(ArrayList<AdjectiveType> adjectiveTypes, Boolean balanced) {
        if (balanced) {
            HashMap<AdjectiveType, Double> adjectiveTypeMap = new HashMap<>();
            for (AdjectiveType adjectiveType : adjectiveTypes) {
                adjectiveTypeMap.put(adjectiveType, 1.0);
            }
            return getAdjective(adjectiveTypeMap);
        } else {
            ArrayList<Adjective> adjectiveList = new ArrayList<>();
            adjectiveTypes.stream().forEach((w) -> adjectiveList.addAll(adjectives.get(w)));
            return adjectiveList.get(random.nextInt(adjectiveList.size()));
        }
    }

    public Adjective getAdjective(HashMap<AdjectiveType, Double> adjectiveTypeMap) {
        AdjectiveType chosenAdjectiveType = null;

        Double sum = 0.0;
        for (Double weight : adjectiveTypeMap.values()) {
            sum += weight;
        }
        Double pointer = random.nextDouble() * sum;
        for (AdjectiveType nt : adjectiveTypeMap.keySet()) {
            pointer -= adjectiveTypeMap.get(nt);
            if (pointer < 0) {
                chosenAdjectiveType = nt;
                break;
            }
        }

        ArrayList<Adjective> adjectiveList = new ArrayList<>(adjectives.get(chosenAdjectiveType));
        return adjectiveList.get(random.nextInt(adjectiveList.size()));
    }

    public Noun getNoun(ArrayList<NounType> nounTypes, Boolean balanced) {
        if (balanced) {
            HashMap<NounType, Double> nounTypeMap = new HashMap<>();
            for (NounType nounType : nounTypes) {
                nounTypeMap.put(nounType, 1.0);
            }
            return getNoun(nounTypeMap);
        } else {
            ArrayList<Noun> nounList = new ArrayList<>();
            nounTypes.stream().forEach((w) -> nounList.addAll(nouns.get(w)));
            return nounList.get(random.nextInt(nounList.size()));
        }
    }

    public Noun getNoun(HashMap<NounType, Double> nounTypeMap) {
        NounType chosenNounType = null;

        Double sum = 0.0;
        for (Double weight : nounTypeMap.values()) {
            sum += weight;
        }
        Double pointer = random.nextDouble() * sum;
        for (NounType nt : nounTypeMap.keySet()) {
            pointer -= nounTypeMap.get(nt);
            if (pointer < 0) {
                chosenNounType = nt;
                break;
            }
        }

        ArrayList<Noun> nounList = new ArrayList<>(nouns.get(chosenNounType));
        return nounList.get(random.nextInt(nounList.size()));
    }

    public Preposition getPreposition(ArrayList<PrepositionType> prepositionTypes, Boolean balanced) {
        if (balanced) {
            HashMap<PrepositionType, Double> prepositionTypeMap = new HashMap<>();
            for (PrepositionType prepositionType : prepositionTypes) {
                prepositionTypeMap.put(prepositionType, 1.0);
            }
            return getPreposition(prepositionTypeMap);
        } else {
            ArrayList<Preposition> prepositionList = new ArrayList<>();
            prepositionTypes.stream().forEach((w) -> prepositionList.addAll(prepositions.get(w)));
            return prepositionList.get(random.nextInt(prepositionList.size()));
        }
    }

    public Preposition getPreposition(HashMap<PrepositionType, Double> prepositionTypeMap) {
        PrepositionType chosenPrepositionType = null;

        Double sum = 0.0;
        for (Double weight : prepositionTypeMap.values()) {
            sum += weight;
        }
        Double pointer = random.nextDouble() * sum;
        for (PrepositionType nt : prepositionTypeMap.keySet()) {
            pointer -= prepositionTypeMap.get(nt);
            if (pointer < 0) {
                chosenPrepositionType = nt;
                break;
            }
        }

        ArrayList<Preposition> prepositionList = new ArrayList<>(prepositions.get(chosenPrepositionType));
        return prepositionList.get(random.nextInt(prepositionList.size()));
    }

    public Verb getVerb(ArrayList<VerbType> verbTypes, Boolean balanced) {
        if (balanced) {
            HashMap<VerbType, Double> verbTypeMap = new HashMap<>();
            for (VerbType verbType : verbTypes) {
                verbTypeMap.put(verbType, 1.0);
            }
            return getVerb(verbTypeMap);
        } else {
            ArrayList<Verb> verbList = new ArrayList<>();
            verbTypes.stream().forEach((w) -> verbList.addAll(verbs.get(w)));
            return verbList.get(random.nextInt(verbList.size()));
        }
    }

    public Verb getVerb(HashMap<VerbType, Double> verbTypeMap) {
        VerbType chosenVerbType = null;

        Double sum = 0.0;
        for (Double weight : verbTypeMap.values()) {
            sum += weight;
        }
        Double pointer = random.nextDouble() * sum;
        for (VerbType nt : verbTypeMap.keySet()) {
            pointer -= verbTypeMap.get(nt);
            if (pointer < 0) {
                chosenVerbType = nt;
                break;
            }
        }

        ArrayList<Verb> verbList = new ArrayList<>(verbs.get(chosenVerbType));
        return verbList.get(random.nextInt(verbList.size()));
    }

    private static void initializeWordType(WordType wordType) {
        final String path = directoryPath + "/" + wordType.toString().toLowerCase();
        ArrayList<String> wordCategoryNames = getFilenamesFromDirectory(path);

        for (String wordCategoryName : wordCategoryNames) {
            initializeWordCategory(wordType, wordCategoryName);
        }
    }

    private static ArrayList<String> getFilenamesFromDirectory(String path) {
        ArrayList<String> wordCategoryNames = new ArrayList<>();

        String urlPath = WordLibrary.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = null;
        try {
            decodedPath = URLDecoder.decode(urlPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final File jarFile = new File(decodedPath);
        if (jarFile.isFile()) {  // Run with JAR file
            try {
                final JarFile jar = new JarFile(jarFile);
                final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
                while (entries.hasMoreElements()) {
                    final String name = entries.nextElement().getName();
                    if (name.startsWith(path + "/") && name.length() > path.length() + 1) { //filter according to the path
                        String[] parts = name.split("/");
                        String part = parts[parts.length - 1];
                        wordCategoryNames.add(part.substring(0, part.length() - 4));
                    }
                }
                jar.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // Run with IDE
            final URL url = WordLibrary.class.getResource("/" + path);
            if (url != null) {
                try {
                    final File folder = new File(new URI(url.toString().replace(" ", "%20")).getSchemeSpecificPart());
                    //final File folder = new File(url.toURI());
                    for (File file : folder.listFiles()) {
                        String[] parts = file.getPath().split("\\\\");
                        String part = parts[parts.length - 1];
                        wordCategoryNames.add(part.substring(0, part.length() - 4));
                    }
                } catch (URISyntaxException ex) {
                    // never happens
                }
            }
        }

        return wordCategoryNames;
    }

    private static void initializeWordCategory(WordType wordType, String wordCategory) {
        ArrayList<String> inputLines = read(
                directoryPath + "/" + wordType.toString().toLowerCase() + "/" + wordCategory + ".txt");

        switch (wordType) {
            case ADJECTIVE:
                adjectives.put(AdjectiveType.valueOf(wordCategory.toUpperCase()), initializeAdjectives(inputLines));
                break;
            case NOUN:
                nouns.put(NounType.valueOf(wordCategory.toUpperCase()), initializeNouns(inputLines));
                break;
            case PREPOSITION:
                prepositions.put(PrepositionType.valueOf(wordCategory.toUpperCase()), initializePrepositions(inputLines));
                break;
            case VERB:
                verbs.put(VerbType.valueOf(wordCategory.toUpperCase()), initializeVerbs(inputLines));
                break;
        }
    }

    private static ArrayList<Adjective> initializeAdjectives(ArrayList<String> inputLines) {
        ArrayList<Adjective> adjectiveWords = new ArrayList<>();
        for (String line : inputLines) {
            String positive = "";
            String comparative = "";
            String superlative = "";

            ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(",")));

            positive = parts.get(0);
            comparative = parts.get(1);
            superlative = parts.get(2);

            if (comparative.equals("-")) {
                comparative = "more " + positive;
            }
            if (superlative.equals("-")) {
                superlative = "most " + positive;
            }

            adjectiveWords.add(new Adjective(positive, comparative, superlative));
        }
        return adjectiveWords;
    }

    private static ArrayList<Noun> initializeNouns(ArrayList<String> inputLines) {
        ArrayList<Noun> nounWords = new ArrayList<>();
        for (String line : inputLines) {
            String singular = "";
            String plural = "";
            boolean countable = true;

            ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(",")));

            singular = parts.get(0);
            plural = parts.get(1);
            countable = parts.get(2).equals("c");

            nounWords.add(new Noun(singular, plural, countable));
        }
        return nounWords;
    }

    private static ArrayList<Preposition> initializePrepositions(ArrayList<String> inputLines) {
        ArrayList<Preposition> prepositionWords = new ArrayList<>();
        for (String line : inputLines) {
            prepositionWords.add(new Preposition(line));
        }
        return prepositionWords;
    }

    private static ArrayList<Verb> initializeVerbs(ArrayList<String> inputLines) {
        ArrayList<Verb> verbWords = new ArrayList<>();
        for (String line : inputLines) {
            String infinitive = "";
            String thirdPerson = "";
            String pastParticiple = "";
            String presentParticiple = "";

            ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(",")));

            infinitive = parts.get(0);
            thirdPerson = parts.get(1);
            pastParticiple = parts.get(2);
            presentParticiple = parts.get(3);

            verbWords.add(new Verb(infinitive, thirdPerson, pastParticiple, presentParticiple));
        }
        return verbWords;
    }

    private static ArrayList<String> read(String filePath) {
        return new ArrayList<>(Arrays.asList(ReaderFunctions.readFile(filePath).split("\n")));
    }
}
