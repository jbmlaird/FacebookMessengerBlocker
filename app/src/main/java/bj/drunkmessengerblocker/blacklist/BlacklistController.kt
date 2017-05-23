package bj.drunkmessengerblocker.blacklist

import bj.drunkmessengerblocker.epoxy.CenterPaddedTextModel_
import bj.drunkmessengerblocker.epoxy.ListItemModel_
import bj.drunkmessengerblocker.utils.SharedPrefsManager
import com.airbnb.epoxy.EpoxyController

/**
 * Created by Josh Laird on 23/05/2017.
 */
class BlacklistController(val presenter: BlacklistPresenter, val sharedPrefsManager: SharedPrefsManager) : EpoxyController() {
    override fun buildModels() {
        if (sharedPrefsManager.storedNames.size > 0)
            for (item in sharedPrefsManager.storedNames) {
                ListItemModel_(item)
                        .id(sharedPrefsManager.storedNames.indexOf(item))
                        .addTo(this)
            }
        else
            CenterPaddedTextModel_("No items")
                    .id("No items")
                    .addTo(this)
    }
}