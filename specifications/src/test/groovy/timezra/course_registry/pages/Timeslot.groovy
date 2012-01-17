package timezra.course_registry.pages

class Timeslot {
	String day
	String start
	String end

	@Override String toString() {
		"$day, $start - $end"
	}
}
