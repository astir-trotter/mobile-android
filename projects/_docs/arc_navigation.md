# ArcNavigationView
# Usage

```xml
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    ...

    <com.rom4ek.arcnavigationview.ArcNavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        android:fitsSystemWindows="true"
        app:itemBackground="@android:color/white"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer"
        app:arc_cropDirection="cropOutside|cropInside"
        app:arc_width="96dp"/>
</android.support.v4.widget.DrawerLayout>
```

# Sample

## Crop Outside

```xml
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    ...

    <com.rom4ek.arcnavigationview.ArcNavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        android:fitsSystemWindows="true"
        app:itemBackground="@android:color/white"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer"
        app:arc_cropDirection="cropOutside"
        app:arc_width="96dp"/>
</android.support.v4.widget.DrawerLayout>
```

## Crop Inside

```xml
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    ...

    <com.rom4ek.arcnavigationview.ArcNavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        android:fitsSystemWindows="true"
        app:itemBackground="@android:color/white"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer"
        app:arc_cropDirection="cropInside"
        app:arc_width="96dp"/>
</android.support.v4.widget.DrawerLayout>
```

## Translucent status or navigation bar

Simply add next lines to your ```styles-v21``` folder

```xml
<style name="AppTheme" parent="AppTheme.Base">
    <item name="android:windowTranslucentNavigation">true</item>
    <item name="android:windowTranslucentStatus">true</item>
</style>
```

# Download

In your module's build.gradle file:

```groovy
dependencies {
    compile 'com.rom4ek:arcnavigationview:1.0.3'
}
```

# Additionally

```ArcNavigationView``` also supports end|right gravity mode for displaying it on the right side of the screen. To prevent child views from cutting I recommend to support right-to-left direction. For that you need:

1. Don't forget to support right-to-left mode by adding ```android:supportsRtl="true"``` inside your ```<application/>``` tag in ```AndroidManifest.xml```.
2. Add ```android:layoutDirection="rtl"``` to ```ArcNavigationView```.

## TODO

* Implement child views re-layout to prevent them from cutting, while using end|right gravity mode with left-to-right direction.
