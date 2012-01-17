<%@ page import="timezra.course_registry.Semester" %>



<div class="fieldcontain ${hasErrors(bean: semesterInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="semester.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" min="${Calendar.instance.get(Calendar.YEAR)}" required="" value="${semesterInstance.year}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: semesterInstance, field: 'season', 'error')} required">
	<label for="season">
		<g:message code="semester.season.label" default="Season" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="season" from="${timezra.course_registry.Season?.values()}" keys="${timezra.course_registry.Season.values()*.name()}" required="" value="${semesterInstance?.season?.name()}"/>
</div>

