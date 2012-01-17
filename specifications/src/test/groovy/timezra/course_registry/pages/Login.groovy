package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class Login extends CourseRegistryPage {

	WebElement email
	WebElement password
	WebElement login_button

	Login(WebDriver driver) {
		super(driver)
	}

	public <T> T doLogin(email, password, Class<T> nextPage) {
		type_email email
		type_password password
		click_login_button nextPage
	}
}
