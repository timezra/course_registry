package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class ShowCourse extends CourseRegistryPage {
	WebElement semester
	WebElement timeslots
	WebElement prerequisites

	ShowCourse(WebDriver driver) {
		super(driver)
	}
}
