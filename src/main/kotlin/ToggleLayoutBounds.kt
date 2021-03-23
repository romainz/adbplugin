import com.android.ddmlib.IDevice
import com.android.ddmlib.MultiLineReceiver
import com.android.ddmlib.NullOutputReceiver
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import org.jetbrains.android.sdk.AndroidSdkUtils

class ToggleLayoutBounds : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project

        val devices = project?.let { AndroidSdkUtils.getDebugBridge(it)?.devices }
        when {
            devices.isNullOrEmpty() -> project.showNotification("There is no device connected")
            devices.size > 1 -> project.showNotification("There is more than one device connected")
            else -> {
                devices.first().toggleLayoutBounds()
            }
        }
    }

    private fun IDevice.toggleLayoutBounds() {
        this.executeShellCommand(
            "getprop debug.layout",
            SingleLineReceiver { firstLine ->
                val enable = firstLine.toBoolean().not()
                enableLayoutBounds(enable)
            }
        )
    }

    private fun IDevice.enableLayoutBounds(enable: Boolean) {
        this.executeShellCommand(
            "setprop debug.layout $enable ; service call activity 1599295570",
            NullOutputReceiver()
        )
    }

    private fun Project?.showNotification(message: String) {
        NotificationGroup("canal", NotificationDisplayType.BALLOON)
            .createNotification(
                "ADB+ Plugin",
                message,
                NotificationType.WARNING,
                null
            ).notify(this)
    }

    private class SingleLineReceiver(private val action: (String) -> Unit) : MultiLineReceiver() {

        private var cancelled = false

        override fun processNewLines(lines: Array<out String>?) {
            lines?.getOrNull(0)?.let { line ->
                action(line)
                cancelled = true
            }
        }

        override fun isCancelled(): Boolean = cancelled
    }

}

