import java.awt.Color
import java.awt.Component
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

class JTTable(size: Int, i1: Int) : JTable(size, i1) {
    var highlightedRow = -1
    var highlightedColumn = -1
    override fun prepareRenderer(renderer: TableCellRenderer?, row: Int, column: Int): Component {
        val component = super.prepareRenderer(renderer, row, column)
        if (row == highlightedRow && (column == highlightedColumn || column == 0)) {
            component.background = Color(0, 255, 0, 150)
        } else {
            component.background = Color.WHITE
        }
        return component
    }
}