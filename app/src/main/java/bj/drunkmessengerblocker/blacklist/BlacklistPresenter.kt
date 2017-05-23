package bj.drunkmessengerblocker.blacklist

import android.text.InputType
import bj.drunkmessengerblocker.R
import bj.drunkmessengerblocker.utils.SharedPrefsManager
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by Josh Laird on 23/05/2017.
 */
class BlacklistPresenter(val view: BlacklistContract.View, val sharedPrefsManager: SharedPrefsManager) : BlacklistContract.Presenter {
    val names: MutableList<String> = sharedPrefsManager.storedNames

    override fun onFabClicked() {
        MaterialDialog.Builder(view as BlacklistActivity)
                .title("Enter name to add")
                .content("You must put their name as what appears on Facebook Messenger. If they have a nickname, they won't be blocked.")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(2, 50, R.color.colorPrimary)
                .onPositive { dialog, _ ->
                    names.add(dialog.inputEditText!!.text.toString())
                    sharedPrefsManager.storeName(dialog.inputEditText!!.text.toString())
                }
                .input("Name", null) { dialog, input ->
                    // Do something
                }.show()
    }

}