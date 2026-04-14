# Katalon Studio v10.4.2 failed to rename a Groovy file in Keywords folder

Here I would report a bug found in Katalon Studio v10.4.2.

## Environment

- macOS 26.3.1
- Katalon Studio 10.4.2

## Steps to reproduce

I created a brand new project of webui.

I created a Groovy file `Keywords/com/kazurayam/ks/BrowserMobProxyManager.groovy`. The content is as follows:

```
package com.kazurayam.ks

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class BrowserMobProxyManager {

	public static void main(String[] args) {
		greeting()
	}

	@Keyword
	public static void greeting() {
		WebUI.comment("Hello, i am " +
            BrowserMobProxyManager.class.getSimpleName())
	}
}
```

Please note that this class implements a method `public static void main(String[])`, which caused a small itch later.

I created a `Test Cases/TC1`. The content is as follows:

```
import com.kazurayam.ks.BrowserMobProxyManager

BrowserMobProxyManager.greeting()
```

When I ran the `TC1`, it worked fine. I saw the following outcome in the console:

```
2026-04-13 22:14:12.890 INFO  c.k.katalon.core.main.TestCaseExecutor   - --------------------
2026-04-13 22:14:12.891 INFO  c.k.katalon.core.main.TestCaseExecutor   - START Test Cases/TC1
2026-04-13 22:14:13.589 DEBUG testcase.TC1                             - 1: BrowserMobProxyManager.greeting()
2026-04-13 22:14:13.780 INFO  c.k.k.c.keyword.builtin.CommentKeyword   - Hello, i am BrowserMobProxyManager
2026-04-13 22:14:13.792 INFO  c.k.katalon.core.main.TestCaseExecutor   - END Test Cases/TC1
```

Then, I tried to rename the `BrowserMobProxyManger.groovy` to `WebDriverPlusHARFactory.groovy`.

![try rename Manager to Factory](https://kazurayam.github.io/KS10.4.2_failed_to_rename_Keywords/images/try_rename_Manager_to_Factory.png)

Then a dialog came up and blocked me.

![main method blocked me](https://kazurayam.github.io/KS10.4.2_failed_to_rename_Keywords/images/main_method_blocked_me.png)

The message said:

>Type com.kazurayam.ks.BrowserMobProxyManager contains a main method - some applications (such as scripts) may not work after refactoring.

I could understand the message. I would accept the warning. Later I will remove the `main` method. This warning dialog is not a problem at all.

For the time being, I decided to ignore the warning and clicked the continue button. Then, I got a series of problems.

**Problem 1: Katalon Studio GUI no longer synced with the file system**

In the Katalon Studio GUI, in the `Keywords` folder, the `BrowserMobProxyManager.groovy` was renamed to `WebDriverPlusHARFactory.groovy`. But on the file system, using Emacs editor, I found that the original `BrowserMobProxyManager.groovy` stayed there and new `WebDriverPlusHARFactory.groovy` wasn't present.

![GUI and file out of sync](https://kazurayam.github.io/KS10.4.2_failed_to_rename_Keywords/images/GUI_file_out_of_sync.png)

**Problem 2: the Keyword was renamed, but the Test Case was left unchanged**

As long as the `BrowserMobProxyManager` class was renamed, I expected that Katalon Studio will automatically refactor the Test Case source which refered to the class. I expected that Katalon Studio should change the `import` statement in the `Test Cases/TC1` from

```
import com.kazurayam.ks.BrowserMobProxyManager
```

to

```
import com.kazurayam.ks.WebDriverPlusHARFactory
```

but, in fact, the Test Case script was left unchanged. I checked their doc ["Introduction to custom keywords in Katalon Studio"](https://docs.katalon.com/katalon-studio/keywords/custom-keywords/introduction-to-custom-keywords-in-katalon-studio). I was surprised to find that the doc does NOT mention of the auto-refactoring of Test Case scripts when a Keyword is renamed.

**Problem 3: failed to renamed the Keyword back to the original**

Next, I tried to rename the `WebDriverPlustHRFactory.groovy` back to the original `BrowserMobProxyManager.groovy`.

![rename keyword back](https://kazurayam.github.io/KS10.4.2_failed_to_rename_Keywords/images/renaming_Keyword_back.png)

Then I got an error dialog:

![rename error](https://kazurayam.github.io/KS10.4.2_failed_to_rename_Keywords/images/renaming_error.png)

The message said

>Type named 'BrowserMobProxyManager' already exists in package 'com.kazurayam.ks'

and actually I could not finish renaming the class back to the original.
