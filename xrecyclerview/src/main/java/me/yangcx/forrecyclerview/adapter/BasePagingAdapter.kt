package me.yangcx.forrecyclerview.adapter

abstract class BasePagingAdapter(private val pageSize: Int = 20) : BaseDataAdapter() {
    /**
     * 获取每页数据大小
     */
    open fun getPageSize() = pageSize

    /**
     * 获取真实数据量(去掉标题、header、footer等数据)
     */
    open fun getRealItemCount() = itemCount

    /**
     * 获取下一页页码
     */
    fun getNextPage(): Int {
        return getRealItemCount() / pageSize + 1
    }
}