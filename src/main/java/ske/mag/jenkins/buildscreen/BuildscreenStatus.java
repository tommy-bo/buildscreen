package ske.mag.jenkins.buildscreen;

import static com.google.common.collect.Iterables.concat;
import static java.lang.System.currentTimeMillis;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 5)
public class BuildscreenStatus {
	private List<FailedJob> failedJobs;
	private List<FailedJob> unstableJobs;
	private static final long extremeLimit = 24;
	private static final long extremeLimitInMilliseconds = extremeLimit * 60 * 60 * 1000;

	@Exported
	public Status getStatus() {
		return failedJobs.isEmpty() ? unstableJobs.isEmpty() ? Status.STABLE : Status.UNSTABLE : Status.FAILED;
	}

	@Exported
	public boolean isExtreme() {
		for (FailedJob failedJob : concat(failedJobs, unstableJobs)) {
			if(isLongEnoughToBeExtreme(failedJob.getLastSuccessfulBuildTime())) {
				return true;
			}
		}
		return false;
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

	private boolean isLongEnoughToBeExtreme(Date buildTime) {
		final Date now = new Date(currentTimeMillis() - extremeLimitInMilliseconds);
		return buildTime != null && buildTime.before(now);
	}
}
