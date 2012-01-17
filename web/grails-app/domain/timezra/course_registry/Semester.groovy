package timezra.course_registry

import org.springframework.context.MessageSourceResolvable

class Semester implements MessageSourceResolvable {

	int year
	Season season

	static transients = [
		"codes",
		"arguments",
		"defaultMessage"
	]

	static constraints = {
		year nullable: false, unique: ['season'], min: Calendar.instance.get(Calendar.YEAR)
		season nullable: false
	}

	@Override Object[] getArguments() {
		[season, year]
	}

	@Override String[] getCodes() {
		[getClass().name]
	}

	@Override String getDefaultMessage() {
		"${season}, ${year}"
	}
}
