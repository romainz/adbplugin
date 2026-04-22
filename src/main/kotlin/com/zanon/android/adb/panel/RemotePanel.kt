package com.zanon.android.adb.panel

import com.intellij.icons.AllIcons
import com.intellij.ui.components.panels.VerticalLayout
import com.zanon.android.adb.android.KeyEvent
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSeparator
import javax.swing.SwingConstants

object RemotePanel {

    fun build(sendShellCommand: (String) -> Unit): JPanel {
        val panelNumbers = JPanel(GridBagLayout()).also { mainPanel ->
            // Numbers
            JButton("1").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_1)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("2").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_2)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("3").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_3)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            JButton("4").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_4)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("5").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_5)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("6").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_6)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            JButton("7").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_7)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("8").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_8)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("9").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_9)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            JButton("0").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_0)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
        val panelDirections = JPanel(GridBagLayout()).also { mainPanel ->
            // Up
            JButton(AllIcons.General.ArrowUp).apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_DPAD_UP)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Left
            JButton(AllIcons.General.ArrowLeft).apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_DPAD_LEFT)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Ok
            JButton("Ok").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_ENTER)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Right
            JButton(AllIcons.General.ArrowRight).apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_DPAD_RIGHT)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 2
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Down
            JButton(AllIcons.General.ArrowDown).apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_DPAD_DOWN)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Back
            JButton("Back").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_BACK)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
            // Home
            JButton("Home").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_HOME)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
        val panelPlayer = JPanel(GridBagLayout()).also { mainPanel ->
            // Play
            JButton("Play").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_MEDIA_PLAY)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Pause
            JButton("Pause").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_MEDIA_PAUSE)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 0
                }
                mainPanel.add(this, constraints)
            }
            // Rewind
            JButton("RW").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_MEDIA_REWIND)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Fast forward
            JButton("FF").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_MEDIA_FAST_FORWARD)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 1
                }
                mainPanel.add(this, constraints)
            }
            // Channel-
            JButton("CH-").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_CHANNEL_DOWN)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Channel+
            JButton("CH+").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_CHANNEL_UP)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 2
                }
                mainPanel.add(this, constraints)
            }
            // Guide TV
            JButton("GUIDE").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_GUIDE)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 0
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
            // Info
            JButton("INFO").apply {
                addActionListener { sendShellCommand(buildShellCommand(KeyEvent.KEYCODE_INFO)) }
                val constraints = GridBagConstraints().apply {
                    weightx = 0.0
                    gridx = 1
                    gridy = 3
                }
                mainPanel.add(this, constraints)
            }
        }
        return JPanel(VerticalLayout(5)).apply {
            add(panelDirections)
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(panelNumbers)
            add(JSeparator(SwingConstants.HORIZONTAL))
            add(panelPlayer)
        }
    }

    private fun buildShellCommand(keyCode: Int): String = "input keyevent $keyCode"


}