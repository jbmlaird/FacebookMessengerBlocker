package bj.drunkmessengerblocker.epoxy

import android.widget.TextView
import bj.drunkmessengerblocker.R
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass

/**
 * Created by Josh Laird on 23/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_text)
abstract class TextModel(@EpoxyAttribute var text: String?) : EpoxyModel<TextView>() {
    override fun bind(view: TextView?) {
        super.bind(view)
        view?.text = text
    }
}