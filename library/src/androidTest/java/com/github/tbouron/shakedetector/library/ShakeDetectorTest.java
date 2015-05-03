/*
 * Copyright 2014 Thomas Bouron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tbouron.shakedetector.library;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.test.mock.MockContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShakeDetectorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void throwsExceptionIfContextIsNull() {
        ShakeDetector.create(null, mock(ShakeDetector.OnShakeListener.class));

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Context must not be null.");
    }

    @Test
    public void throwsExceptionIfOnShakeListenerIsNull() {
        ShakeDetector.create(new MockContext(), null);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Shake listener must not be null.");
    }

    @Test
    public void shakeListenerCorrectlyCreated() {
        Context context = new MockContext();
        SensorManager sensorManager = mock(SensorManager.class);
        when(context.getSystemService(any(String.class))).thenReturn(sensorManager);

        ShakeDetector.create(context, mock(ShakeDetector.OnShakeListener.class));

        verify(sensorManager).registerListener(any(SensorEventListener.class), any(Sensor.class), any(Integer.class));
    }

    @Test
    public void shouldFail() {
        assertTrue(false);
    }
}