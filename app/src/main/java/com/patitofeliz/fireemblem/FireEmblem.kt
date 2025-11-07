package com.patitofeliz.fireemblem

import android.app.Application

class FireEmblem : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        Manager.init(this)
    }
}