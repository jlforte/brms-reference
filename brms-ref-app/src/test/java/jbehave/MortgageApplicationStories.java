package jbehave;

import java.util.LinkedList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.StoryReporterBuilder.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

public class MortgageApplicationStories extends JUnitStories {
	
	private MortgageApplicationStepper mortgageStepper = new MortgageApplicationStepper();
	
	@Override
	protected List<String> storyPaths() {
		List<String> storyPaths = new LinkedList<String>();
		
		storyPaths.add( 
				"jbehave/add_one_new_mortgage_for_one_valid_customer_and_one_valid_application.story" );
	
		return storyPaths;
	}
	
	@Override
	public InjectableStepsFactory stepsFactory(){
		return new InstanceStepsFactory( configuration(), mortgageStepper );
	}
	
	@Override
	public Configuration configuration(){
		
		
		return new MostUsefulConfiguration()
						.useStoryReporterBuilder( new StoryReporterBuilder() 
						.withDefaultFormats()
						.withFormats(Format.CONSOLE,Format.TXT)
					);
	}

}
