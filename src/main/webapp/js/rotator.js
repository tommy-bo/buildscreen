Rotator = function(classToRotate, classForVisibleElement) {
	this.index = 0;
	this.classToRotate = classToRotate;
	this.classForVisibleElement = classForVisibleElement;
		
	this.rotatePages = function(rotatingIntervalInSeconds) {
		this.intervalHandle = setInterval(function(rotator) {
			rotator.showNextView();
		}, rotatingIntervalInSeconds * 1000, this);
	}
		
	this.showNextView = function() {
		viewElements = $$('.pageView');
		nextIndex = (this.index + 1) % viewElements.length;
		currentElement = viewElements[this.index]
		nextElement = this.refreshPage(viewElements[nextIndex]);
		this.allowPageToRefreshBeforeShowing.delay(10, currentElement, nextElement);
		this.index = nextIndex;
	}
	
	this.allowPageToRefreshBeforeShowing = function(currentElement, nextElement) {
		currentElement.removeClassName('visible');
		nextElement.addClassName('visible');
	}
	
	this.refreshPage = function(pageObject) {
		child = pageObject.getElementsBySelector('object')[0];
		child.setAttribute('data', child.getAttribute('data'));
		return pageObject;
	}
}
