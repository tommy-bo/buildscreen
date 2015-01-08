package ske.mag.jenkins.buildscreen.collections;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import hudson.model.AbstractBuild;
import hudson.model.Result;
import hudson.model.Run;
import java.util.Collection;
import static com.google.common.collect.Collections2.*;
import java.util.AbstractCollection;
import java.util.Iterator;
import hudson.model.AbstractProject;
import hudson.model.TopLevelItem;

public class CollectionFilter extends AbstractCollection<AbstractBuild>{
	private final Collection<AbstractBuild> backingCollection;
	private final static Function<TopLevelItem, AbstractBuild> getLastBuildFromItem = new Function<TopLevelItem, AbstractBuild>(){
            @Override
			public AbstractBuild apply(TopLevelItem input) {
                Run lastCompletedBuild = input instanceof AbstractProject ? ((AbstractProject) input).getLastCompletedBuild() : null;
                return AbstractBuild.class.cast(lastCompletedBuild);
			}
		};

	private CollectionFilter(Collection<AbstractBuild> backingCollection) {
		this.backingCollection = backingCollection;
	}

	public static CollectionFilter filter(Collection<TopLevelItem> collectionToFilter) {
		return new CollectionFilter(transform(collectionToFilter, getLastBuildFromItem));
	}

	public CollectionFilter byResult(Result result) {
		return new CollectionFilter(Collections2.filter(backingCollection, new IncludeResultPredicate(result)));
	}

	public CollectionFilter onlyEnabledJobs() {
		return new CollectionFilter(Collections2.filter(backingCollection, new OnlyEnabledJobsPredicate()));
	}

	@Override
	public Iterator<AbstractBuild> iterator() {
		return backingCollection.iterator();
	}

	@Override
	public int size() {
		return backingCollection.size();
	}

	private class OnlyEnabledJobsPredicate implements Predicate<AbstractBuild> {

        @Override
        public boolean apply(AbstractBuild input) {
            return ! input.getProject().isDisabled();
        }
    }
    
	private class IncludeResultPredicate implements Predicate<AbstractBuild> {
		private final Result includeResult;

		public IncludeResultPredicate(Result result) {
			this.includeResult = result;
		}

		public boolean apply(AbstractBuild input) {
			return includeResult.equals(input.getResult());
		}
	}
}
