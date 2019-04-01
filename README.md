# Stack Overflow Challenge

This app allows searching for users on StackOverflow, and viewing limited information about them. It also has relatively comprehensive UI tests.

The architecture is very simple due to only having one piece of functionality, and requiring activities instead of fragments. Users are passed to the `UserActivity` as a parcelable as they're small objects. The project could easily be converted to MVP if further functionality was to be added.


## Setup instructions
1. Check out repo
2. Build!

## Libraries used
* **UI**: AndroidX's appcompat, recyclerview, and constraintlayout
* **Network**: Retrofit2 (inc. an RxJava extension) & OkHttp
* **Observables**: RxJava / RxAndroid
* **Images**: Picasso
* **UI Testing**: Espresso (core / intents / contrib), AndroidX's test runner & rules, OkHttp's MockWebServer

## Notes
* Fully Kotlin & AndroidX.
* Location & age are hidden if they're not present (which is usually the case)
* There is extra padding below badges on the user's profile, as per ASCII design. Unsure if intentional!
* Written on a Windows machine, apologies for any cross-platform issues.