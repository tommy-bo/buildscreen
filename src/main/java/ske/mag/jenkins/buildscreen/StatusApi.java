package ske.mag.jenkins.buildscreen;

import hudson.model.Api;

public class StatusApi {
	private BuildscreenStatus buildscreenStatus;

	public StatusApi(BuildscreenStatus buildscreenStatus) {
		this.buildscreenStatus = buildscreenStatus;
	}

	public Api getApi() {
		return new Api(buildscreenStatus);
	}
}
