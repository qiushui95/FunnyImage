package me.yangcx.forrecyclerview.adapter

open class BasePagingAdapter(private val pageSize: Int = 20) : BaseDataAdapter() {
    private var hasNext = true
    private var lastItemCount = 0

    override fun setItems(newItems: MutableList<*>) {
        super.setItems(newItems)
        hasNext = getRealItemCount() >= lastItemCount + pageSize || getRealItemCount() == pageSize
        lastItemCount = getRealItemCount()
    }

    fun setItems(newItems: MutableList<*>, hasNext: Boolean) {
        super.setItems(newItems)
        this.hasNext = hasNext
    }

    /**
     * 获取每页数据大小
     */
    fun getPageSize() = pageSize

    /**
     * 获取真实数据量(去掉标题、header、footer等数据)
     */
    private fun getRealItemCount() = getRealItemCount(items)

    open fun getRealItemCount(dataList: List<*>): Int {
        return dataList.size
    }

    /**
     * 获取下一页页码
     */
    fun getNextPage(): Int {
        return getRealItemCount() / pageSize + if (getRealItemCount() % pageSize > 0) {
            2
        } else {
            1
        }
    }

    /**
     * 是否没有更多数据
     */
    fun noMoreData(): Boolean {
        return !hasNext
    }

    /**
     * 是否有更多数据
     */
    fun hasNextPage(): Boolean {
        return hasNext
    }
}