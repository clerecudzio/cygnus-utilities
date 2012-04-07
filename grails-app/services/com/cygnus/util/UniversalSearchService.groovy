package com.cygnus.util

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsClassUtils

import com.sun.xml.internal.messaging.saaj.util.LogDomainConstants;

class UniversalSearchService {
	def grailsApplication

	/**
	 * Generate search parameters based on view's params
	 * @param params
	 * @return resultMap
	 */
	def generateSearchParameters(params){
		def resultMap = [:]
		def stringsEnabled = false
		def datesEnabled = false
		def stringEnabled = true
		

		Class thisDomainClass = grailsApplication.domainClasses.find { it.clazz.simpleName == params.dcName }.clazz
		def domainClassName = new DefaultGrailsDomainClass(thisDomainClass)
		def stringResults = []
		try{
			if(params.strings) stringsEnabled = true
			if(params.dates)  datesEnabled = true
			if(params.string) stringEnabled = true
			resultMap.put("dcName",params.dcName)
			if(stringsEnabled){
				stringResults =  generateStringsMapOfDataAndDataType(domainClassName.clazz, params.strings,"strings")
				resultMap.put("strings", stringResults)
			}
			if(stringEnabled){
				stringResults =  generateStringsMapOfDataAndDataType(domainClassName.clazz, params.string,"string")
				resultMap.put("string", stringResults)
			}
		}catch(Exception e){
			log.error e
			e.printStackTrace()
		}
		return resultMap
	}
	
	
	/**
	 * Generate Map Of Data and Data Type
	 * @param domainClass
	 * @param inputList
	 * @param dataTypeLabel
	 * @return
	 * @throws Exception
	 */
	def generateStringsMapOfDataAndDataType(domainClass,inputList,dataTypeLabel) throws Exception{
		def resultList = []
		def sb = new StringBuffer()
		def resultMap = [:]
		try{

			inputList.each{ thisMap->
				def childMap = [:]
				thisMap.value.each{ mapValue ->
					def thisParamsDataType = GrailsClassUtils.getPropertyType(domainClass,mapValue)
					if(thisParamsDataType)
						childMap.put(mapValue, "-")
				}
				resultMap.put(dataTypeLabel+"."+thisMap.key, childMap)
			}
		}catch(Exception e){
			log.error e.getMessage()
			e.printStackTrace()
		}
		return resultMap
	}

	/**
	 * generate universal search result
	 * @param inputParams params from view
	 * @return map of results [resultList:<queryResult>,resultListSize:<queryResultSize>
	 * @throws Exception
	 */
	def generateResult(inputParams) {
		def returningResults = [:]
		def results
		Class thisDomainClass = grailsApplication.domainClasses.find { it.name == inputParams.dcName }.clazz
		results = queryBuilder(thisDomainClass,inputParams)
		log.info "results.size = "+results.size
		returningResults.put("resultListSize", results.size)
		if((inputParams.max)&& (results.size >= inputParams.max))returningResults.put("resultList", results[0..inputParams.max])
		else returningResults.put("resultList", results)
	    
		return returningResults
	}

	
	/**
	 * build query based on input
	 * @param domainClass domain class which queries will be executed
	 * @param inputMap params from view
	 * @return
	 * @throws Exception
	 */
	def queryBuilder(domainClass,inputMap) throws Exception{
		def orderingList = []
		def sb = new StringBuffer()
		sb << "queryBuilder $domainClass.name \n"
		sb << " input parameters = " +inputMap.toString()+"\n"

		def queryResult = domainClass.withCriteria{
			inputMap.each{ inputContent ->
				//process strings values
				if(inputContent.key =~ /strings\./){
					log.info "iterating strings = "+inputContent.value.toString()
					
					def value = inputContent.value as String[]
					
					if(value.length == 2){
						if(inputContent.value[1]){
						sb << " ilike "+inputContent.value[0]+" , "+inputContent.value[1]+"\n"
						ilike inputContent.value[0] , '%'+inputContent.value[1] +'%'
						orderingList.add(inputContent.value[0])
						}
					}
				}
				if(inputContent.key =~ /string\./){
					log.info "iterating string = "+inputContent.value
					
					def stringArray = inputContent.key.tokenize(".")
					if(inputContent.value){
						sb << " ilike "+stringArray[2]+" , "+inputContent.value+"\n"
						ilike stringArray[2] , '%'+inputContent.value +'%'
					}
					orderingList.add(stringArray[2])
				}

			}
			if(inputMap.offset){
				sb << " firstResult("+inputMap.offset+")\n"
				firstResult(new Integer(inputMap.offset).intValue())
			}
			if(inputMap.sort){
				if(!inputMap.order) inputMap.order ="asc"
				sb << " order($inputMap.sort,$inputMap.order)\n"
				order(inputMap.sort,inputMap.order)
			}
			orderingList.each{valueOfOrder ->
				sb << " order($valueOfOrder,asc)\n"
				order("$valueOfOrder","asc")
			}

		}
		log.info sb
		return queryResult
	}
}
