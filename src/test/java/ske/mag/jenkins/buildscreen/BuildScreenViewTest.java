package ske.mag.jenkins.buildscreen;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Lists;
import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;
import java.util.ArrayList;
import org.fest.assertions.api.Assertions;
import org.jvnet.hudson.test.HudsonTestCase;

public class BuildScreenViewTest /*extends HudsonTestCase*/ {

	private static final String VIEW = "bs";

	public void testTitleInView() throws Exception {
//		FreeStyleProject project = createFreeStyleProject();
//		project.getBuildersList().add(new Shell("exit 1"));
//		project.scheduleBuild2(0).get();
//		final ArrayList<String> greenScreenPages = Lists.newArrayList();
//		final BuildScreenView view = new BuildScreenView(VIEW, false, false, greenScreenPages, super.jenkins);
//		view.add(project);
//		super.jenkins.addView(view);
//		HtmlPage page = new WebClient().goTo("view/" + VIEW);
//		HtmlElement mainDisplay = page.getElementById("mainDisplay");
//		String classAttribute = mainDisplay.getAttribute("class");
//		Assertions.assertThat(classAttribute).contains(Status.FAILED.name());
	}
}
