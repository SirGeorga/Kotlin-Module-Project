
import java.util.*

class NoteApp {
    private val archives = mutableListOf<NoteArchive>()

    private fun <T> viewElements(
        list: List<T>,
        createNew: (() -> Unit)? = null,
        selectAction: (T) -> Unit,
        itemLabel: String
    ) {
        var exit = false
        while (!exit) {
            println("Список $itemLabel. Выберите действие:")
            for (i in list.indices) {
                println("$i. ${if(list[i] is NoteArchive)(list[i] as NoteArchive).name else (list[i] as NoteItem).name}")
            }
            if (createNew != null) {
                println("${list.size}. Создать")
            }
            println("${list.size + 1}. Выход")
            when (val input = readInt()) {
                in list.indices -> {
                    if(list[input] is NoteArchive) println("Выбран архив ${(list[input] as NoteArchive).name }")
                    selectAction(list[input])
                }
                list.size -> {
                    createNew?.invoke()
                }
                list.size + 1 -> exit = true
                else -> println("Выберите корректный номер пункта меню от 0 до ${list.size + 1}")
            }
        }
    }

    fun viewArchives() {
        viewElements(
            archives,
            ::createArchive,
            ::viewNotes,
            "архивов")
    }

    private fun viewNotes(archive: NoteArchive) {
        viewElements(
            archive.notes,
            { createNote(archive) },
            ::viewNoteDetails,
            "заметок"
        )
    }

    private fun createArchive() {
        print("Введите название архива: ")
        val name = readLine() ?: ""
        archives.add(NoteArchive(name))
    }

    private fun createNote(archive: NoteArchive) {
        print("Введите название заметки: ")
        val title = readLine() ?: ""
        print("Введите текст заметки: ")
        val content = readLine() ?: ""
        archive.notes.add(NoteItem(title, content))
    }

    private fun viewNoteDetails(note: NoteItem) {
        println("Заголовок: ${note.name}")
        println("Текст заметки: ${note.content}")
        println("Нажмите Enter для возврата")
        readLine()
    }

    private fun readInt(): Int {
        val scanner = Scanner(System.`in`)
        while (!scanner.hasNextInt()){
            println("Введите корректное целое число")
            scanner.nextLine()
        }
        return scanner.nextInt()
    }
}
