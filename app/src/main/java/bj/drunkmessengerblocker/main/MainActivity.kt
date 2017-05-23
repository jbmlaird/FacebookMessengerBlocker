package bj.drunkmessengerblocker.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import bj.drunkmessengerblocker.AppComponent
import bj.drunkmessengerblocker.R
import bj.drunkmessengerblocker.blacklist.BlacklistActivity
import bj.drunkmessengerblocker.common.BaseActivity
import bj.drunkmessengerblocker.utils.OverlayPermission.isAccessibilitySettingsOn
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_main_constraint.*
import javax.inject.Inject


/**
 * Created by Josh Laird on 23/05/2017.
 */
class MainActivity : BaseActivity(), MainContract.View {
    @Inject lateinit var presenter: MainPresenter
    val REQUEST_CODE = 5376
    var disabled: Boolean = false
    var disableCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_constraint)
        ButterKnife.bind(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val drawOverlaysSettingsIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                drawOverlaysSettingsIntent.data = Uri.parse("package:" + packageName)
                startActivity(drawOverlaysSettingsIntent)
            }
        }
    }

    override fun setupComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        /** check if received result code
         * is equal our requested code for draw permission   */
        if (requestCode == REQUEST_CODE) {
            /** if so check once again if we have permission  */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
//                    toggleSwitch.setChecked(true);
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
            }

            if (isAccessibilitySettingsOn(this)) {
                disabled = true
                tvBlock.text = "Unblock"
            }
        }
    }

    @OnClick(R.id.btnOne)
    fun modifyBlockTime() {
        MaterialDialog.Builder(this)
                .title("Hours")
                .items(R.array.hours)
                .positiveText("Confirm")
                .itemsGravity(GravityEnum.CENTER)
                .itemsCallback { dialog, _, position, _ ->
                    tvHours.text = (position + 1).toString()
                    dialog.dismiss()
                }
                .show()
    }

    @OnClick(R.id.btnTwo)
    fun modifyBlacklist() {
        startActivity(BlacklistActivity.createIntent(this))
    }

    @OnClick(R.id.btnThree)
    fun enableBlock() {
        if (!disabled)
            MaterialDialog.Builder(this)
                    .content("You will be taken to the Settings page. Scroll down to Services and set DrunkMessengerBlocker to On. To disable it, come back into this app or the Settings page after the timer has expired.")
                    .positiveText("Settings")
                    .negativeText("Dismiss")
                    .onPositive({ dialog, _ ->
                        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                        startActivity(intent)
                        dialog.dismiss()
                    })
                    .build()
                    .show()
        else {
            if (disableCount == 0) {
                MaterialDialog.Builder(this)
                        .content("Nope! You played yourself.")
                        .negativeText("Dismiss")
                        .build()
                        .show()
            } else if (disableCount == 1) {
                MaterialDialog.Builder(this)
                        .content("You're an idiot.")
                        .negativeText("Dismiss")
                        .build()
                        .show()
            } else if (disableCount == 2) {
                MaterialDialog.Builder(this)
                        .content("Stop being a mug.")
                        .negativeText("Dismiss")
                        .build()
                        .show()
            } else if (disableCount > 2) {
                MaterialDialog.Builder(this)
                        .content("Pathetic.")
                        .negativeText("Dismiss")
                        .build()
                        .show()
            }
            disableCount++
        }
    }
}