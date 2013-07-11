BuildScreenUpdater = function(successSound, failureSound) {

	window.sounds = {"FAILED": new buzz.sound(failureSound),
		"SUCCESS": new buzz.sound(successSound)};

	this.everySeconds = function(updateIntevalInSeconds) {
		this.updateIntevalInSeconds = updateIntevalInSeconds;
		if (!this.intervalHandle) {
			clearInterval(this.intervalHandle);
		}
		this.intervalHandle = setInterval(function(mySelf) {
			mySelf.update();
		}, this.updateIntevalInSeconds * 1000, this);
		return this;
	};
	
	this.update = function() {
		new Ajax.Request(window.location.href + "statusApi/api/json", {
			onSuccess: function(response) {
				if(response.status === 200) {
					$("disconnectedOverlay").removeClassName("disconnected");
					new ViewUpdater(eval("(" + response.responseText + ")")).updateStatus();
				}
				if(response.status === 0) {
					$("disconnectedOverlay").addClassName("disconnected");
				}
			},
			onFailure: function(response) {
				console.log(response.status + ": " + response.statusText);
			}
		});
	};
};

ViewUpdater = function(updateStatus) {
	this.newBuildScreenStatus = updateStatus;

	this.updateStatus = function() {
		if(window.activeBuildScreenStatus) {
			$("mainDisplay").removeClassName(window.activeBuildScreenStatus.status);
			if(this.newBuildScreenStatus.status !== window.activeBuildScreenStatus.status) {
				window.sounds[this.newBuildScreenStatus.status].play();
			}
		}
		$("mainDisplay").addClassName(this.newBuildScreenStatus.status);
		if(this.newBuildScreenStatus.status === 'FAILED') {
			var result;
			dust.render("failedBuilds", this.newBuildScreenStatus, function(err, res) {
				if (err) {
					console.error(err);
				}
				result = res;
			});
			$("failedBuilds").update(result);
		}
		$("status").update(this.newBuildScreenStatus.statusTime);
		window.activeBuildScreenStatus = this.newBuildScreenStatus;
	};
};