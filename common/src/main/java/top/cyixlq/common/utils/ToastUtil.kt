package top.cyixlq.common.utils

import android.widget.Toast
import top.cyixlq.common.CommonManager

object ToastUtil {
    fun showShort(text: String) {
        Toast.makeText(CommonManager.getApplication(), text, Toast.LENGTH_SHORT).show()
    }

    fun showShort(stringId: Int) {
        Toast.makeText(CommonManager.getApplication(), stringId, Toast.LENGTH_SHORT).show()
    }

    fun showLong(stringId: Int) {
        Toast.makeText(CommonManager.getApplication(), stringId, Toast.LENGTH_LONG).show()
    }

    fun showLong(text: String) {
        Toast.makeText(CommonManager.getApplication(), text, Toast.LENGTH_LONG).show()
    }
}