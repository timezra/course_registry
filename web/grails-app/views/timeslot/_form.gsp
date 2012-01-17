<%@ page import="timezra.course_registry.Timeslot" %>



<div class="fieldcontain ${hasErrors(bean: timeslotInstance, field: 'dayOfWeek', 'error')} required">
	<label for="dayOfWeek">
		<g:message code="timeslot.dayOfWeek.label" default="Day Of Week" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="dayOfWeek" from="${timezra.course_registry.DayOfWeek?.values()}" keys="${timezra.course_registry.DayOfWeek.values()*.name()}" required="" value="${timeslotInstance?.dayOfWeek?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: timeslotInstance, field: 'startTime', 'error')} required">
	<label for="startTime">
		<g:message code="timeslot.startTime.label" default="Start Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="startTime" from="${timezra.course_registry.TimeOfDay?.values()}" keys="${timezra.course_registry.TimeOfDay.values()*.name()}" required="" value="${timeslotInstance?.startTime?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: timeslotInstance, field: 'endTime', 'error')} required">
	<label for="endTime">
		<g:message code="timeslot.endTime.label" default="End Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="endTime" from="${timezra.course_registry.TimeOfDay?.values()}" keys="${timezra.course_registry.TimeOfDay.values()*.name()}" required="" value="${timeslotInstance?.endTime?.name()}"/>
</div>

