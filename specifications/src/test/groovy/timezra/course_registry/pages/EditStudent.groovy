package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class EditStudent extends CourseRegistryPage {
	WebElement courses
	WebElement update
	WebElement courses_error

	EditStudent(WebDriver driver) {
		super(driver)
	}
}
