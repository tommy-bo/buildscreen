package ske.mag.jenkins.buildscreen;

import java.util.List;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 5)
public class BuildscreenStatus {
	private List<FailedJob> failedJobs;
	private List<FailedJob> unstableJobs;

	@Exported
	public Status getStatus() {
		return failedJobs.isEmpty() ? unstableJobs.isEmpty() ? Status.STABLE : Status.UNSTABLE : Status.FAILED;
	}

	@Exported
	public List<FailedJob> getFailedJobs() {
		return failedJobs;
	}

	public void setFailedJobs(List<FailedJob> failedJobs) {
		this.failedJobs = failedJobs;
	}

	@Exported
	public List<FailedJob> getUnstableJobs() {
		return unstableJobs;
	}

	public void setUnstableJobs(List<FailedJob> unstableJobs) {
		this.unstableJobs = unstableJobs;
	}

	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}
}
