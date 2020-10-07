package com.ismail.creatvt.registrar

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object AlertDialogManager {

    fun showConfirmation(context: Context, message:Int, yesAction:()->Unit){
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                yesAction()
            }
            .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()
    }
}