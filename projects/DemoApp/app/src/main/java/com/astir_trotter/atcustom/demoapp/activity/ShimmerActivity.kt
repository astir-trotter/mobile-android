/*
 * This file provided by Facebook is for non-commercial testing and evaluation purposes only.
 * Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.astir_trotter.atcustom.demoapp.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.astir_trotter.atcustom.demoapp.R
import com.astir_trotter.atcustom.ui.activity.BaseActivity
import com.astir_trotter.atcustom.ui.layout.shimmer.MaskAngle
import com.astir_trotter.atcustom.ui.layout.shimmer.MaskShape
import com.astir_trotter.atcustom.ui.layout.shimmer.ShimmerFrameLayout

class ShimmerActivity : BaseActivity() {

    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private var mPresetButtons: Array<Button>? = null
    private var mCurrentPreset = -1
    private var mPresetToast: Toast? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shimmer)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container) as ShimmerFrameLayout

        mPresetButtons = arrayOf(findViewById(R.id.preset_button0) as Button, findViewById(R.id.preset_button1) as Button, findViewById(R.id.preset_button2) as Button, findViewById(R.id.preset_button3) as Button, findViewById(R.id.preset_button4) as Button)
        for (i in mPresetButtons!!.indices) {
            val preset = i
            mPresetButtons!![i].setOnClickListener { selectPreset(preset, true) }
        }
    }

    override fun onStart() {
        super.onStart()

        selectPreset(0, false)
    }

    public override fun onResume() {
        super.onResume()
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    public override fun onPause() {
        mShimmerViewContainer!!.stopShimmerAnimation()
        super.onPause()
    }

    /**
     * Select one of the shimmer animation presets.

     * @param preset index of the shimmer animation preset
     * *
     * @param showToast whether to show a toast describing the preset, or not
     */
    private fun selectPreset(preset: Int, showToast: Boolean) {
        if (mCurrentPreset == preset) {
            return
        }
        if (mCurrentPreset >= 0) {
            mPresetButtons!![mCurrentPreset].setBackgroundResource(R.color.preset_button_background)
        }
        mCurrentPreset = preset
        mPresetButtons!![mCurrentPreset].setBackgroundResource(R.color.preset_button_background_selected)

        // Save the state of the animation
        val isPlaying = mShimmerViewContainer!!.isAnimationStarted

        // Reset all parameters of the shimmer animation
        mShimmerViewContainer!!.useDefaults()

        // If a toast is already showing, hide it
        if (mPresetToast != null) {
            mPresetToast!!.cancel()
        }

        when (preset) {
            1 -> {
                // Slow and reverse
                mShimmerViewContainer!!.duration = 5000
                mShimmerViewContainer!!.repeatMode = ObjectAnimator.REVERSE
                mPresetToast = Toast.makeText(this, "Slow and reverse", Toast.LENGTH_SHORT)
            }
            2 -> {
                // Thin, straight and transparent
                mShimmerViewContainer!!.baseAlpha = 0.1f
                mShimmerViewContainer!!.dropoff = 0.1f
                mShimmerViewContainer!!.tilt = 0f
                mPresetToast = Toast.makeText(this, "Thin, straight and transparent", Toast.LENGTH_SHORT)
            }
            3 -> {
                // Sweep angle 90
                mShimmerViewContainer!!.angle = MaskAngle.CW_90
                mPresetToast = Toast.makeText(this, "Sweep angle 90", Toast.LENGTH_SHORT)
            }
            4 -> {
                // Spotlight
                mShimmerViewContainer!!.baseAlpha = 0f
                mShimmerViewContainer!!.duration = 2000
                mShimmerViewContainer!!.dropoff = 0.1f
                mShimmerViewContainer!!.intensity = 0.35f
                mShimmerViewContainer!!.maskShape = MaskShape.RADIAL
                mPresetToast = Toast.makeText(this, "Spotlight", Toast.LENGTH_SHORT)
            }
            else/*, 0*/ ->
                // Default
                mPresetToast = Toast.makeText(this, "Default", Toast.LENGTH_SHORT)
        }

        // Show toast describing the chosen preset, if necessary
        if (showToast) {
            mPresetToast!!.show()
        }

        // Setting a value on the shimmer layout stops the animation. Restart it, if necessary.
        if (isPlaying) {
            mShimmerViewContainer!!.startShimmerAnimation()
        }
    }
}
