package ske.mag.jenkins.buildscreen;

import com.google.common.collect.Lists;
import hudson.model.IViewEntry;
import java.util.List;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;

public class FailedJobs {

	public static List<FailedJob> newFromViewEntries(TreeSet<IViewEntry> failedJobs) {
		List<FailedJob> convertedList = Lists.newArrayList();
		for (IViewEntry iViewEntry : failedJobs) {
			FailedJob failedJob = new FailedJob();
			failedJob.setName(iViewEntry.getName());
			failedJob.setCulprits(StringUtils.join(iViewEntry.getCulprits(), ", "));
			failedJob.setClaim(iViewEntry.getClaim());
			failedJob.setBuilding(iViewEntry.getBuilding());
			failedJob.setQueued(iViewEntry.getQueued());
			convertedList.add(failedJob);
		}
		return convertedList;
	}
	
}
