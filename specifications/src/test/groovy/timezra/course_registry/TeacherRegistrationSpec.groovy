package timezra.course_registry

import timezra.course_registry.pages.Login
import timezra.course_registry.pages.ShowTeacher


class TeacherRegistrationSpec extends CourseRegistrySpec {

	def "a user can register as a teacher"() {
		when:
		def name = TEACHER_NAME
		def showTeacher = createTeacher name, randomEmail(), TEACHER_PASSWORD

		then:
		showTeacher.message.text ==~ /Teacher \d+ created/

		where:
		browser << browsers()
	}

	def "an existing teacher can login"() {
		when:
		def name = TEACHER_NAME
		def email = randomEmail()
		def password = TEACHER_PASSWORD
		createTeacher name, email, password
		def login = goHome().click_login_link Login
		def showTeacher = login.doLogin email, password, ShowTeacher

		then:
		showTeacher.message.text == "Welcome back, ${name}!"

		where:
		browser << browsers()
	}
}
