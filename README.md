# BookStatistic-Java
Репозиторий для приложения, позволяющего отслеживать статистику чтения.

## Разработчики
- [Jb192101](https://github.com/Jb192101).

## Технологии и библиотеки:
- Java 21;
- Spring Boot;
- Hibernate;
- JavaFX;
- GSON;
- Apache Commons Text.

Архитектура: MVVM.

## Важное уточнение:
Программа работает на локальной базе данных (см. [hibernate.cfg.xml](https://github.com/Jb192101/BookStatistic-Java/blob/main/src/main/resources/hibernate.cfg.xml)), поэтому если хотите работать с приложением, воспользуйтесь СУБД PostgreSQL.

## Добавление книги
Для добавления книги в список необходимо нажать на кнопку ниже.
<img width="1259" height="797" alt="image" src="https://github.com/user-attachments/assets/428f556c-10cd-4f6e-b935-4a19cba5f335" />

После этого на экране появится специальное окно-форма для заполнения необходимых данных по книге.
<img width="398" height="448" alt="Снимок экрана 2025-08-13 121242" src="https://github.com/user-attachments/assets/e946f30a-805c-4a68-b677-14eabb641f72" />

## Изменение значений книги
Для изменений данных в книге необходимо нажать на кнопку ниже.
<img width="1259" height="797" alt="image" src="https://github.com/user-attachments/assets/1fa737fb-b4ab-48b5-ac42-578800cd0f88" />

После этого откроется окно-форма с полем для ввода индекса книги
<img width="395" height="180" alt="Снимок экрана 2025-08-13 122625" src="https://github.com/user-attachments/assets/b412fd88-da4c-466c-8a8a-a98287753e22" />

После ввода индекса (если соответствующий индекс есть и при этом в поле нет других символов, кроме чисел), появится окно с
данными книги, которой сооветствует этот индекс.
<img width="384" height="386" alt="Снимок экрана 2025-08-13 122652" src="https://github.com/user-attachments/assets/3e406649-190c-4e48-abe1-8df6f6131b5e" />

## Просмотр статистики чтения в общем/статистики чтения в месяц/количества оценок
Эту информацию можно найти по соответствующим трём кнопкам в нижней части панели:
<img width="1259" height="797" alt="image" src="https://github.com/user-attachments/assets/59f67adb-bae6-41df-9acf-f8453a8f4d28" />

Пример окна с кол-вом оценок:
<img width="1018" height="804" alt="image" src="https://github.com/user-attachments/assets/01449ef4-73ba-4f93-b6fb-7d3ede08044a" />

## Настройки
В настройках можно исправить тему окон со светлой на тёмную и наоборот.

## Поиск книги по названию/автору
Найти книгу с соответствующим названием или автором можно с помощью поля в левом верхнем углу.
<img width="1259" height="797" alt="image" src="https://github.com/user-attachments/assets/c7561bd8-908d-4b42-9bb4-0262f1e841a0" />

Для поиска используется метод расстояний Левенштейна. После ввода слов список будет сокращён до тех значений, которые соответствуют написаному в поле.
<img width="630" height="122" alt="image" src="https://github.com/user-attachments/assets/bdba4247-63f3-470e-8574-a42f60469661" />
