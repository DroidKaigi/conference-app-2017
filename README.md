# ![](app/src/main/res/mipmap-mdpi/ic_launcher.png) DroidKaigi 2017 official Android app [![Circle CI](https://circleci.com/gh/DroidKaigi/conference-app-2017/tree/master.svg?style=svg)](https://circleci.com/gh/DroidKaigi/conference-app-2017/tree/master) [![Stories in Ready](https://badge.waffle.io/DroidKaigi/conference-app-2017.svg?label=ready&title=Ready)](http://waffle.io/DroidKaigi/conference-app-2017)

[DroidKaigi 2017](https://droidkaigi.github.io/2017/en/) is a conference tailored for developers on 9th and 10th March 2017.

[<img src="https://dply.me/rlr6yr/button/large" alt="Try it on your device via DeployGate">](https://dply.me/564onq#install)

## Features

- View conference schedule and details of each session
- Set notification for upcoming sessions on your preference
- View a map
- Search sessions and speakers

## Building and Contributing

### Environment Setup

This app depends on several libraries and plugins so make sure to set them up correctly.

#### Java8 & retrolambda support

This project uses Java8 and [retrolambda](https://github.com/orfjackal/retrolambda). If you haven't set up Java8 yet, install it from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), and set env `JAVA_HOME` or `JAVA8_HOME`.

#### Kotlin

Tests are written in Kotlin!

### Architecture

The app is built upon MVVM architecture using DataBinding, dependency injection and OR-mapper.

#### DataBinding

This project tries to use [DataBinding](http://developer.android.com/intl/ja/tools/data-binding/guide.html).

```xml
<TextView
    android:id="@+id/txt_place"
    style="@style/Tag"
    android:layout_marginEnd="@dimen/spacing_xsmall"
    android:layout_marginRight="@dimen/spacing_xsmall"
    android:layout_marginTop="@dimen/spacing_xsmall"
    android:background="@drawable/tag_language"
    android:text="@{session.place.name}" /
```

Custom attributes are also used like below.

```xml
<ImageView
    android:id="@+id/img_speaker"
    android:layout_width="@dimen/user_image_small"
    android:layout_height="@dimen/user_image_small"
    android:layout_below="@id/tag_container"
    android:layout_marginTop="@dimen/spacing_small"
    android:contentDescription="@string/speaker"
    app:speakerImageUrl="@{session.speaker.imageUrl}" />
```

BindingAdapter like `speakerImageUrl` is written in `DataBindingAttributeUtil.java`.

```java
@BindingAdapter("speakerImageUrl")
public static void setSpeakerImageUrl(ImageView imageView, @Nullable String imageUrl) {
    if (TextUtils.isEmpty(imageUrl)) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_speaker_placeholder));
    } else {
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_speaker_placeholder)
                .error(R.drawable.ic_speaker_placeholder)
                .transform(new CropCircleTransformation())
                .into(imageView);
    }
}
```

#### Dagger2

This project uses DI library [Dagger2](http://google.github.io/dagger/).
See classes in `di` package.

```
src/main/java/io/github/droidkaigi/confsched2017/di
|
|--scope
|  |--ActivityScope.java : Scope annotation for objects being alive within activity lifecycle
|  |--FragmentScope.java : Scope annotation for objects being alive within fragment lifecycle
|
|--AndroidModule.java    : Provides system services(e.g. PackageManager, ActivityManager)
|--ActivityComponent.java:
|--ActivityModule.java   : Provides activity-scoped objects
|--AppComponent.java     :
|--AppModule.java        : Provides application-scoped objects(e.g. SharedPreferences, HttpClient)
|--FragmentComponent.java:
|--FragmentModule.java   : Provides fragment-scoped objects
```

#### Orma

This project uses ORM library [Android-Orma](http://gfx.github.io/Android-Orma/).
Android-Orma is a lightning-fast and annotation based wrapper library of SQLiteDatabase.

Some model classes in `model` package have `@Table` annotation.

```java
@Table
public class Session {
    @Column(indexed = true)
    @SerializedName("id")
    public int id;

    @Column(indexed = true)
    @SerializedName("title")
    public String title;

    // ...
}
```

These classes are saved in database via `dao/SessionDao`.
To know more about Android-Orma, see [document](http://gfx.github.io/Android-Orma/).

### Task Management

We use [waffle.io](https://waffle.io/DroidKaigi/conference-app-2017) to manage tasks.

If you'd like to contribute to the project but are not sure where to start off, please look for issues labelled [welcome contribute)](https://github.com/DroidKaigi/conference-app-2017/labels/welcome%20contribute).

We've designated these issues as good candidates for easy contribution.

## Credit

This project uses some modern Android libraries.

- Android Support Libraries
  - Support v4
  - AppCompat
  - Support Annotations
  - Support Vector Drawables
  - Animated Vector Drawables
  - Design
  - RecyclerView
  - CustomTabs
- Firebase, Dagger2 and Gson - Google
- Retrofit2, Picasso, OkHttp3, AssertJ and LeakCanary - Square
- Android-Orma - gfx
- TwoWayView - Lucas Rocha
- RxJava2 and RxAndroid2 - ReactiveX
- Lightweight Stream API - Victor Melnik
- Timber - JakeWharton
- Calligraphy - Christopher Jenkins
- Stetho - Facebook
- PermissionsDispatcher - hotchemi
- kvs-schema - rejasupotaro
- Robolectric - Robolectric
- Mockito - Mockito
- Kotlin - JetBrains
- Knit - Taro Nagasawa
- Kmockito - sys1yagi

## License

```
Copyright 2017 Yusuke Konishi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
