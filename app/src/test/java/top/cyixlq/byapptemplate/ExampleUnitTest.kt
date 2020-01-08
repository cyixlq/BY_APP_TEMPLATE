package top.cyixlq.byapptemplate

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var i = 0
        while (true) {
            if (i % 2 == 1 && i % 3 == 0 && i % 4 == 1 && i % 5 == 4 && i % 6 == 3 && i % 7 == 0 && i % 8 == 1 && i % 9 == 0) {
                println("result ==>$i")
                break
            }
            i++
        }
    }
}
