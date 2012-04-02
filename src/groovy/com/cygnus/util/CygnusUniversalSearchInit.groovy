package com.cygnus.util

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class CygnusUniversalSearchInit {
	def universalSearchService = new UniversalSearchService()
	
//	def addControllerDynamicMethodCygnusSearch(ctx){
//		for (classes in org.codehaus.groovy.grails.commons.ApplicationHolder.application.controllerClasses){
//			 def controllerClass = classes.clazz
//			 println "adding = "+controllerClass.name
//			 controllerClass.metaClass.static.doCygnusSearch << {GrailsParameterMap args ->
//				 delegate.log.info "this is static insertion -->"+args.toString()
//				 return universalSearchService.generateResult(args)
//			 }
//			 classes.class.metaClass.doCygnusSearch << {GrailsParameterMap args ->
//				 delegate.log.info "this is dynamic insertion -->"+args.toString()
//			 }
//		}
//	}
//	
	def initStaticMissingMethodHandling(ctx){
		for (classes in org.codehaus.groovy.grails.commons.ApplicationHolder.application.controllerClasses){
			def dynamicMethods = ['doCygnusSearch']
			def controllerClass = classes.clazz
			controllerClass.metaClass.'static'.methodMissing = {String name, args ->
				org.codehaus.groovy.grails.commons.metaclass.StaticMethodInvocation method =
						dynamicMethods.find {it.isMethodMatch(name)}
				if (method) {
					controllerClass.metaClass.'static'."$name" = { Object[] varArgs ->
						method.invoke(domainClass.class,name,varArgs)

					}
					result = method.invoke(domainClass.class,name,args)
				}
				else {
					delegate.log.error "error while initiating $name"
					throw new MissingMethodException(name,domainClass.class,args)
				}
				result
			}
		}
		
	}
}
