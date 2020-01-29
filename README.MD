# Slack Demo
This is a template of Java framework for tests for the [Slack](https://app.slack.com/client/TT6882FB6/).


## Dependencies
Make sure you have installed on your operating system:<br/>
1. [Java](http://www.java.com/) 
2. [Git](https://git-scm.com/)
3. [Maven](https://maven.apache.org/)


### Run Test Suites
`mvn clean test -Dbrowser={browser} -Dpassword={password}`




### Parameters
The following are valid test parameters:

*	`-Dbrowser` - chrome, safari<br/>
*	`-Dpassword` - attached to email<br/>


# Reports
In project exist 3 kind of reports:

- [TestNg](http://testng.org/doc/documentation-main.html) produce ‘index.html‘ report and it resides in the same test-output folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output.

`NOT IMPLEMENTED`
- [SureFire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build life-cycle to execute the unit tests of an application.
- [Allure](http://allure.qatools.ru/) report. An open-source framework designed to create test execution reports clear to everyone in the team.


# Agenda:

* `Send message to Slack emtering text;`
* `Verify message appeared in channel`;
* `Click on Star and verify message is searchable with has:star String`;
* `Click on star on upper right and verify message is present in list`


