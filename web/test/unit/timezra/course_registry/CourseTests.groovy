package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Course)
@Mock([Course, Teacher])
class CourseTests {

	@Test
	void theNameCannotBeBlank() {
		def course = new Course(name: '     ')
		assert course.save() == null
		assert course.errors.getFieldError('name')

		course.clearErrors()

		course.name = "Testing 101"
		course.save()
		assert course.errors.getFieldError('name') == null
	}

	@Test
	void theDescriptionCannotBeBlank() {
		def course = new Course(description: '     ')
		assert course.save() == null
		assert course.errors.getFieldError('description')

		course.clearErrors()

		course.description = "Testing 101 description"
		course.save()
		assert course.errors.getFieldError('description') == null
	}

	@Test
	void theCreditsMustNotBeLessThan1() {
		def course = new Course(credits: 0)
		assert course.save() == null
		assert course.errors.getFieldError('credits')

		course.clearErrors()

		course.credits = 1
		course.save()
		assert course.errors.getFieldError('credits') == null
	}

	@Test
	void theCreditsMustNotBeGreaterThan5() {
		def course = new Course(credits: 6)
		assert course.save() == null
		assert course.errors.getFieldError('credits')

		course.clearErrors()

		course.credits = 5
		course.save()
		assert course.errors.getFieldError('credits') == null
	}

	@Test
	void everyCourseMustHaveATeacher() {
		def course = new Course()
		assert course.save() == null
		assert course.errors.getFieldError('teacher')

		course.clearErrors()

		def teacher = new Teacher(name: "John Doe", email: "john.doe@rutgers.edu", password: "1234567")
		teacher.save()
		course.teacher = teacher
		course.save()
		assert course.errors.getFieldError('teacher') == null
	}

	@Test
	void aCourseCanBeCreated() {
		def teacher = new Teacher(name: "John Doe", email: "john.doe@rutgers.edu", password: "1234567")
		teacher.save()

		def course = new Course(name: "Testing 101", description: "Testing 101 description", credits: 3, teacher: teacher)
		assert course.save()
	}

	// FIXME: add tests for prerequisites and scheduling conflicts
}
