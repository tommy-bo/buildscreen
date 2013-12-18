BuildScreenUpdater = function() {
	window.sounds = {};

	this.goLoud = function(goLoud) {
		window.sounds.goLoud = goLoud;
		return this;
	};
	
	this.playOnSuccess = function(successSound) {
		window.sounds["STABLE"] = new buzz.sound(successSound);
		return this;
	};

		this.playOnFailure = function(failureSound) {
		window.sounds["FAILED"] = new buzz.sound(failureSound);
		return this;
	};

	this.playOnExtremeFailure = function(failureSound) {
		window.sounds["EXTREME"] = new buzz.sound(failureSound);
		return this;
	};

	this.playOnUnstable = function(unstableSound) {
		window.sounds["UNSTABLE"] = new buzz.sound(unstableSound);
		return this;
	};


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
			if(this.newBuildScreenStatus.status !== window.activeBuildScreenStatus.status
				&& window.sounds.goLoud === true) {
				window.sounds[this.newBuildScreenStatus.status].play().bind("ended", function() {
					meSpeak.speak('list new culprits here...');
				});
			}
		}
		$("mainDisplay").addClassName(this.newBuildScreenStatus.status);
		if(this.newBuildScreenStatus.status === 'FAILED' || this.newBuildScreenStatus.status === 'UNSTABLE') {
			var result;
			dust.render("failedBuilds", this.newBuildScreenStatus, function(err, res) {
				if (err) {
					console.error(err);
				}
				result = res;
			});
			$("failedBuilds").update(result);
		}
		window.activeBuildScreenStatus = this.newBuildScreenStatus;
	};
};