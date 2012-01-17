package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CreateTimeslot extends CourseRegistryPage {

	WebElement dayOfWeek
	WebElement startTime
	WebElement endTime
	WebElement create

	CreateTimeslot(WebDriver driver) {
		super(driver)
	}

	public <T> T doCreate(dayOfWeek, startTime, endTime, Class<T> nextPage) {
		choose_dayOfWeek dayOfWeek
		choose_startTime startTime
		choose_endTime endTime
		click_create nextPage
	}
}
