<g:each in="${searchParams}" status="i" var="paramsInstance">

	<g:if test="${paramsInstance.key == 'strings'}">
		<g:each in="${paramsInstance.value}" status="j" var="stringsInstance">
			<div
				class="fieldcontain ${hasErrors(bean: stringsInstance, field: 'key', 'error')} ">
				<label for="key"> <g:message code="${stringsInstance.key}"
						default="Search Parameter" />

				</label>
				<g:select name="${stringsInstance.key}"
					from="${stringsInstance.value}" optionKey="key" optionValue="value" />
				<g:textField name="${stringsInstance.key}" />
				<br />

			</div>
		</g:each>
	</g:if>
	<g:if test="${paramsInstance.key == 'string'}">

		<g:each in="${paramsInstance.value}" status="j" var="stringInstance">
			<g:each in="${stringInstance.value}" status="k" var="subInstance">
				<div class="fieldcontain ${hasErrors(bean: stringInstance, field: 'key', 'error')} ">
					<label for="key"> <g:message code="${subInstance.value}"
							default="${subInstance.value}" />

					</label>
					<g:textField name="${subInstance.value}"
						 />
			</g:each>
		</g:each>
	</g:if>
	<g:if test="${paramsInstance.key == 'dates'}">
		<g:each in="${paramsInstance.value}" status="j" var="stringInstance">
			<div
				class="fieldcontain ${hasErrors(bean: stringInstance, field: 'key', 'error')} ">
				<label for="key"> <g:message code="${stringInstance.key}"
						default="Search Parameter" />

				</label>
				<g:datePicker name="${stringInstance.key}" precision="day"
					value="${stringInstance?.value}" />
		</g:each>
	</g:if>
</g:each>