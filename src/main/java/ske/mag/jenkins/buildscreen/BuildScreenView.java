package ske.mag.jenkins.buildscreen;

import hudson.Extension;
import hudson.model.Api;
import hudson.model.Descriptor;
import hudson.model.ListView;
import hudson.model.ViewDescriptor;
import hudson.model.ViewGroup;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 100)
public class BuildScreenView extends ListView {

	private boolean playSoundOnFail;
	private boolean playSoundOnStable;
	private List<String> pages;

	@DataBoundConstructor
	public BuildScreenView(String name, boolean playSoundOnFail, boolean playSoundOnStable, List<String> pages, ViewGroup owner) {
		super(name, owner);
		this.playSoundOnFail = playSoundOnFail;
		this.playSoundOnStable = playSoundOnStable;
		this.pages = pages;
	}

	@Override
	public Api getApi() {
		return new Api(this);
	}
	
	@JavaScriptMethod
	public BuildscreenStatus updateStatus() {
		return getStatus();
	}
	
	@Exported
	public StatusApi getStatusApi() {
		return new StatusApi(getStatus());
	}

	public BuildscreenStatus getStatus() {
		BuildscreenStatus update = new BuildscreenStatus();
		update.setFailedJobs(FailedJobs.brokenBuilds(getBuilds()));
		update.setUnstableJobs(FailedJobs.failedBuilds(getBuilds()));
		return update;
	}

	public boolean isPlaySoundOnFail() {
		return playSoundOnFail;
	}

	public void setPlaySoundOnFail(boolean playSoundOnFail) {
		this.playSoundOnFail = playSoundOnFail;
	}

	public boolean isPlaySoundOnStable() {
		return playSoundOnStable;
	}

	public void setPlaySoundOnStable(boolean playSoundOnStable) {
		this.playSoundOnStable = playSoundOnStable;
	}

	public List<String> getPages() {
		return pages;
	}

	public void setPages(List<String> pages) {
		this.pages = pages;
	}
	
	public void setPages(String[] pages) {
		if(pages != null) {
			setPages(Arrays.asList(pages));
		}
	}

	@Override
	protected void submit(StaplerRequest req) throws ServletException, IOException, 
			Descriptor.FormException {
		super.submit(req);
		setPlaySoundOnFail(Boolean.parseBoolean(req.getParameter("playSoundOnFail")));
		setPlaySoundOnStable(Boolean.parseBoolean(req.getParameter("playSoundOnStable")));
		setPages(req.getParameterValues("page"));
	}
	
	@Extension
	public static final class DescriptorImpl extends ViewDescriptor {

		public String getDisplayName() {
			return Messages.DisplayName();
		}
	}
}
