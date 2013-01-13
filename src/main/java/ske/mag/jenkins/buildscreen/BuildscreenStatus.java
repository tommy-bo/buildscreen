package ske.mag.jenkins.buildscreen;

import hudson.model.IViewEntry;
import java.util.List;
import java.util.TreeSet;
import net.sf.json.JSONObject;

public class BuildscreenStatus {
	private String statusTime;
	private String status;
	private List<FailedJob> failedJobs;

	public String getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FailedJob> getFailedJobs() {
		return failedJobs;
	}

	public void setFailedJobs(List<FailedJob> failedJobs) {
		this.failedJobs = failedJobs;
	}

	public JSONObject toJson() {
		return JSONObject.fromObject(this);
	}
}
