package ske.mag.jenkins.buildscreen;

import com.google.common.base.Function;
import static com.google.common.collect.Iterables.transform;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.User;
import hudson.plugins.claim.AbstractClaimBuildAction;
import java.util.Date;
import jenkins.model.Jenkins;
import static org.apache.commons.lang.StringUtils.join;
import static ske.mag.jenkins.buildscreen.Claim.claimFor;

public class AbstractBuildToFailedJobTransformFunction implements Function<AbstractBuild, FailedJob> {

	private final Jenkins jenkins;

	public AbstractBuildToFailedJobTransformFunction(Jenkins jenkins) {
		this.jenkins = jenkins;
	}

	public FailedJob apply(AbstractBuild build) {
		final String fullProjectName = build.getProject().getFullName();
		FailedJob failedJob = new FailedJob();
		failedJob.setName(fullProjectName + " (" + build.getDisplayName() + ")");
		failedJob.setDescription(build.getDescription());
		failedJob.setUrl(jenkins.getRootUrl() + build.getUrl());
		failedJob.setCulpritIds(join(transform(build.getCulprits(), getId()).iterator(), ", "));
		failedJob.setCulprits(join(build.getCulprits(), ", "));
		failedJob.setClaim(findClaims(build));
		failedJob.setBuilding(isNextBuildBuilding(build));
		failedJob.setQueued(jenkins.getQueue().contains(build.getProject()));
		failedJob.setBuildStatus(Status.fromResult(build.getResult()));
		failedJob.setLastSuccesfulBuildTime(getLastSuccessfulBuildTime(fullProjectName));
		return failedJob;
	}

	private Date getLastSuccessfulBuildTime(String jobName) {
		AbstractProject resolvedProject = jenkins.getItemByFullName(jobName, AbstractProject.class);
		if (resolvedProject == null) {
			return null;
		}
		Run lastSuccessfulBuild = resolvedProject.getLastStableBuild();
		return lastSuccessfulBuild != null ? lastSuccessfulBuild.getTime() : null;
	}

	private static Claim findClaims(AbstractBuild build) {
		AbstractClaimBuildAction claimAction = build.getAction(AbstractClaimBuildAction.class);
		if(claimAction != null && claimAction.isClaimed()) {
				return claimFor(claimAction.getClaimedByName()).withReason(claimAction.getReason());
		}
		return null;
	}

	private boolean isNextBuildBuilding(AbstractBuild build) {
		return build.getNextBuild() != null && build.getNextBuild().isBuilding();
	}

	private static Function<User, String> getId() {
		return new Function<User, String>() {
			public String apply(User user) {
				return user.getId();
			}
		};
	}
}
