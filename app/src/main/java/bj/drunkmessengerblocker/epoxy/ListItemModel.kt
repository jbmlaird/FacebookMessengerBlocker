package bj.drunkmessengerblocker.epoxy

import android.widget.LinearLayout
import android.widget.TextView
import bj.drunkmessengerblocker.R
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass

/**
 * Created by Josh Laird on 23/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_list_item)
abstract class ListItemModel(@EpoxyAttribute var username: String?) : EpoxyModel<LinearLayout>() {
    @BindView(R.id.tvName) lateinit var tvName: TextView

    override fun bind(view: LinearLayout?) {
        super.bind(view)
        ButterKnife.bind(this, view!!)
        tvName.text = username
    }
}