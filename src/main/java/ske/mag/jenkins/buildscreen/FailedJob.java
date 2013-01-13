package ske.mag.jenkins.buildscreen;

public class FailedJob {

	private String name;
	private String culprits;
	private String claim;
	private boolean building;
	private boolean queued;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCulprits() {
		return culprits;
	}

	public void setCulprits(String culprits) {
		this.culprits = culprits;
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

	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	public boolean isQueued() {
		return queued;
	}

	public void setQueued(boolean queued) {
		this.queued = queued;
	}
}
