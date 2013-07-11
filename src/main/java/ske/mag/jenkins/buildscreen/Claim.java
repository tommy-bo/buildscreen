package ske.mag.jenkins.buildscreen;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 5)
public class Claim implements Comparable<Claim> {
	private final String claimedBy;
	private String reason;

	private Claim(String claimedBy) {
		this.claimedBy = claimedBy;
	}

	static Claim claimFor(String claimedBy) {
		return new Claim(claimedBy);
	}

	Claim withReason(String reason) {
		this.reason = reason;
		return this;
	}

	@Exported
	public String getClaimedBy() {
		return claimedBy;
	}

	@Exported
	public String getReason() {
		return reason;
	}

	public int compareTo(Claim o) {
		return this.toString().compareTo(o.toString());
	}

	@Override
	public String toString() {
		return claimedBy + "(" + reason + ")";
	}
}
