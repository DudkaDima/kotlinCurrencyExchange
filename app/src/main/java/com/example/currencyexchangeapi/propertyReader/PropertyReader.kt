package com.example.currencyexchangeapi.propertyReader

import android.content.Context
import android.content.res.AssetManager
import com.example.currencyexchangeapi.exchange.Exchange
import java.io.InputStream
import java.util.Properties

class PropertyReader(
    private var context: Context,
    ) {
    private lateinit var properties: Properties;

    fun getProperties(file: String): Properties {
        try {
            var assetManager: AssetManager = context.assets;
            var inputStream: InputStream = assetManager.open(file);
        } catch (e: Exception) {
            println(e.message)
        }
        return properties;
    }
}