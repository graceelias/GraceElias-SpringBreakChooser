package com.example.graceelias_springbreakchooser

import android.R.layout
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.graceelias_springbreakchooser.databinding.ActivityMainBinding
import java.util.Locale
import java.util.Objects
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val launcherSpeechResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val textSpoken = result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull() ?: ""
            binding.editText.setText(textSpoken)
        }
    }

    private var language = "English"

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("English", "Spanish", "French", "Italian", "Mandarin", "Hindi")

        val adapter = ArrayAdapter(this, layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                Toast.makeText(
                    this@MainActivity,
                    "Say a phrase in $selectedItem",
                    Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    Locale.getDefault()
                )

                if(selectedItem.equals("English"))
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "en-US"
                    )
                    language = "English"
                }
                else if(selectedItem.equals("Spanish"))
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "es-ES"
                    )
                    language = "Spanish"
                }
                else if(selectedItem.equals("French"))
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "fr-FR"
                    )
                    language = "French"
                }
                else if(selectedItem.equals("Italian"))
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "it-IT"
                    )
                    language = "Italian"
                }
                else if(selectedItem.equals("Mandarin"))
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "zh-CN"
                    )
                    language = "Mandarin"
                }
                else
                {
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE,
                        "hi-IN"
                    )
                    language = "Hindi"
                }

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

                launcherSpeechResult.launch(intent)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH


    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            if (acceleration > 12)
            {
                if(language == "English")
                {

                }
                else if(language == "Spanish")
                {

                }
                else if(language == "French")
                {

                }
                else if(language == "Italian")
                {

                }
                else if(language == "Mandarin")
                {

                }
                else
                {
                    
                }
            }
        }
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

}