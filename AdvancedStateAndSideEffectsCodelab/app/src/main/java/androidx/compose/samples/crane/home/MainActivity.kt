/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.samples.crane.details.launchDetailsActivity
import androidx.compose.samples.crane.ui.CraneTheme
import androidx.compose.samples.crane.util.ProvideImageLoader
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lay out your app in full screen.
        // https://developer.android.google.cn/training/gestures/edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            // Makes sure the app is drawn below the OS top bar.
            ProvideWindowInsets {
                ProvideImageLoader {
                    // The App's style/theme.
                    CraneTheme {
                        MainScreen(
                            // Passes a lambda which call the detail view with the clicked item.
                            onExploreItemClicked = { launchDetailsActivity(context = this, item = it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainScreen(onExploreItemClicked: OnExploreItemClicked) {
    Surface(color = MaterialTheme.colors.primary) {
        var showLandingScreen by remember { mutableStateOf(true) }
        if (showLandingScreen) {
            LandingScreen(onTimeout = { showLandingScreen = false })
        } else {
            CraneHome(onExploreItemClicked = onExploreItemClicked)
        }
    }
}
