/*
 * Copyright 2023 Alexey Illarionov
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
package ru.pixnews.foundation.testing.util

import android.app.UiAutomation
import android.os.ParcelFileDescriptor.AutoCloseInputStream
import java.io.InputStream

public fun UiAutomation.executeShellCommandIs(command: String): InputStream {
    return AutoCloseInputStream(this.executeShellCommand(command))
}
