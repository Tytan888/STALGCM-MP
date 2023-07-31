
import java.awt.Color
import java.awt.Component

import javax.swing.JTable

import javax.swing.table.DefaultTableCellRenderer


internal class HeaderRenderer(private var highlighted: Boolean) : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
    ): Component {
        val c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 3, 3)
        if (highlighted)
            c.setBackground(Color(0, 255, 0, 150))
        else
            c.setBackground(Color.WHITE)
        return c
    }
}