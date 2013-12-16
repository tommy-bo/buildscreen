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
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 100)
public class BuildScreenView extends ListView {

	private Integer pageRefreshInHours = 24;
	private Integer pollingIntervalInSeconds;
	private Integer rotationInSeconds;
	private boolean playSounds;
	private List<String> pages;

	@DataBoundConstructor
	public BuildScreenView(
					String name,
					ViewGroup owner) {
		super(name, owner);
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

	public Integer getPageRefreshInSeconds() {
		return pageRefreshInHours * 60 * 60;
	}

	public Integer getPollingIntervalInSeconds() {
		return pollingIntervalInSeconds;
	}

	public Integer getRotationInSeconds() {
		return rotationInSeconds;
	}

	public boolean isPlaySounds() {
		return playSounds;
	}

	public void setPlaySounds(boolean playSounds) {
		this.playSounds = playSounds;
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
		JSONObject json = req.getSubmittedForm();
		this.pollingIntervalInSeconds = json.getInt("pollingIntervalInSeconds");
		this.rotationInSeconds = json.getInt("rotationInSeconds");
		this.playSounds = json.getBoolean("playSounds");
		setPages(req.getParameterValues("page"));
	}
	
	@Extension
	public static final class DescriptorImpl extends ViewDescriptor {

		public String getDisplayName() {
			return Messages.DisplayName();
		}
	}
}
