package com.kazurayam.ks

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class BrowserMobProxyManager {

	public static void main(String[] args) {
		greeting()	
	}
	
	@Keyword
	public static void greeting() {
		WebUI.comment("Hello, i am " + BrowserMobProxyManager.class.getSimpleName())
	}
}
