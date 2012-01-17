package timezra.course_registry

import org.springframework.context.MessageSourceResolvable

enum TimeOfDay implements MessageSourceResolvable {
	_8am, _9am, _10am, _11am, _12m, _1pm, _2pm, _3pm, _4pm, _5pm, _6pm, _7pm, _8pm

	@Override Object[] getArguments() {
		[]
	}

	@Override String[] getCodes() {
		[
			"${getClass().name}.${name()}"
		]
	}

	@Override String getDefaultMessage() {
		name().substring 1
	}
}
