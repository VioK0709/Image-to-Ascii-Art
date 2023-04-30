# Конвертер в текстовую графику

## Реализация требований к конвертеру

- Класс сервера будет использовать наш конвертер. 
- Все требования к конвертеру описаны в интерфейсе TextGraphicsConverter.
- Самый главный метод — это метод convert, который принимает параметром URL в виде текста.
- Внутри метода: качает и анализирует картинку, после чего отдаёт значение типа String, в котором содержится это изображение в виде текстовой графики.

Также конвертер имеет возможность выставлять настройки перед конвертацией без указания конкретных значений:
- максимально допустимое соотношение сторон (ширины и высоты);
- максимально допустимую высоту итогового изображения;
- максимально допустимую ширину итогового изображения;
- текстовую цветовую схему — объект специального интерфейса ColorSchema, который отвечает за превращение степени белого (числа от 0 до 255) в символ. 

Если в сервере поменяют настройки конвертеру, конвертер без изменений кода будет работать с новыми значениями.

Общая схема работы метода convert будет соответствовать последовательности действий:
- Скачиваем картинку по URL.
- Могли выставить максимально допустимое соотношение сторон (ширины и высоты); если оно слишком большое, то конвертация не делается и выбрасывается исключение.
- При конвертации меняем каждый пиксель на символ: чем пиксель темнее, тем жирнее символ. Могли выставить максимальные ширину и высоту итоговой картинки, при этом если исходная картинка больше, то она уменьшиается, соблюдая пропорции.
- Превращаем цветное изображение в чёрно-белое, глядя только на интенсивность цвета.
- Перебираем все пиксели изображения, спрашивая у них степень белого (число от 0 до 255, где 0 — это чёрный, а 255 — это светлый). В зависимости от этого числа выбирается символ из заранее подготовленного набора.
- Собираем все полученные символы в единую строку, отдаём как результат конвертации.
