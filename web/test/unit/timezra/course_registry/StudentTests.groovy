package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Student)
@Mock(Student)
class StudentTests {

	@Test
	void theNameCannotBeBlank() {
		def student = new Student(name: '     ')
		assert student.save() == null
		assert student.errors.getFieldError('name')

		student.clearErrors()

		student.name = "John Doe"
		student.save()
		assert student.errors.getFieldError('name') == null
	}

	@Test
	void theEmailMustBeInEmailFormat() {
		def student = new Student(email: 'john.doe')
		assert student.save() == null
		assert student.errors.getFieldError('email')

		student.clearErrors()

		student.email = "john.doe@rutgers.edu"
		student.save()
		assert student.errors.getFieldError('email') == null
	}

	@Test
	void thePasswordCannotBeBlank() {
		def student = new Student(password: '     ')
		assert student.save() == null
		assert student.errors.getFieldError('password')

		student.clearErrors()

		student.password = "1234567"
		student.save()
		assert student.errors.getFieldError('password') == null
	}

	@Test
	void aStudentCanBeCreated() {
		def student = new Student(name: 'John Doe', email: 'john.doe@rutgers.edu', password: '1234567')
		assert student.save()
	}

	// FIXME: add email uniqueness test
}
