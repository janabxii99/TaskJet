package com.example.taskjet

import android.content.DialogInterface

interface DialogCloseListener {
    fun handleDialogClose(dialog: DialogInterface)
}
