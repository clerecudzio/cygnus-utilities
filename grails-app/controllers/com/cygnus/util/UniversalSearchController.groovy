package com.cygnus.util

class UniversalSearchController {

	def universalSearchService
	def index() {
	}
	static allowedMethods = [doCygnusSearch: "POST"]

	/**
	 * Generate search method based on view's parameters
	 * @return view showSearch
	 */
	def generateSearch(){
		def results = universalSearchService.generateSearchParameters(params)
		def outputMap = [:]
		results.strings.each{ type ->
			type.value.each{ detail ->
				detail.value = message(code:type.key+"."+detail.key)
			}
		}

		results.string.each { type ->
			type.value.each{ detail ->
				detail.value = message(code:type.key+"."+detail.key)
			}
		}

		outputMap.put("strings", results.strings)
		outputMap.put("string", results.string)

		render (view:"showSearch",model:[searchParams:outputMap])
	}
}
