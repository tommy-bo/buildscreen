package ske.mag.jenkins.buildscreen;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean(defaultVisibility = 5)
public class FailedJob {

	private String name;
	private String url;
	private String culprits;
	private Claim claim;
	private boolean building;
	private boolean queued;
	private Status buildStatus;
	private Date lastSuccessfulBuildTime;
	private String culpritIds;
	private String description;

	@Exported
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Exported
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Exported
	public String getCulpritIds() {
		return culpritIds;
	}

	public void setCulpritIds(String culpritIds) {
		this.culpritIds = culpritIds;
	}

	@Exported
	public String getCulprits() {
		return culprits;
	}

	public void setCulprits(String culprits) {
		this.culprits = culprits;
	}

	@Exported
	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	
	@Exported
	public boolean isClaimed() {
		return claim != null;
	}

	@Exported
	public boolean isBuilding() {
		return building;
	}

	public void setBuilding(boolean building) {
		this.building = building;
	}

	@Exported
	public boolean isQueued() {
		return queued;
	}

	public void setQueued(boolean queued) {
		this.queued = queued;
	}

	@Exported
	public Date getLastSuccessfulBuildTime() {
		return lastSuccessfulBuildTime;
	}

	public void setLastSuccesfulBuildTime(Date lastSuccessfulBuildTime) {
		this.lastSuccessfulBuildTime = lastSuccessfulBuildTime;
	}
	
	@Exported
	public String getLastSuccessfulBuildText() {
		if(lastSuccessfulBuildTime == null) {
			return "like forever";
		}
		return new SimpleDateFormat("hh:mm:ss dd.MM.yyyy").format(lastSuccessfulBuildTime);
	}
	
	@Exported
	public Status getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(Status buildStatus) {
		this.buildStatus = buildStatus;
	}

	@Exported
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
	public static Builder ny() {
		return new Builder();
	}

	public static class Builder {
		private FailedJob template = new FailedJob();
		
		public Builder name(String name) {
			template.setName(name);
			return this;
		}
		
		public Builder culprits(String culprits) {
			template.setCulprits(culprits);
			return this;
		}
		
		public Builder claim(Claim claim) {
			template.setClaim(claim);
			return this;
		}
		
		public Builder building(boolean building) {
			template.setBuilding(building);
			return this;
		}
		
		public Builder queued(boolean queued) {
			template.setQueued(queued);
			return this;
		}
		
		public Builder lastSuccessfulBuildTime(Date lastSuccessfulBuildTime) {
			template.setLastSuccesfulBuildTime(lastSuccessfulBuildTime);
			return this;
		}
		
		public Builder buildStatus(Status status) {
			template.setBuildStatus(status);
			return this;
		}
		
		public Builder description(String description) {
			template.setDescription(description);
			return this;
		}
		
		public FailedJob bygg() {
			return template;
		}
	}
}
