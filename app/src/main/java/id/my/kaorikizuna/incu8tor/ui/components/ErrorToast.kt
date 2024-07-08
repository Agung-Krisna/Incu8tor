package id.my.kaorikizuna.incu8tor.ui.components

import android.content.Context
import android.widget.Toast


class ErrorToast(var context: Context? = null) {
    fun show(exceptionMessage: String) {
        Toast.makeText(context, "Error: $exceptionMessage", Toast.LENGTH_LONG).show()
    }
}