import org.codehaus.gant.GantState
/**
 * Gant Script for generating Service Class based on Artifact
 * This Script was used for auto generate service class which is needed to handle basic transactional statements such as:
 * - AuditTrail for tables
 * - Logging before insert / update on tables
 */

includeTargets << grailsScript("_GrailsCreateArtifacts")

includeTargets << new File("${cygnusUtilitiesPluginDir}/scripts/GenerateExt.groovy")
ant.path(id: "grails.compile.classpath", compileClasspath)

println "plugin dir classpath = "+"${cygnusUtilitiesPluginDir}"
println "grails work dir = "+"${grailsHome}"
println  System.getenv().'HOME' 


ant.java(classname: "com.cygnus.grails.util.scaffolding.ServiceGenerator" ) {
	arg(value: "")
	ant.classpath {
		pathelement(location: "${cygnusUtilitiesPluginDir}/target/classes")
		
		
	}
 }

ant.java(classname: "org.codehaus.groovy.grails.scaffolding.DefaultGrailsTemplateGenerator" ) {
	arg(value: "")
	ant.classpath {
		pathelement(location: "${grailsHome}/dist/grails-crud-2.0.0.jar")
		
		
	}
 }



target ('default': "Generates the CRUD service for a specified domain class") {
 	depends(checkVersion, parseArguments, packageApp)
	
	promptForName(type: "Domain Class")
    generateViews = false
    generateForName = argsMap["params"][0]
    generateForOne()
}
