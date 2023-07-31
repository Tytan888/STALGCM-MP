import Constants.APP_NAME
import java.awt.*
import java.util.*
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer


class View() {
    private var mainFrame: JFrame = JFrame(APP_NAME)
    private var gbc = GridBagConstraints()
    private var mainPanel: JPanel = JPanel()

    private var stackPanel: JPanel = JPanel()
    private var stackTable: JTable = JTable(Constants.STACK_HEIGHT, 2)
    private var stackLabel: JLabel = JLabel("Stack")
    private var stack: LinkedList<String> = LinkedList<String>()

    private var rightPanel: JPanel = JPanel()


    init {
        initJFrame()
        initStackPanel()
        initRightPanel()

        mainFrame.add(mainPanel)
        mainFrame.isVisible = true
    }

    private fun initJFrame() {
        mainFrame.setSize(Constants.APP_WIDTH, Constants.APP_HEIGHT)
        mainFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        mainFrame.setLocationRelativeTo(null)
        mainFrame.isResizable = false
        mainPanel.setLayout(GridBagLayout())
    }

    private fun initStackPanel() {
        stackPanel.setLayout(FlowLayout(FlowLayout.CENTER))
        stackTable.setDefaultEditor(Any::class.java, null)

        stackTable.columnModel.getColumn(0).preferredWidth = 50
        stackTable.columnModel.getColumn(1).preferredWidth = 140

        stackTable.border = BorderFactory.createLineBorder(Color.BLACK, 2)

        val centerRenderer = DefaultTableCellRenderer()
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER)
        stackTable.columnModel.getColumn(0).setCellRenderer(centerRenderer)
        stackTable.columnModel.getColumn(1).setCellRenderer(centerRenderer)

        stackPanel.add(stackTable)

        stackLabel.font = Font("Century Gothic", Font.BOLD, 25)
        stackPanel.add(stackLabel)
        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        mainPanel.add(stackPanel, gbc)
    }

    private fun renderStack() {
        val stackSize = stack.size
        if (stackSize <= Constants.STACK_DISPLAY) {
            stackTable.model.setValueAt("<html><b>Index</b></html>", 0, 0)
            stackTable.model.setValueAt("<html><b>Value</b></html>", 0, 1)
            for (i in 0 until Constants.STACK_HEIGHT - 1) {
                stackTable.model.setValueAt(i.toString(), Constants.STACK_HEIGHT - i - 1, 0)
            }
            for (i in 0 until stackSize) {
                stackTable.model.setValueAt(stack[i].toString(), Constants.STACK_HEIGHT - i - 1, 1)
            }
        } else {
            val offset = stackSize - Constants.STACK_DISPLAY
            for (i in 0 until Constants.STACK_HEIGHT - 1) {
                stackTable.model.setValueAt((i + offset).toString(), Constants.STACK_HEIGHT - i - 1, 0)
            }
            for (i in 0 until Constants.STACK_DISPLAY) {
                stackTable.model.setValueAt(stack[i + offset].toString(), Constants.STACK_HEIGHT - i - 1, 1)
            }
        }
    }
    fun addStackItem(item: String) {
        stack.add(item)
        renderStack()
    }

    fun removeStackItem() {
        stack.removeLast()
        renderStack()
    }

    private fun initRightPanel(){
        rightPanel.setBackground(Color.GREEN)
        rightPanel.preferredSize = Dimension(Constants.APP_WIDTH / 4, Constants.APP_HEIGHT)
        rightPanel.setLayout(GridBagLayout())
        gbc.gridx = 1
        gbc.gridy = 0
        gbc.weightx = 5.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        mainPanel.add(rightPanel, gbc)
    }
}