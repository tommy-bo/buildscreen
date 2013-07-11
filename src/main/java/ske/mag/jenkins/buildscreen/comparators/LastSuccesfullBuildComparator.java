package ske.mag.jenkins.buildscreen.comparators;

import com.google.common.base.Objects;
import java.util.Comparator;
import java.util.Date;
import ske.mag.jenkins.buildscreen.FailedJob;
import static ske.mag.jenkins.buildscreen.comparators.ComparingConstants.*;

public class LastSuccesfullBuildComparator implements Comparator<FailedJob> {

	public int compare(FailedJob firstJob, FailedJob secondJob) {
		final Date firstJobBuildTime = firstJob.getLastSuccessfulBuildTime();
		final Date SecondJobBuildTime = secondJob.getLastSuccessfulBuildTime();
		if (Objects.equal(firstJobBuildTime, SecondJobBuildTime)) {
			return SAME;
		}
		if (firstJobBuildTime == null) {
			return FIRST_JOB_FIRST;
		}
		return firstJobBuildTime.compareTo(SecondJobBuildTime);
	}

}
