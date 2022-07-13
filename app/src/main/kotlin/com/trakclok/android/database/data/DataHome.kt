package com.trakclok.android.database.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trakclok.android.database.RealtimeGeneric
import com.trakclok.android.mapping.objects.ObjectHomeHeader
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.utils.Cfg
import com.trakclok.android.utils.F
import com.trakclok.android.utils.extension.log

class DataHome() :
    PagingSource<Int, Any>() {

    // data load
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        val nextPage = params.key ?: 0
        return try {
            val response = Cfg.realtimeGeneric.dataOnce("users/abcd/projects")
            val list = mutableListOf<Any>()

            // --- add first element as header
            list.add(
                ObjectHomeHeader(
                    date = F.currentDate(),
                    month = F.currentMonth(),
                    day = F.currentDay()
                )
            )

            // --- get & sort remaining based on active
            val projList = mutableListOf<ObjectProject>()
            response.children.forEach {
                log.d(it.value.toString())
                it.getValue(ObjectProject::class.java)?.let { l -> list.add(l) }
            }
            projList.sortBy { it.active }
            list.addAll(projList)

            // result
            LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // refresh key
    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}