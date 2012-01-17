package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CreateStudent extends CreateUser {

	ShowStudent register(name, email, password) {
		register name, email, password, ShowStudent
	}
}
