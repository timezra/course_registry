package timezra.course_registry

import org.springframework.context.MessageSourceResolvable

enum DayOfWeek implements MessageSourceResolvable {
	Monday, Tuesday, Wednesday, Thursday, Friday

	@Override Object[] getArguments() {
		[]
	}

	@Override String[] getCodes() {
		[
			"${getClass().name}.${name()}"
		]
	}

	@Override String getDefaultMessage() {
		name()
	}
}
