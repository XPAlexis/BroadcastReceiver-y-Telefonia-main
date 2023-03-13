package com.example.proyectobroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {

                // El teléfono está sonando, obtén el número de teléfono que está llamando
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                Log.i("telefono",phoneNumber.toString())

                Log.i("num saves","numero guardado")

                // Obtenemos los datos guardados en las preferencias compartidas
                val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                Log.i("myPrefs",sharedPrefs.toString())
                val savedPhoneNumber = sharedPrefs.getString("phoneNumber", "")
                Log.i("numero",savedPhoneNumber.toString())
                val savedMessage = sharedPrefs.getString("message", "")
                Log.i("mensaje",savedMessage.toString())

                // Si el número de teléfono que está llamando coincide con el número guardado en las preferencias,
                // enviamos la respuesta automática
                if (phoneNumber == savedPhoneNumber) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(phoneNumber, null, savedMessage, null, null)
                    Log.i("respuesta","mensaje enviado")
                }else{
                    Log.i("respuesta","mensaje NO enviado")
                }
            }
        }
    }
}
