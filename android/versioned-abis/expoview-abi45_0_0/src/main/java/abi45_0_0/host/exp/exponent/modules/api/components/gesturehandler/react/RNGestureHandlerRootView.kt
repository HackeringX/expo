package abi45_0_0.host.exp.exponent.modules.api.components.gesturehandler.react

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import abi45_0_0.com.facebook.react.bridge.ReactContext
import abi45_0_0.com.facebook.react.bridge.UiThreadUtil
import abi45_0_0.com.facebook.react.common.ReactConstants
import abi45_0_0.com.facebook.react.uimanager.RootView
import abi45_0_0.com.facebook.react.views.view.ReactViewGroup

class RNGestureHandlerRootView(context: Context?) : ReactViewGroup(context) {
  private var _enabled = false
  private var rootHelper: RNGestureHandlerRootHelper? = null // TODO: resettable lateinit
  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    _enabled = !hasGestureHandlerEnabledRootView(this)
    if (!_enabled) {
      Log.i(
        ReactConstants.TAG,
        "[GESTURE HANDLER] Gesture handler is already enabled for a parent view"
      )
    }
    if (_enabled && rootHelper == null) {
      rootHelper = RNGestureHandlerRootHelper(context as ReactContext, this)
    }
  }

  fun tearDown() {
    rootHelper?.tearDown()
  }

  override fun dispatchTouchEvent(ev: MotionEvent) =
    if (_enabled && rootHelper!!.dispatchTouchEvent(ev)) {
      true
    } else super.dispatchTouchEvent(ev)

  override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    if (_enabled) {
      rootHelper!!.requestDisallowInterceptTouchEvent(disallowIntercept)
    }
    super.requestDisallowInterceptTouchEvent(disallowIntercept)
  }

  companion object {
    private fun hasGestureHandlerEnabledRootView(viewGroup: ViewGroup): Boolean {
      UiThreadUtil.assertOnUiThread()

      var parent = viewGroup.parent
      while (parent != null) {
        if (parent is RNGestureHandlerEnabledRootView || parent is RNGestureHandlerRootView) {
          return true
        }
        // Checks other roots views but it's mainly for ReactModalHostView.DialogRootViewGroup
        // since modals are outside RN hierachy and we have to initialize GH's root view for it
        // Note that RNGestureHandlerEnabledRootView implements RootView - that's why this check has to be below
        if (parent is RootView) {
          return false
        }
        parent = parent.parent
      }
      return false
    }
  }
}
