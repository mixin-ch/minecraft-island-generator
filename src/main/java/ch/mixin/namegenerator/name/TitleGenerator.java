package ch.mixin.namegenerator.name;

import ch.mixin.namegenerator.word.WordLibrary;
import ch.mixin.namegenerator.word.WordModifier;
import ch.mixin.namegenerator.word.adjective.Adjective;
import ch.mixin.namegenerator.word.adjective.AdjectiveType;
import ch.mixin.namegenerator.word.noun.Noun;
import ch.mixin.namegenerator.word.noun.NounType;
import ch.mixin.namegenerator.word.preposition.Preposition;
import ch.mixin.namegenerator.word.preposition.PrepositionType;
import ch.mixin.namegenerator.word.verb.Verb;
import ch.mixin.namegenerator.word.verb.VerbType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TitleGenerator {
    private final WordLibrary wordLibrary;
    private final Random random;

    public TitleGenerator(Random random) {
        this.random = random;
        wordLibrary = new WordLibrary(random);
    }

    public String generateTitle(Integer lengthMin, Integer lengthMax) {
        return generateTitle(lengthMin, lengthMax, new ArrayList<NounType>(Arrays.asList(NounType.values())));
    }

    public String generateTitle(Integer lengthMin, Integer lengthMax, NounType subjectWordType) {
        return generateTitle(lengthMin, lengthMax, new ArrayList<NounType>(Arrays.asList(subjectWordType)));
    }

    public String generateTitle(Integer lengthMin, Integer lengthMax, ArrayList<NounType> subjectWordTypes) {
        String title = null;
        while (title == null || title.length() < lengthMin || title.length() > lengthMax) {
            title = object_chain(subjectWordTypes);
        }
        return title;
    }

    private String object_chain(ArrayList<NounType> subjectWordTypes) {
        String title = "";

        if (0.35 > random.nextDouble()) {
            String genitivObject = object_chain(null);
            String lastCharacter = genitivObject.substring(genitivObject.length() - 1);
            String addition = "'s ";
            if (lastCharacter.equals("s") || lastCharacter.equals("z"))
                addition = "' ";
            title += genitivObject + addition;
        } else {
            if (0.5 > random.nextDouble()) {
                title += "the ";
            }
        }

        if (0.40 > random.nextDouble()) {
            title += adjective_chain(true) + " ";
        }

        if (0.20 > random.nextDouble()) {
            title += noun_chain() + " ";
        }

        title += noun(subjectWordTypes);

        if (0.50 > random.nextDouble()) {
            if (0.5 > random.nextDouble()) {
                title += " of ";
            } else {
                title += " " + preposition() + " ";
            }
            title += object_chain(null);
        }

        return title;
    }

    private String adjective_chain(Boolean first) {
        String title = "";

        if (0.20 > random.nextDouble()) {
            title += adjective_chain(false);
            title += (first & 0.5 > random.nextDouble() ? " and " : ", ");
        }

        title += adjective();

        return title;
    }

    private String noun_chain() {
        String title = "";

        if (0.15 > random.nextDouble()) {
            title += noun_chain() + " ";
        }

        title += nounSingular();

        return title;
    }

    private String preposition() {
        return getPrepositionWord().getValue();
    }

    private Preposition getPrepositionWord() {
        return wordLibrary.getPreposition(new ArrayList<>(Arrays.asList(PrepositionType.values())), true);
    }

    private String noun(ArrayList<NounType> subjectWordTypes) {
        String word = "";

        while (word.length() == 0) {
            Noun noun = (subjectWordTypes == null ? getNounWord() : getNounWord(subjectWordTypes));

            if (noun.isCountable() && 0.4 > random.nextDouble()) {
                word = WordModifier.capital(noun.getPlural());
            } else {
                word = WordModifier.capital(noun.getSingular());
            }
        }

        return word;
    }

    private String nounSingular() {
        return WordModifier.capital(getNounWord().getSingular());
    }

    private Noun getNounWord(ArrayList<NounType> subjectWordTypes) {
        return wordLibrary.getNoun(subjectWordTypes, true);
    }

    private Noun getNounWord() {
        return wordLibrary.getNoun(new ArrayList<>(Arrays.asList(NounType.values())), true);
    }

    private String adjective() {
        String preword = "";
        String prefix = "";
        String word = "";

        Double d = random.nextDouble();
        if (0.1 > d) {
            preword = "ever ";
        } else if (0.2 > d) {
            preword = "never ";
        } else if (0.3 > d) {
            preword = "partly ";
        }

        if (0.75 > random.nextDouble()) {
            Adjective adjective = getAdjectiveWord();

            d = random.nextDouble();
            if (0.8 > d) {
                word = adjective.getPositive();
            } else if (0.87 > d) {
                word = adjective.getComparative();
            } else {
                word = adjective.getSuperlative();
            }
        } else {
            Verb verb = getVerbWord();

            d = random.nextDouble();
            if (0.1 > d) {
                prefix = "un";
            } else if (0.2 > d) {
                prefix = "re";
            } else if (0.3 > d) {
                prefix = "anti";
            }

            if (0.65 > random.nextDouble()) {
                word = verb.getPresentParticiple();
            } else {
                word = verb.getPastParticiple();
            }
        }

        return preword + prefix + word;
    }

    private Adjective getAdjectiveWord() {
        return wordLibrary.getAdjective(new ArrayList<>(Arrays.asList(AdjectiveType.values())), true);
    }

    private Verb getVerbWord() {
        return wordLibrary.getVerb(new ArrayList<>(Arrays.asList(VerbType.values())), true);
    }
}
