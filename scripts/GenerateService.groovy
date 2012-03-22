import org.codehaus.gant.GantState
/**
 * Gant Script for generating Service Class based on Artifact
 * This Script was used for auto generate service class which is needed to handle basic transactional statements such as:
 * - AuditTrail for tables
 * - Logging before insert / update on tables
 */
includeTargets << new File("${cygnusUtilitiesPluginDir}/scripts/GenerateExt.groovy")
includeTargets << grailsScript("_GrailsCreateArtifacts")


ant.java(classname: "com.cygnus.grails.util.scaffolding.ServiceGenerator" ) {
	arg(value: "")
	ant.classpath {
		pathelement(location: "${cygnusUtilitiesPluginDir}/target/classes")
		pathelement(location: "${grailsHome}/dist/grails-core-2.0.0.jar")
		pathelement(location: "${grailsHome}/dist/grails-crud-2.0.0.jar")
		
		pathelement(location: "${grailsHome}/lib/org.springframework/spring-beans/jars/spring-beans-3.1.0.RELEASE.jar")
		pathelement(location: "${grailsHome}/lib/org.codehaus.groovy/groovy-all/jars/groovy-all-1.8.4.jar")
		pathelement(location: "${grailsHome}/lib/other/commons-logging-1.0.2.jar")
		pathelement(location: "${grailsHome}/lib/org.springframework/spring-core/jars/spring-core-3.1.0.RELEASE.jar")
		pathelement(location: "${grailsHome}/lib/org.springframework/spring-context/jars/spring-context-3.1.0.RELEASE.jar")
		
		
		
		
	}
 }



target ('default': "Generates the CRUD service for a specified domain class") {
 	depends(checkVersion, parseArguments, packageApp)
	
	promptForName(type: "Domain Class")
    generateViews = false
    generateForName = argsMap["params"][0]
    generateForOne()
}
