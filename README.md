# Приложение для формирования треугольной сетки разбиения для заданной двумерной области (2023)

Запустим приложение и посмотрим работает ли оно вообще.
 
 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/19c0a68b-cb90-48a3-84e9-d533eb85053b)

Рисунок 1 – Запуск приложения

Как видно из рисунка, приложение запускается и показывает поля для выбора количества точек, на основе которых будет создана областьдля заполнения.  Попробуем создать таблицу с выбранным количеством строк и столбцов.
 
 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/91edf80c-caec-4d5b-af88-3b4c90f2f56a)

Рисунок 2 – создание таблицы заданной сложности

Таблица создается без проблем. Чтоб не заполнять все данные вручную, загрузим заранее заготовленный пример из файла.

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/c7b453ce-6310-4e2c-9880-140f793cd901)

Рисунок 3 – Чтение из файла

При нажатии на кнопку загрузить мы видим окно, в котором необходимо выбрать файл с данными для формирования треугольных сеток.

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/7a8212bb-d1ff-4ef3-8eb1-d64698ded167)

Рисунок 4 – Результат чтения из файла 

После выбора файла в приложении отображается все его данные и мы можем сгенерировать изоюращение с заполненными треугольными сетками.

![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/09c32e6c-8071-4cc2-af7d-33b141b92928)
 
Рисунок 5 – Результат выполнения

Красным цветом отображается область, которая была построена на основе всех введенных точек. Внутри этой области были сформированытреугольные сетки разбиения.
Данные из файла читаются в следующем порядке:
•	Шаг сетки
•	Количество точек
•	Координата Х точки
•	Координата Y точки

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/7f73f13c-d4e3-42ef-8b87-6592a6789e71)

Рисунок 6 – Спецификация загружаемого файла

После построение треугольной сетки, пользователь может сохранить изображение
 
 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/a9547ad3-1b19-4938-9d5e-d64bc0be64d1)

Рисунок 7 – Сохранение результата

После нажатия на кнопку сохранения нам предложат выбрать название файла и метсо его сохранения.

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/bd806abf-80b3-4905-a0e1-0eaf3f51c4df)

Рисунок 8  Вид сохраненного файла

После всех этих манипуляций файл сохраняется в том же виде, в каком он был отображен на экране.

На случай незаполнения всех полей предусмотрен выброс ошибок.

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/8ce4a185-6cbe-4048-abd6-20ce6cd722c3)

Рисунок 9 - Вызов ошибки при попытке формирования сетки без заполненных полей

Так же в приложении имеется проверка на правильность ввода.

 ![image](https://github.com/Evgescha/Course-work---application-for-forming-a-triangular-partition-grid-for-a-given-two-dimensional-area/assets/38140129/5e57e6a5-d397-4f16-85e8-a1fb437d85b5)

Рисунок 10 – Вызов ошибки при некорректном вводе



