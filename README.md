# NyTimes News APP

### Build With 🏗️

- Kotlin - Programming language for Android
- Hilt-Dagger - Standard library to incorporate Dagger dependency injection into an Android application.
- Retrofit -  A HTTP client.
- Coroutines - For asynchronous
- LiveData - Data objects that notify views.
- ViewModel - Stores UI-related data that isn't destroyed on UI changes.
- ViewBinding - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- Glide - An image loading and caching library for Android focused on smooth scrolling

### Project Architecture 🗼

This app uses [MVVM (Model View View-Model)] architecture.

### How to run all tests from terminal

- To run the tests open terminal
- run './gradlew testDebugUnitTest'
- In case of Success, all tests have been passed


### How to check code coverage

- To check test coverage, open terminal and run the following command: "./gradlew testDebugUnitTestCoverage"
- In case of Success, you will be able to open the tests coverage report on rootDirectory/build/coverage-report/index.html
- Open the html file of report in browser