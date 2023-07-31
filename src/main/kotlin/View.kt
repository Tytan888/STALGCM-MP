import Constants.APP_NAME
import java.awt.*
import java.awt.event.ActionListener
import java.util.*
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer


class View(
    private var Q: List<String>,
    private var Delta: LinkedList<Transition>,
    private var qI: String,
    private var F: List<String>,
    private var uniqueInputs: LinkedList<String>
) {
    private var mainFrame: JFrame = JFrame(APP_NAME)
    private var gbc = GridBagConstraints()
    private var mainPanel: JPanel = JPanel()

    private var stackPanel: JPanel = JPanel()
    private var stackTable: JTable = JTable(Constants.STACK_HEIGHT, 2)
    private var stackLabel: JLabel = JLabel("Stack")
    private var stack: LinkedList<String> = LinkedList<String>()

    private var rightPanel: JPanel = JPanel()

    private var inputPanel: JPanel = JPanel()
    private var inputLabel: JLabel = JLabel("Input String:")
    private var inputField: JTextField = JTextField("ababa") //TODO: REMOVE THIS
    private var inputLabelAfter: JLabel = JLabel()

    private var descPanel: JPanel = JPanel()
    private var descLabel: JLabel = JLabel("Machine is ready to run...")

    private lateinit var tablePanel: JScrollPane
    private lateinit var tableTable: JTTable

    private var controlPanel: JPanel = JPanel()
    private var controlLeftButton: JButton = JButton("Step Run")
    private var controlRightButton: JButton = JButton("Timer Run")

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
            for (i in 0..<Constants.STACK_HEIGHT - 1) {
                stackTable.model.setValueAt(i.toString(), Constants.STACK_HEIGHT - i - 1, 0)
            }
            for (i in 0..<stackSize) {
                stackTable.model.setValueAt(stack[i], Constants.STACK_HEIGHT - i - 1, 1)
            }
        } else {
            val offset = stackSize - Constants.STACK_DISPLAY
            for (i in 0..<Constants.STACK_HEIGHT - 1) {
                stackTable.model.setValueAt((i + offset).toString(), Constants.STACK_HEIGHT - i - 1, 0)
            }
            for (i in 0..<Constants.STACK_DISPLAY) {
                stackTable.model.setValueAt(stack[i + offset], Constants.STACK_HEIGHT - i - 1, 1)
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

    private fun initRightPanel() {
        rightPanel.preferredSize = Dimension(Constants.APP_WIDTH / 4, Constants.APP_HEIGHT)
        rightPanel.setLayout(GridBagLayout())

        initInputPanel()
        initDescPanel()
        initTablePanel()
        initControlPanel()

        gbc.gridx = 1
        gbc.gridy = 0
        gbc.weightx = 3.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        mainPanel.add(rightPanel, gbc)
    }

    private fun initInputPanel() {
        inputPanel.setLayout(GridBagLayout())
        inputLabel.font = Font("Century Gothic", Font.BOLD, 20)

        val inputgbc = GridBagConstraints()
        inputgbc.insets = Insets(15, 5, 5, 5)

        inputPanel.add(inputLabel, inputgbc)
        inputField.preferredSize = Dimension(150, 25)
        inputPanel.add(inputField, inputgbc)
        inputLabelAfter.font = Font("Century Gothic", Font.BOLD or Font.ITALIC, 20)
        inputLabelAfter.isVisible = false
        inputLabelAfter.text = "ababbba"
        inputPanel.add(inputLabelAfter, inputgbc)
        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        rightPanel.add(inputPanel, gbc)

    }

    fun getInputFieldText(): String {
        return inputField.text
    }
    fun turnInputFieldtoLabel() {
        inputField.isVisible = false
        inputLabelAfter.text = inputField.text
        inputLabelAfter.isVisible = true
    }

    fun highlightInputLabel(index: Int){
        val before = inputLabelAfter.text.substring(0, index + 1)
        val after = inputLabelAfter.text.substring(index + 1)
        inputLabelAfter.text = "<html><font color = 'red'>$before</font>$after</html>"
    }

    private fun initDescPanel() {
        descPanel.setLayout(GridBagLayout())
        descLabel.font = Font("Century Gothic", Font.BOLD, 13)
        descPanel.add(descLabel)
        gbc.gridx = 0
        gbc.gridy = 1
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        rightPanel.add(descPanel, gbc)

        updateDesc(0)
    }

    fun resetDesc() {
        descLabel.text = "Machine is ready to run..."
    }

    fun updateDesc(index: Int) {
        val from = Delta[index].getFrom()
        val read = Delta[index].getRead()
        val pop = Delta[index].getPop()
        val to = Delta[index].getTo()
        val push = Delta[index].getPush()

        descLabel.text =
            "<html>Being in State <i>$from</i>, reading a <i>$read</i>, and popping a <i>$pop</i>,<br></br>the machine goes to State <i>$to</i> and pushes a <i>$push</i>.</html>"
    }

    private fun initTablePanel() {
        tableTable = JTTable(Q.size, uniqueInputs.size + 1)
        tableTable.setDefaultEditor(Any::class.java, null)
        tableTable.tableHeader.resizingAllowed = false
        tableTable.tableHeader.reorderingAllowed = false

        val centerRenderer = DefaultTableCellRenderer()
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER)

        tableTable.columnModel.getColumn(0).preferredWidth = 30
        tableTable.columnModel.getColumn(0).headerValue = ""
        tableTable.columnModel.getColumn(0).setCellRenderer(centerRenderer)

        initTableVals(centerRenderer)

        tableTable.border = BorderFactory.createLineBorder(Color.BLACK, 2)
        tableTable.autoResizeMode = JTable.AUTO_RESIZE_OFF

        tablePanel = JScrollPane(
            tableTable,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        gbc.gridx = 0
        gbc.gridy = 2
        gbc.weightx = 1.0
        gbc.weighty = 5.0
        gbc.fill = GridBagConstraints.BOTH
        rightPanel.add(tablePanel, gbc)
    }

    private fun initTableVals(centerRenderer: DefaultTableCellRenderer) {
        for (i in 1..uniqueInputs.size) {
            tableTable.columnModel.getColumn(i).preferredWidth = 50
            tableTable.columnModel.getColumn(i).headerValue = uniqueInputs[i - 1]
            tableTable.columnModel.getColumn(i).setCellRenderer(centerRenderer)
        }

        for (i in 1..Q.size) {
            tableTable.setValueAt(Q[i - 1], i - 1, 0)
        }

        Delta.forEach {
            val from = it.getFrom()
            val input = it.formatInput()
            var row = -1
            for (i in 0..Q.size) {
                if (Q[i] == from) {
                    row = i
                    break
                }
            }
            var col = -1
            for (i in 0..uniqueInputs.size) {
                if (uniqueInputs[i] == input) {
                    col = i
                    break
                }
            }

            tableTable.setValueAt(it.formatOutput(), row, col + 1)
        }
    }

    fun setHighlightedTransition(index: Int) {
        for (i in 0..uniqueInputs.size) {
            val tc = tableTable.getTableHeader().columnModel.getColumn(i)
            val renderer = HeaderRenderer(false)
            renderer.horizontalAlignment = SwingConstants.CENTER
            tc.setHeaderRenderer(renderer)
        }
        if (index == -1) {
            tableTable.highlightedRow = -1
            tableTable.highlightedColumn = -1
            return
        } else {
            println(Delta[index])
            val from = Delta[index].getFrom()
            val input = Delta[index].formatInput()
            var row = -1
            for (i in 0..Q.size) {
                if (Q[i] == from) {
                    row = i
                    break
                }
            }
            var col = -1
            for (i in 0..uniqueInputs.size) {
                if (uniqueInputs[i] == input) {
                    col = i
                    break
                }
            }
            tableTable.highlightedRow = row
            tableTable.highlightedColumn = col + 1

            val tc = tableTable.getTableHeader().columnModel.getColumn(col + 1)
            val renderer = HeaderRenderer(true)
            renderer.horizontalAlignment = SwingConstants.CENTER
            tc.setHeaderRenderer(renderer)
        }
    }

    private fun initControlPanel() {
        controlPanel.setLayout(GridBagLayout())
        val controlgbc = GridBagConstraints()
        controlgbc.insets = Insets(5, 20, 5, 20)
        controlLeftButton.isFocusPainted = false
        controlRightButton.isFocusPainted = false
        controlPanel.add(controlLeftButton, controlgbc)
        controlPanel.add(controlRightButton, controlgbc)
        gbc.gridx = 0
        gbc.gridy = 3
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.fill = GridBagConstraints.BOTH
        rightPanel.add(controlPanel, gbc)
    }

// TODO: Note: The plan is to make the left and right buttons be initially "Step Run" and "Timed Run" respectively.
// "Timed Run" will run the machine automatically, and "Step Run" will run the machine one step at a time.
// When the machine is running on "Step Run", the buttons will change to "Step" and "Reset" respectively.
// "Step" will run the machine one step at a time, and "Reset" will reset the machine to its initial state.
// When the machine is running on "Timed Run", the buttons will be disabled until the machine finishes running.
// When the machine finishes running, the left button will still be disabled while the right button will change to "Reset".
// "Reset" will reset everything, including these buttons, to their initial states.
    fun setLeftButtonActionListener(listener: ActionListener) {
        controlLeftButton.addActionListener(listener)
    }

    fun setRightButtonActionListener(listener: ActionListener) {
        controlRightButton.addActionListener(listener)
    }
}