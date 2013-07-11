package ske.mag.jenkins.buildscreen;

import hudson.model.Result;

public enum Status {
	FAILED,
	UNSTABLE,
	STABLE;
	
	public static Status fromResult(Result result) {
		if(Result.FAILURE.isBetterOrEqualTo(result)) {
			return Status.FAILED;
		} else if(Result.UNSTABLE.isBetterOrEqualTo(result)) {
			return Status.STABLE;
		} else {
			return Status.UNSTABLE;
		}
	}
}
