package ch.mixin.namegenerator.word.adjective;

public class Adjective {
	private String positive;
	private String comparative;
	private String superlative;

	public Adjective(String positive, String comparative, String superlative) {
		super();
		this.positive = positive;
		this.comparative = comparative;
		this.superlative = superlative;
	}

	public String getPositive() {
		return positive;
	}

	public void setPositive(String positive) {
		this.positive = positive;
	}

	public String getComparative() {
		return comparative;
	}

	public void setComparative(String comparative) {
		this.comparative = comparative;
	}

	public String getSuperlative() {
		return superlative;
	}

	public void setSuperlative(String superlative) {
		this.superlative = superlative;
	}
}
