package com.trakclok.android.ui.container

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.trakclok.android.ui.layout.LayoutListRefreshError
import com.trakclok.android.ui.layout.LayoutListReloadError
import com.trakclok.android.ui.item.ItemLottieReload

@ExperimentalMaterial3Api
@Composable
fun <T : Any> CtLazy(
    data: LazyPagingItems<T>,
    item: @Composable LazyItemScope.(item: T, index: Int) -> Unit,
    scrolled: () -> Unit,
    refresh: LazyListScope.() -> Unit,
    reload: (LazyListScope.() -> Unit)? = null,
    refreshError: (LazyListScope.() -> Unit)? = null,
    reloadError: (LazyListScope.() -> Unit)? = null,
    loaded: (LazyListScope.() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    // nested scrolling listener
    val scroll = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                //val delta = available.y
                scrolled()
                return Offset.Zero
            }
        }
    }

    val state = rememberLazyListState()

    LazyColumn(
        state = state,
        modifier = Modifier.nestedScroll(scroll),
        contentPadding = contentPadding
    ) {
        val scope = this

        if (data.loadState.refresh is LoadState.NotLoading) {
            // items are loaded
            loaded?.let { it(scope) }

            // set items
            items(data.itemCount) { index ->
                data[index]?.let {
                    item(it, index)
                }
            }
        }

        data.apply {
            when {
                // refresh
                loadState.refresh is LoadState.Loading -> refresh(scope)

                // reload
                loadState.append is LoadState.Loading -> if (reload == null) item { ItemLottieReload() } else reload()

                // refresh error
                loadState.refresh is LoadState.Error -> if (refreshError == null) {
                    val e = data.loadState.refresh as LoadState.Error
                    item { LayoutListRefreshError(error = e.toString()) { data.refresh() } }
                } else refreshError()

                // reload error
                loadState.append is LoadState.Error -> if (reloadError == null) {
                    val e = data.loadState.append as LoadState.Error
                    item { LayoutListReloadError(error = e.toString()) { data.retry() } }
                } else reloadError()
            }
        }
    }

    // lazy column
    /*LazyColumn(
        modifier = modifier,
        content = {

            // data
            items(data.itemCount, null, itemContent)

            // load states
            lazyListStates(images = data, useDarkIcons = useDarkIcons, scope = this, refresh)
        }
    )*/
}