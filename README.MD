![AppIcon](https://github.com/JoaquimLey/avenging/blob/development/core/src/main/res/mipmap-xxhdpi/ic_launcher.png) 

# Avenging - MVP project

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Avenging-brightgreen.svg?style=flat)](http://android-arsenal.com/details/3/4545)
[![MaterialUp](https://img.shields.io/badge/MaterialUp-Avenging-blue.svg?style=flat)](https://material.uplabs.com/posts/avenging-mvp-project)
[![Build Status](https://travis-ci.org/JoaquimLey/avenging.svg?branch=development)](https://travis-ci.org/JoaquimLey/avenging)
[![codecov](https://codecov.io/gh/JoaquimLey/avenging/branch/development/graph/badge.svg)](https://codecov.io/gh/JoaquimLey/avenging)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fa075cf6a50a4bc0b875406842f1496a)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JoaquimLey/avenging&amp;utm_campaign=Badge_Grade)
![minSdkVersion](https://img.shields.io/badge/minSdkVersion-17-yellow.svg?style=true)
![compileSdkVersion](https://img.shields.io/badge/compileSdkVersion-24-green.svg?style=true)

### Read more on the blog post: [Avening - MVP without RxJava or Dagger](https://blog.joaquimley.com/avenging-android-mvp-23461aebe9b5#.sc9ty759u)

Android app built with an **MVP** approach **without** RxJava or Dagger. 

Uses [Marvel Comics API](https://developer.marvel.com) as a service which contains all the information about its vast library.
_Data provided by Marvel. © 2016 MARVEL_


What is MVP (ModelViewPresenter)?
---------------------------
- View is a layer that displays data and reacts to user actions. On Android, this could be an Activity, a Fragment, an android.view.View or a Dialog.
- Model is a data access layer such as database API or remote server API.
- Presenter is a layer that provides View with data from Model. Presenter also handles background tasks.

Most of the modern Android applications just use View-Model architecture.
Programmers are involved into fight with View complexities instead of solving business tasks.
Using only Model-View in your application you usually end up with "everything is connected with everything", which translates into harder scalability. With the MVP approach it easier to understand the role of each element. You didn’t became a developer to be wasting time on boring code maintenance, but to build awesome stuff!

![Mobile List](../development/art/ss_mobile_list.png) 
![Mobile Profile](../development/art/ss_mobile_profile.png) 
![Mobile Comic](../development/art/ss_mobile_comic.png)
![Mobile Search](../development/art/ss_mobile_search.png)
![Mobile List Error](../development/art/ss_mobile_error.png) 

![Wear List Default](../development/art/ss_wear_list_default.png)
![Wear List Gallery](../development/art/ss_wear_list_gallery.png)
![Wear List Loading](../development/art/ss_wear_loading.png)
![Wear List Error](../development/art/ss_wear_list_error.png)

This sample is available

[![Get it on Google Play](../development/art/google-play-badge.png)](https://play.google.com/store/apps/details?id=com.joaquimley.avenging)

## Configs & API authentication
If you wish to run your own **CI** (I'm using Travis) ensure you have the following environment variables on **your CI config**:

* IS_ CI_JOB = true
* CI_ PUBLIC_KEY = < YOUR-PUBLIC-KEY >
* CI_ PRIVATE_KEY = < YOUR-PRIVATE-KEY >

To run **locally** have your private and public keys in your **gradle.properties** file (project's root folder).

* marvelPublicKey = < YOUR-PUBLIC-KEY >
* marvelPrivateKey = < YOUR-PRIVATE-KEY >

_Mind the markedown spaces._


## Dependencies

**CORE**

* [Retrofit](http://square.github.io/retrofit)
A type-safe HTTP client for Android and Java
* [Jackson](https://github.com/square/retrofit/tree/master/retrofit-converters/jackson)
A default ObjectMapper instance will be created or one can be configured and passed to the JacksonConverter construction to further control the serialization.
* [Mockito](http://mockito.org/)
Mocking framework for unit tests written in Java
* [LeakCanary](https://github.com/square/leakcanary)
A memory leak detection library for Android and Java.

**MOBILE**

* [Design Support Library](http://developer.android.com/intl/tools/support-library/features.html#design)
The Design package provides APIs to support adding material design components and patterns to your apps.A flexible view for providing a limited window into a large data set.
* [Picasso](http://square.github.io/picasso/)
A powerful image downloading and caching library for Android 
* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/index.html)
Use Espresso to write concise, beautiful, and reliable Android UI tests

**WEAR**

* [Play Services](https://developers.google.com/android/guides/setup)
Google Play services.
* [Google Support Support v4](https://developer.android.com/topic/libraries/support-library/features.html#v4)
This library is designed to be used with Android 1.6 (API level 4) and higher. It includes the largest set of APIs compared to the other libraries
* [Picasso](http://square.github.io/picasso/)
A powerful image downloading and caching library for Android


**Issues:**
Fell free to open a new issue. Follow the [ISSUE_TEMPLATE.MD](../development/ISSUE_TEMPLATE.MD)

## Contributing

Contributions are always welcome!

Follow the "fork-and-pull" Git workflow.

 1. **Fork** the repo on GitHub
 2. **Clone** the project to your own machine
 3. **Commit** changes to your own branch
 4. **Merge** with current *development* branch
 5. **Push** your work back up to your fork
 7. Submit a **Pull request** your changes can be reviewed (please refere the issue if reported)

**Prevent** code-style related changes (at least run Ctrl+⌥+O, ⌥+⌘+L) before commiting.


### License

	Copyright © 2016 Joaquim Ley

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
	implied.
	See the License for the specific language governing permissions and
	limitations under the License.
