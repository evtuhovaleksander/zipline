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
package app.cash.zipline.gradle

import app.cash.zipline.loader.SignatureAlgorithmId
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

abstract class ZiplineExtension {
  abstract val mainModuleId: Property<String>
  abstract val mainFunction: Property<String>
  abstract val version: Property<String>
  abstract val signingKeys: NamedDomainObjectContainer<SigningKey>
  abstract val httpServerPort: Property<Int>
  abstract val metadata: MapProperty<String, String>

  abstract class SigningKey(val name: String) {
    abstract val algorithmId: Property<SignatureAlgorithmId>
    abstract val privateKeyHex: Property<String>
  }
}
