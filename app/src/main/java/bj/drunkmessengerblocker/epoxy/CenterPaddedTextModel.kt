package bj.drunkmessengerblocker.epoxy

import android.widget.RelativeLayout
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
@EpoxyModelClass(layout = R.layout.model_center_padded_text)
abstract class CenterPaddedTextModel(@EpoxyAttribute var text: String?) : EpoxyModel<RelativeLayout>() {
    @BindView(R.id.textView) lateinit var textView: TextView

    override fun bind(view: RelativeLayout) {
        super.bind(view)
        ButterKnife.bind(this, view)
        textView.text = text
    }
}