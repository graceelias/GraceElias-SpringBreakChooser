package com.example.graceelias_springbreakchooser

import android.R.layout
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
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

    private var language = ""

    private var sensorManager: SensorManager? = null
    private var currentAcceleration = 0f

    private var uris = arrayOf("geo:40.71298,74.00720?q=New York City",
        "geo:19.43011,99.13361?q=Mexico City",
        "geo:48.85679,2.35108?q=Paris",
        "geo:41.88929,12.49355?q=Rome",
        "geo:39.90275,116.40082?q=Beijing",
        "geo:28.64393,77.09298?q=Delhi")

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Choose Language", "English", "Spanish", "French", "Italian", "Mandarin", "Hindi")

        val adapter = ArrayAdapter(this, layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(selectedItem != "Choose Language") {
                    Toast.makeText(
                        this@MainActivity,
                        "Say a phrase in $selectedItem",
                        Toast.LENGTH_SHORT
                    )
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

                    if (selectedItem.equals("English")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "en-US"
                        )
                        language = "English"
                    } else if (selectedItem.equals("Spanish")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "es-ES"
                        )
                        language = "Spanish"
                    } else if (selectedItem.equals("French")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "fr-FR"
                        )
                        language = "French"
                    } else if (selectedItem.equals("Italian")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "it-IT"
                        )
                        language = "Italian"
                    } else if (selectedItem.equals("Mandarin")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "zh-CN"
                        )
                        language = "Mandarin"
                    } else if (selectedItem.equals("Hindi")) {
                        intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE,
                            "hi-IN"
                        )
                        language = "Hindi"
                    }

                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

                    launcherSpeechResult.launch(intent)
                }
                else
                {
                    language = ""
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        Objects.requireNonNull(sensorManager)!!
            .registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)

        currentAcceleration = SensorManager.GRAVITY_EARTH
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

            if (currentAcceleration > 10)
            {
                if(language == "English")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[0]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.english)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }

                }
                else if(language == "Spanish")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[1]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.spanish)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }
                }
                else if(language == "French")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[2]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.french)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }
                }
                else if(language == "Italian")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[3]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.italian)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }
                }
                else if(language == "Mandarin")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[4]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.mandarin)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }
                }
                else if(language == "Hindi")
                {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uris[5]))
                    startActivity(intent)
                    val hello: MediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.hindi)
                    hello.start()
                    hello.setOnCompletionListener { hello ->
                        hello.release()
                    }
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