BDD/BRMS Demo

LINKS
rhcdemo GitHub: https://github.com/rhcdemo/bdd-brms
Jenkins on OpenShift: http://bdd-rhcdemo.rhcloud.com/job/demo/
user//pass : admin//admin

ACCESSING THE PROJECT
1. Email sklenkar@redhat.com and bmeisele@redhat.com with your GitHub username stating that you want access to the BDD/BRMS demo and you will be made a collaborator shortly after.
2. Once you have been made a collaborator. clone the repo locally to your computer
1. Mac GUI: http://mac.github.com/, Guide: http://mac.github.com/help.html
2. Windows GUI: http://windows.github.com/, Guide: http://windows.github.com/help.html
3. Linux/ command line guide: https://help.github.com/articles/set-up-git
3. You now have access to all of the scenarios, rules and source java code necessary!
4. If you’d like to perform a sanity check, import it as an existing Maven Project in Eclipse and verify that there are no red Xs. Do a jUnit test of: /drools-reference-implementation/examples/insurance/src/test/java/com/rhc/insurance/RunCukesTest.java
You should see at least some tests pass. (There are some intentionally failing scenarios for demonstration).

VIEWING AND EDITING THE SCENARIOS
1. The scenarios are located at :
/drools-reference-implementation/examples/insurance/src/test/resources/features
There is only one scenario file, titled healthQuadrant.feature, in the folder.
2. Open that file in your favorite text editor and add, remove or change scenarios as you please.
3. Commit your changes back to GitHub.
4. Go to the Jenkins project page linked above and verify that your commit is building by checking the Build History box on the left.
5. You may also view the file in your web browser at:
https://github.com/rhcdemo/bdd-brms/blob/master/drools-reference-implementation/examples/insurance/src/test/resources/features/healthQuadrant.feature
For details on formatting your scenarios, see below.

VIEWING AND EDITING THE RULES/DECISION TABLES
1. The rules are located at:
/drools-reference-implementation/examples/insurance/src/main/resources/rules
There are two decision table files, SeverityLevels.xls and AssignQuadrants.xls
2. Open whichever decision table you want to view/edit and add, remove or change the rules as you please
3. Commit your changes back to GitHub
4. Go to the Jenkins project page linked above and verify that your commit is building by checking the Build History box on the left.
For details on formatting your rules, see below.

VIEWING CUCUMBER REPORTS
1. Navigate to the Jenkins project page linked above
2. If you wish to view reports for the latest build (i.e. most recent commit to GitHub), click Cucumber Reports
3. If you wish to view reports for an earlier build, select that build from the Build History box on the left and then click Cucumber Reports
4. Here, you will see two pie graphs showing the status of your tests
1. The one on the right shows what percentage of your scenarios passed and what percentage failed
2. The one on the left shows what percentage of your steps passed, what percentage failed and what percentage were skipped
5. Below that, you will see statistics for each of the .feature files that were ran. In this case, there is only the one.
6. Click the name of the Feature to see specifically which scenarios and steps in that feature passed, which failed and which were skipped. It also shows error reports for any failed steps.

FORMATTING SCENARIOS
All the scenarios in this project must follow this format:

@{ScenarioTag}
Scenario: {Scenario title as it will appear on Cucumber Report}

Given a member "{name}"

And "{name}" has condition "{condition}" of degree "{mild/severe}"

And "{name}" has condition "{condition}" of degree "{mild/severe}"

When determining the health quadrant for "{name}"

Then "{name}" should be placed in Quadrant {number}

And "{name}" {result}

{name} can be any name you want to use
{condition} must be one of {asthma, diabetes, cardiovascular, depression, anxiety, eatingDisorder}. You can assign more conditions using more And statements.
{number} must be one of {1, 2, 3, 4}, according to what quadrant the member should be assigned to.
{result} must be one of {receives standard care, is assigned a behavioral health case manager, is assigned a specialty disease care manager}. Again, with more And statements, you can have multiple results. Note that the result is not in quotation marks.
You can also edit the existing scenarios using the same guidelines.

FORMATTING RULES
-The excel parser looks for the keyword “RuleSet” to begin parsing for rules. Anything written to the left or above RuleSet is ignored and can be used for notes
-com.rhc.insurance.rules is the package location of the rule in the directory structure
-Import com.rhc.insurance.Member tells the rule to import the Member class
-FALSE Sequential says that the rules do not need to be fired in top to bottom order
-The keyword “RuleTable” tells the parser that rules are about to follow
-The words following RuleTable are used as an identifier for all the rules in that table, until the parser comes across another RuleTable keyword with a new title
-Each row in a rule table represents a single rule
-CONDITION tells the parser that the elements in that column are part of the LHS of the rule
-ACTION tells the parser that the lements in that column are part of the RHS of the rule
-PRIORITY allows you to set a priority ranking for the order in which the rules fire. This overrides the TRUE Sequential option if that’s set.
-The row below the CONDITION, ACTION or PRIORITY row is where the object that is being modified is specified. In our case, this is always Member. Merging the cells specifying Member for the conditions indicates that there is one member with all of the qualities listed below.
-The row below that indicates the action that is being taken. $param takes whatever is in the cell in that column and uses that
-The row below that is for note-taking to specify what is going on in that column
-All rows following that are rules

