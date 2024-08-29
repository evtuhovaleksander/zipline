/*
 * Copyright (C) 2022 Block, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.cash.zipline.samples.worldclock


import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MainActivityContent()
    }
  }

  @Composable
  fun MainActivityContent() {
    Column {
      Text(text = "Activity A")

      Button(onClick = {
        val intent = Intent(this@MainActivity,WorldClockActivity::class.java)
        startActivity(intent)
      }) {
        Text(text = "Open Activity B")
      }
    }
  }
}






@NoLiveLiterals
class WorldClockActivity : ComponentActivity() {
  private val scope = MainScope()
  val leakerLis = LeakerLis()


  private lateinit var worldClockAndroid: WorldClockAndroid

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val model = worldClockAndroid.models.collectAsState()
      Column(
        modifier = Modifier
          .padding(horizontal = 12.dp)
          .fillMaxWidth()
          .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
      ) {
        Text(
          text = model.value.label,
          fontSize = 38.sp,
          textAlign = TextAlign.Left,
        )
      }
    }

    worldClockAndroid = WorldClockAndroid(applicationContext, scope)
    scope.launch {
      worldClockAndroid.start()
    }
  }

  override fun onDestroy() {
    worldClockAndroid.close()
    scope.cancel()
    super.onDestroy()
  }
}
