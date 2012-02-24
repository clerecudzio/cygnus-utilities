package com.cygnus.grails.util.scaffolding

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.scaffolding.DefaultGrailsTemplateGenerator;
import org.springframework.util.Assert

class ServiceGenerator extends DefaultGrailsTemplateGenerator {

	void generateService(GrailsDomainClass domainClass, String destdir) {
		Assert.hasText destdir, "Argument [destdir] not specified"

		if (domainClass) {
			def fullName = domainClass.fullName
			def pkg = ""
			def pos = fullName.lastIndexOf('.')
			if (pos != -1) {
				// Package name with trailing '.'
				pkg = fullName[0..pos]
			}

			def destFile = new File("${destdir}/grails-app/services/${pkg.replace('.' as char, '/' as char)}${domainClass.shortName}Controller.groovy")
			if (canWrite(destFile)) {
				destFile.parentFile.mkdirs()

				destFile.withWriter { w ->
					generateService(domainClass, w)
				}

				LOG.info("Service generated at ${destFile}")
			}
		}
	}

}
