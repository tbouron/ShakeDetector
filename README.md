Shake detector library (ShakeDetector)
========

This library provides a easy way to detect a shake movement using the build-in accelerometer and fire
a callback on the UI thread every times it happens.

Usage
=======

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