package ske.mag.jenkins.buildscreen;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.IViewEntry;
import hudson.model.RadiatorView;
import hudson.model.ViewDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.ServletException;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class ByggeskjermView extends RadiatorView {

	private boolean playSoundOnFail;
	private boolean playSoundOnStable;
	private List<String> pages;

	@DataBoundConstructor
	public ByggeskjermView(String name, boolean playSoundOnFail, boolean playSoundOnStable, List<String> pages) {
		this(name);
		this.playSoundOnFail = playSoundOnFail;
		this.playSoundOnStable = playSoundOnStable;
		this.pages = pages;
	}

	@DataBoundConstructor
	public ByggeskjermView(String name) {
		super(name);
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
		setPages(Arrays.asList(pages));
	}
	
	public TreeSet<IViewEntry> getFailedJobs() {
		return getContents().getFailingJobs();
	}
	
	public boolean isBroken() {
		return getContents().getBroken();
	}
	
	public String getTimeSinceLastFailure() {
		return getBuilds().failureOnly().getLastBuild().getTimestampString();
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
