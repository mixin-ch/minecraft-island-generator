package ch.mixin.namegenerator.word.verb;

public class Verb {
	private String infinitive;
	private String thirdPerson;
	private String pastParticiple;
	private String presentParticiple;

	public Verb(String infinitive, String thirdPerson, String pastParticiple, String presentParticiple) {
		super();
		this.infinitive = infinitive;
		this.thirdPerson = thirdPerson;
		this.pastParticiple = pastParticiple;
		this.presentParticiple = presentParticiple;
	}

	public String getInfinitive() {
		return infinitive;
	}

	public void setInfinitive(String infinitive) {
		this.infinitive = infinitive;
	}

	public String getThirdPerson() {
		return thirdPerson;
	}

	public void setThirdPerson(String thirdPerson) {
		this.thirdPerson = thirdPerson;
	}

	public String getPastParticiple() {
		return pastParticiple;
	}

	public void setPastParticiple(String pastParticiple) {
		this.pastParticiple = pastParticiple;
	}

	public String getPresentParticiple() {
		return presentParticiple;
	}

	public void setPresentParticiple(String presentParticiple) {
		this.presentParticiple = presentParticiple;
	}
}
