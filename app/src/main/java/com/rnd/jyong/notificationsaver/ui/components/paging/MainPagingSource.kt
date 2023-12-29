//package com.rnd.jyong.notificationsaver.ui.components.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.rnd.jyong.notificationsaver.database.notification.entity.Message
//import kotlinx.coroutines.delay
//import kotlin.math.max
//
//private const val STARTING_KEY = 0
//private const val LOAD_DELAY_MILLIS = 3_000L
//
//class MainPagingSource : PagingSource<Int, Message>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Message> {
//        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
//        val startKey = params.key ?: STARTING_KEY
//
//        // We fetch as many articles as hinted to by params.loadSize
//        val range = startKey.until(startKey + params.loadSize)
//
//        // Simulate a delay for loads adter the initial load
//        if (startKey != STARTING_KEY) delay(LOAD_DELAY_MILLIS)
//
//        return LoadResult.Page(
//            data = range.map { number ->
//                Article(
//                    id = number,
//                    title = "Article $number",
//                    description = "This describes article $number",
//                    created = firstArticleCreatedTime.minusDays(number.toLong())
//                )
//            },
//            prevKey = when (startKey) {
//                STARTING_KEY -> null
//                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
//                    // We're at the start, there's nothing more to load
//                    STARTING_KEY -> null
//                    else -> prevKey
//                }
//            },
//            nextKey = range.last + 1
//        )
//    }
//
//    // The refresh key is used for the initial load of the next PagingSource, after invalidation
//    override fun getRefreshKey(state: PagingState<Int, Message>): Int? {
//        // In our case we grab the item closest to the anchor position
//        // then return its id - (state.config.pageSize / 2) as a buffer
//        val anchorPosition = state.anchorPosition ?: return null
//        val article = state.closestItemToPosition(anchorPosition) ?: return null
//        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
//    }
//
//    /**
//     * Makes sure the paging key is never less than [STARTING_KEY]
//     */
//    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
//}
