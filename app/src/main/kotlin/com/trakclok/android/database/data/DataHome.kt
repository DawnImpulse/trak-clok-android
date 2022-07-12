package com.trakclok.android.database.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.trakclok.android.database.RealtimeGeneric
import com.trakclok.android.mapping.objects.ObjectProject
import com.trakclok.android.utils.Cfg

class DataHome() :
    PagingSource<Int, ObjectProject>() {

    // data load
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ObjectProject> {
        val nextPage = params.key ?: 0
        return try {
            val response = Cfg.realtimeGeneric.dataOnce("users/abcd/projects")
            val list = mutableListOf<ObjectProject>()
            response.children.forEach {
                it.getValue(ObjectProject::class.java)?.let { l -> list.add(l) }
            }

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
    override fun getRefreshKey(state: PagingState<Int, ObjectProject>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}