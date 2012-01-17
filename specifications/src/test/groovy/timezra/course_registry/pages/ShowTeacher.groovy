package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class ShowTeacher extends CourseRegistryPage {
	WebElement add_course_link

	ShowTeacher(WebDriver driver) {
		super(driver)
	}
}
