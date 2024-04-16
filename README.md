# Pixnews

Repository for experimenting with modern Android technologies.\
There will be a complete readme someday.

### Associated libraries

Some parts of the project have been moved to separate repositories and are now developed as stand-alone libraries.
Here is a list of all related projects.

##### Igdbclient

Kotlin Multiplatform wrapper for the IGDB.com (Internet Game Database API.

https://github.com/illarionov/igdbclient

##### Compose Debug Layout Toolbox

A set of tools for Compose to help you align objects: layouts, grids and rulers.

https://github.com/illarionov/pixnews-debuglayout

##### Pixnews Anvil codegen

Some code generators using the Anvil compiler plugin used in the application that simplify the creation of Dagger
components and modules.

https://github.com/illarionov/pixnews-anvil-codegen

##### Wasm SQLite Open Helper

Experimental implementation of the *SupportSQLiteOpenHelper* based on SQLite compiled for WASM. Intended for use
in Android non-instrumented tests with Android Room Library.

https://github.com/illarionov/wasm-sqlite-open-helper

##### Fbase Config Generator Gradle Plugin

Gradle plugin that generates configuration for Firebase services so that they can be used without using
*google-services.json* and without Firebase's built-in automatic initialization via *FirebaseInitProvider*.

https://github.com/illarionov/fbase-config-generator-gradle-plugin

##### Prefiller Gradle Plugin fork

Fork of the Gradle plugin that generates pre-populated Room databases at compile time. It includes some
fixes and additional features.

https://github.com/illarionov/prefiller-gradle-plugin

### Acknowledgements

This code was inspired by the following projects and repositories:

- [Now in Android App](https://github.com/android/nowinandroid)
  Fully functional Android sample app from Google
- [Tivi](https://github.com/chrisbanes/tivi)
  TV show tracking Android app by Chris Banes
- [CatchUp](https://github.com/ZacSweers/CatchUp)
  An app for catching up on things by Zac Sweers
- [ReactiveCircus/streamlined](https://github.com/ReactiveCircus/streamlined)
  News app by Yang C
- [Whetstone](https://github.com/deliveryhero/whetstone)
  Dependency injection framework for Android from Delivery Hero
- [Gradle Project Setup Howto](https://github.com/jjohannes/gradle-project-setup-howto/tree/android)
  "How to structure a growing Gradle project" guide by Jendrik Johannes

Thanks to the authors of these projects and the authors of all other tools and libraries used in the code.

### License

Pixnews is distributed under the terms of the Apache License (Version 2.0). See the
[license](https://github.com/illarionov/Pixnews/blob/main/LICENSE) for more information.
