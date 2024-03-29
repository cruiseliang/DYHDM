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
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.yuan.dyhdm.R
import com.yuan.dyhdm.interfaceP.BookService
import com.yuan.dyhdm.adapter.lv.ChatAdapter
import com.yuan.dyhdm.entity.Book
import com.yuan.dyhdm.entity.ChatMessage
import com.yuan.dyhdm.utils.UtilsLog
import com.yuan.dyhdm.utils.doSomething
import com.yuan.dyhdm.utils.open
import com.yuan.dyhdm.utils.startActivity2
import com.yuan.dyhdm.view.PieGraphView
import com.yuan.dyhdm.view.PieGraphView.ItemChangeListener
import kotlinx.android.synthetic.main.act_kotline.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import kotlin.concurrent.thread
import androidx.core.content.edit as edit1

class KotlinActivity : Activity(), View.OnClickListener,ItemChangeListener {

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
    var colorList = ArrayList<Int>()
    var rateList =  ArrayList<Float>()

    private val colors = intArrayOf(-0x64245, -0xc93a0, -0x316c28, -0x504001, -0x4d2025, -0xff533f, -0x3223c7, -0xda64dc)


    private var colorIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_kotline)
        if(::msg.isInitialized){

        }



        val input=openFileInput("data")
        val reader=BufferedReader(InputStreamReader(input))
        reader.use {
            reader.forEachLine {

            }
        }



        initView()
        init()
        tv_title.setText("extension code ")
      val aaa=  KotlinActivity::class
        //javaclass 等于java getclass()
        UtilsLog.e("tag",javaClass.name)





        Log.e("d", javaClass.simpleName)
        doSomething()
        method2(12)

        // 添加的是颜色

        colorList.add(R.color.color_ffa200);
        colorList.add(R.color.color_31cc64);
        colorList.add(R.color.color_3377ff);

        colorList.add(R.color.color_ff3e60);
        colorList.add(R.color.color_ffa200);
        colorList.add(R.color.color_31cc64);
        colorList.add(R.color.color_3377ff);

        //  添加的是百分比

        rateList.add(15f);
        rateList.add(5f);
        rateList.add(40f);
        rateList.add(10f);
        rateList.add(5f);
        rateList.add(5f);
        rateList.add(20f);
        ringView.setShow(colorList, rateList, true, true);


        pieGv.setRingWidthFactor(0.37f)


        // 造例子数据

        // 造例子数据
        val groups: Array<PieGraphView.ItemGroup?> = arrayOfNulls<PieGraphView.ItemGroup>(3)

        for (i in groups.indices) {
            val itemGroup = PieGraphView.ItemGroup()
            groups[i] = itemGroup
            itemGroup.id = "zu@$i"
            val items: Array<PieGraphView.Item?> = arrayOfNulls<PieGraphView.Item>(4)
            itemGroup.items = items
            var index=0
            for (j in items.indices) {
                val item = PieGraphView.Item()
                item.id = "it@$index"
                item.value = index* 24 + 24.0
                item.color = colors.get(colorIndex++ % colors.size)
                items[j] = item
                index++
            }

        }

        pieGv.setData(groups)


//        pieGv.setItemChangeListener(PieGraphViewCopy.ItemChangeListener { group, item ->
//            val msg = "group = " + group.id.toString() + ", item = " + item.id
//            Log.d(KotlinActivity::class.java.getSimpleName(), msg)
//        })

//



        lv_kotlin_first.setAdapter(ChatAdapter(this, ChatMessage.MOCK_DATAS))
        lv_kotlin_first.setOnItemClickListener { parent, view, position, id ->

        }


    }

    private fun initView() {
        btn_camera.setOnClickListener(this)
        btn_notify.setOnClickListener(this)
        btn_pic.setOnClickListener(this)
        getSharedPreferences("",Context.MODE_PRIVATE).edit1 {
            putBoolean("ddd",false)
        }

        getSharedPreferences("",Context.MODE_PRIVATE).edit1 {
            putBoolean("ddd",false)
        }

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
            this.putString("key", "value1")

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
            R.id.btn_pic -> {

            }
        }
    }

    fun getNetDataByRetrofit(){
        val retrofit= Retrofit.Builder().baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val bookService=retrofit.create(BookService::class.java)
        bookService.getBookData().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                TODO("Not yet implemented")
                val list = response.body()

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

        Thread(object :Runnable{
            override fun run() {
                println("is running")
                var count="ddddd".lettersCount()
            }

        }).start()

        Thread(Runnable {
            println("is running")
        }).start()

        Thread({
            println("is running")
        }).start()

        Thread{
            println("is running")
        }.start()



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
            putExtra("", "")
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
//        Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show()

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
        var mapfruit=HashMap<String,String>()
        mapfruit.put("Appale","1")
        mapfruit["pera"]="2"

      var pear=  mapfruit.get("pera")
        pear=mapfruit["pera"]

       val mapfruit2= mapOf("Appale" to "1","pee" to "d")
        for ((name,num) in mapfruit2){

        }

        //lambda编程
        //定义：一小段可以作为参数传递的代码  {参数1：参数类型 参数2 ：参数类型2->函数体}
        //最后一行代码作为返回值，当lambda参数是函数最后一个参数时候可以移到括号外面  当表达式只有一个参数时候 不需要声明 用it代替
        val lambda={fruit:String->fruit.length}
        val maxlength=items.maxBy(lambda)

        var resule= with(java.lang.StringBuilder()){
            append("ddd")
            toString()
        }

         resule= java.lang.StringBuilder().run{
            append("ddd")
            toString()
        }


       val resule2= java.lang.StringBuilder().apply{
            append("ddd")

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

    override fun onItemSelected(group: PieGraphView.ItemGroup?, item: PieGraphView.Item?) {
        TODO("Not yet implemented")
    }


}