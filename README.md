# Stack Overflow Challenge

This app allows searching for users on StackOverflow, and viewing limited information about them. It also has relatively comprehensive UI tests.

The architecture is very simple due to only having one piece of functionality, and requiring activities instead of fragments. The project could easily be converted to MVP if further functionality was to be added.


## Setup instructions
1. Check out repo
2. Build!

## Libraries used
* **UI**: AndroidX's appcompat, recyclerview, and constraintlayout
* **Network**: Retrofit2 (inc. an RxJava extension) & OkHttp
* **Observables**: RxJava / RxAndroid
* **Images**: Picasso
* **UI Testing**: Espresso (core / intents / contrib), AndroidX's test runner & rules, OkHttp's MockWebServer