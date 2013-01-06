StatusUpdater = function(updateHandler) {
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
		$("status").update(buildscreenUpdate.responseObject().statusTime);
	}
}
