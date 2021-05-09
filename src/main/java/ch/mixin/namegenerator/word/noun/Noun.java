package ch.mixin.namegenerator.word.noun;

public class Noun {
	protected final String singular;
	protected final String plural;
	protected final boolean countable;

	public Noun(String singular, String plural, boolean countable) {
		super();
		this.singular = singular;
		this.plural = plural;
		this.countable = countable;
	}

	public String getSingular() {
		return singular;
	}

	public String getPlural() {
		return plural;
	}

	public boolean isCountable() {
		return countable;
	}
}
