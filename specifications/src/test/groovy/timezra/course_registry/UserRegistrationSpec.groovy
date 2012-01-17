package timezra.course_registry


class UserRegistrationSpec extends CourseRegistrySpec {

	def "a user is greeted with an intro screen"() {
		when:
		def home = goHome()

		then:
		home.greeting.text == "Welcome to the course registry."

		where:
		browser << browsers()
	}
}
