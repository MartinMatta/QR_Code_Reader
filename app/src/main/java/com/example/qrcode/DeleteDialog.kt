package com.example.qrcode

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button

class DeleteDialog(private var context: Context,
                   private var adapter: ListAdapter, private var table: String) {


    fun show(position: Model, database: QrCodeDatabase, id: Int) :ListAdapter {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null)
        val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

        val btnCancel = mDialogView.findViewById<Button>(R.id.btnCancel)
        val btnOk = mDialogView.findViewById<Button>(R.id.btnOk)

        val  mAlertDialog = mBuilder.show()

        btnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

        btnOk.setOnClickListener {
            adapter.remove(position)
            database.delete(id, table)
            mAlertDialog.dismiss()
        }
        return adapter
    }
}