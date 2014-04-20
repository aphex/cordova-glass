##Cordova Google Glass Plugin##
---

This plugin modifies the Android platform in your Cordova based application with Google Glass Compatibility. At its most basic usage it will allow you to launch your application via a voice trigger. Advanced usage allows for voice prompts to be captured during the launch process and sent into the application. By default the plugin will automatically dispatch touch events ('**touchstart**', '**touchend**' and '**touchmove**') and dispatch native gesture events on the document. 

###Basic Usage###

Add plugin via Node

`cordova plugin add https://github.com/aphex/cordova-glass`

Modify Voice Trigger located in **{app}/platforms/android/res/values/glass.xml**

`<string name="app_launch_voice_trigger">hello cordova</string>`


###Voice Trigger Prompt###

A Voice Trigger prompt allows your application to ask the user for voice input before the application launches.  An example of what this might sound like could be 

- 'Ok Glass' (wake up glass for voice input)
- 'Open movie search' (launch your movie search application)
- 'Goodfellas' (**capture a voice trigger prompt from the user**)

To use first uncomment the following line from **{app}/platforms/android/res/xml/app_launch_voice_trigger.xml**

` <input prompt="@string/app_launch_voice_prompt"/>`

Then modify Voice Trigger Prompt located in **{app}/platforms/android/res/values/glass.xml**

`<string name="app_launch_voice_prompt">prompt question</string>`

After your application has received a 'deviceready' event you will be able to retrieve the input from the user as shown in the follow where 'results' is a array.

```
rossgerbasi.glass.getLaunchParams(
	function(results) {
		console.log(results);
	},
	function () {
		console.log("Error getting launch Params");
	}
);
```

###Native Gestures###
Add an event listener to the document of your web application

```
document.addEventListener('swiperight', 
	function() {
		console.log('Got that Swipe Right');
	}
);
```

For example to exit your application on swipe down you can use the following

```
document.addEventListener('swipedown', function() {
	if(navigator && navigator.app) {
		navigator.app.exitApp();
	} else {
		console.log("Exiting Application");
	}
})
```

####Basic Events####
- tap
- longpress
- swipeup
- swipedown
- swipeleft 
- swiperight
- twotap
- twolongpress
- twoswipeup
- twoswipedown
- twoswipeleft
- twoswiperight
- threetap
- threelongpress


*A helpful note here a swipe to the left is backwards on the glass touchpad (from your eye towards your ear) where a swipe to the right is from your ear towards your eye.*

####Advanced Events####
Advanced events have data associated with them

- scroll
- twofingerscroll
- fingercountchanged


####Advanced Examples####

```
// Data contains properties type, displacement, delta and velocity
document.addEventListener("scroll", function(data) {
	console.log("Got that scroll");
	console.log(data.displacement);
	console.log(data.delta);
	console.log(data.velocity);
})
```

```
// Data contains properties type, displacement, delta and velocity
document.addEventListener("twofingerscroll", function(data) {
	console.log("Got that two finger scroll");
	console.log(data.displacement);
	console.log(data.delta);
	console.log(data.velocity);
})
```

```
// Data contains properties type, from and to
document.addEventListener("fingercountchanged", function(data) {
	console.log("Fingers changed from " + data.from + " to " + data.to);
})
```


###Config###
The following config.xml preferences are available via this plugin

####keepAwake####

`<preference name="rossgerbasi.glass.keepAwake" value="false"/>`

Setting this to true will force your application to always stay on, no screen dimming. A User will need to exit your application via a swipe down gesture to close it.

####touchDisabled####

`<preference name="rossgerbasi.glass.touchDisabled" value="false"/>`

Setting this to true will disable the translation of touch events from the touchpad to the web view. Essentially turning off 'touchstart', 'touched' and 'touchmove' events.

####gesturesDisabled####

`<preference name="rossgerbasi.glass.gesturesDisabled" value="false"/>`

Setting this to true will disable all native gesture events (swipe, long press, scroll, etc).