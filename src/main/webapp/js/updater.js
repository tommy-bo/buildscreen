StatusUpdater = function(updateHandler, successSound, failureSound) {
	window.sounds = {"FAILED": new buzz.sound(failureSound),
                   "SUCCESS": new buzz.sound(successSound)};
	this.updateHandler = updateHandler;
	
	this.updateId= function(id) {
		this.updateId = id;
		return this;
	}
	
	this.withField = function(fieldFromResult) {
		this.fieldFromResult = fieldFromResult;
		return this;
	}
	
	this.everySeconds = function(updateIntevalInSeconds) {
		this.updateIntevalInSeconds = updateIntevalInSeconds;
		if(!this.intervalHandle) {
			clearInterval(this.intervalHandle);
		}
		this.intervalHandle = setInterval(function(updater) {
			updater.update();
		}, this.updateIntevalInSeconds * 1000, this);
		return this;
	}
	
	this.update = function() {
		this.updateHandler.updateStatus(this.updateView);
	}
	
	this.updateView = function(buildscreenUpdate) {
		newStatus = buildscreenUpdate.responseObject()
		$("mainDisplay").removeClassName(buildScreenStatus.status);
		$("mainDisplay").addClassName(newStatus.status);
		if(newStatus.status != window.buildScreenStatus.status) {
			window.sounds[newStatus.status].play();
		}
		if(newStatus.status == 'FAILED') {
			$("failedJobsList").update();
			newStatus.failedJobs.forEach(function(failedJob) {
				failedJobElement = new Element("li");
				failedJobElement.appendChild(new Element("h1").update(failedJob.name));
				failedJobElement.appendChild(new Element("div", {'class': 'culprits'}).update(failedJob.culprits));
				if(failedJob.claimed) {
					failedJobElement.appendChild(new Element("div", {'class': 'claimedBy'}).update(failedJob.claim));
				}
				$("failedJobsList").appendChild(failedJobElement);
			});
		}
		$("status").update(buildScreenStatus.statusTime);
		window.buildScreenStatus = newStatus;
	}
}
