package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Teacher)
@Mock(Teacher)
class TeacherTests {

	@Test
	void theNameCannotBeBlank() {
		def teacher = new Teacher(name: '     ')
		assert teacher.save() == null
		assert teacher.errors.getFieldError('name')

		teacher.clearErrors()

		teacher.name = "John Doe"
		teacher.save()
		assert teacher.errors.getFieldError('name') == null
	}

	@Test
	void theEmailMustBeInEmailFormat() {
		def teacher = new Teacher(email: 'john.doe')
		assert teacher.save() == null
		assert teacher.errors.getFieldError('email')

		teacher.clearErrors()

		teacher.email = "john.doe@rutgers.edu"
		teacher.save()
		assert teacher.errors.getFieldError('email') == null
	}

	@Test
	void thePasswordCannotBeBlank() {
		def teacher = new Teacher(password: '     ')
		assert teacher.save() == null
		assert teacher.errors.getFieldError('password')

		teacher.clearErrors()

		teacher.password = "1234567"
		teacher.save()
		assert teacher.errors.getFieldError('password') == null
	}

	@Test
	void aTeacherCanBeCreated() {
		def teacher = new Teacher(name: 'John Doe', email: 'john.doe@rutgers.edu', password: '1234567')
		assert teacher.save()
	}

	// FIXME: add email uniqueness test
}
