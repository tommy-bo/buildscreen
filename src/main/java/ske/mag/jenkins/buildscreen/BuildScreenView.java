package ske.mag.jenkins.buildscreen;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.google.common.base.Optional;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;
import hudson.Extension;
import hudson.model.Api;
import hudson.model.Descriptor;
import hudson.model.Item;
import hudson.model.TopLevelItem;
import hudson.model.View;
import hudson.model.ViewDescriptor;
import hudson.model.ViewGroup;
import hudson.util.ListBoxModel;
import hudson.util.RunList;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

@ExportedBean(defaultVisibility = 100)
public class BuildScreenView extends View {

    private String basedOnView;
	private Integer pageRefreshInHours = 24;
	private Integer pollingIntervalInSeconds;
	private Integer rotationInSeconds;
	private boolean playSounds;
    private boolean talk;
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

    @SuppressWarnings("unchecked")
	public BuildscreenStatus getStatus() {
		BuildscreenStatus update = new BuildscreenStatus();
        Collection<TopLevelItem> items = getItems();
		update.setFailedJobs(FailedJobs.brokenBuilds(items));
		update.setUnstableJobs(FailedJobs.failedBuilds(items));
		return update;
	}

	public Integer getPageRefreshInSeconds() {
		return pageRefreshInHours * 60 * 60;
	}

	public Integer getPageRefreshInHours() {
		return pageRefreshInHours;
	}

	public Integer getPollingIntervalInSeconds() {
		return pollingIntervalInSeconds;
	}

	public Integer getRotationInSeconds() {
		return rotationInSeconds;
	}

    public String getBasedOnView() {
        return basedOnView;
    }

    public void setBasedOnView(String basedOnView) {
        this.basedOnView = basedOnView;
    }
    
    public View getWrappedView() {
        String[] paths = Optional.fromNullable(basedOnView).or("").split("\\\\");
        Jenkins jenkins = Jenkins.getInstance();
        ViewGroup currentViewGroup = jenkins;
        View currentView = jenkins.getPrimaryView();
        for (String path : paths) {
            currentView = currentViewGroup.getView(path);
            if(currentView instanceof ViewGroup) {
                currentViewGroup = (ViewGroup) currentView;
            }
        }
        return currentView;
    }

	public boolean isPlaySounds() {
		return playSounds;
	}

	public void setPlaySounds(boolean playSounds) {
		this.playSounds = playSounds;
	}

    public boolean isTalk() {
        return talk;
    }

    public void setTalk(boolean talk) {
        this.talk = talk;
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
		JSONObject json = req.getSubmittedForm();
		this.basedOnView = json.getString("basedOnView");
		this.pollingIntervalInSeconds = json.getInt("pollingIntervalInSeconds");
		this.rotationInSeconds = json.getInt("rotationInSeconds");
		this.pageRefreshInHours = json.getInt("pageRefreshInHours");
		this.playSounds = json.getBoolean("playSounds");
        this.talk = json.getBoolean("talk");
		setPages(req.getParameterValues("page"));
	}

    @Override
    public Collection<TopLevelItem> getItems() {
        View wrappedView = getWrappedView();
        if (wrappedView== null)
        	return Collections.emptyList();
		return wrappedView.getItems();
    }

    @Override
    public boolean contains(TopLevelItem item) {
        View wrappedView = getWrappedView();
		return wrappedView != null && wrappedView.contains(item);
    }

    @Override
    public Item doCreateItem(StaplerRequest request, StaplerResponse response) throws IOException, ServletException {
        View wrappedView = getWrappedView();
        if (wrappedView== null){
        	return null;
        }
		return wrappedView.doCreateItem(request, response);
    }
	
	@Extension
	public static final class DescriptorImpl extends ViewDescriptor {

		public String getDisplayName() {
			return Messages.DisplayName();
		}
        
		public ListBoxModel doFillBasedOnViewItems() {
            Collection<String> viewPaths = findViewsInViewGroup("", Jenkins.getInstance());
			ListBoxModel list = new ListBoxModel();
            for (String viewPath : viewPaths) {
				list.add(viewPath);
            }
			return list;
		}
        
        public Collection<String> findViewsInViewGroup(String prefix, ViewGroup currentViewGroup) {
            Collection<String> viewPaths = new ArrayList<String>();
            for (View view : currentViewGroup.getViews()) {
                if(view instanceof ViewGroup) {
                    ViewGroup subViewGroup = (ViewGroup) view;
                    viewPaths.addAll(findViewsInViewGroup(prefix + subViewGroup.getDisplayName() + "\\", subViewGroup));
                } else {
                    viewPaths.add(prefix + view.getDisplayName());
                }
            }
            return viewPaths;
        }
	}
}
