package com.example.bingtoy.di

import com.example.bingtoy.data.remote.websocket.EchoWebSocketListener
import com.example.bingtoy.data.remote.websocket.UpbeatWebSocketListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object WebsocketModule {
    @Provides
    @Named("echo")
    fun provideEchoWebSocket(
        @Named("websocket") okHttpClient: OkHttpClient,
        @Named("echo") request: Request,
        listener: EchoWebSocketListener,
    ): WebSocket = okHttpClient.newWebSocket(request, listener) // 수정

    @Singleton
    @Provides
    @Named("echo")
    fun provideEchoRequest(): Request =
        Request.Builder()
            .url(ECHO_URL)
            .build()

    @Singleton
    @Provides
    fun provideEchoWebSocketListener(): EchoWebSocketListener = EchoWebSocketListener()

    @Singleton
    @Provides
    @Named("upbeat")
    fun provideUpbeatWebSocket(
        @Named("websocket") okHttpClient: OkHttpClient,
        @Named("upbeat") request: Request,
        listener: UpbeatWebSocketListener,
    ): WebSocket = okHttpClient.newWebSocket(request, listener) // 수정

    @Singleton
    @Provides
    @Named("upbeat")
    fun provideUpbeatRequest(): Request =
        Request.Builder()
            .url(UPBEAT_URL)
            .build()

    @Singleton
    @Provides
    fun provideUpbeatWebSocketListener(): UpbeatWebSocketListener = UpbeatWebSocketListener()

    private const val ECHO_URL = "wss://echo.websocket.events"
    private const val UPBEAT_URL = "wss://api.upbit.com/websocket/v1"
}
