package com.example.skripsol.FunctionHelper

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent


    object Get {
        private val argumentsMap: MutableMap<String, String> = mutableMapOf()

        fun to(activity: Activity, targetActivity: Class<*>, vararg arguments: Pair<String, String>) {
            val intent = Intent(activity, targetActivity)
            arguments.forEachIndexed { index, argument ->
                intent.putExtra("arg$index", argument.second)
                argumentsMap[argument.first] = argument.second
            }
            activity.startActivity(intent)

    //        Cara penggunaan hanya untuk berpindah halaman saja
    //        Get.to(this, KelasTujuan::class.java)
    //        Berpindah halaman dengan melempar value
    //        Get.to(this, KelasTujuan::class.java,Pair("key","value"), Pair("key","value"))
    //        editTextOTP.setText("${Get.arguments("key1")}, ${Get.arguments(" key2 ") }")
        }

        fun off(activity: Activity, targetActivity: Class<*>, vararg arguments: Pair<String, String>) {
            val intent = Intent(activity, targetActivity)
            arguments.forEachIndexed { index, argument ->
                intent.putExtra("arg$index", argument.second)
                argumentsMap[argument.first] = argument.second
            }
            activity.startActivity(intent)
            activity.finish()
//        Get.off(this, KelasTujuan::class.java) penggunaan method ini akan menghapus activity yang sedang dijalankan ketika berpindah
//        example: 1 -> 2 -> 3 kamu sedang di form 2 kamu menggunakan Get.off(this,Tujuan::class.java) maka jika kamu menekan tombol kembali maka akan diarahkan ke form1
//        method ini juga dapat disandingkan dengan Get.off(this, KelasTujuan::class.java,Pair("key1","value")) yap betul dengan argumen juga
//        editTextOTP.setText("${Get.arguments("key1")}")
        }

        fun offAll(activity: Activity, targetActivity: Class<*>, vararg arguments: Pair<String, String>) {
            val intent = Intent(activity, targetActivity)
            arguments.forEachIndexed { index, argument ->
                intent.putExtra("arg$index", argument.second)
                argumentsMap[argument.first] = argument.second
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
//        Get.off(this, KelasTujuan::class.java) penggunaan method ini akan menghapus activity yang sedang dijalankan ketika berpindah
//        example : 1 -> 2 -> 3 kamu sedang berada di form ke 2 lalu pindah ke form 3 jika kamu berpindah menggunakan methode Get.offAll() maka aktivitas
//        yang pernah kamu lakukan di form 1 dan 2 akan hilang sehingga jika kamu menekan tombol kembali berulang kali akan di keluarkan dari aplikasi
//        method ini juga dapat disandingkan dengan Get.off(this, KelasTujuan::class.java,Pair("key1","value")) yap betul dengan argumen juga
//        editTextOTP.setText("${Get.arguments("key1")}")
        }

        fun back(activity: Activity) {
            activity.finish()
//          Cara pakai hanya perlu Get.back(this)
        }

        fun arguments(key: String): String? {
            return argumentsMap[key]
        }

}
//    fun dialog(
//        setTitle: String,
//        setMessage: String,
//        positiveButtonText : String,
//        negativeButtonText: String,
//        positiveResponse: () -> Unit,
//        negativeResponse: (() -> Unit)?=null,
//        activity: Activity
//    ) {
//        val builder = AlertDialog.Builder(activity)
//        builder.setMessage(setMessage)
//        builder.setTitle(setTitle)
//        builder.setCancelable(false)
//        builder.setPositiveButton(positiveButtonText, { dialog: DialogInterface?, which: Int ->
//            dismissDialog(dialog)
//            positiveResponse.invoke()
//
//        })
//        builder.setNegativeButton(negativeButtonText,
//            { dialog: DialogInterface?, which: Int ->
//                dismissDialog(dialog)
//                negativeResponse?.invoke()
//
//            })
//        val alertDialog = builder.create()
//        alertDialog.show()
//    }
//
//    private fun dismissDialog(dialog:DialogInterface?) {
//        dialog?.dismiss()
//    }