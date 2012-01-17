package timezra.course_registry

import java.util.List

import org.springframework.context.MessageSourceResolvable

class Timeslot implements MessageSourceResolvable {

	DayOfWeek dayOfWeek
	TimeOfDay startTime
	TimeOfDay endTime

	static transients = [
		"codes",
		"arguments",
		"defaultMessage"
	]

	static constraints = {
		dayOfWeek nullable: false, unique: ['startTime', 'endTime']
		startTime nullable: false
		endTime nullable: false, validator: { val, obj ->
			obj.startTime < val ? true : "afterStartTime"
		}
	}

	boolean overlaps(List timeslots) {
		for (Timeslot t : timeslots) {
			if(dayOfWeek == t.dayOfWeek) {
				if(startTime == t.startTime) return true
				if(startTime < t.startTime && endTime > t.startTime) return true
				if(startTime > t.startTime && startTime < t.endTime) return true
			}
		}
		false
	}

	@Override Object[] getArguments() {
		[
			dayOfWeek,
			startTime,
			endTime
		]
	}

	@Override String[] getCodes() {
		[getClass().name]
	}

	@Override String getDefaultMessage() {
		"${dayOfWeek}, ${startTime} - ${endTime}"
	}
}
