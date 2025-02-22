package abi45_0_0.host.exp.exponent.modules.api.components.webview.events

import abi45_0_0.com.facebook.react.bridge.WritableMap
import abi45_0_0.com.facebook.react.uimanager.events.Event
import abi45_0_0.com.facebook.react.uimanager.events.RCTEventEmitter

/**
 * Event emitted when loading is completed.
 */
class TopLoadingFinishEvent(viewId: Int, private val mEventData: WritableMap) :
  Event<TopLoadingFinishEvent>(viewId) {
  companion object {
    const val EVENT_NAME = "topLoadingFinish"
  }

  override fun getEventName(): String = EVENT_NAME

  override fun canCoalesce(): Boolean = false

  override fun getCoalescingKey(): Short = 0

  override fun dispatch(rctEventEmitter: RCTEventEmitter) =
    rctEventEmitter.receiveEvent(viewTag, eventName, mEventData)
}
