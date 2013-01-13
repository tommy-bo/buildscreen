package ske.mag.jenkins.buildscreen;

public class FailedJob {

	private String name;
	private String culprit;
	private String claim;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCulprit() {
		return culprit;
	}

	public void setCulprit(String culprit) {
		this.culprit = culprit;
	}

	public String getClaim() {
		return claim;
	}

	public void setClaim(String claim) {
		this.claim = claim;
	}
	
	public boolean isClaimed() {
		return claim != null;
	}
}
