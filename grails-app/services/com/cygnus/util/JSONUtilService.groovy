package com.cygnus.util

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

/**
 * 
 * @author haris ibrahim
 *
 */
class JSONUtilService {

	def sessionFactory

	/**
	 * Convert query result into JSON standardize formatting 
	 * @param inputList query result
	 * @param idLabelValue list for id, label,value
	 * @return Map
	 */
	def transformToStandardizedJSONFormat(ArrayList inputList,ArrayList idLabelValue){
		log.info 'JSONUtilService.transformToStandardizedJSONFormat'
		def resultList = []

		try{
			def sb = new StringBuffer()
			inputList.each{domainClass->
				def resultMap = [:]
				def mappingForIdLabelValue = transformIdLabelValue(idLabelValue)
				mappingForIdLabelValue.each{milv ->
					resultMap.put(milv.key, domainClass[milv.value])
				}


				sb << resultMap.toString()
				resultList.add(resultMap)
			}
			log.info sb.toString()
		}catch(Exception e){
			log.error e.getMessage()
			e.printStackTrace()
		}


		return resultList
	}
	/**
	 * Convert query result into JSON standardize formatting with additional parameters
	 * @param inputList query result
	 * @param idLabelValue list for id, label,value
	 * @param otherValue
	 * @return
	 */
	def transformToStandardizedJSONFormat(ArrayList inputList,ArrayList idLabelValue,ArrayList otherValue){
		log.info 'JSONUtilService.transformToStandardizedJSONFormat addedValue'
		def resultList = []

		try{
			def sb = new StringBuffer()
			inputList.each{domainClass->
				def resultMap = [:]
				def mappingForIdLabelValue = transformIdLabelValue(idLabelValue)
				mappingForIdLabelValue.each{milv ->
					resultMap.put(milv.key, domainClass[milv.value])
				}
				otherValue.each{ oValue->
					resultMap.put(oValue, domainClass[oValue])
				}

				sb << resultMap.toString()
				resultList.add(resultMap)
			}
			log.info sb.toString()
		}catch(Exception e){
			log.error e.getMessage()
			e.printStackTrace()
		}


		return resultList
	}
	
	
	
	/**
	 * transform id,label,value from input from list into map
	 * @param idLabelValueParams
	 * @return Map
	 * 
	 */
	def transformIdLabelValue(idLabelValueParams){
		def resultMap = [:]
		try{
			if(idLabelValueParams.size == 3){
				resultMap.put("id", idLabelValueParams[0])
				resultMap.put("label", idLabelValueParams[1])
				resultMap.put("value", idLabelValueParams[2])
				
				
			}else{
			throw new Exception("invalid parameters for id label value")
			}
		}catch(Exception e){
			log.error e.getMessage
		}
		return resultMap
	}
}
