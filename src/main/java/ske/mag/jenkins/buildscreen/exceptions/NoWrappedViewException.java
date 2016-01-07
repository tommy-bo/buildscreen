package ske.mag.jenkins.buildscreen.exceptions;

import javax.servlet.ServletException;

public class NoWrappedViewException extends ServletException {
	public NoWrappedViewException(String message) {
		super(message);
	}

}
