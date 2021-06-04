package com.yuan.dyhdm.activity

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.yuan.dyhdm.R
import com.yuan.dyhdm.`interface`.BookService
import com.yuan.dyhdm.adapter.lv.ChatAdapter
import com.yuan.dyhdm.entity.Book
import com.yuan.dyhdm.entity.ChatMessage
import com.yuan.dyhdm.utils.doSomething
import com.yuan.dyhdm.utils.open
import com.yuan.dyhdm.utils.startActivity2
import kotlinx.android.synthetic.main.act_kotline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import kotlin.concurrent.thread

class KotlinActivity : Activity(), View.OnClickListener {

    private lateinit var msg: String

    val TAG: String = "KotlinActivity";

    //var  val
    var b = 6
    var c: Int = 6


    var a = 1

    // 模板中的简单名称：
    val s1 = "a is $a"

    // 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $a"

    // 模板中的任意表达式：
    val s3 = "${s1.replace("is", "was")}, but now is $a"

    //单行
    var colorList = ArrayList<Int>();
    var rateList = ArrayList<Float>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_kotline)
        initView()
        init()
        tv_title.setText("extension code ")



        Log.e("d", javaClass.simpleName)
        doSomething()
        method2(12)

        // 添加的是颜色

        colorList.add(R.color.color_ff3e60);
        colorList.add(R.color.color_ffa200);
        colorList.add(R.color.color_31cc64);
        colorList.add(R.color.color_3377ff);

        //  添加的是百分比
        rateList.add(10f);
        rateList.add(5f);
        rateList.add(45f);
        rateList.add(40f);
        ringView.setShow(colorList, rateList, false, true);



        lv_kotlin_first.setAdapter(ChatAdapter(this, ChatMessage.MOCK_DATAS))
        lv_kotlin_first.setOnItemClickListener { parent, view, position, id ->

        }


    }

    private fun initView() {
        btn_camera.setOnClickListener(this)
        btn_notify.setOnClickListener(this)
        btn_pic.setOnClickListener(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    //高阶函数应用 简化
    fun gaojieyingy() {
        val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString("key", "value1")
        editor.apply()

        getSharedPreferences("data", Context.MODE_PRIVATE).open {
            putString("key", "value1")

        }
    }

    //泛型类
    class MyClass<T> {
        fun meth(param: T): T {
            return param
        }
    }

    //泛型方法
    fun <T> method2(param: T): T {
        return param
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_notify -> {
                notifyMethod()
            }
            R.id.btn_camera -> {
                takePhoto()
            }
            R.id.btn_pic->{

            }
        }
    }

    fun getNetDataByRetrofit(){
        val retrofit= Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val bookService=retrofit.create(BookService::class.java)
        bookService.getBookData().enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                TODO("Not yet implemented")
                val list=response.body()

            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })




    }

    //通知栏通知
    fun notifyMethod() {

        val notifyManage = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("message", "消息", NotificationManager.IMPORTANCE_HIGH)
            notifyManage.createNotificationChannel(channel)
        }
        val mIntent=Intent(this, KotlinActivity::class.java)
        val appIntent = PendingIntent.getActivity(this, 0, mIntent, 0)

        val notifycation=NotificationCompat.Builder(applicationContext, "message").
        setSmallIcon(R.drawable.renma).setContentTitle("下载").
        setContentText("正在下载正在下载正在下载正在下载正在下载").
                //setStyle(NotificationCompat.BigPictureStyle().bigPicture("")).
        setContentIntent(appIntent).setAutoCancel(true).build()

        //长文本使用  setStyle NotificationCompat.BigTextStyle().bigText("")
        //大图片 NotificationCompat.BigPictureStyle().bigPicture("")


        notifyManage.notify(1, notifycation)

    }
    //
    fun takePhoto(){
        val tempFile=File("")
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        val photoUri: Uri
        photoUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                    applicationContext,
                    "fileprovider",
                    tempFile)
        } else {
            Uri.fromFile(tempFile)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

    }
    //多线程
    fun theadMethod(){
        val myThead= MyThead()
        myThead.start()

        Thread{

        }.start()

        thread {

        }
    }
    class MyThead:Thread(){
        override fun run() {
            super.run()
        }
    }

    class MyThead2:Runnable{
        override fun run() {

        }
    }
    val handler=object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    fun start(){
        startActivity2<MainActivity>(this){
            putExtra("","")
        }
    }

    /**
     * 文件写操作
     * openFileOutput
     * FileOutputStream    输出流 写入文件
     * BufferedWriter  默认缓冲区大小构造字符缓冲输出流对象
     * OutputStreamWriter 是Writer的子类，是从字符流到字节流的桥梁。
     */

    fun saveText(inputText: String) {

        try {
            val out = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(out))
            writer.use {
                it.write(inputText)
            }
        } catch (e: IOException) {

        }

    }

    /**
     * 文件读操作
     * openFileInput
     * FileInputStream
     * InputStreamReader
     * BufferedReader
     *
     */

    fun load(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("data")
            val read = BufferedReader(InputStreamReader(input))
            read.use {
                read.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {

        }

        return content.toString()

    }


    fun init() {

        sum(5, 6)
        Log.e(TAG, sum2(5, 9).toString())
        Log.e(TAG, sum3(9, 9).toString())
        vars(1, 2, 3, 5, 6)
        logUtil(sumLambda(1, 2))
        logUtil(getStringLength(123))
        Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show()

        //区间
        for (i in 1..4) {

            logUtil(i)
        }

        for (i in 4..1) {

            logUtil(i)
        }

        for (i in 5..10 step 3)
            logUtil(i)

        logUtil(getStringLengthh("a").toString())

        //区间
        for (i in 1..4) print(i) // 输出“1234”
        main11()
    }

    fun sum0(a: Int, b: Int): Int {
        return a + b;
    }


    fun sum2(a: Int, b: Int) = a + b;

    public fun sum3(a: Int, b: Int) = a + b

    fun vars(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

    //lambda 表达式
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }


    fun logUtil(a: String) {
        Log.e(TAG, a)
    }

    fun logUtil(a: Int?) {
        if (a != null) {
            Log.e(TAG, a.toString())
        }
    }

    //类型后面加?表示可为空
    var age: String? = "123"

    //抛出空指针异常
    val ages = age!!.toInt()

    //不做处理返回 null
    val ages1 = age?.let {

    }

    //age为空返回-1
    val ages2 = age?.toInt() ?: -1

    var curAge: String? = "344"
    val curages = curAge!!.toInt()

    //类型自动转换 instanceof
    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }

        if (obj !is String) {
            return 9
        }

        return null


    }

    fun getStringLengthh(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }
        return 7
    }


//fun

    fun sum(a: Int, b: Int) {
        print(a + b)
    }

    fun sum(a: Int) = a
    public fun sun(a: Int, b: Int) = a + b

    fun varss(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

    // 测试
    fun main(args: Array<String>) {
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        val deleteddd: (Int, Int) -> Int = { x, y -> x + y }
        println(sumLambda(1, 2))  // 输出 3
    }

    val x: IntArray = intArrayOf(1, 2, 3)
    val xx: LongArray = longArrayOf(1, 2, 3)


    fun main11() {
        val text = """
    |多行字符串
    |菜鸟教程
           |多行字符串
    |Runoob
    """.trimMargin()
        logUtil(text)   // 输出有一些前置空格
    }

    //Kotlin 条件控制

    fun control() {
        var max = a
        if (a < b)
            max = b

        if (a > b) {
            max = a
        } else {
            max = b
        }
        val max1 = if (a > b) a else b

        val max2 = if (a > b) {
            a
        } else {
            b
        }

        val c = if (true) a else b
        var x1 = 6
        if (x1 in 1..9) {

        }
        when (x1) {
            1 -> 1
            2 -> 2
            3, 4 -> 4
            in 1..9 -> 9
            !in 10..20 -> 20
            else -> {
                3
            }
        }

        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {
            println(item)
        }

        for (index in items.indices) {
            println("item at $index is ${items[index]}")
        }

        for ((index, value) in items.withIndex()) {
            println("$index and $value")
        }

        loop@ for (i in 1..9) {

        }

        fun foo() {
            items.forEach {
                if (it is String) return@forEach
                print(it)
            }
        }

        items.forEach lit@{
            if (it is String) return@lit
            print(it)
        }

    }


}