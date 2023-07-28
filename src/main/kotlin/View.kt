import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class View {
    init {
        var frame = JFrame("Hello, World!")
        frame.setSize(300, 300)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
        var panel = JPanel()
        var label = JLabel("Hello, World!")
        panel.add(label)
        frame.add(panel)
    }
}