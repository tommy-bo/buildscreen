package ske.mag.jenkins.buildscreen;

import com.google.common.collect.Lists;
import hudson.model.IViewEntry;
import java.util.List;
import java.util.TreeSet;

public class FailedJobs {

	public static List<FailedJob> newFromViewEntries(TreeSet<IViewEntry> failedJobs) {
		List<FailedJob> convertedList = Lists.newArrayList();
		for (IViewEntry iViewEntry : failedJobs) {
			FailedJob failedJob = new FailedJob();
			failedJob.setName(iViewEntry.getName());
			failedJob.setCulprit(iViewEntry.getCulprit());
			failedJob.setClaim(iViewEntry.getClaim());
			convertedList.add(failedJob);
		}
		return convertedList;
	}
	
}
