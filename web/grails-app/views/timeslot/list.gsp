
<%@ page import="timezra.course_registry.Timeslot" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'timeslot.label', default: 'Timeslot')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-timeslot" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-timeslot" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="dayOfWeek" title="${message(code: 'timeslot.dayOfWeek.label', default: 'Day Of Week')}" />
					
						<g:sortableColumn property="startTime" title="${message(code: 'timeslot.startTime.label', default: 'Start Time')}" />
					
						<g:sortableColumn property="endTime" title="${message(code: 'timeslot.endTime.label', default: 'End Time')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${timeslotInstanceList}" status="i" var="timeslotInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${timeslotInstance.id}">${fieldValue(bean: timeslotInstance, field: "dayOfWeek")}</g:link></td>
					
						<td>${fieldValue(bean: timeslotInstance, field: "startTime")}</td>
					
						<td>${fieldValue(bean: timeslotInstance, field: "endTime")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${timeslotInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
