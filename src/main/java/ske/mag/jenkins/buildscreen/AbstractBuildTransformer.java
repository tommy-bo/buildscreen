package ske.mag.jenkins.buildscreen;

import static com.google.common.collect.Collections2.transform;
import hudson.model.AbstractBuild;
import java.util.Collection;
import jenkins.model.Jenkins;

public class AbstractBuildTransformer {

	private final Jenkins jenkins;
	private final Collection<AbstractBuild> buildsToConvert;

	public AbstractBuildTransformer(Jenkins jenkins, Collection<AbstractBuild> buildsToConvert) {
		this.jenkins = jenkins;
		this.buildsToConvert = buildsToConvert;
	}

	public Collection<FailedJob> toFailedJobs() {
		return transform(buildsToConvert, new AbstractBuildToFailedJobTransformFunction(jenkins));
	}
}
