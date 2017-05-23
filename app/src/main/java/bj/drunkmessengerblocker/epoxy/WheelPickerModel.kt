package bj.drunkmessengerblocker.epoxy

import android.widget.LinearLayout
import bj.drunkmessengerblocker.R
import com.aigestudio.wheelpicker.WheelPicker
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass

/**
 * Created by Josh Laird on 23/05/2017.
 */
@EpoxyModelClass(layout = R.layout.model_wheelpicker)
abstract class WheelPickerModel : EpoxyModel<LinearLayout>() {
    override fun bind(view: LinearLayout?) {
        super.bind(view)
        (view?.findViewById(R.id.wheelpicker) as WheelPicker).data = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    }
}
