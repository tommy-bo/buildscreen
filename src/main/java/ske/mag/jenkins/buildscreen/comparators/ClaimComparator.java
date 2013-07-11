package ske.mag.jenkins.buildscreen.comparators;

import com.google.common.base.Objects;
import java.util.Comparator;
import ske.mag.jenkins.buildscreen.FailedJob;
import static ske.mag.jenkins.buildscreen.comparators.ComparingConstants.*;

public class ClaimComparator implements Comparator<FailedJob> {

	public int compare(FailedJob firstJob, FailedJob secondJob) {
		if (Objects.equal(firstJob.getClaim(), secondJob.getClaim())) {
			return SAME;
		}
		if (firstJob.getClaim() == null) {
			return FIRST_JOB_FIRST;
		} else {
			return firstJob.getClaim().compareTo(secondJob.getClaim());
		}
	}

}
