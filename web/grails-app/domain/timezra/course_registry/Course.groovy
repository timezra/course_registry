package timezra.course_registry

class Course {

	String name
	String description
	int credits
	Semester semester
	Teacher teacher
	static hasMany = [timeslots: Timeslot, prerequisites: Course]

	static constraints = {
		name blank: false
		description blank: false
		credits min: 1, max: 5
		semester nullable: true
		teacher nullable: false
		timeslots validator: { val, obj ->
			int i = 1
			List timeslots = val as List
			for (Timeslot t : timeslots) {
				if(t.overlaps(timeslots.subList(i++, timeslots.size()))) {
					return "timeslotsOverlap"
				}
			}
			true
		}
		prerequisites validator: { val, obj ->
			def hasCircularDependency = false
			for (Course c : val) {
				if(obj.hasCyclicDependency(c)) {
					return "circularDependency"
				}
			}
			true
		}
	}

	def hasCyclicDependency(course) {
		if(equals(course)) return true
		course.prerequisites.each {
			if(hasCyclicDependency(it)) return true
		}
		false
	}

	String toString() {
		name
	}
}
