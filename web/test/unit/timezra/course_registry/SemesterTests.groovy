package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Semester)
@Mock(Semester)
class SemesterTests {

	@Test
	void theYearMustBeAtLeastThisYear() {
		def semester = new Semester(year: Calendar.instance.get(Calendar.YEAR) - 1)
		assert semester.save() == null
		assert semester.errors.getFieldError('year')

		semester.clearErrors()

		semester.year = Calendar.instance.get Calendar.YEAR
		semester.save()
		assert semester.errors.getFieldError('year') == null
	}

	@Test
	void theSeasonMustBeNonNull() {
		def semester = new Semester()
		assert semester.save() == null
		assert semester.errors.getFieldError('season')

		semester.clearErrors()

		semester.season = Season.Winter
		semester.save()
		assert semester.errors.getFieldError('season') == null
	}

	@Test
	void aSemesterCanBeCreated() {
		def semester = new Semester(season: Season.Winter, year: Calendar.instance.get(Calendar.YEAR))
		assert semester.save()
	}

	// FIXME: add test for uniqueness
}
