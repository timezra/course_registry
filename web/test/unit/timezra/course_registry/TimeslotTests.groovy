package timezra.course_registry

import grails.test.mixin.TestFor

import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Timeslot)
class TimeslotTests {

	@Test
	void aTimeslotMustHaveADayOfTheWeek() {
		def timeslot = new Timeslot()
		assert timeslot.save() == null
		assert timeslot.errors.getFieldError('dayOfWeek')

		timeslot.clearErrors()

		timeslot.dayOfWeek = DayOfWeek.Monday
		timeslot.save()
		assert timeslot.errors.getFieldError('dayOfWeek') == null
	}

	@Test
	void aTimeslotMustHaveAStartTime() {
		def timeslot = new Timeslot()
		assert timeslot.save() == null
		assert timeslot.errors.getFieldError('startTime')

		timeslot.clearErrors()

		timeslot.startTime = TimeOfDay._8am
		timeslot.save()
		assert timeslot.errors.getFieldError('startTime') == null
	}

	@Test
	void aTimeslotMustHaveAnEndTime() {
		def timeslot = new Timeslot()
		assert timeslot.save() == null
		assert timeslot.errors.getFieldError('endTime')

		timeslot.clearErrors()

		timeslot.endTime = TimeOfDay._9am
		timeslot.save()
		assert timeslot.errors.getFieldError('endTime') == null
	}

	@Test
	void theEndTimeMustBeAfterTheStartTime() {
		def timeslot = new Timeslot(startTime: TimeOfDay._9am, endTime: TimeOfDay._8am)
		assert timeslot.save() == null
		assert timeslot.errors.getFieldError('endTime')

		timeslot.clearErrors()

		timeslot.startTime = TimeOfDay._8am
		timeslot.endTime = TimeOfDay._9am
		timeslot.save()
		assert timeslot.errors.getFieldError('endTime') == null
	}

	@Test
	void aTimeslotCanBeCreated() {
		def timeslot = new Timeslot(dayOfWeek: DayOfWeek.Monday, startTime: TimeOfDay._8am, endTime: TimeOfDay._9am)
		assert timeslot.save()
	}
}
