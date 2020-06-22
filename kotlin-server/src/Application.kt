package com.example

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*



fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(ForwardedHeaderSupport)
    install(DefaultHeaders)
    install(CORS)
    {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        allowCredentials = true
        anyHost()
    }
    val transactionsManager = TransactionsManager()

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, transactionsManager.get())
        }

        post("/transaction") {
            val postParameters: Parameters = call.receiveParameters()

            val type = postParameters["type"].toString()
            val amount = postParameters["amount"]?.toIntOrNull()
            var transaction: Any? = null

            if(amount != null) {
                transaction = transactionsManager.processTransaction(type, amount)
            }

            call.respond(
                HttpStatusCode.Forbidden,
                transaction ?: ""
            )
        }

        get("/transaction/{id}") {
            val id = call.parameters["id"]
            var transaction:Any? = null

            if (id != null) {
                transaction  = transactionsManager.findById(id)
            }

            call.respond(
                if (transaction == null) HttpStatusCode.NotFound else HttpStatusCode.OK,
                (transaction)?: ""
            )
        }

        get("/transaction") {
            call.respond(HttpStatusCode.OK, transactionsManager.get())
        }
    }
}
