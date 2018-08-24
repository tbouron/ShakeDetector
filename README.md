Shake Detector library for Android (ShakeDetector)
========

This library provides a easy way to detect a shake movement using the build-in accelerometer and fire
a callback on the UI thread every times it happens.

Usage
=======

Shake Detector library is pushed to Maven Central as an AAR, so you just need to add the following dependency to your `build.gradle`

    dependencies {
        implementation 'com.github.tbouron.shakedetector:library:1.0.0@aar'
    }

The API is designed to follow closely an activity or fragment lifecycle. Here is an example of how
to use it within an activity

    public class myActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.my_activity);

            ShakeDetector.create(this, new OnShakeListener() {
                @Override
                public void OnShake() {
                    Toast.makeText(getApplicationContext(), "Device shaken!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        protected void onResume() {
            super.onResume();
            ShakeDetector.start();
        }

        @Override
        protected void onStop() {
            super.onStop();
            ShakeDetector.stop();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            ShakeDetector.destroy();
        }
    }

You can also change the default configuration by using the following method:

    ShakeDetector.updateConfiguration(float sensibility, int numberOfShake);

For more information, please check out [the source code of the example app on Github](https://github.com/tbouron/ShakeDetector/blob/master/example/src/main/java/com/github/tbouron/shakedetector/example/MainActivity.java)

Example app
=======

You can test the library through **[example app published on Google Play](https://play.google.com/store/apps/details?id=com.github.tbouron.shakedetector.example)**

<img src="https://raw.githubusercontent.com/tbouron/ShakeDetector/master/art/screenshots/en/device-2014-03-21-095736.png" width="150" hspace="20">
<img src="https://raw.githubusercontent.com/tbouron/ShakeDetector/master/art/screenshots/en/device-2014-03-21-095822.png" width="150" hspace="20">
<img src="https://raw.githubusercontent.com/tbouron/ShakeDetector/master/art/screenshots/en/device-2014-03-21-095850.png" width="150" hspace="20">

Author
=======

Thomas Bouron - [tbouron@gmail.com](mailto:tbouron@gmail.com) - [Google+](https://plus.google.com/u/0/104567775398355774153/posts)

License
=======

    Copyright 2014 Thomas Bouron.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
