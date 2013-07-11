package ske.mag.jenkins.buildscreen;

import com.google.common.collect.Lists;
import hudson.model.Run;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import jenkins.model.Jenkins;
import ske.mag.jenkins.buildscreen.comparators.ClaimComparator;
import ske.mag.jenkins.buildscreen.comparators.LastSuccesfullBuildComparator;
import static com.google.common.collect.Ordering.from;
import hudson.model.AbstractBuild;
import static hudson.model.Result.FAILURE;
import static hudson.model.Result.UNSTABLE;
import static ske.mag.jenkins.buildscreen.collections.CollectionFilter.filter;
import hudson.util.RunList;
import static ske.mag.jenkins.buildscreen.Claim.claimFor;

public class FailedJobs {

	public static List<FailedJob> brokenBuilds(RunList<Run> allBuilds) {
		return sortByClaimAndLastSuccessfulBuild(convertList(filter(allBuilds).byResult(FAILURE).byLastCompletedBuilds()));
	}

	public static List<FailedJob> failedBuilds(Collection<Run> allBuilds) {
		return sortByClaimAndLastSuccessfulBuild(convertList(filter(allBuilds).byResult(UNSTABLE).byLastCompletedBuilds()));
	}

	private static Collection<FailedJob> convertList(Collection<AbstractBuild> buildsToConvert) {
		return transform(buildsToConvert).toFailedJobs();
	}

	private static List<FailedJob> createBrokenDummyBuilds() {
		return Lists.newArrayList(
						FailedJob.ny()
						.name("Dette er bygg nr1")
						.culprits("Tommy Bø")
						.buildStatus(Status.FAILED)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 165435641))
						.bygg(),
						FailedJob.ny()
						.name("Andre feilet bygg")
						.culprits("Tommy Bø")
						.building(true)
						.buildStatus(Status.FAILED)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 175435641))
						.bygg(),
						FailedJob.ny()
						.name("Da var det tredje i listen")
						.claim(claimFor("Tommy Bø").withReason("any reason"))
						.queued(true)
						.buildStatus(Status.FAILED)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 185435641))
						.bygg(),
						FailedJob.ny()
						.name("Fire")
						.culprits("Tommy Bø")
						.claim(claimFor("Tommy Bø").withReason("who else will do it?"))
						.buildStatus(Status.FAILED)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 15435641))
						.bygg());
	}

	private static List<FailedJob> createFailedDummyBuilds() {
		return Lists.newArrayList(
						FailedJob.ny()
						.name("Her er fem")
						.culprits("Tommy Bø")
						.buildStatus(Status.UNSTABLE)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 146875641))
						.bygg(),
						FailedJob.ny()
						.name("bygg-nr-6")
						.queued(true)
						.buildStatus(Status.UNSTABLE)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 179435641))
						.bygg(),
						FailedJob.ny()
						.name("Til slutt nr syv")
						.culprits("Tommy Bø")
						.claim(claimFor("Tommy Bø").withReason("This is entirely my fault. I even did it on purpose"))
						.building(true)
						.buildStatus(Status.UNSTABLE)
						.lastSuccessfulBuildTime(new Date(System.currentTimeMillis() - 185435641))
						.bygg());
	}

	private static List<FailedJob> sortByClaimAndLastSuccessfulBuild(Collection<FailedJob> jobsToSort) {
		return from(new ClaimComparator()).compound(new LastSuccesfullBuildComparator()).sortedCopy(jobsToSort);
	}

	private static AbstractBuildTransformer transform(Collection<AbstractBuild> buildsToConvert) {
		return new AbstractBuildTransformer(Jenkins.getInstance(), buildsToConvert);
	}
}
