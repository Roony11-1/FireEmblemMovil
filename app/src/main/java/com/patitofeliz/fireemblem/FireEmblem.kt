package com.patitofeliz.fireemblem

import android.app.Application
import com.patitofeliz.fireemblem.Manager

class FireEmblem : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        Manager.init(this)
    }
}