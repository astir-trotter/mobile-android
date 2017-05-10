# Shape Image View

Gradle dependency:
```Groovy
compile 'com.github.siyamed:android-shape-imageview:0.9.+@aar'
```

###Shader Based ImageView's
####BubbleImageView
```XML
<com.github.siyamed.shapeimageview.BubbleImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@drawable/neo"
    app:siArrowPosition="right"
    app:siSquare="true"/>
```

Attributes:
* `siTriangleHeight` the height of the bubble pointer in dp
* `siArrowPosition` where to point the arrow, currently `left|right`
* `siSquare` set width and height to the minimum of the given values `true|false`

####RoundedImageView
```XML
<com.github.siyamed.shapeimageview.RoundedImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@drawable/neo"
    app:siRadius="6dp"
    app:siBorderWidth="6dp"
    app:siBorderColor="@color/darkgray"
    app:siSquare="true"/>
```

Attributes:
* `siBorderColor` border color
* `siBorderWidth` border width in dp
* `siBorderAlpha` alpha value of the border between 0.0-1.0
* `siRadius` corner radius in dp
* `siSquare` set width and height to the minimum of the given values `true|false`

####CircularImageView
```XML
<com.github.siyamed.shapeimageview.CircularImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@drawable/neo"
    app:siBorderWidth="6dp"
    app:siBorderColor="@color/darkgray"/>
```

Attributes:
* `siBorderColor` border color
* `siBorderWidth` border width in dp
* `siBorderAlpha` alpha value of the border between 0.0-1.0

####ShapeImageView

- DiamondImageView
- PentagonImageView
- HexagonImageView
- OctogonImageView
- StarImageView
- HeartImageView

```XML
<com.github.siyamed.shapeimageview.{ClassName}
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="8dp"
    android:src="@drawable/neo"
    app:siBorderWidth="8dp"
    app:siBorderColor="@color/darkgray"/>
```

Attributes:
* `siBorderColor` border color
* `siBorderWidth` border width in dp
* `siBorderAlpha` alpha value of the border between 0.0-1.0
* `siStrokeCap` border stroke cap type `butt|round|square`
* `siStrokeJoin` border stroke join type `bevel|miter|round`
* `siSquare` set width and height to the minimum of the given values `true|false`
* `siShape` a reference to an SVG. This is used by ShapeImageView, not the subclasses of it.

###Bitmap Mask Based ImageViews

This view uses extra bitmaps for bitmap masks. Therefore it would be good to use them for very custom shapes,
possibly not in a recycling view.

```XML
<com.github.siyamed.shapeimageview.mask.PorterShapeImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:siShape="@drawable/star"
    android:src="@drawable/neo"
    app:siSquare="true"/>
```

```XML
<com.github.siyamed.shapeimageview.mask.PorterShapeImageView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:siShape="@drawable/shape_rounded_rectangle"
    android:src="@drawable/neo"
    app:siSquare="true"/>
```

rounded rectangle shape definition in XML:

```XML
<shape android:shape="rectangle" xmlns:android="http://schemas.android.com/apk/res/android">
    <corners
        android:topLeftRadius="18dp"
        android:topRightRadius="18dp"
        android:bottomLeftRadius="18dp"
        android:bottomRightRadius="18dp" />
    <solid android:color="@color/black" />
</shape>
```

Attributes:
* `siShape` the bitmap mask shape, either a shape drawable or a bitmap
* `siSquare` set width and height to the minimum of the given values `true|false`

This method reads a shape file (either bitmap or an android shape xml), creates a bitmap object using this shape, and finally combines the bitmap of the real image to be shown and the mast bitmap using xfermode.

## Proguard

```
-dontwarn android.support.v7.**
-keep class android.support.v7.** { ; }
-keep interface android.support.v7.* { ; }
-keepattributes *Annotation,Signature
-dontwarn com.github.siyamed.**
-keep class com.github.siyamed.shapeimageview.**{ *; }
```
