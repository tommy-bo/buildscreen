package ske.mag.jenkins.buildscreen.collections;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import hudson.model.AbstractBuild;
import hudson.model.FreeStyleProject;
import hudson.model.TopLevelItem;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CollectionFilterTest {
    
    @Test
    public void testCastOfNull() {
        assertNull(AbstractBuild.class.cast(null));
    }

    @Test
    public void getLastBuildFromItemDoesntFailWhenItsNull() {
        FreeStyleProject jobNeverExecuted = mock(FreeStyleProject.class);
        when(jobNeverExecuted.getLastCompletedBuild()).thenReturn(null);
        AbstractBuild lastBuild = CollectionFilter.getLastBuildFromItem.apply(jobNeverExecuted);
        assertEquals(null, lastBuild);
    }

    @Test
    public void filterRemovesNullValues() {
        List<TopLevelItem> allJobs = new ArrayList<TopLevelItem>();
        for (int i = 0; i < 50; i++) {
            allJobs.add(mock(FreeStyleProject.class));
        }
        assertEquals(0, CollectionFilter.filter(allJobs).size());
    }
}
