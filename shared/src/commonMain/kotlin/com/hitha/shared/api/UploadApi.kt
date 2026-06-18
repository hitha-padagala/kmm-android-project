package com.hitha.shared.api

import com.hitha.shared.model.UploadResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

class UploadApi(private val client: HttpClient) {

    suspend fun uploadFile(
        fileName: String,
        fileBytes: ByteArray,
        contentType: String = "application/octet-stream"
    ): Result<UploadResponse> = runCatching {
        client.submitFormWithBinaryData(
            url = "https://httpbin.org/post",
            formData = formData {
                append("file", fileBytes, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                    append(HttpHeaders.ContentType, contentType)
                })
            }
        ).body<UploadResponse>()
    }
}
