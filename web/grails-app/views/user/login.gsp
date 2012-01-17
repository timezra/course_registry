<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="main" />
		<title><g:message code="login.title" /></title>
	</head>
	<body>
	    <div id="login" class="content" role="main">
	        <h1><g:message code="login.header" /></h1>
			<g:if test="${flash.message}">
				<div id="statusMessage" class="message" role="status">${flash.message}</div>
			</g:if>
	        <g:form action="authenticate" method="post">
	            <fieldset class="form">
					<div class="fieldcontain required">
						<label for="email">
							<g:message code="login.email.label" />
							<span class="required-indicator">*</span>
						</label>
						<input type="text" name="email" required="" value="${user?.email}" id="email" />
					</div>
					<div class="fieldcontain required">
						<label for="password">
							<g:message code="login.password.label" />
							<span class="required-indicator">*</span>
						</label>
						<input type="password" name="password" required="" value="${user?.password}" id="password" />
					</div>
	            </fieldset>
				<fieldset class="buttons">
                    <input type="submit" name="login_button" class="find" value="<g:message code='login.button.label' />" id="login_button" />
				</fieldset>
	        </g:form>
	    </div>
	</body>
</html>