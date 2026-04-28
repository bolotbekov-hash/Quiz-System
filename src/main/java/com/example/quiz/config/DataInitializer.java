package com.example.quiz.config;

import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.User;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuizRepository quizRepository;

    private Question q(Quiz quiz, String text, List<String> options, String correct) {
        Question q = new Question();
        q.setText(text);
        q.setOptions(options);
        q.setCorrectAnswer(correct);
        q.setQuiz(quiz);
        return q;
    }

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_USER", "ROLE_ADMIN"));
            userRepository.save(admin);
            System.out.println(">>> Admin created: admin / admin123");
        }

        if (userRepository.findByUsername("manager").isEmpty()) {
            User manager = new User();
            manager.setUsername("manager");
            manager.setPassword(passwordEncoder.encode("manager1234"));
            manager.setRoles(Set.of("ROLE_USER", "ROLE_MANAGER"));
            userRepository.save(manager);
        }

        if (quizRepository.count() == 0) {

            // ─── Квиз 1: Основы Java ───────────────────────────────────────
            Quiz javaQuiz = new Quiz();
            javaQuiz.setTitle("Основы Java");
            javaQuiz.setDescription("15 вопросов по базовому синтаксису и концепциям Java");
            javaQuiz.setCategory("Программирование");
            javaQuiz.setQuestions(Arrays.asList(
                q(javaQuiz, "Какое ключевое слово используется для наследования в Java?",
                    Arrays.asList("extends", "implements", "inherits", "super"), "extends"),
                q(javaQuiz, "Что выведет System.out.println(10 / 3)?",
                    Arrays.asList("3", "3.33", "3.0", "Ошибка компиляции"), "3"),
                q(javaQuiz, "Какой модификатор доступа делает поле доступным только внутри класса?",
                    Arrays.asList("private", "protected", "public", "default"), "private"),
                q(javaQuiz, "Что такое JVM?",
                    Arrays.asList("Виртуальная машина Java", "Компилятор Java", "Менеджер пакетов", "Отладчик"), "Виртуальная машина Java"),
                q(javaQuiz, "Какой тип данных хранит одиночный символ в Java?",
                    Arrays.asList("char", "String", "byte", "short"), "char"),
                q(javaQuiz, "Как объявить константу в Java?",
                    Arrays.asList("final", "const", "static", "readonly"), "final"),
                q(javaQuiz, "Какой интерфейс нужно реализовать для создания потока в Java?",
                    Arrays.asList("Runnable", "Threadable", "Executable", "Parallel"), "Runnable"),
                q(javaQuiz, "Что делает ключевое слово 'static'?",
                    Arrays.asList("Принадлежит классу, а не объекту", "Запрещает изменение", "Делает поле публичным", "Создаёт singleton"), "Принадлежит классу, а не объекту"),
                q(javaQuiz, "Какова длина типа long в Java?",
                    Arrays.asList("64 бита", "32 бита", "16 бит", "128 бит"), "64 бита"),
                q(javaQuiz, "Как называется конструктор без параметров?",
                    Arrays.asList("Конструктор по умолчанию", "Пустой конструктор", "Базовый конструктор", "Нулевой конструктор"), "Конструктор по умолчанию"),
                q(javaQuiz, "Какое исключение выбрасывается при делении на ноль целых чисел?",
                    Arrays.asList("ArithmeticException", "NullPointerException", "NumberFormatException", "IllegalArgumentException"), "ArithmeticException"),
                q(javaQuiz, "Что возвращает метод toString() по умолчанию?",
                    Arrays.asList("Имя класса и хэш-код", "null", "Пустую строку", "Имя объекта"), "Имя класса и хэш-код"),
                q(javaQuiz, "Чем отличается ArrayList от LinkedList?",
                    Arrays.asList("ArrayList быстрее при доступе по индексу", "LinkedList быстрее при доступе по индексу", "Они идентичны", "ArrayList не поддерживает null"), "ArrayList быстрее при доступе по индексу"),
                q(javaQuiz, "Что такое autoboxing в Java?",
                    Arrays.asList("Автоматическое преобразование примитива в обёртку", "Автоматическое приведение типов", "Копирование объектов", "Упаковка методов"), "Автоматическое преобразование примитива в обёртку"),
                q(javaQuiz, "Какой оператор используется для проверки типа объекта?",
                    Arrays.asList("instanceof", "typeof", "is", "checktype"), "instanceof")
            ));
            quizRepository.save(javaQuiz);

            // ─── Квиз 2: Общие знания ──────────────────────────────────────
            Quiz generalQuiz = new Quiz();
            generalQuiz.setTitle("Общие знания");
            generalQuiz.setDescription("15 вопросов на общую эрудицию");
            generalQuiz.setCategory("Общее");
            generalQuiz.setQuestions(Arrays.asList(
                q(generalQuiz, "Сколько планет в Солнечной системе?",
                    Arrays.asList("8", "9", "7", "10"), "8"),
                q(generalQuiz, "Какая страна является самой большой по площади?",
                    Arrays.asList("Россия", "Китай", "США", "Канада"), "Россия"),
                q(generalQuiz, "В каком году была основана компания Apple?",
                    Arrays.asList("1976", "1980", "1984", "1991"), "1976"),
                q(generalQuiz, "Какой элемент имеет химический символ Au?",
                    Arrays.asList("Золото", "Серебро", "Алюминий", "Медь"), "Золото"),
                q(generalQuiz, "Сколько сторон у правильного шестиугольника?",
                    Arrays.asList("6", "5", "7", "8"), "6"),
                q(generalQuiz, "Какая планета является самой большой в Солнечной системе?",
                    Arrays.asList("Юпитер", "Сатурн", "Уран", "Нептун"), "Юпитер"),
                q(generalQuiz, "Кто написал роман «Война и мир»?",
                    Arrays.asList("Лев Толстой", "Фёдор Достоевский", "Антон Чехов", "Иван Тургенев"), "Лев Толстой"),
                q(generalQuiz, "Какой газ составляет большую часть атмосферы Земли?",
                    Arrays.asList("Азот", "Кислород", "Углекислый газ", "Аргон"), "Азот"),
                q(generalQuiz, "В каком году закончилась Вторая мировая война?",
                    Arrays.asList("1945", "1944", "1946", "1943"), "1945"),
                q(generalQuiz, "Сколько нот в музыкальной октаве?",
                    Arrays.asList("7", "8", "12", "5"), "7"),
                q(generalQuiz, "Столица Австралии?",
                    Arrays.asList("Канберра", "Сидней", "Мельбурн", "Брисбен"), "Канберра"),
                q(generalQuiz, "Какой орган тела вырабатывает инсулин?",
                    Arrays.asList("Поджелудочная железа", "Печень", "Почки", "Надпочечники"), "Поджелудочная железа"),
                q(generalQuiz, "Скорость света в вакууме приблизительно равна?",
                    Arrays.asList("300 000 км/с", "150 000 км/с", "500 000 км/с", "1 000 000 км/с"), "300 000 км/с"),
                q(generalQuiz, "Какой материк является самым маленьким?",
                    Arrays.asList("Австралия", "Антарктида", "Европа", "Южная Америка"), "Австралия"),
                q(generalQuiz, "Кто изобрёл телефон?",
                    Arrays.asList("Александр Белл", "Томас Эдисон", "Никола Тесла", "Гульельмо Маркони"), "Александр Белл")
            ));
            quizRepository.save(generalQuiz);

            // ─── Квиз 3: История России ────────────────────────────────────
            Quiz historyQuiz = new Quiz();
            historyQuiz.setTitle("История России");
            historyQuiz.setDescription("15 вопросов по истории России от древности до наших дней");
            historyQuiz.setCategory("История");
            historyQuiz.setQuestions(Arrays.asList(
                q(historyQuiz, "В каком году было основано Московское княжество?",
                    Arrays.asList("1147", "1237", "1380", "1480"), "1147"),
                q(historyQuiz, "Кто был первым царём России?",
                    Arrays.asList("Иван IV (Грозный)", "Пётр I", "Борис Годунов", "Василий III"), "Иван IV (Грозный)"),
                q(historyQuiz, "В каком году Пётр I основал Санкт-Петербург?",
                    Arrays.asList("1703", "1712", "1698", "1721"), "1703"),
                q(historyQuiz, "Как называлась битва 1380 года, в которой Дмитрий Донской разбил монголов?",
                    Arrays.asList("Куликовская", "Ледовое побоище", "Бородинская", "Полтавская"), "Куликовская"),
                q(historyQuiz, "В каком году отменили крепостное право в России?",
                    Arrays.asList("1861", "1812", "1905", "1881"), "1861"),
                q(historyQuiz, "Кто возглавлял Россию во время Отечественной войны 1812 года?",
                    Arrays.asList("Александр I", "Николай I", "Павел I", "Александр II"), "Александр I"),
                q(historyQuiz, "В каком году произошла Октябрьская революция?",
                    Arrays.asList("1917", "1905", "1914", "1918"), "1917"),
                q(historyQuiz, "Кто был первым президентом России?",
                    Arrays.asList("Борис Ельцин", "Михаил Горбачёв", "Владимир Путин", "Виктор Черномырдин"), "Борис Ельцин"),
                q(historyQuiz, "В каком году СССР распался?",
                    Arrays.asList("1991", "1989", "1993", "1990"), "1991"),
                q(historyQuiz, "Как назывался главный орган власти в СССР?",
                    Arrays.asList("Верховный Совет", "Государственная Дума", "Сенат", "Народный Конгресс"), "Верховный Совет"),
                q(historyQuiz, "В каком году Россия выиграла Северную войну со Швецией?",
                    Arrays.asList("1721", "1709", "1700", "1725"), "1721"),
                q(historyQuiz, "Кто командовал русскими войсками в Бородинском сражении?",
                    Arrays.asList("Кутузов", "Суворов", "Багратион", "Барклай-де-Толли"), "Кутузов"),
                q(historyQuiz, "В каком году Россия запустила первый искусственный спутник Земли?",
                    Arrays.asList("1957", "1961", "1955", "1959"), "1957"),
                q(historyQuiz, "Как звали первого космонавта Земли?",
                    Arrays.asList("Юрий Гагарин", "Герман Титов", "Алексей Леонов", "Валентина Терешкова"), "Юрий Гагарин"),
                q(historyQuiz, "В каком году была принята действующая Конституция России?",
                    Arrays.asList("1993", "1991", "1995", "1990"), "1993")
            ));
            quizRepository.save(historyQuiz);

            System.out.println(">>> Demo quizzes created: 'Основы Java', 'Общие знания', 'История России' — по 15 вопросов каждый.");
        }
    }
}
