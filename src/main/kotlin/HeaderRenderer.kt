
import java.awt.Color
import java.awt.Component

import javax.swing.JTable

import javax.swing.table.DefaultTableCellRenderer


internal class HeaderRenderer(private var status: Int) : DefaultTableCellRenderer() {
    override fun getTableCellRendererComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 3, 3)
        when (status) {
            1 -> c.setBackground(Color(0, 255, 0, 150))
            2 -> c.setBackground(Color(0, 0, 255, 100))
            3 -> c.setBackground(Color(255, 0, 0, 150))
            else -> c.setBackground(Color.WHITE)
        }
        return c
    }
}