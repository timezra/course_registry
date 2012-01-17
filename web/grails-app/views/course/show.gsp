
<%@ page import="timezra.course_registry.Course" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-course" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-course" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list course">
			
				<g:if test="${courseInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="course.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${courseInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="course.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${courseInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.credits}">
				<li class="fieldcontain">
					<span id="credits-label" class="property-label"><g:message code="course.credits.label" default="Credits" /></span>
					
						<span class="property-value" aria-labelledby="credits-label"><g:fieldValue bean="${courseInstance}" field="credits"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.semester}">
				<li class="fieldcontain">
					<span id="semester-label" class="property-label"><g:message code="course.semester.label" default="Semester" /></span>
					
						<span class="property-value" aria-labelledby="semester-label"><g:link elementId="semester" controller="semester" action="show" id="${courseInstance?.semester?.id}">${fieldValue(bean: courseInstance, field: 'semester')}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.timeslots}">
				<li class="fieldcontain" id="timeslots">
					<span id="timeslots-label" class="property-label"><g:message code="course.timeslots.label" default="Timeslots" /></span>
					
						<g:each in="${courseInstance.timeslots}" var="t">
						<span class="property-value" aria-labelledby="timeslots-label"><g:link controller="timeslot" action="show" id="${t.id}">${message(code: 'timezra.course_registry.Timeslot', args: [t.dayOfWeek, t.startTime, t.endTime])}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.prerequisites}">
				<li class="fieldcontain" id="prerequisites">
					<span id="prerequisites-label" class="property-label"><g:message code="course.prerequisites.label" default="Prerequisites" /></span>
					
						<g:each in="${courseInstance.prerequisites}" var="p">
						<span class="property-value" aria-labelledby="prerequisites-label"><g:link controller="course" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${courseInstance?.teacher}">
				<li class="fieldcontain">
					<span id="teacher-label" class="property-label"><g:message code="course.teacher.label" default="Teacher" /></span>
					
						<span class="property-value" aria-labelledby="teacher-label"><g:link controller="teacher" action="show" id="${courseInstance?.teacher?.id}">${courseInstance?.teacher?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${courseInstance?.id}" />
					<g:link class="edit" action="edit" id="${courseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
