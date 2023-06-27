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
package ru.pixnews.library.igdb.internal

import ru.pixnews.library.igdb.IgdbEndpoint
import ru.pixnews.library.igdb.IgdbResult
import ru.pixnews.library.igdb.IgdbWebhookApi
import ru.pixnews.library.igdb.IgdbWebhookApi.WebhookMethod
import ru.pixnews.library.igdb.IgdbWebhookApi.WebhookMethod.CREATE
import ru.pixnews.library.igdb.IgdbWebhookApi.WebhookMethod.DELETE
import ru.pixnews.library.igdb.IgdbWebhookApi.WebhookMethod.UPDATE
import ru.pixnews.library.igdb.error.IgdbHttpErrorResponse
import ru.pixnews.library.igdb.internal.IgdbRequest.DeleteRequest
import ru.pixnews.library.igdb.internal.IgdbRequest.FormUrlEncodedPostRequest
import ru.pixnews.library.igdb.internal.IgdbRequest.GetRequest
import ru.pixnews.library.igdb.internal.webhook.IgdbWebhookListJsonParser
import ru.pixnews.library.igdb.model.IgdbWebhook
import ru.pixnews.library.igdb.model.IgdbWebhookId

internal class IgdbWebhookApiImplementation(
    val requestExecutor: RequestExecutor,
) : IgdbWebhookApi {
    override suspend fun registerWebhook(
        endpoint: IgdbEndpoint<*>,
        url: String,
        method: WebhookMethod,
        secret: String,
    ): IgdbResult<List<IgdbWebhook>, IgdbHttpErrorResponse> {
        val request = FormUrlEncodedPostRequest(
            path = endpoint.webhookPath,
            formUrlEncodedParameters = mapOf(
                "url" to url,
                "method" to method.igdbApiId,
                "secret" to secret,
            ),
            successResponseParser = IgdbWebhookListJsonParser,
        )
        return requestExecutor(request)
    }

    override suspend fun getAllWebhooks(): IgdbResult<List<IgdbWebhook>, IgdbHttpErrorResponse> {
        val request = GetRequest(
            path = "webhooks",
            queryParameters = mapOf(),
            successResponseParser = IgdbWebhookListJsonParser,
        )
        return requestExecutor(request)
    }

    override suspend fun getWebhook(webhookId: IgdbWebhookId): IgdbResult<List<IgdbWebhook>, IgdbHttpErrorResponse> {
        val request = GetRequest(
            path = "webhooks/${webhookId.value}",
            queryParameters = mapOf(),
            successResponseParser = IgdbWebhookListJsonParser,
        )
        return requestExecutor(request)
    }

    override suspend fun deleteWebhook(webhookId: IgdbWebhookId): IgdbResult<List<IgdbWebhook>, IgdbHttpErrorResponse> {
        val request = DeleteRequest(
            path = "webhooks/${webhookId.value}",
            queryParameters = mapOf(),
            successResponseParser = IgdbWebhookListJsonParser,
        )
        return requestExecutor(request)
    }

    override suspend fun testWebhook(
        endpoint: IgdbEndpoint<*>,
        webhookId: IgdbWebhookId,
        entityId: String,
    ): IgdbResult<String, IgdbHttpErrorResponse> {
        val webhookPath = endpoint.getTestWebhookPath(webhookId)
        val request = FormUrlEncodedPostRequest(
            path = webhookPath,
            formUrlEncodedParameters = mapOf(),
            queryParameters = mapOf(
                "entityId" to entityId,
            ),
            successResponseParser = { stream -> stream.reader().readText() },
        )
        return requestExecutor(request)
    }

    private companion object {
        private val WebhookMethod.igdbApiId: String
            get() = when (this) {
                CREATE -> "create"
                DELETE -> "delete"
                UPDATE -> "update"
            }
    }
}
