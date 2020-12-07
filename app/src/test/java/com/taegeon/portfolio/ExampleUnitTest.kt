package com.taegeon.portfolio

import com.taegeon.portfolio.data.Documents
import com.taegeon.portfolio.data.ImgData
import com.taegeon.portfolio.net.DaumApiManager
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : KoinTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @get:Rule
    val KoinTestRule = org.koin.test.KoinTestRule.create {
        modules(daumApiModule)
    }

    @Test
    fun daumApiManagerTest() {
        val cl = CountDownLatch(1)
        val daumApiManager: DaumApiManager by inject()
        val arrayList: ArrayList<Documents> = ArrayList()
        daumApiManager.retrofitService.requestSearchImg(keyword = "강아지", page = 1).enqueue(object :
            Callback<ImgData> {
            override fun onFailure(call: Call<ImgData>, t: Throwable) {
                fail()
            }

            override fun onResponse(call: Call<ImgData>, response: Response<ImgData>) {
                if (response.isSuccessful) {
                    arrayList.addAll(response.body()?.documents ?: emptyList())
                    assertTrue(arrayList.size != 0)
                } else {
                    fail()
                }
            }
        })

        cl.await(3, TimeUnit.SECONDS)
    }
}