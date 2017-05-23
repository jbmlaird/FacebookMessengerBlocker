![app_gif](https://media.giphy.com/media/TNrGtawLaLaLK/giphy.gif)

# FacebookMessengerBlocker
Other "drunk message" apps on the Store will block Messenger entirely but what if you're lost and need to meet up with your friends? You would have to use an external application. This app makes it possible to block specific contacts on Facebook Messenger without blocking Messenger itself. 

# How
Using Android's in-built [AccessibilityService](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService.html) one is able to monitor screen changes. When the user opens a new Messenger chat and it has a name that the user has added to their blacklist, it will display a view over Messenger until they press back.

However, [since Android 4.0.3](https://commonsware.com/blog/2012/03/03/tapjacking-defunct.html) that system alert view is not able to intercept touches so the user is able to press buttons behind the view even though they can't see them. This means that the user could press the phone or the video button to call the user even with the screen displayed. This means that if the user pressed back they would still be on the chat screen when the view disappeared.

Pressing back unfortunately does not cause the AccessibilityService to register a new event so it's not possible to just check that the user has left the chat.

Because of these issues and the fact that the user has to explicitly turn the AccessibilityService on in the Settings (and therefore they could just go back to the Settings and turn it off) I will not be taking this project any further.

# References
[The CommonsBlog - Tapjacking, Defunct?](https://commonsware.com/blog/2012/03/03/tapjacking-defunct.html)
