package com.example.kotlinflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // printUser
        printUser()  // ①
        Log.d(TAG, "print user completed")  // ②

        // suspend fun to print user list
//        CoroutineScope(Dispatchers.IO).launch {
//            printUserList()  // ④
//        }
//
//        Log.d(TAG, "print user completed from suspend function")  // ③

        // simple flow
        CoroutineScope(Dispatchers.IO).launch {

           // collectはsuspend関数であるため、コルーチン内で実行する
           // パラメータとしてラムダを受け取り、新しい値ごとに呼び出される
           simpleFlow().collect { user ->
               Log.d(TAG, user + "flow")
           }
        }
    }

    // If you want to switch the context of execution of a flow,
    // use the flowOn operator.
    private fun simpleFlow() : Flow<String> = flow {
        userList.forEach { user ->
            // データストリームに新しい値を出力するためにはemit関数を使用する
            emit(user)
            delay(500)

            // test
        }
    }


    private suspend fun printUserList() {
        userList.forEach { user ->
            Log.d(TAG, user + "suspend")
        }
    }


    private fun printUser() {
        userList.forEach {  user ->
            Log.d(TAG, user)

        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}