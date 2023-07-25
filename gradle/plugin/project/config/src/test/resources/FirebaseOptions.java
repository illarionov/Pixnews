/*
 * Copyright (c) 2023, the Pixnews project authors and contributors. Please see the AUTHORS file for details.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */
package com.google.firebase;

// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import java.util.Objects;

// Stub FirebaseOptions from firebase for tests

/**
 * Configurable Firebase options.
 */
public final class FirebaseOptions {

    private final String apiKey;
    private final String applicationId;
    private final String databaseUrl;
    private final String gaTrackingId;
    private final String gcmSenderId;
    private final String storageBucket;
    private final String projectId;

    public static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            IllegalStateException expression1 = new IllegalStateException(String.valueOf(errorMessage));
            throw expression1;
        }
    }

    public static boolean isEmptyOrWhitespace(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Builder for constructing FirebaseOptions.
     */
    public static final class Builder {
        private String apiKey;
        private String applicationId;
        private String databaseUrl;
        private String gaTrackingId;
        private String gcmSenderId;
        private String storageBucket;
        private String projectId;

        /**
         * Constructs an empty builder.
         */
        public Builder() {
        }

        /**
         * Initializes the builder's values from the options object.
         *
         * <p>The new builder is not backed by this objects values, that is changes made to the new
         * builder don't change the values of the origin object.
         */
        public Builder(FirebaseOptions options) {
            applicationId = options.applicationId;
            apiKey = options.apiKey;
            databaseUrl = options.databaseUrl;
            gaTrackingId = options.gaTrackingId;
            gcmSenderId = options.gcmSenderId;
            storageBucket = options.storageBucket;
            projectId = options.projectId;
        }

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder setApplicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder setDatabaseUrl(String databaseUrl) {
            this.databaseUrl = databaseUrl;
            return this;
        }

        /**
         * @hide
         */
        // TODO: unhide once an API (AppInvite) starts reading it.
        public Builder setGaTrackingId(String gaTrackingId) {
            this.gaTrackingId = gaTrackingId;
            return this;
        }

        public Builder setGcmSenderId(String gcmSenderId) {
            this.gcmSenderId = gcmSenderId;
            return this;
        }

        public Builder setStorageBucket(String storageBucket) {
            this.storageBucket = storageBucket;
            return this;
        }


        public Builder setProjectId(String projectId) {
            this.projectId = projectId;
            return this;
        }


        public FirebaseOptions build() {
            return new FirebaseOptions(
                    applicationId, apiKey, databaseUrl, gaTrackingId, gcmSenderId, storageBucket, projectId);
        }
    }

    private FirebaseOptions(
            String applicationId,
            String apiKey,
            String databaseUrl,
            String gaTrackingId,
            String gcmSenderId,
            String storageBucket,
            String projectId) {
        checkState(!isEmptyOrWhitespace(applicationId), "ApplicationId must be set.");
        this.applicationId = applicationId;
        this.apiKey = apiKey;
        this.databaseUrl = databaseUrl;
        this.gaTrackingId = gaTrackingId;
        this.gcmSenderId = gcmSenderId;
        this.storageBucket = storageBucket;
        this.projectId = projectId;
    }

    /**
     * API key used for authenticating requests from your app, e.g.
     * AIzaSyDdVgKwhZl0sTTTLZ7iTmt1r3N2cJLnaDk, used to identify your app to Google servers.
     */

    public String getApiKey() {
        return apiKey;
    }

    /**
     * The Google App ID that is used to uniquely identify an instance of an app.
     */

    public String getApplicationId() {
        return applicationId;
    }

    /**
     * The database root URL, e.g. http://abc-xyz-123.firebaseio.com.
     */

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    /**
     * The tracking ID for Google Analytics, e.g. UA-12345678-1, used to configure Google Analytics.
     *
     * @hide
     */
    // TODO: unhide once an API (AppInvite) starts reading it.
    public String getGaTrackingId() {
        return gaTrackingId;
    }

    /**
     * The Project Number from the Google Developer's console, for example 012345678901, used to
     * configure Google Cloud Messaging.
     */

    public String getGcmSenderId() {
        return gcmSenderId;
    }

    /**
     * The Google Cloud Storage bucket name, e.g. abc-xyz-123.storage.firebase.com.
     */

    public String getStorageBucket() {
        return storageBucket;
    }

    /**
     * The Google Cloud project ID, e.g. my-project-1234
     */

    public String getProjectId() {
        return projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirebaseOptions that = (FirebaseOptions) o;
        return Objects.equals(apiKey, that.apiKey) && Objects.equals(applicationId, that.applicationId) && Objects.equals(databaseUrl, that.databaseUrl) && Objects.equals(gaTrackingId, that.gaTrackingId) && Objects.equals(gcmSenderId, that.gcmSenderId) && Objects.equals(storageBucket, that.storageBucket) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, applicationId, databaseUrl, gaTrackingId, gcmSenderId, storageBucket, projectId);
    }

    @Override
    public String toString() {
        return "FirebaseOptions{" +
                "apiKey='" + apiKey + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", gaTrackingId='" + gaTrackingId + '\'' +
                ", gcmSenderId='" + gcmSenderId + '\'' +
                ", storageBucket='" + storageBucket + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
