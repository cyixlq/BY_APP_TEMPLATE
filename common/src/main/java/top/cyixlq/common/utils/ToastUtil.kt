package top.cyixlq.common.utils

import android.widget.Toast
import androidx.annotation.StringRes
import top.cyixlq.common.CommonManager

object ToastUtil {
    fun showShort(text: String) {
        Toast.makeText(CommonManager.getApplication(), text, Toast.LENGTH_SHORT).show()
    }

    fun showShort(@StringRes stringId: Int) {
        Toast.makeText(CommonManager.getApplication(), stringId, Toast.LENGTH_SHORT).show()
    }

    fun showLong(@StringRes stringId: Int) {
        Toast.makeText(CommonManager.getApplication(), stringId, Toast.LENGTH_LONG).show()
    }

    fun showLong(text: String) {
        Toast.makeText(CommonManager.getApplication(), text, Toast.LENGTH_LONG).show()
    }
}