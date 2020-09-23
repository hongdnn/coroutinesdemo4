package com.example.demo4coroutines

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.NumberFormatException
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {
    private var listSize: Int = 0
    private var total: Int = 0
    private var items: ArrayList<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShow.setOnClickListener(View.OnClickListener {
            if(items.size>0){
                items.clear()
                total=0
            }
            try {
                listSize = edtNum.text.toString().toInt()
                for (i in 0 until listSize) {
                    items.add(Random.nextInt(0..100))
                    println(items[i])
                }
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        progressbar.visibility = View.VISIBLE
                    }
                    //val job = runBlocking {
                    for (i in 0 until listSize) {
                        total += items[i]
                        withContext(Dispatchers.Main) {
                            txvResult.text = "Result: $total"
                            if (i < listSize - 1) {
                                delay(1000)
                            }
                        }
                    }
                    withContext(Dispatchers.Main) {
                        progressbar.visibility = View.INVISIBLE
                    }
                    // }
                }
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Format exception", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}