package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver

@InheritConstructors
class ShowSemester extends CourseRegistryPage {

	ShowSemester(WebDriver driver) {
		super(driver)
	}
}
