package com.cygnus.util

import org.springframework.context.i18n.LocaleContextHolder as LCH

class CygnusHelperService {

    def messageSource
	def generateMessageCodeForCygnusParamsGenerator(java.util.LinkedHashMap parameters){
		parameters.each{ type ->
			type.value.each{ detail -> 
				detail.value = messageSource.getMessage(type.key+"."+detail.key, null, null, LCH.getLocale())
			}
		}
		return parameters
	}
}
