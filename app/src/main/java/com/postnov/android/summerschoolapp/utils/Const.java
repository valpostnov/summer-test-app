/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.postnov.android.summerschoolapp.utils;

/**
 *
 * Constants used by multiple classes in this package
 */
public final class Const {

    public static final String BROADCAST_ACTION = "com.postnov.android.summerschoolapp.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.postnov.android.summerschoolapp.STATUS";


    // Bad Request
    public static final int STATE_ACTION_UNKNOWN = -1;

    // Bad Request
    public static final int STATE_ACTION_400 = 400;

    // Not Found
    public static final int STATE_ACTION_404 = 404;

    // Gateway Timeout
    public static final int STATE_ACTION_504 = 504;

    // Service Unavailable
    public static final int STATE_ACTION_503 = 503;

    // OK
    public static final int STATE_ACTION_200 = 200;

}
